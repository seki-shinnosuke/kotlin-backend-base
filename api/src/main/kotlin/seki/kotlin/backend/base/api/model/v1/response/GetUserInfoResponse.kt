package seki.kotlin.backend.base.api.model.v1.response

import seki.kotlin.backend.base.common.enumeration.AuthenticationStatus
import seki.kotlin.backend.base.common.enumeration.Authority
import seki.kotlin.backend.base.common.enumeration.UserStatus

data class GetUserInfoResponse(
    /** クライアントユーザーID */
    val clientUserId: Long,
    /** クライアントユーザーログインID */
    val clientUserLoginId: String,
    /** ユーザー名 */
    val userName: String,
    /** メールアドレス */
    val emailAddress: String?,
    /** 権限 */
    val authority: Authority,
    /** ユーザー状態 */
    val userStatus: UserStatus,
    /** 認証状態 */
    val authenticationStatus: AuthenticationStatus,
)
