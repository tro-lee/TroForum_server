package com.troForum_server.domain.dao

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
        try {
            replyPostMapper.insert(replyPost)
        } catch (e: Exception) {
            throw e
        }
    }

    //获得主题页
    fun getTopicPostPage(page: Page<TopicPost>, keyword: String): Page<TopicPost> {
        return topicPostMapper.selectPage(
            page,
            QueryWrapper<TopicPost>()
                .like("title", keyword)
                .orderByDesc("likes")
                .orderByDesc("created_time")
                .eq("deleted", 0)
        )
    }

    //获取作者的主题贴
    fun getTopicPostPageByAuthor(page: Page<TopicPost>, authorId: String): Page<TopicPost> {
        return topicPostMapper.selectPage(
            page,
            QueryWrapper<TopicPost>()
                .eq("author_id", authorId)
                .orderByDesc("likes")
                .orderByDesc("created_time")
                .eq("deleted", 0)
        )
    }

    //获取回复组
    fun getReplyPostList(keyword: String): MutableList<ReplyPost> {
        return replyPostMapper.selectList(
            QueryWrapper<ReplyPost>()
                .eq("master", keyword)
                .eq("deleted", 0)
        )
    }

    //获得回复页
    fun getReplyPostPage(page: Page<ReplyPost>, keyword: String): Page<ReplyPost> {
        return replyPostMapper.selectPage(
            page,
            QueryWrapper<ReplyPost>()
                .eq("master", keyword)
                .orderByDesc("likes")
                .orderByDesc("created_time")
                .eq("deleted", 0)
        )
    }

    //获取主题贴
    fun getTopicPost(postId: String): TopicPost? {
        return topicPostMapper.selectOne(
                QueryWrapper<TopicPost>()
                    .eq("post_id", postId)
            )
    }
}