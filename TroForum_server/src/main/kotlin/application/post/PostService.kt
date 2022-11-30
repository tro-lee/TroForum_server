package com.troForum_server.application.post

import com.troForum_server.domain.service.PostRepository
import com.troForum_server.domain.service.ReplyPost
import com.troForum_server.domain.service.TopicPost
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@Service
class PostService {
    @Autowired
    private lateinit var postRepository: PostRepository

    //插入主题帖
    fun insertTopicPost(
        authorId: String, content: String, title: String,
        theme: String, introduction: String
    ) {
        //判断是否违规
        if ( content.length > 3000 ) {
            throw Exception("字数超出3000字")
        }
        if ( introduction.length > 64 || title.length > 32 ) {
            throw Exception("引言或标题过长")
        }
        val topicPost = TopicPost()
        val timestamp = Instant.now()
        topicPost.postId = authorId.substring(5, 10) + timestamp.toEpochMilli().toString()
        topicPost.authorId = authorId
        topicPost.content = content
        topicPost.title = title
        topicPost.introduction = introduction
        postRepository.insertTopicPost(topicPost)
    }

    //插入回复帖
    fun insertReplyPost(
        authorId: String, content: String, master: String
    ) {
        //判断是否违规
        if ( content.length > 3000 ) {
            throw Exception("字数超出3000字")
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
}