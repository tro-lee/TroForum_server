package com.troForum_server.apis

import cn.dev33.satoken.annotation.SaCheckLogin
import com.alibaba.fastjson.JSONObject
import com.troForum_server.application.account.AccountService
import com.troForum_server.domain.entity.account.Account
import com.troForum_server.infrastructure.common.result
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
@SaCheckLogin
class AccountController {
    @Autowired
    private lateinit var accountService: AccountService

    @PostMapping("/selectAccount")
    fun selectAccount() = result {
        val account = accountService.selectAccount()
        val json = JSONObject()
        json["userId"] = account!!.userId
        json["userName"] = account.userName
        return@result json
    }

    @PostMapping("/selectAccountById")
    fun selectAccountById(@RequestBody userId: String) = result {
        return@result accountService.selectAccountById(userId)
    }

    class UpdateUserNameReq {
        val userName: String = ""
    }

    @PostMapping("/updateUserName")
    fun updateUserName(@RequestBody req: UpdateUserNameReq) = result {
        return@result accountService.updateUserName(req.userName)
    }

    class UpdatePasswordReq {
        val password: String = ""
    }

    @PostMapping("/updatePassword")
    fun updatePassword(@RequestBody req: UpdatePasswordReq) = result {
        return@result accountService.updatePassword(req.password)
    }
}