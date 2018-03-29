package io.corbs.csvkit

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.data.redis.serializer.RedisSerializationContext

@EnableRedisRepositories
@Configuration
class CsvConfig {

    @Bean
    fun reactiveRedisTemplate(connectionFactory: ReactiveRedisConnectionFactory):
            ReactiveRedisTemplate<String, String> {

        return ReactiveRedisTemplate(connectionFactory, RedisSerializationContext.string())
    }

    @Bean
    fun redisConnectionFactory(): LettuceConnectionFactory {
        return LettuceConnectionFactory()
    }

}