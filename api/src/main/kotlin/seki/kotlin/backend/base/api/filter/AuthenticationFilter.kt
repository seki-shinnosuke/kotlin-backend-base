package seki.kotlin.backend.base.api.filter

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import seki.kotlin.backend.base.api.annotation.BypassAuthorization
import seki.kotlin.backend.base.api.enumeration.ApiErrorCode
import seki.kotlin.backend.base.api.exception.ApiException
import seki.kotlin.backend.base.api.extension.sessionId
import seki.kotlin.backend.base.api.model.session.SessionData
import seki.kotlin.backend.base.common.log.MsgLogId
import seki.kotlin.backend.base.common.log.MsgLogger

/**
 * 認証フィルター
 */
class AuthenticationFilter : HandlerInterceptor {
    @Autowired
    private lateinit var msgLogger: MsgLogger

    @Autowired
    private lateinit var sessionData: SessionData

    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
    ): Boolean {
        // リクエスト内のIdTokenを取得
        val sessionId = request.sessionId()
        // ルーティングに該当するリクエストのみハンドリングする。（handler == HandlerMethod で判定）
        if (handler is HandlerMethod) {
            val bypassAuthorization = handler.method.getAnnotation(BypassAuthorization::class.java)
            if (bypassAuthorization != null) {
                return super.preHandle(request, response, handler)
            }
            if (!StringUtils.equals(sessionId, sessionData.sessionId)) {
                // セッションに保持してるセッションIDに変更があった場合
                msgLogger.msg(MsgLogId.API_LOG_00001)
                throw ApiException(ApiErrorCode.NOT_LOGIN)
            }
        }
        return super.preHandle(request, response, handler)
    }
}
