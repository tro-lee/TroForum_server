package com.troForum_server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import java.util.*
import javax.annotation.PostConstruct

@SpringBootApplication
@EnableCaching
class TroForumServerApplication

fun main(args: Array<String>) {
	runApplication<TroForumServerApplication>(*args)
}

@PostConstruct
fun started() {
    TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
}
