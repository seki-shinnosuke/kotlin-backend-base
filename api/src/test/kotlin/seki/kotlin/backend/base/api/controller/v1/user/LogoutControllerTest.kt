package seki.kotlin.backend.base.api.controller.v1.user

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpSession
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import seki.kotlin.backend.base.api.BaseApiTestBase

internal class LogoutControllerTest : BaseApiTestBase() {
    private lateinit var mockMvc: MockMvc
    private lateinit var session: MockHttpSession

    @Autowired
    private lateinit var webApplicationContext: WebApplicationContext

    @BeforeEach
    fun setup() {
        mockMvc =
            MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply<DefaultMockMvcBuilder?>(SecurityMockMvcConfigurers.springSecurity())
                .build()
        // 共通Mock
        session = Mockito.spy(MockHttpSession::class.java)
    }

    @DisplayName("LogoutController：正常：ログアウト成功")
    @Test
    fun testLogoutControllerSuccess01() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/user/logout")
                .contentType(MediaType.APPLICATION_JSON)
                .session(session),
        )
            .andExpect(MockMvcResultMatchers.status().isOk)

        verify(session, times(1)).invalidate()
    }
}
