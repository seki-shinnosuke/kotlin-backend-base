package seki.kotlin.backend.base.api

import com.github.springtestdbunit.annotation.DbUnitConfiguration
import com.github.springtestdbunit.dataset.ReplacementDataSetLoader
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.junit.jupiter.SpringExtension
import seki.kotlin.backend.base.api.annotation.BaseApiTest

@ExtendWith(SpringExtension::class)
@BaseApiTest
@DbUnitConfiguration(dataSetLoader = ReplacementDataSetLoader::class)
abstract class BaseApiTestBase
