package seki.kotlin.backend.base.batch.logic.minutely.emailnotification.service

import com.github.springtestdbunit.annotation.DatabaseOperation
import com.github.springtestdbunit.annotation.DatabaseSetup
import com.github.springtestdbunit.annotation.DatabaseSetups
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.TestPropertySource
import seki.kotlin.backend.base.batch.BaseBatchTestBase
import seki.kotlin.backend.base.batch.core.BatchContext
import seki.kotlin.backend.base.batch.core.service.BatchRunner
import seki.kotlin.backend.base.common.component.VirtualDatetimeComponent
import seki.kotlin.backend.base.common.extension.toFormatLocalDateTime
import seki.kotlin.backend.base.common.log.MsgLogger

@TestPropertySource(properties = ["BATCH_ID=EmailNotification"])
internal class EmailNotificationServiceTest : BaseBatchTestBase() {
    @Autowired
    private lateinit var msgLogger: MsgLogger

    @Autowired
    private lateinit var batchContext: BatchContext

    @Autowired
    private lateinit var emailNotificationService: EmailNotificationService

    @MockBean
    private lateinit var virtualDatetimeComponent: VirtualDatetimeComponent

    @BeforeEach
    fun setUp() {
        // Mock
        doReturn("2024-01-01 12:00:00".toFormatLocalDateTime()).whenever(virtualDatetimeComponent).now()
    }

    /**
     * バッチ処理実行
     */
    private fun executeRun() {
        batchContext.apply {
            cleanUp()
        }
        BatchRunner(msgLogger, batchContext, emailNotificationService).run()
    }

    @DisplayName("EmailNotificationService：正常：対象なし")
    @DatabaseSetups(
        DatabaseSetup("/dbunit/delete_all.xml", type = DatabaseOperation.TRUNCATE_TABLE),
    )
    @Test
    fun testEmailNotificationSuccess01() {
        // バッチ処理実行
        executeRun()

        // 検証: 実施結果
        Assertions.assertSame(false, batchContext.hasError())
        Assertions.assertSame(0L, batchContext.totalCount())
        Assertions.assertSame(0L, batchContext.successCount)
        Assertions.assertSame(0L, batchContext.failCount)
        Assertions.assertSame(0L, batchContext.skipCount)
    }
}
