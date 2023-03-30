package com.troForum_server.application.chat

import cn.dev33.satoken.stp.StpUtil
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.troForum_server.application.account.AccountService
import com.troForum_server.application.websocket.WebSocketService
import com.troForum_server.domain.entity.chat.PublicChat
import com.troForum_server.domain.dao.ChatRepository
import com.troForum_server.infrastructure.common.toJsonWrapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.format.DateTimeFormatter

@Service
class PublicChatService {
    @Autowired
    lateinit var webSocketService: WebSocketService
    @Autowired
    lateinit var chatRepository: ChatRepository
    @Autowired
    lateinit var accountService: AccountService

    //publicChat转json
    fun publicChatToJson(publicChat: PublicChat): JSONObject {
        val json = JSONObject()
        json["chatId"] = publicChat.chatId
        json["authorId"] = publicChat.authorId
        json["content"] = publicChat.content
        json["createdTime"] = publicChat.createdTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        json["userName"] = accountService.selectAccountById(publicChat.authorId)?.userName
        return json
    }

    //插入公共聊天
    fun insertPublicChat(content: String) {
        //生成publicChat对象
        val publicChat = PublicChat()
        publicChat.chatId = Instant.now().toEpochMilli().toString()
        publicChat.authorId = StpUtil.getLoginId().toString()
        publicChat.content = content

        try {
            chatRepository.insertPublicChat(publicChat)
        } catch (e: Exception) {
            throw e
        }

        //当前消息发送给大家
        webSocketService.sendMessageToAll(publicChatToJson(publicChat).toJSONString())
    }

    //获取公共聊天页
    fun getPublicChatPage(current: Long, size: Long, keyword: String): JSONObject {
        fun Page<PublicChat>.toJson(): JSONObject {
            val res = this.toJsonWrapper()
            val data = JSONArray()
            records.forEach {
                data.add(publicChatToJson(it))
            }
            res["value"] = data
            return res
        }
        return try {
            chatRepository.getPublicChatPage(Page(current, size), keyword).toJson()
        } catch (e: Exception) {
            throw e
        }
    }
}