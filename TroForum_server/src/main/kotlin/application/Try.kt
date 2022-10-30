package com.troForum_server.application

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController {
    @GetMapping("/hi")
    fun HI(): String {
        return "hi"
    }

    @PostMapping("/h")
    fun h(): String {
        return "h"
    }
}