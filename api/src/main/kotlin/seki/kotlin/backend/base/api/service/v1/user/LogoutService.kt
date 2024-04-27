package seki.kotlin.backend.base.api.service.v1.user

import jakarta.servlet.http.HttpSession
import org.springframework.stereotype.Service

@Service
class LogoutService(
    private val httpSession: HttpSession,
) {
    fun logout() {
        // Httpセッションを破棄して強制ログアウトとする
        httpSession.invalidate()
    }
}
