package seki.kotlin.backend.base.api.component

import com.github.springtestdbunit.annotation.DatabaseOperation
import com.github.springtestdbunit.annotation.DatabaseSetup
import com.github.springtestdbunit.annotation.DatabaseSetups
import com.github.springtestdbunit.annotation.ExpectedDatabase
import com.github.springtestdbunit.assertion.DatabaseAssertionMode
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import seki.kotlin.backend.base.api.BaseApiTestBase
import seki.kotlin.backend.base.common.enumeration.AccessType
import seki.kotlin.backend.base.common.extension.toFormatLocalDateTime
import seki.kotlin.backend.base.common.extension.toFormatString

internal class AccessLogManagementComponentTest : BaseApiTestBase() {
    companion object {
        private const val IP_ADDRESS = "192.168.1.0"
        private const val USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103"
        private val SYSTEM_DATETIME = "2024-01-01 12:00:00".toFormatLocalDateTime()
        private const val REQUEST_URI = "/api/v1/user/login"
    }

    @Autowired
    private lateinit var accessLogManagementComponent: AccessLogManagementComponent

    @DisplayName("AccessLogManagementComponent：正常：アクセス制限中")
    @Test
    @DatabaseSetups(
        DatabaseSetup("/dbunit/delete_all.xml", type = DatabaseOperation.TRUNCATE_TABLE),
        DatabaseSetup(
            "accesslogmanagement/01_testAccessLogManagementComponentCanAccessSuccess_data.xml",
            type = DatabaseOperation.INSERT,
        ),
    )
    @ExpectedDatabase(
        value = "accesslogmanagement/01_testAccessLogManagementComponentCanAccessSuccess_data.xml",
        assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
    )
    fun testAccessLogManagementComponentCanAccessSuccess01() {
        val result =
            accessLogManagementComponent.canAccess(
                IP_ADDRESS,
                USER_AGENT,
                SYSTEM_DATETIME,
                AccessType.USER_LOGIN,
                REQUEST_URI,
            )
        Assertions.assertEquals(result.first, false)
        Assertions.assertEquals(result.second?.toFormatString("yyyy/MM/dd HH:mm:ss"), "2024/01/01 12:58:00")
    }

    @DisplayName("AccessLogManagementComponent：正常：アクセス可")
    @Test
    @DatabaseSetups(
        DatabaseSetup("/dbunit/delete_all.xml", type = DatabaseOperation.TRUNCATE_TABLE),
        DatabaseSetup(
            "accesslogmanagement/02_testAccessLogManagementComponentCanAccessSuccess_data.xml",
            type = DatabaseOperation.INSERT,
        ),
    )
    @ExpectedDatabase(
        value = "accesslogmanagement/02_testAccessLogManagementComponentCanAccessSuccess_expect.xml",
        assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
    )
    fun testAccessLogManagementComponentCanAccessSuccess02() {
        val result =
            accessLogManagementComponent.canAccess(
                IP_ADDRESS,
                USER_AGENT,
                SYSTEM_DATETIME,
                AccessType.USER_LOGIN,
                REQUEST_URI,
            )
        Assertions.assertEquals(result.first, true)
        Assertions.assertEquals(result.second, null)
    }

    @DisplayName("AccessLogManagementComponent：正常：アクセス制限時間設定")
    @Test
    @DatabaseSetups(
        DatabaseSetup("/dbunit/delete_all.xml", type = DatabaseOperation.TRUNCATE_TABLE),
        DatabaseSetup(
            "accesslogmanagement/01_testAccessLogManagementComponentAccessLimitSuccess_data.xml",
            type = DatabaseOperation.INSERT,
        ),
    )
    @ExpectedDatabase(
        value = "accesslogmanagement/01_testAccessLogManagementComponentAccessLimitSuccess_expect.xml",
        assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
    )
    fun testAccessLogManagementComponentAccessLimitSuccess01() {
        accessLogManagementComponent.accessLimit(
            IP_ADDRESS,
            SYSTEM_DATETIME,
            AccessType.USER_LOGIN,
            REQUEST_URI,
        )
    }

    @DisplayName("AccessLogManagementComponent：正常：アクセス制限解除")
    @Test
    @DatabaseSetups(
        DatabaseSetup("/dbunit/delete_all.xml", type = DatabaseOperation.TRUNCATE_TABLE),
        DatabaseSetup(
            "accesslogmanagement/01_testAccessLogManagementComponentAccessLimitReleaseSuccess_data.xml",
            type = DatabaseOperation.INSERT,
        ),
    )
    @ExpectedDatabase(
        value = "accesslogmanagement/01_testAccessLogManagementComponentAccessLimitReleaseSuccess_expect.xml",
        assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
    )
    fun testAccessLogManagementComponentAccessLimitReleaseSuccess04() {
        accessLogManagementComponent.accessLimitRelease(IP_ADDRESS, AccessType.USER_LOGIN)
    }
}
