package com.troForum_server.application.relation

import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import com.troForum_server.application.account.AccountService
import com.troForum_server.application.websocket.SystemMessage
import com.troForum_server.application.websocket.WebSocketService
import com.troForum_server.domain.dao.RelationRepository
import com.troForum_server.domain.entity.realtion.UserRelation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/*
* 关系服务层，通过拼接id来建立关系，0未建立关系，可以是取关，1是建立关系
 */
@Service
class RelationService {
    /*
    *   分为两大部分
    *   1.可以关注别人，和取消关注别人
    *   2.可以查看自己关注的人和关注自己的人
    *   3.只有更新表和查询表才能知道是否之前关注过，其他只有存在记录和没有记录关系
    */

    //type 0:没建立关系 1:已经关注了
    @Autowired
    private lateinit var relationRepository: RelationRepository

    @Autowired
    private lateinit var accountService: AccountService

    @Autowired
    private lateinit var webSocketService: WebSocketService

    //加工关系id
    fun processRelationId(userId: String, followerId: String): String {
        return userId.substring(8) + '-' + followerId.substring(8)
    }

    //查询关系
    fun checkRelation(userId: String, followerId: String): Int {
        val relationId = processRelationId(userId, followerId)
        //判断是否已经是好友
        if (relationRepository.checkingUserRelation(relationId)) {
            return relationRepository.getUserRelation(relationId).type
        }
        return 0
    }

    //添加关注 如果以前有联系记录则恢复申请
    fun addFriend(userId: String, followerId: String): Boolean {
        val relationId = processRelationId(userId, followerId)
        //判断是否已经是好友
        if (relationRepository.checkingUserRelation(relationId)) {
            //判断是否已经删除
            val relation = relationRepository.getUserRelation(relationId)
            //无论是否删除，都直接恢复为添加关注
            relation.type = 1
            try {
                relationRepository.updateUserRelation(relation)
            } catch (e: Exception) {
                return false
            }
            return true
        }
        //没有联系，则建立
        val userRelation = UserRelation()
        //取userId和friendId后八位作为relationId
        userRelation.relationId = relationId
        userRelation.starterId = userId
        userRelation.followerId = followerId
        userRelation.type = 1
        try {
            relationRepository.insertUserRelation(userRelation)
        } catch (e: Exception) {
            return false
        }

        //通知被关注者
        val systemMessage = SystemMessage()
        systemMessage.targetId = followerId
        systemMessage.content = "用户" + accountService.idToAccount(userId)!!.userName + "关注了您"
        webSocketService.sendSystem(systemMessage)

        return true
    }

    //删除好友
    fun deleteFollower(userId: String, followerId: String): Boolean {
        val relation = relationRepository.getUserRelation(processRelationId(userId, followerId))
        relation.type = 0
        try {
            relationRepository.updateUserRelation(relation)
        } catch (e: Exception) {
            return false
        }
        return true
    }

    //获取关注列表
    fun getFollowerList(userId: String): JSONObject {
        val userRelationList = relationRepository.getFollowerList(userId)
        val json = JSONObject()
        val jsonArray = JSONArray()
        for (userRelation in userRelationList) {
            val account = accountService.idToAccount(userRelation.followerId)
            val jsonObject = JSONObject()
            jsonObject["userId"] = account!!.userId
            jsonObject["userName"] = account.userName
            jsonObject["avatarUrl"] = account.avatarUrl
            jsonObject["relationId"] = userRelation.relationId
            jsonArray.add(jsonObject)
        }
        json["value"] = jsonArray
        json["length"] = jsonArray.size
        return json
    }

    //获取被关注列表
    fun getFollowed(userId: String): JSONObject {
        val userRelationList = relationRepository.getFollowedList(userId)
        val json = JSONObject()
        val jsonArray = JSONArray()
        for (userRelation in userRelationList) {
            val account = accountService.idToAccount(userRelation.starterId)
            val jsonObject = JSONObject()
            jsonObject["userId"] = account!!.userId
            jsonObject["userName"] = account.userName
            jsonObject["avatarUrl"] = account.avatarUrl
            jsonObject["relationId"] = userRelation.relationId
            jsonArray.add(jsonObject)
        }
        json["value"] = jsonArray
        json["length"] = jsonArray.size
        return json
    }
}
