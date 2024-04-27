package seki.kotlin.backend.base.common.config

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.BeanCreationException
import org.springframework.beans.factory.InjectionPoint
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import seki.kotlin.backend.base.common.log.MsgLogger

/**
 * ロガー設定
 */
@Configuration
class LoggerConfig {
    @Bean
    @Scope("prototype")
    fun logger(ip: InjectionPoint): Logger {
        return LoggerFactory.getLogger(
            ip.methodParameter?.containingClass
                ?: ip.field?.declaringClass
                ?: throw BeanCreationException("Failed logger injection."),
        )
    }

    @Bean
    @Scope("prototype")
    fun msgLogger(ip: InjectionPoint): MsgLogger {
        return MsgLogger(
            LoggerFactory.getLogger(
                ip.methodParameter?.containingClass
                    ?: ip.field?.declaringClass
                    ?: throw BeanCreationException("Failed msg logger injection."),
            ),
        )
    }
}
