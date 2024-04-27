package seki.kotlin.backend.base.api.controller.v1.user

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.springtestdbunit.annotation.DatabaseOperation
import com.github.springtestdbunit.annotation.DatabaseSetup
import com.github.springtestdbunit.annotation.DatabaseSetups
import com.github.springtestdbunit.annotation.ExpectedDatabase
import com.github.springtestdbunit.assertion.DatabaseAssertionMode
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.http.MediaType
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import seki.kotlin.backend.base.api.BaseApiTestBase
import seki.kotlin.backend.base.api.component.AccessLogManagementComponent
import seki.kotlin.backend.base.api.model.session.SessionData
import seki.kotlin.backend.base.api.model.v1.request.PostLoginRequest
import seki.kotlin.backend.base.common.component.VirtualDatetimeComponent
import seki.kotlin.backend.base.common.enumeration.AuthenticationStatus
import seki.kotlin.backend.base.common.enumeration.Authority
import seki.kotlin.backend.base.common.extension.toFormatLocalDateTime

internal class LoginControllerTest : BaseApiTestBase() {
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var webApplicationContext: WebApplicationContext

    @SpyBean
    private lateinit var passwordEncoder: PasswordEncoder

    @MockBean
    private lateinit var virtualDatetimeComponent: VirtualDatetimeComponent

    @MockBean
    private lateinit var accessLogManagementComponent: AccessLogManagementComponent

    @BeforeEach
    fun setup() {
        mockMvc =
            MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply<DefaultMockMvcBuilder?>(SecurityMockMvcConfigurers.springSecurity())
                .build()
        // 共通Mock
        doReturn("2024-01-01 12:00:00".toFormatLocalDateTime()).whenever(virtualDatetimeComponent).now()
    }

