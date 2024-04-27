package seki.kotlin.backend.base.api.extension

import jakarta.servlet.http.HttpServletRequest
import java.util.UUID

/**
 * プロキシーを考慮したIPアドレスを取得する
 */
fun HttpServletRequest.ipAddress(): String {
    return this.getHeader("X-Forwarded-For")?.let {
        if (it.contains(",")) {
            it.split(",")[0].trim()
        } else {
            it.trim()
        }
    } ?: this.remoteAddr
}

/**
 * ユーザーエージェントを取得する
 */
fun HttpServletRequest.userAgent(): String = this.getHeader("User-Agent") ?: "Unknown"

/**
 * リクエストIDを取得する
 * 存在しない場合はUUIDを発行する
 */
fun HttpServletRequest.xRequestId(): String = this.getHeader("X-Request-Id") ?: UUID.randomUUID().toString()

/**
 * セッションIDを返却する
 * 存在しない場合はセッションを新規作成する
 */
fun HttpServletRequest.sessionId(): String = this.getSession(true).id
