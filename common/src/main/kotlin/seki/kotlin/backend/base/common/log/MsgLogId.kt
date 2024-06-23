package seki.kotlin.backend.base.common.log

import org.springframework.boot.logging.LogLevel

/**
 * ログ出力用メッセージの列挙型
 */
enum class MsgLogId(val logLevel: LogLevel, val message: String) {
    // Common
    COMMON_LOG_00001(LogLevel.WARN, "Log."),

    // API
    API_LOG_00001(LogLevel.WARN, "Not Login."),
    API_LOG_00002(LogLevel.WARN, "Bad parameter. {}"),
    API_LOG_00003(LogLevel.WARN, "Authorization is not defined. method={}"),
    API_LOG_00004(LogLevel.WARN, "Authority is not defined. method={}"),
    API_LOG_00005(LogLevel.WARN, "Access limited. ipAddress={}"),
    API_LOG_00006(LogLevel.WARN, "Client user login failed. Client user is not found. clientUserLoginId={}"),
    API_LOG_00007(LogLevel.WARN, "Client user login failed. Password is not matched. clientUserLoginId={}"),

    // Batch
    BATCH_LOG_00001(LogLevel.INFO, "Batch Process Start[{}]"),
    BATCH_LOG_00002(LogLevel.INFO, "Batch Process Result[{} All:{}/Success:{}/Fail:{}/Skip:{}]"),
    BATCH_LOG_00003(LogLevel.WARN, "Batch is running. Skip processing. batchId={}"),
}
