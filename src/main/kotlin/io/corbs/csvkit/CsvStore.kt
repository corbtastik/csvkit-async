package io.corbs.csvkit

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class CsvStore(@Autowired val redis: ReactiveRedisTemplate<String, String>) {

    fun save(mono: Mono<CsvLine>) {

        mono.subscribe {
            redis.opsForList().rightPush(it.tag, it.line.trim()).subscribe()
        }
    }

    fun findByTag(tag: String, size: Long = 100): Flux<CsvLine> {
        val listOps = redis.opsForList()
        return listOps.range(tag, 0, size).map { line -> CsvLine(tag, line) }
    }

    fun remove(tag: String) {
        // nothing happens until subscribe is called...delete(tag) by itself
        // doesn't trigger the async delete...you have to call subscribe :)
        redis.opsForList().delete(tag).subscribe()
    }
}

