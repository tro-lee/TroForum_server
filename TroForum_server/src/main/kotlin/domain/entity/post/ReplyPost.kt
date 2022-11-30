package com.troForum_server.domain.entity.post

import com.baomidou.mybatisplus.annotation.TableName
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Mapper
import java.util.*

@TableName(value = "reply_post", autoResultMap = true)
class ReplyPostTable {
    var postId: String = ""
    var master: String = ""
}

@Mapper
interface ReplyPostMapper: BaseMapper<ReplyPostTable>