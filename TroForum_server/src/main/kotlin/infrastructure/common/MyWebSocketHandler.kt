package com.troForum_server.infrastructure.common

import com.troForum_server.infrastructure.utils.SessionManager
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.WebSocketMessage
import org.springframework.web.socket.WebSocketSession

class MyWebSocketHandler: WebSocketHandler {

    override fun afterConnectionEstablished(session: WebSocketSession) {
        SessionManager.addSession(session.id, session)
        println("成功建立连接")
    }

    override fun handleMessage(session: WebSocketSession, message: WebSocketMessage<*>) {
        println("来消息啦")
        println(message.payload.toString())
    }

    override fun handleTransportError(session: WebSocketSession, exception: Throwable) {
        SessionManager.removeAndCloseSession(session.id)
        throw Exception("连接错误")
    }

    override fun afterConnectionClosed(session: WebSocketSession, closeStatus: org.springframework.web.socket.CloseStatus) {
        SessionManager.removeAndCloseSession(session.id)
    }

    override fun supportsPartialMessages(): Boolean {
        return false
    }
}