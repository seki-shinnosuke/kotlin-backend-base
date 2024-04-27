package seki.kotlin.backend.base.api.controller.v1.user

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import seki.kotlin.backend.base.api.annotation.BypassAuthorization
import seki.kotlin.backend.base.api.service.v1.user.LogoutService

/**
 * ログアウトコントローラー
 */
@RestController
class LogoutController(
    private val logoutService: LogoutService,
) {
    /**
     * ログアウト
     */
    @PostMapping("/api/v1/user/logout")
    @BypassAuthorization
    fun postLogout(): ResponseEntity<Void> {
        logoutService.logout()

        return ResponseEntity.status(HttpStatus.OK).build()
    }
}
