package seki.kotlin.backend.base.api.controller

import jakarta.servlet.RequestDispatcher
import jakarta.servlet.http.HttpServletRequest
import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

/**
 * エラーコントローラー
 * SpringBootのデフォルトエラーをカスタマイズしResponseをJsonにする
 */
@Controller
class ErrorController : ErrorController {
    @RequestMapping(value = ["/error"])
    fun error(request: HttpServletRequest): ResponseEntity<Void> {
        val statusCode = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE)
        if (statusCode != null && statusCode.toString() == HttpStatus.NOT_FOUND.value().toString()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
    }
}
