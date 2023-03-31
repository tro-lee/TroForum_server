package com.troForum_server.application.chat

import cn.dev33.satoken.stp.StpUtil
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.troForum_server.application.account.AccountService
import com.troForum_server.application.websocket.WebSocketService
import com.troForum_server.domain.dao.ChatRepository
import com.troForum_server.domain.entity.chat.PrivateChat
import com.troForum_server.infrastructure.common.toJsonWrapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.format.DateTimeFormatter

@Service
class PrivateChatService {
    @Autowired
    lateinit var webSocketService: WebSocketService
    @Autowired
    lateinit var chatRepository: ChatRepository
    @Autowired
    lateinit var accountService: AccountService

    //privateChat转json
    fun privateChatToJson(privateChat: PrivateChat): JSONObject {
        val json = JSONObject()
        json["chatId"] = privateChat.chatId
        json["authorId"] = privateChat.authorId
        json["relationId"] = privateChat.relationId
        json["content"] = privateChat.content
        json["createdTime"] = privateChat.createdTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        json["userName"] = accountService.selectAccountById(privateChat.authorId)?.userName
        return json
    }

    //插入私聊
    fun insertPrivateChat(content: String, relationId: String) {
        //生成privateChat对象
        val privateChat = PrivateChat()
        privateChat.chatId = Instant.now().toEpochMilli().toString().substring(0, 13) + Math.random().toString().substring(2, 5)
        privateChat.authorId = StpUtil.getLoginId().toString()
        privateChat.relationId = relationId
        privateChat.content = content

        try {
            chatRepository.insertPrivateChat(privateChat)
        } catch (e: Exception) {
            throw e
        }

        //当前消息发送给大家
        webSocketService.sendFriendMessage(privateChatToJson(privateChat).toJSONString(), relationId)
    }

    //获取私聊页
    fun getPrivateChatPage(current: Long, size: Long, keyword: String, relationId: String): JSONObject {
        fun Page<PrivateChat>.toJson(): JSONObject {
            val res = this.toJsonWrapper()
            val data = JSONArray()
            records.forEach {
                data.add(privateChatToJson(it))
            }
            res["value"] = data
            return res
        }
        return chatRepository.getPrivateChatPage(Page(current, size), relationId, keyword).toJson()
    }
}