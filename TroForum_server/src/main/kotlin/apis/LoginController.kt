package com.troForum_server.apis

import cn.dev33.satoken.annotation.SaCheckLogin
import cn.dev33.satoken.util.SaResult
import com.troForum_server.application.login.LoginService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

//提供进出系统的接口
@RestController
class LoginController {
    @Autowired
    private lateinit var loginService: LoginService

    @GetMapping("/register")
    fun signIn() {
        loginService.register("123", "123")
    }

    @GetMapping("/login")
    fun loginIn(): SaResult? {
        return try {
            loginService.login("123", "111")
            SaResult.ok("登录成功")
        } catch (e: Exception) {
            SaResult.error("登录失败")
        }
    }

    @SaCheckLogin
    @GetMapping("/logout")
    fun loginOut(): String {
        loginService.logout()
        return "登出"
    }

    @GetMapping("/isLogin")
    fun isLogin(): String {
        return if (loginService.isLogin()) "在" else  "不在"
    }

}