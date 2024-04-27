package seki.kotlin.backend.base.api.controller.v1.user

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import seki.kotlin.backend.base.api.annotation.Authorization
import seki.kotlin.backend.base.api.model.v1.response.GetUserInfoResponse
import seki.kotlin.backend.base.api.service.v1.user.UserService
import seki.kotlin.backend.base.common.enumeration.AuthenticationStatus
import seki.kotlin.backend.base.common.enumeration.Authority

/**
 * ユーザーコントローラー
 */
@RestController
class UserController(
    private val userService: UserService,
) {
    /**
     * ユーザー情報取得
     */
    @GetMapping("/api/v1/user/info")
    @Authorization(
        [AuthenticationStatus.REQUIRED_CHANGE_PASSWORD, AuthenticationStatus.AUTHENTICATED],
        [Authority.USER, Authority.ADMINISTRATOR],
    )
    fun getUserInfo(): GetUserInfoResponse {
        return userService.getUserInfo()
    }
}
