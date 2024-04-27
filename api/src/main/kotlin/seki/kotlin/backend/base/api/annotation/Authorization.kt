package seki.kotlin.backend.base.api.annotation

import seki.kotlin.backend.base.common.enumeration.AuthenticationStatus
import seki.kotlin.backend.base.common.enumeration.Authority

/**
 * クライアントユーザー認可用独自アノテーション
 * 許可する権限と認証状態を定義しコントローラーメソッドに付与する
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Authorization(
    val authenticationStatuses: Array<AuthenticationStatus>,
    val authorities: Array<Authority>,
)
