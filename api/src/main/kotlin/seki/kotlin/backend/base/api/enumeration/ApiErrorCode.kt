package seki.kotlin.backend.base.api.enumeration

import org.springframework.http.HttpStatus

/**
 * APIエラーコードの列挙型
 */
enum class ApiErrorCode(val status: HttpStatus) {
    /** 未ログイン */
    NOT_LOGIN(status = HttpStatus.UNAUTHORIZED),

    /** 不正リクエスト */
    BAD_REQUEST(status = HttpStatus.BAD_REQUEST),

    /** 閲覧禁止 */
    FORBIDDEN(status = HttpStatus.FORBIDDEN),

    /** 存在しない */
    NOT_FOUND(status = HttpStatus.NOT_FOUND),

    /** アクセス制限 */
    ACCESS_LIMITED(status = HttpStatus.UNPROCESSABLE_ENTITY),

    /** 内部エラー */
    INTERNAL_SERVER_ERROR(status = HttpStatus.INTERNAL_SERVER_ERROR),

    /** ログイン失敗 */
    LOGIN_FAILED(status = HttpStatus.UNPROCESSABLE_ENTITY),

    /** 排他制限 */
    EXCLUSIVE_RESTRICTION(status = HttpStatus.UNPROCESSABLE_ENTITY),

    /** 外部サービスエラー */
    EXTERNAL_SERVER_ERROR(status = HttpStatus.UNPROCESSABLE_ENTITY),
}
