package com.troForum_server.application.websocket

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service
import org.springframework.web.socket.TextMessage


//服务器主动推消息
@Service
class WebSocketService {
    @Autowired
    private lateinit var messageTemplate: SimpMessagingTemplate

    //发送给/topic/test的消息
    fun sendTestMessage(message: String) {
        messageTemplate.convertAndSend("/topic/test", message)
    }
}