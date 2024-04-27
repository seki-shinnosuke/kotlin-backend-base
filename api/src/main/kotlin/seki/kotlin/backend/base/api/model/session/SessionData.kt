package seki.kotlin.backend.base.api.model.session

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.springframework.stereotype.Component
import org.springframework.web.context.annotation.SessionScope
import seki.kotlin.backend.base.common.enumeration.AuthenticationStatus
import seki.kotlin.backend.base.common.enumeration.Authority
import seki.kotlin.backend.base.common.enumeration.UserStatus

/**
 * セッション情報
 */
@Component
@SessionScope
@JsonIgnoreProperties(ignoreUnknown = true)
data class SessionData(
    /** セッションID */
    var sessionId: String?,
    /** クライアントユーザーID */
    var clientUserId: Long?,
    /** クライアントユーザーログインID */
    var clientUserLoginId: String?,
    /** ユーザー名 */
    var userName: String?,
    /** メールアドレス */
    var emailAddress: String?,
    /** 認証状態 */
    var authenticationStatus: AuthenticationStatus?,
    /** ユーザー状態 */
    var userStatus: UserStatus?,
    /** 権限 */
    var authority: Authority?,
)
