package com.troForum_server.domain.entity.post

import com.baomidou.mybatisplus.annotation.TableName
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Mapper
import java.time.LocalDateTime

@TableName(value = "topic_post", autoResultMap = true)
class TopicPost {
    var postId: String = ""
    var authorId: String = ""
    var content: String = ""
    var createdTime: LocalDateTime = LocalDateTime.now()
    var deleted: Boolean = false
    var likes: Int = 0
    var title: String = ""
    var theme: String = ""
    var introduction: String = ""
    var clickRate: Int = 0
}

@Mapper
interface TopicPostMapper: BaseMapper<TopicPost>