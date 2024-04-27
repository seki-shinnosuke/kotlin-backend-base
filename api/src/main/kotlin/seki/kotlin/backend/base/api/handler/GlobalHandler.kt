package seki.kotlin.backend.base.api.handler

import org.springframework.beans.propertyeditors.StringTrimmerEditor
import org.springframework.boot.logging.LogLevel
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.lang.Nullable
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.InitBinder
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import org.springframework.web.servlet.resource.NoResourceFoundException
import seki.kotlin.backend.base.api.exception.ApiException
import seki.kotlin.backend.base.api.exception.ApiWithMsgException
import seki.kotlin.backend.base.api.extension.fieldErrorsJoinToString
import seki.kotlin.backend.base.common.log.MsgLogId
import seki.kotlin.backend.base.common.log.MsgLogger

/**
 * グローバルハンドラークラス
 * 独自例外クラスをキャッチして指定のレスポンス型に置換する
 */
@ControllerAdvice
class GlobalHandler(
    private val msgLogger: MsgLogger,
) : ResponseEntityExceptionHandler() {
    @InitBinder
    fun initBinder(binder: WebDataBinder) {
        // 未入力の文字列は空文字ではなく null としてバインドする。
        binder.registerCustomEditor(String::class.java, StringTrimmerEditor(true))
    }

    @ExceptionHandler(ApiException::class)
    private fun handleApiException(ex: ApiException): ResponseEntity<Map<String, Any?>> {
        return ResponseEntity(mapOf("message" to ex.status.reasonPhrase), ex.status)
    }

    @ExceptionHandler(ApiWithMsgException::class)
    private fun handleApiWithMsgException(ex: ApiWithMsgException): ResponseEntity<Map<String, Any?>> {
        return ResponseEntity(mapOf("message" to ex.message), ex.status)
    }

    @ExceptionHandler(Exception::class)
    private fun handleAll(ex: Exception): ResponseEntity<Any> {
        msgLogger.loggingException(LogLevel.ERROR, "${ex.message} (${ex::class.simpleName})")
        return ResponseEntity(mapOf("message" to HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase), HttpStatus.INTERNAL_SERVER_ERROR)
    }

    override fun handleExceptionInternal(
        ex: Exception,
        @Nullable body: Any?,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest,
    ): ResponseEntity<Any> {
        return when (ex) {
            is NoResourceFoundException -> {
                ResponseEntity(mapOf("message" to HttpStatus.NOT_FOUND.reasonPhrase), HttpStatus.NOT_FOUND)
            }
            is MethodArgumentNotValidException -> {
                msgLogger.msg(MsgLogId.API_LOG_00002, ex.bindingResult.fieldErrorsJoinToString())
                ResponseEntity(mapOf("message" to HttpStatus.BAD_REQUEST.reasonPhrase), HttpStatus.BAD_REQUEST)
            }
            else -> {
                msgLogger.loggingException(getLogLevel(status), "${ex.message} (${ex::class.simpleName})")
                val httpStatus = HttpStatus.valueOf(status.value())
                ResponseEntity(mapOf("message" to httpStatus.reasonPhrase), status)
            }
        }
    }

    private fun getLogLevel(status: HttpStatusCode): LogLevel {
        return when {
            status.is5xxServerError && status != HttpStatus.SERVICE_UNAVAILABLE -> LogLevel.ERROR
            else -> LogLevel.INFO
        }
    }
}
