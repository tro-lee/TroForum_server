package com.troForum_server.application.login

import cn.dev33.satoken.secure.BCrypt
import cn.dev33.satoken.stp.StpUtil
import com.troForum_server.domain.entity.account.Account
import com.troForum_server.domain.service.AccountRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class LoginService {
    @Autowired
    private lateinit var accountRepository: AccountRepository

    fun register(userName: String, password: String) {
        val timestamp = Instant.now()
        var account = Account()
        account.userId = timestamp.toEpochMilli().toString() + (100..999).random().toString()
        account.userName = userName
        account.password = BCrypt.hashpw(password, BCrypt.gensalt())
        account.role = "user"
        account.deleted = 0
        accountRepository.insertAccount(account)
    }

    fun login(userName: String, password: String) {
        val user = accountRepository.selectName(userName)
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