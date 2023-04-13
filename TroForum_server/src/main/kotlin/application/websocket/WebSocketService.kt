package com.troForum_server.application.websocket

import com.alibaba.fastjson.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service
import org.springframework.web.socket.TextMessage
//定义系统通用消息
class SystemMessage {
    var targetId: String = ""
    var content: String = ""
}

//系统通用消息转jsonString
fun SystemMessage.toJson(): String {
    var json = JSONObject()
    json["targetId"] = targetId
    json["content"] = content
    return json.toJSONString()
}

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

    //系统消息（好友发送）
    fun sendSystem(message: SystemMessage) {
        messageTemplate.convertAndSend("/topic/system", message.toJson())
    }
}
