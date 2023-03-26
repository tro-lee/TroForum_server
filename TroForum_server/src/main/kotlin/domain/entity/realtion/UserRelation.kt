package com.troForum_server.domain.entity.realtion

import com.baomidou.mybatisplus.annotation.TableName
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Mapper
import java.time.LocalDateTime

@TableName(value = "user_relation", autoResultMap = true)
class UserRelation {
    var relationId: String = ""
    var starterId: String = ""
    var receiverId: String = ""
    var type: Int = 1
    var createdTime: LocalDateTime = LocalDateTime.now()
}

@Mapper
interface UserRelationMapper: BaseMapper<UserRelation>