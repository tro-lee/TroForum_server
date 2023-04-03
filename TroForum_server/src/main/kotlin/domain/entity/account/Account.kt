package com.troForum_server.domain.entity.account

import com.baomidou.mybatisplus.annotation.TableName
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Mapper

//账号实体
@TableName(value = "account", autoResultMap = true)
class Account {
    var userId: String = ""
    var userName: String = ""
    var role: String = ""
    var password: String = ""
    var deleted: Int = 0
    var avatarUrl: String = ""
    var description: String = ""
}

@Mapper
interface AccountMapper: BaseMapper<Account>
