package com.troForum_server.domain.entity.poem

import com.baomidou.mybatisplus.annotation.TableName
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Mapper
import java.time.LocalDateTime

@TableName(value = "poem", autoResultMap = true)
class Poem {
    var poemId: String = ""
    var authorId: String = ""
    var content: String = ""
    var createdTime: LocalDateTime = LocalDateTime.now()
    var deleted: Boolean = false
    var likes: Int = 0
    var title: String = ""
}

@Mapper
interface PoemMapper: BaseMapper<Poem>
