package com.troForum_server.infrastructure.utils

import org.springframework.web.socket.WebSocketSession
import java.util.concurrent.ConcurrentHashMap

object SessionManager {
    //存放session
    val sessionMap = ConcurrentHashMap<String, WebSocketSession>()

    //添加session
    fun addSession(key: String, session: WebSocketSession) {
        sessionMap[key] = session
    }

    //删除session
    private fun removeSession(key: String): WebSocketSession?{
        return sessionMap.remove(key)
    }

    //删除和关闭连接
    fun removeAndCloseSession(key: String) {
        val session = removeSession(key)
        try {
            session?.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //获取session
    fun getSession(key: String): WebSocketSession? {
        return sessionMap[key]
    }
}