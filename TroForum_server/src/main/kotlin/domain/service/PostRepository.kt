package com.troForum_server.domain.service

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.troForum_server.domain.entity.post.*
import org.springframework.stereotype.Repository

//帖子的仓库类，提供处理帖子数据的服务
@Repository
class PostRepository(
    val replyPostMapper: ReplyPostMapper,
    val topicPostMapper: TopicPostMapper,
) {
    //插入主题帖子
    fun insertTopicPost(topicPost: TopicPost) {
        try {
            topicPostMapper.insert(topicPost)
        } catch (e: Exception) {
            throw e
        }
    }

    //插入回复贴
    fun insertReplyPost(replyPost: ReplyPost) {
        replyPostMapper.insert(replyPost)
    }

    //获得主题帖
    fun getTopicPostPage(page: Page<TopicPost>, keyword: String): Page<TopicPost> {
        return topicPostMapper.selectPage(
            page,
            QueryWrapper<TopicPost>()
                .like("title", keyword)
                .or()
                .like("introduction", keyword)
                .orderByDesc("likes")
                .orderByAsc("created_time")
        )
    }

}