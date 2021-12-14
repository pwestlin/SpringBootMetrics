package com.example.springbootmetrics

import io.micrometer.core.annotation.Timed
import io.micrometer.core.aop.TimedAspect
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Metrics
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.function.Predicate

@SpringBootApplication
class SpringBootMetricsApplication

fun main(args: Array<String>) {
    runApplication<SpringBootMetricsApplication>(*args)
}

@Configuration
class MetricsConfiguration {

    @Bean
    fun timedAspect(registry: MeterRegistry): TimedAspect {
        return TimedAspect(registry, Predicate { false })
    }
}

@RestController
class ShitController(private val service: ShitService) {

    @GetMapping(value = ["/shit"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @Timed
    fun shit(): Set<String> {
        Metrics.counter("rov").increment()

        return service.findShit()
    }
}

interface ShitService {
    fun findShit(): Set<String>
}

@Service
class ShitServiceImpl(private val repository: ShitRepository) : ShitService {

    @Timed
    override fun findShit(): Set<String> {
        return setOf(repository.getSomeShit(), repository.getSomeMoreShit())
    }
}

@Repository
class ShitRepository {

    @Timed
    fun getSomeShit(): String {
        Thread.sleep(100)
        return "some shit"
    }

    @Timed
    fun getSomeMoreShit(): String {
        Thread.sleep(200)
        return "some more shit"
    }
}