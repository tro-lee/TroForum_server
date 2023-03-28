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
    //只使用followerId完成业务

    @Autowired
    private lateinit var accountService: AccountService
    @Autowired
    private lateinit var relationService: RelationService
    class FollowerIdReq {
        val followerId: String = ""
    }

    //检查关系
    @PostMapping("/checkRelation")
    fun checkRelation(@RequestBody req: FollowerIdReq) = result {
        return@result relationService.checkRelation(accountService.selectAccount()!!.userId, req.followerId)
    }

    //关注
    @PostMapping("/follow")
    fun follow(@RequestBody req: FollowerIdReq) = result {
        return@result relationService.addFriend(accountService.selectAccount()!!.userId, req.followerId)
    }

    //取消关注
    @PostMapping("/deleteFollower")
    fun deleteFollower(@RequestBody req: FollowerIdReq) = result {
        return@result relationService.deleteFollower(accountService.selectAccount()!!.userId, req.followerId)
    }

    //获取关注列表
    @PostMapping("/getFollowerList")
    fun getFollowerList() = result {
        return@result relationService.getFollowerList(accountService.selectAccount()!!.userId)
    }

    //获取被关注列表
    @PostMapping("/getFollowed")
    fun getFollowed() = result {
        return@result relationService.getFollowed(accountService.selectAccount()!!.userId)
    }
}