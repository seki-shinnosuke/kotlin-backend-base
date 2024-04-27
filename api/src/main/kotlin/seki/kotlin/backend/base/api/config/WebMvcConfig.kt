package seki.kotlin.backend.base.api.config

import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes
import org.springframework.boot.web.servlet.error.ErrorAttributes
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import seki.kotlin.backend.base.api.filter.AuthenticationFilter
import seki.kotlin.backend.base.api.filter.AuthorizationFilter

/**
 * SpringMVCカスタムクラス
 */
@Configuration
class WebMvcConfig : WebMvcConfigurer {
    @Bean
    fun authenticationFilter(): AuthenticationFilter = AuthenticationFilter()

    @Bean
    fun authorizationFilter(): AuthorizationFilter = AuthorizationFilter()

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.apply {
            // 認証フィルター設定
            addInterceptor(authenticationFilter()).apply {
                addPathPatterns("/api/**")
            }
            // 認可フィルター設定
            addInterceptor(authorizationFilter()).apply {
                addPathPatterns("/api/**")
            }
        }
        super.addInterceptors(registry)
    }

    /**
     * ErrorHandlerでは制御できない例外のハンドリング設定
     * 未設定URLへアクセスされた場合、Interceptorまで到達せずにエラーレスポンスされErrorHandlerで制御できす、
     * SpringBootデフォルトのエラーフォーマットでレスポンスされるため、本処理でレスポンスをカスタマイズする
     */
    @Bean
    fun errorAttributes(): ErrorAttributes {
        return object : DefaultErrorAttributes() {
            override fun getErrorAttributes(
                webRequest: WebRequest?,
                options: ErrorAttributeOptions?,
            ): MutableMap<String, Any> {
                val attrs = super.getErrorAttributes(webRequest, options)
                val httpStatus = HttpStatus.valueOf(attrs["status"] as Int)
                return mutableMapOf("message" to httpStatus.reasonPhrase)
            }
        }
    }
}
