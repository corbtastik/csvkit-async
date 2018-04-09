package io.corbs.csvkit

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
class CsvKitAPI(@Autowired val csvStore: CsvStore) {

    companion object { val LOG = LoggerFactory.getLogger(CsvKitAPI::class.java.name) }

    @PostMapping("/csv/{tag}")
    fun createCsv(@PathVariable("tag") tag: String, @RequestBody body: Flux<String>): Mono<String> {
        var lines = 0
        body.filter { it -> !it.trim().isEmpty() }
            .map{ s -> Mono.just(CsvLine("$tag", line = "$s".trim())) }
            .subscribe {
                lines++
                csvStore.save(it)
        }

        val message = "thank,you,for,$lines,lines"
        LOG.info(message)
        return Mono.just(message)
    }

    @GetMapping("/csv/{tag}")
    fun csvRead(@PathVariable("tag") tag: String,
                @RequestParam("size", required = false) size: Int?): Flux<String> {

        return csvStore.findByTag(tag).map { csvLine -> csvLine.line + System.lineSeparator() }
    }

    @DeleteMapping("/csv/{tag}")
    fun csvRemove(@PathVariable("tag") tag: String) {
        return csvStore.remove(tag);
    }

}


