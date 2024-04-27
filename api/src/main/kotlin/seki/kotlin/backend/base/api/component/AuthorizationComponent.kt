package seki.kotlin.backend.base.api.component

import org.springframework.stereotype.Component
import seki.kotlin.backend.base.api.enumeration.ApiErrorCode
import seki.kotlin.backend.base.api.exception.ApiException
import seki.kotlin.backend.base.api.model.session.SessionData
import seki.kotlin.backend.base.common.enumeration.AuthenticationStatus
import seki.kotlin.backend.base.common.enumeration.Authority
import seki.kotlin.backend.base.common.log.MsgLogId
import seki.kotlin.backend.base.common.log.MsgLogger

/**
 * 認可コンポーネント
 * 本コンポーネントでは認証状態及び認可項目をセッション情報から抽出し確認する
 */
@Component
class AuthorizationComponent(
    private val msgLogger: MsgLogger,
    private val sessionData: SessionData,
) {
    /**
     * クライアントユーザー認可
     */
    fun clientUserAuthorization(
        authenticationStatuses: Array<AuthenticationStatus>,
        authorities: Array<Authority>,
        methodName: String,
    ) {
        // 認証状態
        val authenticationStatus = sessionData.authenticationStatus
        if (authenticationStatuses.none { it == authenticationStatus }) {
            msgLogger.msg(MsgLogId.API_LOG_00003, methodName)
            throw ApiException(ApiErrorCode.FORBIDDEN)
        }

        // 権限
        val authority = sessionData.authority
        if (authorities.none { it == authority }) {
            msgLogger.msg(MsgLogId.API_LOG_00004, methodName)
            throw ApiException(ApiErrorCode.FORBIDDEN)
        }
    }
}
