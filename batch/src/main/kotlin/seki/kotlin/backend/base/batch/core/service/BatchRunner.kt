package seki.kotlin.backend.base.batch.core.service

import seki.kotlin.backend.base.batch.core.BatchContext
import seki.kotlin.backend.base.common.db.mapper.deleteByPrimaryKey
import seki.kotlin.backend.base.common.db.mapper.insertSelective
import seki.kotlin.backend.base.common.db.mapper.selectByPrimaryKey
import seki.kotlin.backend.base.common.db.model.BatchStartControl
import seki.kotlin.backend.base.common.log.MsgLogId
import seki.kotlin.backend.base.common.log.MsgLogger

class BatchRunner(
    private val msgLogger: MsgLogger,
    private val batchContext: BatchContext,
    private val batchService: BatchService,
) {
    fun run(vararg args: String?) {
        msgLogger.msg(MsgLogId.BATCH_LOG_00001, "${batchContext.batchId}:${batchContext.batchName}")
        batchContext.args = args.asList()
        // バッチ制御から多重起動確認を行い起動中の場合は処理を起動を終了させる(BatchRunnerがDIに対応していないためbatchContextからMapperを取得)
        val batchStartControl = batchContext.batchStartControlMapper.selectByPrimaryKey(batchContext.batchId)
        batchStartControl?.run {
            msgLogger.msg(MsgLogId.BATCH_LOG_00003, batchContext.batchId)
            return
        }

        try {
            val systemDatetime = batchContext.virtualDatetimeComponent.now()
            // バッチ制御登録
            batchContext.batchStartControlMapper.insertSelective(
                BatchStartControl(
                    batchId = batchContext.batchId,
                    startDatetime = systemDatetime,
                    createBy = batchContext.batchId,
                    createAt = systemDatetime,
                    updateBy = batchContext.batchId,
                    updateAt = systemDatetime,
                ),
            )

            // メインバッチ処理
            batchService.processBefore()
            batchService.process()
            batchService.processAfter()
        } catch (e: Throwable) {
            batchContext.exception = e
            msgLogger.error("Unexpected error has occurred.", e)
            return
        } finally {
            // バッチ制御削除
            batchContext.batchStartControlMapper.deleteByPrimaryKey(batchContext.batchId)
        }

        msgLogger.msg(
            MsgLogId.BATCH_LOG_00002,
            "${batchContext.batchId}:${batchContext.batchName}",
            batchContext.totalCount(),
            batchContext.successCount,
            batchContext.failCount,
            batchContext.skipCount,
        )
    }
}
