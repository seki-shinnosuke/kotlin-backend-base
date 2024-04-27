package seki.kotlin.backend.base.api

import org.mybatis.spring.annotation.MapperScan
import org.mybatis.spring.annotation.MapperScans
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

/**
 * アプリケーション起動用クラス
 */
@MapperScans(
    MapperScan("seki.kotlin.backend.base.common.db"),
    MapperScan("seki.kotlin.backend.base.api.db"),
)
@SpringBootApplication(
    scanBasePackages = [
        "seki.kotlin.backend.base.common",
        "seki.kotlin.backend.base.api",
    ],
)
@ConfigurationPropertiesScan(
    basePackages = [
        "seki.kotlin.backend.base.common",
        "seki.kotlin.backend.base.api",
    ],
)
class ApiApplication {
    companion object {
        @JvmStatic fun main(args: Array<String>) {
            runApplication<ApiApplication>(*args)
        }
    }
}
