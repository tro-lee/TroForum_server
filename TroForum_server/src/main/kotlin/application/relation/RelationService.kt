package com.troForum_server.application.relation

import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import com.troForum_server.domain.dao.RelationRepository
import com.troForum_server.domain.entity.realtion.UserRelation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RelationService {
    /*
    *   分为两大部分
    *   1.处理好友关系，分为申请好友、同意好友、拉黑、删除好友
    *   2.获取好友列表、获取好友请求列表
    */

    //type 0:申请好友 1:好友 2:黑名单 3:删除好友
    @Autowired
    private lateinit var relationRepository: RelationRepository

    //添加好友
    fun addFriend(userId: String, friendId: String): Boolean {
        val relationId = userId.substring(8) + friendId.substring(8)
        //判断是否已经是好友
        if (relationRepository.checkingUserRelation(relationId)) {
            //判断是否在黑名单或删除中
            val relation = relationRepository.getUserRelation(relationId)
            if (relation.type == 2 || relation.type == 3) {
                relation.type = 0
                try {
                    relationRepository.updateUserRelation(relation)
                } catch (e: Exception) {
                    return false
                }
                return true
            } else throw Exception("已经是好友")
        }

        val userRelation = UserRelation()
        //取userId和friendId后八位作为relationId
        userRelation.relationId = relationId
        userRelation.starterId = userId
        userRelation.receiverId = friendId
        userRelation.type = 1
        try {
            relationRepository.insertUserRelation(userRelation)
        } catch (e: Exception) {
            return false
        }
        return true
    }

    //同意好友
    fun agreeFriend(relationId: String): Boolean {
        val relation = relationRepository.getUserRelation(relationId)
        relation.type = 1
        try {
            relationRepository.updateUserRelation(relation)
        } catch (e: Exception) {
            return false
        }
        return true
    }

    //拉黑好友
    fun addBlackList(relationId: String): Boolean {
        val relation = relationRepository.getUserRelation(relationId)
        relation.type = 2
        try {
            relationRepository.updateUserRelation(relation)
        } catch (e: Exception) {
            return false
        }
        return true
    }

    //删除好友
    fun deleteFriend(relationId: String): Boolean {
        val relation = relationRepository.getUserRelation(relationId)
        relation.type = 3
        try {
            relationRepository.updateUserRelation(relation)
        } catch (e: Exception) {
            return false
        }
        return true
    }

    //获取好友列表
    fun getFriendList(userId: String): JSONArray {
        val jsonArray = JSONArray()
        val userRelationList = relationRepository.getUserRelationList(userId)
        for (userRelation in userRelationList) {
            //判断是否没加好友，或者是删除好友
            if (userRelation.type == 0 || userRelation.type == 3) continue

            val jsonObject = JSONObject.toJSON(userRelation) as JSONObject
            jsonArray.add(jsonObject)
        }
        return jsonArray
    }

    //获取好友请求列表
    fun getFriendRequestList(userId: String): JSONArray {
        val jsonArray = JSONArray()
        val userRelationList = relationRepository.getUserRelationList(userId)
        for (userRelation in userRelationList) {
            //判断是否是申请好友
            if (userRelation.type != 0) continue

            val jsonObject = JSONObject.toJSON(userRelation) as JSONObject
            jsonArray.add(jsonObject)
        }
        return jsonArray
    }
}