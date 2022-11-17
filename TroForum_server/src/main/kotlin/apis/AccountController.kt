package com.troForum_server.apis

import cn.dev33.satoken.annotation.SaCheckLogin
import com.alibaba.fastjson.JSONObject
import com.troForum_server.application.account.AccountService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@SaCheckLogin
class AccountController {
    @Autowired
    private lateinit var accountService: AccountService

    @PostMapping("/selectAccount")
    fun selectAccount(): JSONObject {
        val account = accountService.selectAccount()
        val json = JSONObject()
        json["userId"] = account!!.userId
        json["userName"] = account.userName
        return json
    }
}