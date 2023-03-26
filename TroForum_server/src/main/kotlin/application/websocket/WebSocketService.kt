package com.troForum_server.application.websocket

import org.springframework.stereotype.Service
import com.troForum_server.infrastructure.utils.SessionManager
import org.springframework.web.socket.TextMessage

@Service
class WebSocketService {
    //发送消息
    fun sendMessage(key: String, message: String) {
        val session = SessionManager.getSession(key)
        session?.sendMessage(TextMessage(message))
    }

    //群发消息
    fun sendMessageToAll(message: String) {
        val sessionMap = SessionManager.sessionMap
        for (session in sessionMap.values) {
            session.sendMessage(TextMessage(message))
        }
    }
}