package seki.kotlin.backend.base.api.annotation

/**
 * 認可用独自アノテーション
 * バイパスしたいコントローラーメソッドに付与する
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class BypassAuthorization
