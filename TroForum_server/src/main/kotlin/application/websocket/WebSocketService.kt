package com.troForum_server.application.websocket

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service
import org.springframework.web.socket.TextMessage


//提供WebSocket服务，消息通知在这里配置，约定好消息格式
@Service
class WebSocketService {
    @Autowired
    private lateinit var messageTemplate: SimpMessagingTemplate

    //主页聊天框
    fun sendTestMessage(message: String) {
        messageTemplate.convertAndSend("/topic/publicChat", message)
    }

    //好友聊天
    fun sendFriendMessage(message: String, relationId: String) {
        messageTemplate.convertAndSend("/queue/privateChat/${relationId}", message)
    }
}