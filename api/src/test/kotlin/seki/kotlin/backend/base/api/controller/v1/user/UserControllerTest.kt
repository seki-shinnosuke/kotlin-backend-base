package seki.kotlin.backend.base.api.controller.v1.user

import com.github.springtestdbunit.annotation.DatabaseOperation
import com.github.springtestdbunit.annotation.DatabaseSetup
import com.github.springtestdbunit.annotation.DatabaseSetups
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpSession
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import seki.kotlin.backend.base.api.BaseApiTestBase
import seki.kotlin.backend.base.api.model.session.SessionData
import seki.kotlin.backend.base.common.enumeration.AuthenticationStatus
import seki.kotlin.backend.base.common.enumeration.Authority
import seki.kotlin.backend.base.common.enumeration.UserStatus

internal class UserControllerTest : BaseApiTestBase() {
    private lateinit var mockMvc: MockMvc
    private lateinit var session: MockHttpSession

    @Autowired
    private lateinit var webApplicationContext: WebApplicationContext

    @SpyBean
    private lateinit var request: MockHttpServletRequest

    @MockBean
    private lateinit var sessionData: SessionData

    @BeforeEach
    fun setup() {
        mockMvc =
            MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply<DefaultMockMvcBuilder?>(SecurityMockMvcConfigurers.springSecurity())
                .build()
        session = Mockito.spy(MockHttpSession::class.java)
        doReturn(session.id).whenever(sessionData).sessionId
        doReturn(1L).whenever(sessionData).clientUserId
        doReturn("base").whenever(sessionData).clientUserLoginId
        doReturn("テストアカウント").whenever(sessionData).userName
        doReturn("sample@example.com").whenever(sessionData).emailAddress
        doReturn(AuthenticationStatus.AUTHENTICATED).whenever(sessionData).authenticationStatus
        doReturn(UserStatus.ACTIVE).whenever(sessionData).userStatus
        doReturn(Authority.ADMINISTRATOR).whenever(sessionData).authority
    }

    @DisplayName("UserController：異常：ユーザー情報取得：未ログイン")
    @Test
    fun testUserControllerUserInfoFail00() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/v1/user/info"),
        )
            .andExpect(MockMvcResultMatchers.status().isUnauthorized)
    }

    @DisplayName("UserController：正常：ユーザー情報取得")
    @Test
    @DatabaseSetups(
        DatabaseSetup("/dbunit/delete_all.xml", type = DatabaseOperation.TRUNCATE_TABLE),
    )
    fun testUserControllerUserInfoSuccess02() {
        val expectedJson =
            "{" +
                "\"clientUserId\": 1,\n" +
                "\"clientUserLoginId\": \"base\",\n" +
                "\"userName\": \"テストアカウント\",\n" +
                "\"emailAddress\": \"sample@example.com\",\n" +
                "\"authority\": \"ADMINISTRATOR\",\n" +
                "\"userStatus\": \"ACTIVE\",\n" +
                "\"authenticationStatus\": \"AUTHENTICATED\"\n" +
                "}"
        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/v1/user/info").session(session),
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json(expectedJson, true))
    }
}
