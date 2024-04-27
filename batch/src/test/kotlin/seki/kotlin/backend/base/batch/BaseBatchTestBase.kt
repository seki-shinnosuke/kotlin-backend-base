package seki.kotlin.backend.base.batch

import com.github.springtestdbunit.annotation.DbUnitConfiguration
import com.github.springtestdbunit.dataset.ReplacementDataSetLoader
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.junit.jupiter.SpringExtension
import seki.kotlin.backend.base.batch.annotation.BaseBatchTest

@ExtendWith(SpringExtension::class)
@BaseBatchTest
@DbUnitConfiguration(dataSetLoader = ReplacementDataSetLoader::class)
abstract class BaseBatchTestBase
