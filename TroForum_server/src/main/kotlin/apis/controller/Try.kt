package com.troForum_server.apis.controller

import com.troForum_server.domain.account.entity.Account
import com.troForum_server.domain.account.repository.AccountRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class Try {
    @Autowired
    private lateinit var accountRepository: AccountRepository

    @GetMapping("/hi")
    fun get(): Account? {
        return accountRepository.select("1")
    }
}