package io.corbs.csvkit

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
class CsvKitAPI(@Autowired val csvStore: CsvStore) {

    @PostMapping("/csv/{tag}")
    fun csvBody(@PathVariable("tag") tag: String, @RequestBody body: Flux<String>) : Mono<String> {

        body.map{ s -> Mono.just(CsvLine("$tag", line = "$s")) }
            .subscribe {
                csvStore.save(it)
            }

        return Mono.just("howdy,yo,thank,you,for,tag,$tag,yummy,yum,yum")
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


