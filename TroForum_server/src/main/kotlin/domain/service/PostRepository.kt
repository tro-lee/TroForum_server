package com.troForum_server.domain.service

import com.troForum_server.domain.entity.post.*
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*

//直接使用的类，在仓库类处分开
class TopicPost : Post() {
    var title: String = ""
    var theme: String = ""
    var introduction: String = ""
    var clickRate: Int = 0
}

class ReplyPost : Post() {
    var master: String = ""
}

//帖子的仓库类，提供处理帖子数据的服务
@Repository
class PostRepository(
    val postMapper: PostMapper,
    val replyPostMapper: ReplyPostMapper,
    val topicPostMapper: TopicPostMapper,
) {
    //插入主题帖子
    fun insertTopicPost(topicPost: TopicPost) {
        val topicPostTable = TopicPostTable()
        topicPostTable.postId = topicPost.postId
        topicPostTable.title = topicPost.title
        topicPostTable.theme = topicPost.theme
        topicPostTable.introduction = topicPost.introduction
        topicPostTable.clickRate = topicPost.clickRate
        try {
            topicPostMapper.insert(topicPostTable)
        } catch (e: Exception) {
            throw e
        }
        val post: Post = topicPost
        try {
            postMapper.insert(post)
        } catch (e: Exception) {
            throw e
        }
    }

    //插入回复贴
    fun insertReplyPost(replyPost: ReplyPost) {
        val replyPostTable = ReplyPostTable()
        replyPostTable.postId = replyPost.postId
        replyPostTable.master = replyPost.master
        replyPostMapper.insert(replyPostTable)

        val post: Post = replyPost
        postMapper.insert(post)
    }

}