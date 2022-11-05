package com.troForum_server.domain.account.entity

import com.baomidou.mybatisplus.annotation.TableName
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Mapper

@TableName(value = "account", autoResultMap = true)
class Account {
    var userId: String = ""
    var userName: String = ""
    var role: String = ""
    var password: String = ""
    var deleted: Int = 0
}

@Mapper
interface AccountMapper: BaseMapper<Account>
