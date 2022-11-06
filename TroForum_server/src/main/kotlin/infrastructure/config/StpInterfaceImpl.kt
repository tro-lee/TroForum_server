package com.troForum_server.infrastructure.config

import cn.dev33.satoken.stp.StpInterface
import org.springframework.stereotype.Component

//配置SaToken权限组
@Component
class StpInterfaceImpl : StpInterface{
    override fun getPermissionList(loginId: Any?, loginType: String?): MutableList<String> {
        TODO("Not yet implemented")
    }

    override fun getRoleList(loginId: Any?, loginType: String?): MutableList<String> {
        var list = mutableListOf<String>()
        list.add("user")
        list.add("admin")
        return list
    }

}