    @DisplayName("LoginController：異常：ログイン：バリデーションエラー")
    @Test
    fun testLoginControllerLoginFail00() {
        val requestBody =
            PostLoginRequest(
                clientUserLoginId = "",
                password = "",
            )
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonObjectMapper().writeValueAsString(requestBody)),
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @DisplayName("LoginController：異常：ログイン：アクセス制限中")
    @Test
    fun testLoginControllerLoginFail01() {
        doReturn(
            Pair(false, "2024-01-01 12:00:00".toFormatLocalDateTime()),
        ).whenever(accessLogManagementComponent).canAccess(any(), any(), any(), any(), any())
        val expectedJson = "{\"message\":\"ユーザーIDまたはパスワードが一致しません。\"}"
        val requestBody =
            PostLoginRequest(
                clientUserLoginId = "clientUserLoginId",
                password = "password",
            )
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonObjectMapper().writeValueAsString(requestBody)),
        )
            .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity)
            .andExpect(MockMvcResultMatchers.content().json(expectedJson, true))
    }

    @DisplayName("LoginController：異常：ログイン：ユーザー情報なし")
    @Test
    @DatabaseSetups(
        DatabaseSetup("/dbunit/delete_all.xml", type = DatabaseOperation.TRUNCATE_TABLE),
    )
    fun testLoginControllerLoginFail02() {
        doReturn(Pair(true, null)).whenever(accessLogManagementComponent).canAccess(any(), any(), any(), any(), any())
        doReturn(4).whenever(accessLogManagementComponent).accessLimit(any(), any(), any(), any())
        val expectedJson = "{\"message\":\"ユーザーIDまたはパスワードが一致しません。\"}"
        val requestBody =
            PostLoginRequest(
                clientUserLoginId = "clientUserLoginId",
                password = "password",
            )
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonObjectMapper().writeValueAsString(requestBody)),
        )
            .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity)
            .andExpect(MockMvcResultMatchers.content().json(expectedJson, true))
    }

    @DisplayName("LoginController：異常：ログイン：パスワード不一致")
    @Test
    @DatabaseSetups(
        DatabaseSetup("/dbunit/delete_all.xml", type = DatabaseOperation.TRUNCATE_TABLE),
        DatabaseSetup("/dbunit/common_data.xml", type = DatabaseOperation.INSERT),
    )
    fun testLoginControllerLoginFail03() {
        doReturn(Pair(true, null)).whenever(accessLogManagementComponent).canAccess(any(), any(), any(), any(), any())
        doReturn(4).whenever(accessLogManagementComponent).accessLimit(any(), any(), any(), any())
        doReturn(false).whenever(passwordEncoder).matches(any(), any())
        val expectedJson = "{\"message\":\"ユーザーIDまたはパスワードが一致しません。\"}"
        val requestBody =
            PostLoginRequest(
                clientUserLoginId = "base",
                password = "password",
            )
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonObjectMapper().writeValueAsString(requestBody)),
        )
            .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity)
            .andExpect(MockMvcResultMatchers.content().json(expectedJson, true))
    }

    @DisplayName("LoginController：正常：ログイン：ログイン成功")
    @Test
    @DatabaseSetups(
        DatabaseSetup("/dbunit/delete_all.xml", type = DatabaseOperation.TRUNCATE_TABLE),
        DatabaseSetup("/dbunit/common_data.xml", type = DatabaseOperation.INSERT),
    )
    @ExpectedDatabase(
        value = "logincontroller/04_testLoginSuccess_expect.xml",
        assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
    )
    fun testLoginControllerLoginSuccess04() {
        doReturn(Pair(true, null)).whenever(accessLogManagementComponent).canAccess(any(), any(), any(), any(), any())
        doReturn(4).whenever(accessLogManagementComponent).accessLimit(any(), any(), any(), any())
        doReturn(true).whenever(passwordEncoder).matches(any(), any())
        doNothing().whenever(accessLogManagementComponent).accessLimitRelease(any(), any())
        val requestBody =
            PostLoginRequest(
                clientUserLoginId = "base",
                password = "password",
            )
        val result =
            mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/user/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jacksonObjectMapper().writeValueAsString(requestBody)),
            )
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn()

        val sessionData = result.request.session?.getAttribute("scopedTarget.sessionData") as SessionData
        Assertions.assertEquals(sessionData.clientUserId, 1L)
        Assertions.assertEquals(sessionData.authenticationStatus, AuthenticationStatus.AUTHENTICATED)
        Assertions.assertEquals(sessionData.authority, Authority.ADMINISTRATOR)
    }

    @DisplayName("LoginController：正常：ログイン：仮パスワード状態で仮パスワードログイン")
    @Test
    @DatabaseSetups(
        DatabaseSetup("/dbunit/delete_all.xml", type = DatabaseOperation.TRUNCATE_TABLE),
        DatabaseSetup("logincontroller/05_testLoginSuccess_data.xml", type = DatabaseOperation.INSERT),
    )
    @ExpectedDatabase(
        value = "logincontroller/05_testLoginSuccess_expect.xml",
        assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
    )
    fun testLoginControllerLoginSuccess05_01() {
        doReturn(Pair(true, null)).whenever(accessLogManagementComponent).canAccess(any(), any(), any(), any(), any())
        doReturn(4).whenever(accessLogManagementComponent).accessLimit(any(), any(), any(), any())
        doReturn(true).doReturn(false).whenever(passwordEncoder).matches(any(), any())
        doNothing().whenever(accessLogManagementComponent).accessLimitRelease(any(), any())
        val requestBody =
            PostLoginRequest(
                clientUserLoginId = "base",
                password = "password",
            )
        val result =
            mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/user/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jacksonObjectMapper().writeValueAsString(requestBody)),
            )
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn()

        val sessionData = result.request.session?.getAttribute("scopedTarget.sessionData") as SessionData
        Assertions.assertEquals(sessionData.clientUserId, 1L)
        Assertions.assertEquals(sessionData.authenticationStatus, AuthenticationStatus.REQUIRED_CHANGE_PASSWORD)
        Assertions.assertEquals(sessionData.authority, Authority.ADMINISTRATOR)
    }

    @DisplayName("LoginController：正常：ログイン：仮パスワード状態で本パスワードログイン")
    @Test
    @DatabaseSetups(
        DatabaseSetup("/dbunit/delete_all.xml", type = DatabaseOperation.TRUNCATE_TABLE),
        DatabaseSetup("logincontroller/05_testLoginSuccess_data.xml", type = DatabaseOperation.INSERT),
    )
    @ExpectedDatabase(
        value = "logincontroller/05_testLoginSuccess_expect.xml",
        assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
    )
    fun testLoginControllerLoginSuccess05_02() {
        doReturn(Pair(true, null)).whenever(accessLogManagementComponent).canAccess(any(), any(), any(), any(), any())
        doReturn(4).whenever(accessLogManagementComponent).accessLimit(any(), any(), any(), any())
        doReturn(false).doReturn(true).whenever(passwordEncoder).matches(any(), any())
        doNothing().whenever(accessLogManagementComponent).accessLimitRelease(any(), any())
        val requestBody =
            PostLoginRequest(
                clientUserLoginId = "base",
                password = "password1234",
            )
        val result =
            mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/user/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jacksonObjectMapper().writeValueAsString(requestBody)),
            )
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn()

        val sessionData = result.request.session?.getAttribute("scopedTarget.sessionData") as SessionData
        Assertions.assertEquals(sessionData.clientUserId, 1L)
        Assertions.assertEquals(sessionData.authenticationStatus, AuthenticationStatus.AUTHENTICATED)
        Assertions.assertEquals(sessionData.authority, Authority.ADMINISTRATOR)
    }

    @DisplayName("LoginController：正常：ログイン：仮パスワード有効期限切れ状態で本パスワードログイン")
    @Test
    @DatabaseSetups(
        DatabaseSetup("/dbunit/delete_all.xml", type = DatabaseOperation.TRUNCATE_TABLE),
        DatabaseSetup("logincontroller/06_testLoginSuccess_data.xml", type = DatabaseOperation.INSERT),
    )
    @ExpectedDatabase(
        value = "logincontroller/06_testLoginSuccess_expect.xml",
        assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
    )
    fun testLoginControllerLoginSuccess06() {
        doReturn(Pair(true, null)).whenever(accessLogManagementComponent).canAccess(any(), any(), any(), any(), any())
        doReturn(4).whenever(accessLogManagementComponent).accessLimit(any(), any(), any(), any())
        doReturn(true).whenever(passwordEncoder).matches(any(), any())
        doNothing().whenever(accessLogManagementComponent).accessLimitRelease(any(), any())
        val requestBody =
            PostLoginRequest(
                clientUserLoginId = "base",
                password = "password",
            )
        val result =
            mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/user/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jacksonObjectMapper().writeValueAsString(requestBody)),
            )
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn()

        val sessionData = result.request.session?.getAttribute("scopedTarget.sessionData") as SessionData
        Assertions.assertEquals(sessionData.clientUserId, 1L)
        Assertions.assertEquals(sessionData.authenticationStatus, AuthenticationStatus.AUTHENTICATED)
        Assertions.assertEquals(sessionData.authority, Authority.ADMINISTRATOR)
    }
}
