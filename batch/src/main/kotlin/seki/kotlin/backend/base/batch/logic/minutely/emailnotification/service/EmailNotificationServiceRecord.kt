package seki.kotlin.backend.base.batch.logic.minutely.emailnotification.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import seki.kotlin.backend.base.batch.core.BatchContext
import seki.kotlin.backend.base.common.db.mapper.EmailSendMapper
import seki.kotlin.backend.base.common.db.mapper.updateByPrimaryKey
import seki.kotlin.backend.base.common.db.model.EmailSend
import seki.kotlin.backend.base.common.enumeration.SendStatus
import java.time.LocalDateTime

/**
 * メール通知バッチレコード毎処理
 */
@Service
class EmailNotificationServiceRecord(
    private val emailSendMapper: EmailSendMapper,
    private val batchContext: BatchContext,
) {
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = [Throwable::class])
    fun processRecord(
        target: EmailSend,
        systemDatetime: LocalDateTime,
    ) {
        emailSendMapper.updateByPrimaryKey(
            target.copy(
                sendCompletedDatetime = systemDatetime,
                sendStatus = SendStatus.COMPLETED.name,
                updateBy = batchContext.batchName,
                updateAt = LocalDateTime.now(),
            ),
        )
    }
}
