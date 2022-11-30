package com.troForum_server.domain.entity.post

import com.baomidou.mybatisplus.annotation.TableName
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Mapper
import java.util.*

@TableName(value = "topic_post", autoResultMap = true)
class TopicPostTable {
    var postId: String = ""
    var title: String = ""
    var theme: String = ""
    var introduction: String = ""
    var clickRate: Int = 0
}

@Mapper
interface TopicPostMapper: BaseMapper<TopicPostTable>