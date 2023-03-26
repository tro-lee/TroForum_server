package com.troForum_server.domain.dao

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.troForum_server.domain.entity.realtion.UserRelation
import com.troForum_server.domain.entity.realtion.UserRelationMapper
import org.springframework.stereotype.Repository

@Repository
class RelationRepository(
    val userRelationMapper: UserRelationMapper
) {
    //查询是否存在该用户关系
    fun checkingUserRelation(relationId: String): Boolean {
        val userRelation = userRelationMapper.selectById(relationId)
        return userRelation != null
    }

    //获取用户关系
    fun getUserRelation(relationId: String): UserRelation {
        return userRelationMapper.selectById(relationId)
    }

    //获取关系列表
    fun getUserRelationList(userId: String): MutableList<UserRelation> {
        return userRelationMapper.selectList(
            QueryWrapper<UserRelation>()
                .eq("starter_id", userId)
                .or()
                .eq("receiver_id", userId)
        )
    }

    //插入用户关系
    fun insertUserRelation(userRelation: UserRelation) {
        try {
            userRelationMapper.insert(userRelation)
        } catch (e: Exception) {
            throw e
        }
    }

    //更新用户关系
    fun updateUserRelation(userRelation: UserRelation) {
        try {
            userRelationMapper.updateById(userRelation)
        } catch (e: Exception) {
            throw e
        }
    }
}