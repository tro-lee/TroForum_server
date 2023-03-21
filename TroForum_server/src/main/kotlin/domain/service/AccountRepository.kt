package com.troForum_server.domain.service

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.troForum_server.domain.entity.account.Account
import com.troForum_server.domain.entity.account.AccountMapper
import org.springframework.stereotype.Repository

//账号的仓库类，提供基本操作服务
@Repository
class AccountRepository(val accountMapper: AccountMapper) {
    fun selectAccountByName(userName: String): Account? {
        return accountMapper.selectOne(
            QueryWrapper<Account>()
                .eq("user_name", userName)
                .eq("deleted", 0)
        )
    }

    fun selectAccountById(userId: String): Account? {
        return accountMapper.selectOne(
            QueryWrapper<Account>()
                .eq("user_id", userId)
                .eq("deleted", 0)
        )
    }

    fun insertAccount(account: Account): Int {
        return accountMapper.insert(account)
    }

    fun checkingUserName(userName: String): Boolean {
        return accountMapper.exists(
            QueryWrapper<Account>()
                .eq("user_name", userName)
                .eq("deleted", 0)
        )
    }

    fun updateBaseAccount(userId: String, account: Account): Int {
        return accountMapper.update(
            account,
            QueryWrapper<Account>()
                .eq("user_id", userId)
                .eq("deleted", 0)
        )
    }
}