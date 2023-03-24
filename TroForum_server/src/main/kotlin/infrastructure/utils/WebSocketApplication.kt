package infrastructure.utils

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class WebSocketApplication

fun main(args: Array<String>) {
    runApplication<WebSocketApplication>(*args)
}