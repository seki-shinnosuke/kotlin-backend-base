package seki.kotlin.backend.base.batch.logic.minutely.emailnotification.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import seki.kotlin.backend.base.batch.core.BatchContext
import seki.kotlin.backend.base.batch.core.service.BatchService
import seki.kotlin.backend.base.common.component.VirtualDatetimeComponent
import seki.kotlin.backend.base.common.db.mapper.EmailSendDynamicSqlSupport
import seki.kotlin.backend.base.common.db.mapper.EmailSendMapper
import seki.kotlin.backend.base.common.db.mapper.select
import seki.kotlin.backend.base.common.enumeration.SendStatus

/**
 * メール通知バッチサービス処理
 */
@Service
class EmailNotificationService(
    private val virtualDatetimeComponent: VirtualDatetimeComponent,
    private val emailSendMapper: EmailSendMapper,
    private val batchContext: BatchContext,
    private val emailNotificationServiceRecord: EmailNotificationServiceRecord,
) : BatchService {
    companion object {
        // 1回のバッチ起動で送信するメールの上限件数
        private const val EMAIL_PROCESS_LIMIT_SIZE = 200L
    }

    override fun processBefore() {
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = [Throwable::class])
    override fun process() {
        try {
            // バッチ起動時点でのシステム日時を基準とする
            val systemDatetime = virtualDatetimeComponent.now()
            val targets =
                emailSendMapper.select {
                    where {
                        EmailSendDynamicSqlSupport.sendReservationDatetime.isLessThanOrEqualTo(systemDatetime)
                        and { EmailSendDynamicSqlSupport.sendCompletedDatetime.isNull() }
                        and { EmailSendDynamicSqlSupport.sendStatus.isEqualTo(SendStatus.RESERVED.name) }
                    }
                    orderBy(EmailSendDynamicSqlSupport.sendReservationDatetime)
                    limit(EMAIL_PROCESS_LIMIT_SIZE)
                }
            targets.forEach {
                try {
                    emailNotificationServiceRecord.processRecord(it, systemDatetime)
                    batchContext.successCountUp()
                } catch (e: Throwable) {
                    batchContext.failCountUp()
                }
            }
        } catch (e: Throwable) {
            throw e
        }
    }

    override fun processAfter() {
    }
}
