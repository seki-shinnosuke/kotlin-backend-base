package seki.kotlin.backend.base.api.exception

import org.springframework.http.HttpStatus
import seki.kotlin.backend.base.api.enumeration.ApiErrorCode

/**
 * API用独自例外クラス
 */
class ApiException(
    code: ApiErrorCode,
    override val message: String? = null,
    override val cause: Throwable? = null,
) : RuntimeException() {
    val status: HttpStatus = code.status
}
