package seki.kotlin.backend.base.api.annotation

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestExecutionListeners

@Target(AnnotationTarget.FIELD, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@SpringBootTest
@AutoConfigureWebTestClient
@TestExecutionListeners(
    listeners = [
        TransactionDbUnitTestExecutionListener::class,
    ],
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
)
annotation class BaseApiTest
