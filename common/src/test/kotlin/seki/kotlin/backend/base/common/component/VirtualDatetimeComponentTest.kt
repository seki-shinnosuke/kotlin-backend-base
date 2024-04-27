package seki.kotlin.backend.base.common.component

import com.github.springtestdbunit.annotation.DatabaseOperation
import com.github.springtestdbunit.annotation.DatabaseSetup
import com.github.springtestdbunit.annotation.DatabaseSetups
import com.github.springtestdbunit.annotation.ExpectedDatabase
import com.github.springtestdbunit.assertion.DatabaseAssertionMode
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.MockedStatic
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.springframework.beans.factory.annotation.Autowired
import seki.kotlin.backend.base.common.BaseCommonTestBase
import seki.kotlin.backend.base.common.extension.toFormatLocalDateTime
import java.time.LocalDateTime
import java.time.ZoneId

internal class VirtualDatetimeComponentTest : BaseCommonTestBase() {
    @Autowired
    private lateinit var virtualDatetimeComponent: VirtualDatetimeComponent

    private fun mockLocalDateTime(): MockedStatic<LocalDateTime> {
        val localDateTime = "2024-01-01 00:30:00".toFormatLocalDateTime()
        val mockClass = Mockito.mockStatic(LocalDateTime::class.java, Mockito.CALLS_REAL_METHODS)
        mockClass.`when`<Any> { LocalDateTime.now(any<ZoneId>()) }.thenReturn(localDateTime)
        return mockClass
    }

    @DisplayName("VirtualDatetimeComponent：正常：仮想日時取得")
    @DatabaseSetups(
        DatabaseSetup("/dbunit/delete_all.xml", type = DatabaseOperation.TRUNCATE_TABLE),
        DatabaseSetup("virtualdatetimecomponent/01_testVirtualDatetimeComponentSuccess_data.xml", type = DatabaseOperation.INSERT),
    )
    @ExpectedDatabase(
        value = "virtualdatetimecomponent/01_testVirtualDatetimeComponentSuccess_data.xml",
        assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
    )
    @Test
    fun testVirtualDatetimeComponentSuccess01() {
        val mockLocalDateTime = mockLocalDateTime()
        val virtualDatetime = virtualDatetimeComponent.now()
        mockLocalDateTime.close()
        Assertions.assertEquals(virtualDatetime, "2024-01-01 01:30:00".toFormatLocalDateTime())
    }
}
