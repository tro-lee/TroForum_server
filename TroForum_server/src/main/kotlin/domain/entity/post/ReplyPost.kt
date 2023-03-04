package com.troForum_server.domain.entity.post

import com.baomidou.mybatisplus.annotation.TableName
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Mapper
import java.time.LocalDateTime
import java.util.*

@TableName(value = "reply_post", autoResultMap = true)
class ReplyPost {
    var postId: String = ""
    var authorId: String = ""
    var content: String = ""
    var createdTime: LocalDateTime = LocalDateTime.now()
    var deleted: Boolean = false
    var likes: Int = 0
    var master: String = ""
    var masterUserId: String = ""
}

@Mapper
interface ReplyPostMapper: BaseMapper<ReplyPost>