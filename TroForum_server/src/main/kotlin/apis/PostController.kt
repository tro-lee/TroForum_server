package com.troForum_server.apis
import cn.dev33.satoken.annotation.SaCheckLogin
import com.troForum_server.application.post.PostService
import com.troForum_server.domain.entity.post.TopicPost
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@SaCheckLogin
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
            req.authorId, req.content, req.title, req.theme, req.introduction
        )
    }

    data class InsertReplyPostReq(
        val authorId: String,
        val content: String,
        val master: String
    )

    @PostMapping("/replyPost")
    fun insertReplyPost(@RequestBody req: InsertReplyPostReq) {
        postService.insertReplyPost(
            req.authorId, req.content, req.master
        )
    }

    data class GetTopicPostPageReq(
        val current: Long,
        val size: Long,
        val keyword: String
    )

    @PostMapping("/topicPostPage")
    fun getTopicPostPage(@RequestBody req: GetTopicPostPageReq): MutableList<TopicPost> {
        return postService.getTopicPostPage(
            req.current, req.size, req.keyword
        )
    }
}