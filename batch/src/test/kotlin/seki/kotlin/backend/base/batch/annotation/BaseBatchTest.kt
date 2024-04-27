package seki.kotlin.backend.base.batch.annotation

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestExecutionListeners
import seki.kotlin.backend.base.batch.BatchApplication

@Target(AnnotationTarget.FIELD, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@ContextConfiguration(
    classes = [BatchApplication::class],
    initializers = [ConfigDataApplicationContextInitializer::class],
)
@TestExecutionListeners(
    listeners = [TransactionDbUnitTestExecutionListener::class],
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
)
annotation class BaseBatchTest
