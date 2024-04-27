package seki.kotlin.backend.base.common.config.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties

@EnableConfigurationProperties
@ConfigurationProperties(prefix = "virtual-datetime")
data class VirtualDatetimeProperties(
    val isVirtualDatetimeOn: Boolean,
)
