package com.troForum_server.apis

import cn.dev33.satoken.annotation.SaCheckLogin
import com.alibaba.fastjson.JSONObject
import com.troForum_server.application.account.AccountService
import com.troForum_server.infrastructure.common.result
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

/*
* 用户信息的接口
 */
@RestController
@SaCheckLogin
class AccountController {
    @Autowired
    private lateinit var accountService: AccountService

    //查询用户信息
    @PostMapping("/selectAccount")
    fun selectAccount() = result {
        return@result accountService.selectAccount()
    }

    //查询用户信息
    class SelectAccountByIdReq {
        val userId: String = ""
    }
    @PostMapping("/selectAccountById")
    fun selectAccountById(@RequestBody req: SelectAccountByIdReq) = result {
        return@result accountService.selectAccountById(req.userId)
    }

    //获取用户页
    class GetAccountPageReq {
        val current: Long = 1
        val size: Long = 10
        val keyword: String = ""
    }

    @PostMapping("/getAccountPage")
    fun getAccountPage(@RequestBody req: GetAccountPageReq) = result {
        return@result accountService.getAccountPage(req.current, req.size, req.keyword)
    }

    //获取搜索用户
    class GetSearchAccountReq {
        val keyword: String = ""
    }
    @PostMapping("/getSearchAccount")
    fun getSearchAccount(@RequestBody req: GetSearchAccountReq) = result {
        return@result accountService.getSearchAccount(req.keyword)
    }

    //更新用户名
    class UpdateUserNameReq {
        val userName: String = ""
    }
    @PostMapping("/updateUserName")
    fun updateUserName(@RequestBody req: UpdateUserNameReq) = result {
        if ( req.userName.length > 16 ) {
            throw Exception("用户名过长")
        }
        return@result accountService.updateUserName(req.userName)
    }

    //更新密码
    class UpdatePasswordReq {
        val password: String = ""
    }
    @PostMapping("/updatePassword")
    fun updatePassword(@RequestBody req: UpdatePasswordReq) = result {
        return@result accountService.updatePassword(req.password)
    }

    //更新头像
    @PostMapping("/updateAvatar")
    fun updateAvatar(@RequestParam("avatar") avatar: MultipartFile) = result {
        return@result accountService.updateAvatar(avatar)
    }

    //更新描述
    class UpdateDescriptionReq {
        val description: String = ""
    }
    @PostMapping("/updateDescription")
    fun updateDescription(@RequestBody req: UpdateDescriptionReq) = result {
        return@result accountService.updateDescription(req.description)
    }
}