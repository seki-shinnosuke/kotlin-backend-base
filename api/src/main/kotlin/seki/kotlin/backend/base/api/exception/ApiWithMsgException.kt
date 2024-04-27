package seki.kotlin.backend.base.api.exception

import org.springframework.http.HttpStatus
import seki.kotlin.backend.base.api.enumeration.ApiErrorCode

class ApiWithMsgException(
    code: ApiErrorCode,
    override val message: String,
    override val cause: Throwable? = null,
) : RuntimeException() {
    val status: HttpStatus = code.status
}
