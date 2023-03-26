package com.troForum_server.apis

import cn.dev33.satoken.annotation.SaCheckLogin
import com.troForum_server.application.account.AccountService
import com.troForum_server.application.relation.RelationService
import com.troForum_server.infrastructure.common.result
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
@SaCheckLogin
class RelationController {
    //只使用friendId完成业务
    //好友关系表中的relationId为starterId和receiverId后八位拼接

    @Autowired
    private lateinit var accountService: AccountService
    @Autowired
    private lateinit var relationService: RelationService
    class FriendIdReq {
        val friendId: String = ""
    }

    //加好友
    @PostMapping("/addFriend")
    fun addFriend(@RequestBody req: FriendIdReq) = result {
        return@result relationService.addFriend(accountService.selectAccount()!!.userId, req.friendId)
    }

    //同意好友
    @PostMapping("/agreeFriend")
    fun agreeFriend(@RequestBody req: FriendIdReq) = result {
        return@result relationService.agreeFriend(accountService.selectAccount()!!.userId.substring(8) + req.friendId.substring(8))
    }

    //拉黑
    @PostMapping("/BlackList")
    fun addBlackList(@RequestBody req: FriendIdReq) = result {
        return@result relationService.addBlackList(accountService.selectAccount()!!.userId.substring(8) + req.friendId.substring(8))
    }

    //删除好友
    @PostMapping("/deleteFriend")
    fun deleteFriend(@RequestBody req: FriendIdReq) = result {
        return@result relationService.deleteFriend(accountService.selectAccount()!!.userId.substring(8) + req.friendId.substring(8))
    }

    //获取好友列表
    @PostMapping("/getFriendList")
    fun getFriendList() = result {
        return@result relationService.getFriendList(accountService.selectAccount()!!.userId)
    }

    //获取好友请求列表
    @PostMapping("/getFriendRequestList")
    fun getFriendRequestList() = result {
        return@result relationService.getFriendRequestList(accountService.selectAccount()!!.userId)
    }
}