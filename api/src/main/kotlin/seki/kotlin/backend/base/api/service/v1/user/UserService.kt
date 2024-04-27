package seki.kotlin.backend.base.api.service.v1.user

import org.springframework.stereotype.Service
import seki.kotlin.backend.base.api.model.session.SessionData
import seki.kotlin.backend.base.api.model.v1.response.GetUserInfoResponse

/**
 * ユーザーサービス
 */
@Service
class UserService(
    private val sessionData: SessionData,
) {
    fun getUserInfo(): GetUserInfoResponse {
        return GetUserInfoResponse(
            clientUserId = sessionData.clientUserId!!,
            clientUserLoginId = sessionData.clientUserLoginId!!,
            userName = sessionData.userName!!,
            emailAddress = sessionData.emailAddress,
            authority = sessionData.authority!!,
            userStatus = sessionData.userStatus!!,
            authenticationStatus = sessionData.authenticationStatus!!,
        )
    }
}
