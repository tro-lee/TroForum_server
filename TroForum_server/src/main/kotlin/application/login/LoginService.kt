package com.troForum_server.application.login

import cn.dev33.satoken.secure.BCrypt
import cn.dev33.satoken.stp.StpUtil
import com.troForum_server.domain.entity.account.Account
import com.troForum_server.domain.dao.AccountRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class LoginService {
    @Autowired
    private lateinit var accountRepository: AccountRepository

    fun register(userName: String, password: String) {
        val timestamp = Instant.now()
        val account = Account()
        account.userId = timestamp.toEpochMilli().toString() + (100..999).random().toString()
        account.userName = userName
        if ( accountRepository.checkingUserName(userName) ) {
            throw Exception("用户名重复")
        }
        if ( password.isEmpty() ) {
            throw Exception("密码不能为空")
        }
        account.password = BCrypt.hashpw(password, BCrypt.gensalt())
        account.role = "user"
        account.deleted = 0
        try {
            accountRepository.insertAccount(account)
        } catch (e: Exception) {
            throw Exception("注册失败")
        }
    }

    fun login(userName: String, password: String) {
        val user = accountRepository.selectAccountByName(userName)
            ?: throw Exception("未查找到该用户")
        if (BCrypt.checkpw(password, user.password)) {
            StpUtil.login(user.userId)
            //录入user的缓存
            StpUtil.getSession().set("user", user)
        } else {
            throw Exception("密码错误")
        }
    }

    fun logout() {
        StpUtil.logout()
    }

    fun isLogin(): Boolean {
        return StpUtil.isLogin()
    }
}