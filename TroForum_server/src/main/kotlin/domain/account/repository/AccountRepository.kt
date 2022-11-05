package com.troForum_server.domain.account.repository

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.troForum_server.domain.account.entity.Account
import com.troForum_server.domain.account.entity.AccountMapper
import org.springframework.stereotype.Repository

@Repository
class AccountRepository(val accountMapper: AccountMapper) {

    fun select(user_id: String): Account? {
        return accountMapper.selectOne(
            QueryWrapper<Account>().eq("user_id", user_id)
        )
    }
}