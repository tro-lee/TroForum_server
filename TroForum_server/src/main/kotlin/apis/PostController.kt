package com.troForum_server.apis

import com.troForum_server.application.post.PostService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/post")
class PostController {
    @Autowired
    private lateinit var postService: PostService

    data class InsertTopicPostReq(
        val authorId: String,
        val content: String,
        val title: String,
        val theme: String,
        val introduction: String
    )

    @PostMapping("/topicPost")
    fun insertTopicPost(@RequestBody req: InsertTopicPostReq) {
        postService.insertTopicPost(
            req.authorId, req.content, req.title, req.theme, req.introduction)
    }

    data class InsertReplyPost(
        val authorId: String,
        val content: String,
        val master: String
    )

    @PostMapping("/replyPost")
    fun insertReplyPost(@RequestBody req: InsertReplyPost) {
        postService.insertReplyPost(
            req.authorId, req.content, req.master
        )
    }
}