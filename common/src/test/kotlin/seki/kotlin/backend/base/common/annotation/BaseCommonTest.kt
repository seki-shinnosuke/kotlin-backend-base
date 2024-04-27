package seki.kotlin.backend.base.common.annotation

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestExecutionListeners

@Target(AnnotationTarget.FIELD, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@SpringBootTest
@AutoConfigureMockMvc
@TestExecutionListeners(
    listeners = [
        TransactionDbUnitTestExecutionListener::class,
    ],
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
)
annotation class BaseCommonTest()
