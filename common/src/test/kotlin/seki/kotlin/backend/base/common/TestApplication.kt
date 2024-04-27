package seki.kotlin.backend.base.common

import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

/**
 * commonプロジェクト単体テスト用Applicationクラス
 * commonを継承するサブプロジェクトでSpringBootTest時にテストクラスを共通利用するために利用
 */
@SpringBootApplication(
    scanBasePackages = [
        "seki.kotlin.backend.base.common",
    ],
)
@ConfigurationPropertiesScan(
    basePackages = [
        "seki.kotlin.backend.base.common",
    ],
)
class TestApplication {
    companion object {
        @JvmStatic fun main(args: Array<String>) {
            runApplication<TestApplication>(*args) {
                webApplicationType = WebApplicationType.NONE
            }
        }
    }
}
