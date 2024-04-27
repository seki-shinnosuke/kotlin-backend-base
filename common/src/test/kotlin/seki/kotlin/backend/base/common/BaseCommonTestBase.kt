package seki.kotlin.backend.base.common

import com.github.springtestdbunit.annotation.DbUnitConfiguration
import com.github.springtestdbunit.dataset.ReplacementDataSetLoader
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.junit.jupiter.SpringExtension
import seki.kotlin.backend.base.common.annotation.BaseCommonTest

@ExtendWith(SpringExtension::class)
@BaseCommonTest
@DbUnitConfiguration(dataSetLoader = ReplacementDataSetLoader::class)
abstract class BaseCommonTestBase
