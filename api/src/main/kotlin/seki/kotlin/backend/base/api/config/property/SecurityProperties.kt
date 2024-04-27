package seki.kotlin.backend.base.api.config.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties

@EnableConfigurationProperties
@ConfigurationProperties(prefix = "spring.security")
data class SecurityProperties(
    val corsClientUrls: String,
    val hstsOn: Boolean,
)
