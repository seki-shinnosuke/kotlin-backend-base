package seki.kotlin.backend.base.api.filter

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import seki.kotlin.backend.base.api.annotation.Authorization
import seki.kotlin.backend.base.api.annotation.BypassAuthorization
import seki.kotlin.backend.base.api.component.AuthorizationComponent
import seki.kotlin.backend.base.api.enumeration.ApiErrorCode
import seki.kotlin.backend.base.api.exception.ApiException
import seki.kotlin.backend.base.common.log.MsgLogId
import seki.kotlin.backend.base.common.log.MsgLogger

/**
 * 認可フィルター
 */
class AuthorizationFilter : HandlerInterceptor {
    @Autowired
    private lateinit var msgLogger: MsgLogger

    @Autowired
    private lateinit var authorizationComponent: AuthorizationComponent

    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
    ): Boolean {
        // ルーティングに該当するリクエストのみハンドリングする。（handler == HandlerMethod で判定）
        if (handler is HandlerMethod) {
            val bypassAuthorization = handler.method.getAnnotation(BypassAuthorization::class.java)
            val authorization = handler.method.getAnnotation(Authorization::class.java)
            val targetMethod = "${handler.beanType.simpleName}.${handler.method.name}"

            if (authorization != null) {
                authorizationComponent.clientUserAuthorization(
                    authorization.authenticationStatuses,
                    authorization.authorities,
                    targetMethod,
                )
            } else if (bypassAuthorization == null) {
                msgLogger.msg(MsgLogId.API_LOG_00003)
                throw ApiException(ApiErrorCode.NOT_FOUND)
            }
        }

        return super.preHandle(request, response, handler)
    }
}
