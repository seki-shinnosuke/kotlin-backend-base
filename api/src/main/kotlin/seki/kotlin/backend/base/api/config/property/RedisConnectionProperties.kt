package seki.kotlin.backend.base.api.config.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties

@EnableConfigurationProperties
@ConfigurationProperties(prefix = "spring.redis")
data class RedisConnectionProperties(
    val host: String,
    val port: Int,
    val password: String,
    val ssl: Boolean,
)
