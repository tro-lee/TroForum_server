package com.troForum_server.domain.dao

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.troForum_server.domain.entity.account.Account
import com.troForum_server.domain.entity.account.AccountMapper
import org.springframework.stereotype.Repository

/*
* 账号的管理类，提供最基本的账号操作
 */
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

    //获取用户页
    fun getAccountPage(page: Page<Account>, keyword: String): Page<Account> {
        return accountMapper.selectPage(
            page,
            QueryWrapper<Account>()
                .like("user_name", keyword)
                .orderByDesc("created_time")
                .eq("deleted", 0)
        )
    }

    //用于搜索用户
    fun getSearchAccount(keyword: String): MutableList<Account> {
        return accountMapper.selectList(
            QueryWrapper<Account>()
                .like("user_name", keyword)
                .eq("deleted", 0)
        )
    }
}