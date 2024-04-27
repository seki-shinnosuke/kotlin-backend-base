package seki.kotlin.backend.base.batch.logic.minutely.emailnotification

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import seki.kotlin.backend.base.batch.core.BatchContext
import seki.kotlin.backend.base.batch.core.service.BatchRunner
import seki.kotlin.backend.base.batch.logic.minutely.emailnotification.service.EmailNotificationService
import seki.kotlin.backend.base.common.log.MsgLogger

class EmailNotificationRunner(
    private val runner: BatchRunner,
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        runner.run()
    }

    @Configuration
    class EmailNotificationConfig(
        private val msgLogger: MsgLogger,
        private val batchContext: BatchContext,
        private val batchService: EmailNotificationService,
    ) {
        @Bean
        @ConditionalOnProperty(name = ["BATCH_ID"], havingValue = "EmailNotification")
        fun emailNotificationRunner(): EmailNotificationRunner {
            batchContext.batchName = "メール通知バッチ"
            return EmailNotificationRunner(BatchRunner(msgLogger, batchContext, batchService))
        }
    }
}
