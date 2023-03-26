package com.troForum_server.application.account

import cn.dev33.satoken.secure.BCrypt
import cn.dev33.satoken.stp.StpUtil
import com.troForum_server.domain.entity.account.Account
import com.troForum_server.domain.dao.AccountRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AccountService {
    @Autowired
    private lateinit var accountRepository: AccountRepository

    //获取当前用户账号
    fun selectAccount(): Account? {
        return accountRepository.selectAccountById((StpUtil.getLoginId().toString()))
    }

    //用id查账号
    fun selectAccountById(userId: String): Account? {
        return accountRepository.selectAccountById(userId)
    }

    //更新用户名
    fun updateUserName(userName: String): Int {
        val account = selectAccountById(StpUtil.getLoginId().toString())?: throw Exception("账号不存在")
        if ( accountRepository.checkingUserName(userName) ) {
            throw Exception("用户名重复")
        }
        account.userName = userName
        return accountRepository.updateBaseAccount(StpUtil.getLoginId().toString(), account)
    }

    //更新密码
    fun updatePassword(password: String): Int {
        val account = selectAccountById(StpUtil.getLoginId().toString())?: throw Exception("账号不存在")
        account.password = BCrypt.hashpw(password, BCrypt.gensalt())
        return accountRepository.updateBaseAccount(StpUtil.getLoginId().toString(), account)
    }
}