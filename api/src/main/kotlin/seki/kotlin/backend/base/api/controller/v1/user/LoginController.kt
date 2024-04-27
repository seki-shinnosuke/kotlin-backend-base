package seki.kotlin.backend.base.api.controller.v1.user

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import seki.kotlin.backend.base.api.annotation.BypassAuthorization
import seki.kotlin.backend.base.api.model.v1.request.PostLoginRequest
import seki.kotlin.backend.base.api.service.v1.user.LoginService

/**
 * ログインコントローラー
 */
@RestController
class LoginController(
    private val loginService: LoginService,
) {
    /**
     * ログイン
     */
    @PostMapping("/api/v1/user/login")
    @BypassAuthorization
    fun postLogin(
        @RequestBody @Validated
        postLoginRequest: PostLoginRequest,
        request: HttpServletRequest,
    ): ResponseEntity<Void> {
        loginService.postLogin(postLoginRequest, request)

        return ResponseEntity.status(HttpStatus.OK).build()
    }
}
