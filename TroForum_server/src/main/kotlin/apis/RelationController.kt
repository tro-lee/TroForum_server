package com.troForum_server.apis

import cn.dev33.satoken.annotation.SaCheckLogin
import cn.dev33.satoken.stp.StpUtil
import com.troForum_server.application.account.AccountService
import com.troForum_server.application.relation.RelationService
import com.troForum_server.infrastructure.common.result
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

/*
* 用户关系的接口
 */
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
        return@result relationService.checkRelation(StpUtil.getLoginId().toString(), req.followerId)
    }

    //关注
    @PostMapping("/follow")
    fun follow(@RequestBody req: FollowerIdReq) = result {
        return@result relationService.addFriend(StpUtil.getLoginId().toString(), req.followerId)
    }

    //取消关注
    @PostMapping("/deleteFollower")
    fun deleteFollower(@RequestBody req: FollowerIdReq) = result {
        return@result relationService.deleteFollower(StpUtil.getLoginId().toString(), req.followerId)
    }

    //获取关注列表
    @PostMapping("/getFollowerList")
    fun getFollowerList() = result {
        return@result relationService.getFollowerList(StpUtil.getLoginId().toString())
    }

    //获取被关注列表
    @PostMapping("/getFollowed")
    fun getFollowed() = result {
        return@result relationService.getFollowed(StpUtil.getLoginId().toString())
    }
}