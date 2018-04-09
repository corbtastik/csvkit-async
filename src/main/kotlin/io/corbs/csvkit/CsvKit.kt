package io.corbs.csvkit

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker
import org.springframework.cloud.netflix.hystrix.EnableHystrix

@EnableHystrix
@EnableCircuitBreaker
@SpringBootApplication
class CsvKit

fun main(args: Array<String>) {
    runApplication<CsvKit>(*args)
}

