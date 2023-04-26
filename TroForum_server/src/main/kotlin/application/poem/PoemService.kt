package com.troForum_server.application.poem

import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.troForum_server.application.account.AccountService
import com.troForum_server.application.websocket.SystemMessage
import com.troForum_server.application.websocket.WebSocketService
import com.troForum_server.domain.dao.PoemRepository
import com.troForum_server.domain.dao.RelationRepository
import com.troForum_server.domain.entity.poem.Poem
import com.troForum_server.infrastructure.common.toJsonWrapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PoemService {
    @Autowired
    lateinit var poemRepository: PoemRepository
    @Autowired
    lateinit var relationRepository: RelationRepository
    @Autowired
    lateinit var accountService: AccountService
    @Autowired
    lateinit var webSocketService: WebSocketService


    //插入诗
    fun insertPoem(authorId: String, title: String, content: String) {
        val poem = Poem()
        poem.authorId = authorId
        poem.title = title
        poem.content = content
        poem.poemId = System.currentTimeMillis().toString()
        try {
            poemRepository.insertPoem(poem)
        } catch (e: Exception) {
            throw e
        }

        //获取关注列表，对关注列表用户依次通知
        val relationList = relationRepository.getFollowedList(authorId)
        relationList.forEach {
            val systemMessage = SystemMessage()
            systemMessage.targetId = it.starterId
            systemMessage.content = "您的关注${accountService.idToAccount(authorId)!!.userName}发布了新诗"
            webSocketService.sendSystem(systemMessage)
        }
    }


    fun Page<Poem>.toJson(): JSONObject {
        val res = this.toJsonWrapper()
        val data = JSONArray()
        records.forEach {
            val item = JSONObject.toJSON(it) as JSONObject
            item["userId"] = it.authorId
            item["userName"] = accountService.idToAccount(it.authorId)!!.userName
            item["avatarUrl"] = accountService.idToAccount(it.authorId)!!.avatarUrl
            data.add(item)
        }
        res["value"] = data
        return res
    }
    //获取诗页
    fun getPoemPage(current: Long, size: Long, keyword: String): JSONObject {
        return try {
            poemRepository.getPoemPage(Page(current, size), keyword).toJson()
        } catch (e: Exception) {
            throw e
        }
    }

    //获取作者的诗页
    fun getPoemPageByAuthorId(current: Long, size: Long, authorId: String): JSONObject {
        return try {
            poemRepository.getPoemPageByAuthor(Page(current, size), authorId).toJson()
        } catch (e: Exception) {
            throw e
        }
    }
}
