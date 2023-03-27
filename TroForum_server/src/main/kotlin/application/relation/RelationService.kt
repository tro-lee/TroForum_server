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
    *   1.处理好友关系，分为申请好友、同意好友、删除好友
    *   2.获取好友列表、获取好友请求列表
    */

    //type -1:没建立关系 0:申请好友 1:好友 3:删除好友 4:被申请好友
    @Autowired
    private lateinit var relationRepository: RelationRepository

    //加工关系id
    fun processRelationId(userId: String, friendId: String): String {
        return listOf(userId.substring(8), friendId.substring(8)).sorted().joinToString("1")
    }

    //查询关系 如果没建立关系或者已经删除返回-1
    fun checkRelation(userId: String, friendId: String): Int {
        val relationId = processRelationId(userId, friendId)
        //判断是否已经是好友
        if (relationRepository.checkingUserRelation(relationId)) {
            val relation = relationRepository.getUserRelation(relationId)
            //判断是否被申请好友与申请好友
            if (relation.type == 0) {
                return if (relation.starterId == userId) 0
                else 4
            }
            //判断是否已经删除
            if (relation.type == 3) return -1
            return relation.type
        }
        return -1
    }

    //添加好友 如果以前有联系记录则恢复申请
    fun addFriend(userId: String, friendId: String): Boolean {
        val relationId = processRelationId(userId, friendId)
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
        userRelation.type = 0
        try {
            relationRepository.insertUserRelation(userRelation)
        } catch (e: Exception) {
            return false
        }
        return true
    }

    //同意好友
    fun agreeFriend(userId: String, friendId: String): Boolean {
        val relation = relationRepository.getUserRelation(processRelationId(userId, friendId))
        relation.type = 1
        try {
            relationRepository.updateUserRelation(relation)
        } catch (e: Exception) {
            return false
        }
        return true
    }

    //删除好友
    fun deleteFriend(userId: String, friendId: String): Boolean {
        val relation = relationRepository.getUserRelation(processRelationId(userId, friendId))
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