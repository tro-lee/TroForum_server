package com.troForum_server.apis

import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.annotation.SubscribeMapping
import org.springframework.stereotype.Controller

@Controller
class WebSocketController {

    @MessageMapping("/sendTest")
    @SendTo("/topic/test")
    fun sendTest(message: String): String {
        return message
    }
}