package com.troForum_server.application.account

import cn.dev33.satoken.stp.StpUtil
import com.troForum_server.domain.entity.account.Account
import com.troForum_server.domain.service.AccountRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AccountService {
    @Autowired
    private lateinit var accountRepository: AccountRepository

    fun selectAccount(): Account? {
        return accountRepository.selectAccountById((StpUtil.getLoginId().toString()))
    }

    fun selectAccountById(userId: String): Account? {
        return accountRepository.selectAccountById(userId)
    }
}