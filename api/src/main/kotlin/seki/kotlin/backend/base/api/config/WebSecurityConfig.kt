package seki.kotlin.backend.base.api.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import seki.kotlin.backend.base.api.config.property.SecurityProperties

/**
 * SpringSecurityカスタムクラス
 */
@Configuration
@EnableWebSecurity
class WebSecurityConfig(
    private val securityProperties: SecurityProperties,
) {
    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun userDetailsService(passwordEncoder: PasswordEncoder): InMemoryUserDetailsManager {
        // Spring Securityのデフォルトユーザーを作成しないようにするために実装
        return InMemoryUserDetailsManager(
            User
                .withUsername("BASIC_AUTH_USERNAME")
                .passwordEncoder(passwordEncoder::encode)
                .password("BASIC_AUTH_PASSWORD")
                .roles("USER")
                .build(),
        )
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.authorizeHttpRequests { authorize ->
            authorize.requestMatchers("/**").permitAll()
        }.csrf { csrf ->
            csrf.disable()
        }.cors { cors ->
            cors.configurationSource(corsConfigurationSource())
        }.formLogin { formLogin ->
            formLogin.disable()
        }.httpBasic { httpBasic ->
            httpBasic.disable()
        }.requestCache { requestCache ->
            requestCache.disable()
        }.headers { headers ->
            // HSTS
            if (securityProperties.hstsOn) {
                headers.httpStrictTransportSecurity { httpStrictTransportSecurity ->
                    httpStrictTransportSecurity.includeSubDomains(true)
                    httpStrictTransportSecurity.preload(true)
                    httpStrictTransportSecurity.maxAgeInSeconds(31536000)
                }
            }
        }
        return http.build()
    }

    private fun corsConfigurationSource(): CorsConfigurationSource {
        val corsConfiguration = CorsConfiguration()

        val authorizeUrlList = securityProperties.corsClientUrls.split(",").toList()
        corsConfiguration.allowedOriginPatterns = authorizeUrlList
        corsConfiguration.addAllowedHeader(CorsConfiguration.ALL)
        val allowedMethods = listOf(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.OPTIONS)
        corsConfiguration.allowedMethods = allowedMethods.map { it.name() }
        corsConfiguration.allowCredentials = true

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", corsConfiguration)
        return source
    }
}
