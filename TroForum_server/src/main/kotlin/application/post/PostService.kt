package com.troForum_server.application.post

import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.troForum_server.application.account.AccountService
import com.troForum_server.domain.entity.post.ReplyPost
import com.troForum_server.domain.entity.post.TopicPost
import com.troForum_server.domain.dao.PostRepository
import com.troForum_server.infrastructure.common.toJsonWrapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.format.DateTimeFormatter

/*
* 帖子服务层，分为主题帖和回复帖
 */
@Service
class PostService {
    @Autowired
    private lateinit var postRepository: PostRepository

    @Autowired
    private lateinit var accountService: AccountService

    //主题帖

    //插入主题帖
    fun insertTopicPost(
        authorId: String, content: String, title: String,
        theme: String
    ) {
        val topicPost = TopicPost()
        val timestamp = Instant.now()
        topicPost.postId = timestamp.toEpochMilli().toString()
        topicPost.authorId = authorId
        topicPost.content = content
        topicPost.title = title
        postRepository.insertTopicPost(topicPost)
    }

    fun Page<TopicPost>.toJson(): JSONObject {
        val res = this.toJsonWrapper()
        val data = JSONArray()
        records.forEach {
            val json = JSONObject.toJSON(it) as JSONObject
            json["userName"] = accountService.idToAccount(it.authorId)!!.userName
            json["userId"] = it.authorId
            json["avatarUrl"] = accountService.idToAccount(it.authorId)!!.avatarUrl
            json["createdTime"] = it.createdTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            json.remove("content")
            data.add(json)
        }
        res["value"] = data
        return res
    }

    //获得主题页
    fun getTopicPostPage(current: Long, size: Long, keyword: String): JSONObject {
        return try {
            postRepository.getTopicPostPage(Page(current, size), keyword).toJson()
        } catch (e: Exception) {
            throw e
        }
    }

    //获取作者的主题帖
    fun getTopicPostPageByAuthor(current: Long, size: Long, authorId: String): JSONObject {
        return try {
            postRepository.getTopicPostPageByAuthor(Page(current, size), authorId).toJson()
        } catch (e: Exception) {
            throw e
        }
    }

    //获取主题贴
    fun getTopicPost(postId: String): JSONObject? {
        val data = postRepository.getTopicPost(postId) ?: throw Exception("返回主页")
        val json = JSONObject.toJSON(data) as JSONObject
        json["userName"] = accountService.idToAccount(data.authorId)!!.userName
        json["createdTime"] = data.createdTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        json["userId"] = data.authorId
        json["avatarUrl"] = accountService.idToAccount(data.authorId)!!.avatarUrl
        return json
    }

    //回复贴

    //插入回复帖
    fun insertReplyPost(
        authorId: String, content: String, master: String, masterUserId: String
    ) {
        val replyPost = ReplyPost()
        val timestamp = Instant.now()
        replyPost.postId = timestamp.toEpochMilli().toString()
        replyPost.authorId = authorId
        replyPost.content = content
        replyPost.master = master
        replyPost.masterUserId = masterUserId
        try {
            postRepository.insertReplyPost(replyPost)
        } catch (e: Exception) {
            throw Exception("发帖失败")
        }

    }

    //获取回复帖
    fun getReplyPostPage(current: Long, size: Long, postId: String): JSONObject {
        fun Page<ReplyPost>.toJson(): JSONObject {
            val res = this.toJsonWrapper()
            val data = JSONArray()
            //回复页
            records.forEach {
                //回复组
                val replyData = JSONArray()
                val replyDataList = postRepository.getReplyPostList(it.postId)
                replyDataList.forEach { item ->
                    run {
                        val json = JSONObject.toJSON(item) as JSONObject
                        json["ReplyName"] = accountService.idToAccount(it.authorId)!!.userName
                        json["userName"] = accountService.idToAccount(item.authorId)!!.userName
                        json["userId"] = item.authorId
                        json["avatarUrl"] = accountService.idToAccount(item.authorId)!!.avatarUrl
                        json["createdTime"] = item.createdTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                        replyData.add(json)
                    }
                }
                val json = JSONObject.toJSON(it) as JSONObject
                json["userName"] = accountService.idToAccount(it.authorId)!!.userName
                json["userId"] = it.authorId
                json["avatarUrl"] = accountService.idToAccount(it.authorId)!!.avatarUrl
                json["createdTime"] = it.createdTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                json["replyData"] = replyData
                data.add(json)
            }
            res["value"] = data
            return res
        }
        return try {
            postRepository.getReplyPostPage(Page(current, size), postId).toJson()
        } catch (e: Exception) {
            throw e
        }
    }
}