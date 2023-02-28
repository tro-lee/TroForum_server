package com.troForum_server.application.post

import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.troForum_server.application.account.AccountService
import com.troForum_server.domain.entity.post.ReplyPost
import com.troForum_server.domain.entity.post.TopicPost
import com.troForum_server.domain.service.PostRepository
import com.troForum_server.infrastructure.common.toJsonWrapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class PostService {
    @Autowired
    private lateinit var postRepository: PostRepository
    @Autowired
    private lateinit var accountService: AccountService

    //插入主题帖
    fun insertTopicPost(
        authorId: String, content: String, title: String,
        theme: String
    ) {
        //判断是否违规
        if (content.length > 3000) {
            throw Exception("字数超出3000字")
        }
        if (title.length > 32) {
            throw Exception("标题过长")
        }
        val topicPost = TopicPost()
        val timestamp = Instant.now()
        topicPost.postId = authorId.substring(5, 10) + timestamp.toEpochMilli().toString()
        topicPost.authorId = authorId
        topicPost.content = content
        topicPost.title = title
        postRepository.insertTopicPost(topicPost)
    }

    //插入回复帖
    fun insertReplyPost(
        authorId: String, content: String, master: String
    ) {
        //判断是否违规
        if (content.length > 2000) {
            throw Exception("字数超出2000字")
        }
        val replyPost = ReplyPost()
        val timestamp = Instant.now()
        replyPost.postId = authorId.substring(5, 10) + timestamp.toEpochMilli().toString()
        replyPost.authorId = authorId
        replyPost.content = content
        replyPost.master = master
        try {
            postRepository.insertReplyPost(replyPost)
        } catch (e: Exception) {
            throw Exception("发帖失败")
        }

    }

    //获得主题页
    fun getTopicPostPage(current: Long, size: Long, keyword: String): JSONObject {
        fun Page<TopicPost>.toJson(): JSONObject {
            val res = this.toJsonWrapper()
            val data = JSONArray()
            records.forEach {
                val json = JSONObject.toJSON(it) as JSONObject
                json["userName"] = accountService.selectAccountById(it.authorId)?.userName
                json.remove("content")
                data.add(json)
            }
            res["value"] = data
            return res
        }
        return try {
            postRepository.getTopicPostPage(Page(current, size), keyword).toJson()
        } catch (e: Exception) {
            throw e
        }
    }

    //获取主题贴
    fun getTopicPost(postId: String): JSONObject? {
        val data = postRepository.getTopicPost(postId) ?: throw Exception("返回主页")
        val json = JSONObject.toJSON(data) as JSONObject
        json["userName"] = accountService.selectAccountById(data.authorId)?.userName
        return json
    }

    //获取回复帖
    fun getReplyPostPage(current: Long, size: Long, postId: String): JSONObject {
        fun Page<ReplyPost>.toJson(): JSONObject {
            val res = this.toJsonWrapper()
            val data = JSONArray()
            records.forEach {
                val json = JSONObject.toJSON(it) as JSONObject
                json["userName"] = accountService.selectAccountById(it.authorId)?.userName
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