package com.troForum_server.apis

import cn.dev33.satoken.annotation.SaCheckLogin
import com.troForum_server.application.post.PostService
import com.troForum_server.domain.dao.PostRepository
import com.troForum_server.infrastructure.common.result
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/*
* 帖子的接口
 */
@RestController
@SaCheckLogin
@RequestMapping("/post")
class PostController {
    @Autowired
    private lateinit var postService: PostService

    @Autowired
    private lateinit var postRepository: PostRepository

    data class InsertTopicPostReq(
        val authorId: String,
        val content: String,
        val title: String,
        val theme: String,
    )

    @PostMapping("/insTopicPost")
    fun insertTopicPost(@RequestBody req: InsertTopicPostReq) {
        //判断是否违规
        if (req.content.length > 3000) {
            throw Exception("字数超出3000字")
        }
        if (req.title.length > 32) {
            throw Exception("标题过长")
        }
        postService.insertTopicPost(
            req.authorId, req.content, req.title, req.theme
        )
    }

    data class InsertReplyPostReq(
        val authorId: String,
        val content: String,
        val master: String,
        val masterUserId: String
    )

    @PostMapping("/insReplyPost")
    fun insertReplyPost(@RequestBody req: InsertReplyPostReq) {
        //判断是否违规
        if (req.content.length > 2000) {
            throw Exception("字数超出2000字")
        }
        postService.insertReplyPost(
            req.authorId, req.content, req.master, req.masterUserId
        )
    }

    data class GetTopicPostPageReq(
        val current: Long,
        val size: Long,
        val keyword: String
    )

    @PostMapping("/topicPostPage")
    fun getTopicPostPage(@RequestBody req: GetTopicPostPageReq) = result {
        return@result postService.getTopicPostPage(
            req.current, req.size, req.keyword
        )
    }

    data class GetTopicPostPageByAuthorReq(
        val current: Long,
        val size: Long,
        val authorId: String
    )

    @PostMapping("/topicPostPageByAuthor")
    fun getTopicPostPageByAuthor(@RequestBody req: GetTopicPostPageByAuthorReq) = result {
        return@result postService.getTopicPostPageByAuthor(
            req.current, req.size, req.authorId
        )
    }

    class GetTopicPostReq {
        val postId: String = ""
    }

    @PostMapping("/getTopicPost")
    fun getTopicPost(@RequestBody req: GetTopicPostReq) = result {
        return@result postService.getTopicPost(req.postId)
    }

    data class ReplyPostPageReq(
        val current: Long,
        val size: Long,
        val postId: String
    )

    @PostMapping("/replyPostPage")
    fun getReplyPost(@RequestBody req: ReplyPostPageReq) = result {
        return@result postService.getReplyPostPage(req.current, req.size, req.postId)
    }
}