package com.troForum_server.domain.entity.chat

import com.baomidou.mybatisplus.annotation.TableName
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Mapper
import java.time.LocalDateTime

@TableName(value = "private_chat", autoResultMap = true)
class PrivateChat {
    var chatId: String = ""
    var relationId: String = ""
    var authorId: String = ""
    var content: String = ""
    var createdTime: LocalDateTime = LocalDateTime.now()
    var deleted: Boolean = false
}

@Mapper
interface PrivateChatMapper: BaseMapper<PrivateChat>