package com.troForum_server.domain.service

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.troForum_server.domain.entity.account.Account
import com.troForum_server.domain.entity.account.AccountMapper
import org.springframework.stereotype.Repository

//账号的仓库类，提供基本操作服务
@Repository
class AccountRepository(val accountMapper: AccountMapper) {
    fun selectName(userName: String): Account? {
        return accountMapper.selectOne(
            QueryWrapper<Account>().eq("user_name", userName)
        )
    }

    fun insertAccount(account: Account): Int {
        return accountMapper.insert(account)
    }
}