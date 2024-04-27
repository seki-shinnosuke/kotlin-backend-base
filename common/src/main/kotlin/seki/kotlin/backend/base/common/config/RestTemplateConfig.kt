package seki.kotlin.backend.base.common.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.web.client.RestTemplate

/**
 * RestTemplate設定
 * API通信で個別での変換処理やタイムアウト値の設定を変更したい場合は本クラスに定義
 */
@Configuration
class RestTemplateConfig() {
    @Bean
    @Primary
    fun restTemplate(): RestTemplate {
        return RestTemplate()
    }
}
