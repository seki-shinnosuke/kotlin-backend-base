package seki.kotlin.backend.base.api.config

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisPassword
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer
import org.springframework.session.data.redis.config.ConfigureRedisAction
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession
import org.springframework.session.web.http.CookieSerializer
import org.springframework.session.web.http.DefaultCookieSerializer
import seki.kotlin.backend.base.api.config.property.RedisConnectionProperties
import java.io.IOException
import java.time.OffsetDateTime
import java.time.ZoneId

/**
 * SpringRedisSessionカスタムクラス
 */
@Configuration
@EnableRedisHttpSession(redisNamespace = "api", maxInactiveIntervalInSeconds = 3600)
class RedisSessionConfig(
    private val properties: RedisConnectionProperties,
) {
    private lateinit var serializer: GenericJackson2JsonRedisSerializer

    /**
     * シリアライズ設定
     */
    init {
        val mapper =
            jacksonObjectMapper()
                .setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE)
                .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
                .activateDefaultTyping(
                    LaissezFaireSubTypeValidator.instance,
                    ObjectMapper.DefaultTyping.NON_FINAL,
                    JsonTypeInfo.As.PROPERTY,
                )
                .registerModule(JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        val simpleModule = SimpleModule()
        simpleModule.addDeserializer(
            OffsetDateTime::class.java,
            object : JsonDeserializer<OffsetDateTime>() {
                @Throws(IOException::class, JsonProcessingException::class)
                override fun deserialize(
                    arg0: JsonParser,
                    ctxt: DeserializationContext,
                ): OffsetDateTime {
                    // OffsetDateTimeをdeserializeする際にJSTに置換
                    return OffsetDateTime.parse(arg0.text).atZoneSameInstant(ZoneId.of("Asia/Tokyo")).toOffsetDateTime()
                }
            },
        )
        mapper.registerModule(simpleModule)
        this.serializer = GenericJackson2JsonRedisSerializer(mapper)
    }

    /**
     * SessionのデータをRedisで保存する際にデータオブジェクトをJSON形式にシリアライズ・デシリアライズする
     */
    @Bean
    @Qualifier("springSessionDefaultRedisSerializer")
    fun getRedisSerializer(): RedisSerializer<Any> = this.serializer

    /**
     * Redis接続用設定
     */
    @Bean
    fun redisConnectionFactory(): LettuceConnectionFactory {
        val config =
            RedisStandaloneConfiguration().also {
                it.hostName = properties.host
                it.port = properties.port
                it.password = RedisPassword.of(properties.password)
            }
        return if (properties.ssl) {
            LettuceConnectionFactory(config, LettuceClientConfiguration.builder().useSsl().build())
        } else {
            LettuceConnectionFactory(config)
        }
    }

    @Bean
    fun configureRedisAction(): ConfigureRedisAction = ConfigureRedisAction.NO_OP

    @Bean
    fun redisContainer(): RedisMessageListenerContainer {
        val container = RedisMessageListenerContainer()
        container.setConnectionFactory(redisConnectionFactory())
        return container
    }

    @Bean
    fun redisTemplate(): StringRedisTemplate {
        val redisTemplate = StringRedisTemplate()
        redisTemplate.connectionFactory = redisConnectionFactory()
        redisTemplate.keySerializer = StringRedisSerializer()
        return redisTemplate
    }

    @Bean
    fun cookieSerializer(): CookieSerializer {
        val serializer = DefaultCookieSerializer()
        if (properties.ssl) {
            serializer.setUseSecureCookie(true)
        }
        serializer.setUseHttpOnlyCookie(true)
        serializer.setCookiePath("/")
        return serializer
    }
}
