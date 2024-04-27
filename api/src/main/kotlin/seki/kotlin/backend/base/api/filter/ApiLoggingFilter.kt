package seki.kotlin.backend.base.api.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.MDC
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import seki.kotlin.backend.base.api.extension.ipAddress
import seki.kotlin.backend.base.api.extension.userAgent
import seki.kotlin.backend.base.api.extension.xRequestId
import seki.kotlin.backend.base.api.model.session.SessionData
import seki.kotlin.backend.base.common.log.MsgLogger

/**
 * APIフィルター
 * リクエスト時とレスポンス時にAPIパスと結果のログを出力する
 */
@Component
class ApiLoggingFilter(
    private val msgLogger: MsgLogger,
    private val sessionData: SessionData,
) : OncePerRequestFilter() {
    companion object {
        const val IP_ADDRESS = "ipAddress"
        const val CLIENT_USER_ID = "clientUserId"
        const val X_REQUEST_ID = "xRequestId"

        val EXCLUDE_URL = listOf("/api/healthz", "/api/healthz/")
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        // MDC設定
        MDC.remove(IP_ADDRESS)
        MDC.remove(CLIENT_USER_ID)
        MDC.remove(X_REQUEST_ID)
        MDC.put(IP_ADDRESS, request.ipAddress())
        sessionData.clientUserId?.let { MDC.put(CLIENT_USER_ID, it.toString()) }
        MDC.put(X_REQUEST_ID, request.xRequestId())

        try {
            msgLogger.info("REQUEST - {} {} from {}", request.method, request.requestURI, request.userAgent())
            filterChain.doFilter(request, response)
        } catch (e: Throwable) {
            throw e
        } finally {
            if (!shouldNotFilter(request)) {
                msgLogger.info("RESPONSE - {} {} : {}", request.method, request.requestURI, response.status)
            }
        }
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return EXCLUDE_URL.contains(request.requestURI)
    }
}
