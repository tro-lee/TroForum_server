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
        val userRelation = userRelationMapper.selectOne(
            QueryWrapper<UserRelation>()
                .eq("relation_id", relationId)
        )
        return userRelation != null
    }

    //获取用户关系
    fun getUserRelation(relationId: String): UserRelation {
        return userRelationMapper.selectOne(
            QueryWrapper<UserRelation>()
                .eq("relation_id", relationId)
        )
    }

    //获取关注他人列表
    fun getFollowerList(userId: String): MutableList<UserRelation> {
        return userRelationMapper.selectList(
            QueryWrapper<UserRelation>()
                .eq("type", 1)
                .eq("starter_id", userId)
        )
    }

    //获取被他人关注列表
    fun getFollowedList(userId: String): MutableList<UserRelation> {
        return userRelationMapper.selectList(
            QueryWrapper<UserRelation>()
                .eq("type", 1)
                .eq("follower_id", userId)
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
            userRelationMapper.update(
                userRelation,
                QueryWrapper<UserRelation>()
                    .eq("relation_id", userRelation.relationId)
            )
        } catch (e: Exception) {
            throw e
        }
    }
}