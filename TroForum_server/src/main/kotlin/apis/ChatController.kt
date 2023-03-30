package com.troForum_server.apis

import cn.dev33.satoken.annotation.SaCheckLogin
import cn.dev33.satoken.stp.StpUtil
import com.fasterxml.jackson.databind.ObjectMapper
import com.troForum_server.application.chat.PublicChatService
import com.troForum_server.domain.entity.chat.PublicChat
import com.troForum_server.infrastructure.common.result
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

@RestController
@SaCheckLogin
class ChatController {
    @Autowired
    private lateinit var publicChatService: PublicChatService

    //插入公共聊天
    class PublicChatReq {
        var content: String = ""
    }
    @PostMapping("/insertPublicChat")
    fun insertPublicChat(@RequestBody req: PublicChatReq) = result {
        //检查content
        if (req.content.length > 100) {
            throw Exception("内容太多，超过字体限制")
        }
        if (req.content.isEmpty()) {
            throw Exception("没有内容")
        }
        return@result publicChatService.insertPublicChat(req.content)
    }

    //获取公共聊天页
    class PublicChatPageReq {
        var current: Long = 1
        var size: Long = 6
        var keyword: String = ""
    }
    @PostMapping("/getPublicChatPage")
    fun getPublicChat(@RequestBody req: PublicChatPageReq) = result {
        return@result publicChatService.getPublicChatPage(req.current, req.size, req.keyword)
    }
}