package com.troForum_server.apis

import cn.dev33.satoken.annotation.SaCheckLogin
import com.troForum_server.application.login.LoginService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/*
* 登录注册的接口
 */
@RestController
class LoginController {
    @Autowired
    private lateinit var loginService: LoginService

    data class LoginReq(
        var userName: String,
        var password: String
    )

    @PostMapping("/register")
    fun signIn(@RequestBody req: LoginReq) {
        if ( req.password.isEmpty() ) {
            throw Exception("密码不能为空")
        }
        if ( req.userName.length > 16 ) {
            throw Exception("用户名过长")
        }
        loginService.register(req.userName, req.password)
    }

    @PostMapping("/login")
    fun loginIn(@RequestBody req: LoginReq) {
        loginService.login(req.userName, req.password)
    }

    @SaCheckLogin
    @PostMapping("/logout")
    fun loginOut(): String {
        loginService.logout()
        return "登出"
    }

    @PostMapping("/isLogin")
    fun isLogin(): String {
        return if (loginService.isLogin()) "在" else  "不在"
    }

}