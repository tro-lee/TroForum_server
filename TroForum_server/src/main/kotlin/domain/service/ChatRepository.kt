package com.troForum_server.domain.service

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.troForum_server.domain.entity.chat.PublicChat
import com.troForum_server.domain.entity.chat.PublicChatMapper
import org.springframework.stereotype.Repository

@Repository
class ChatRepository(
    val publicChatMapper: PublicChatMapper
) {
    //插入公共聊天
    fun insertPublicChat(publicChat: PublicChat) {
        try {
            publicChatMapper.insert(publicChat)
        } catch (e: Exception) {
            throw e
        }
    }

    //获取全部公共聊天
    fun getAllPublicChat(): MutableList<PublicChat> {
        return publicChatMapper.selectList(
            null
        )
    }

    //获取公共聊天页
    fun getPublicChatPage(page: Page<PublicChat>, keyword: String): Page<PublicChat> {
        return publicChatMapper.selectPage(
            page,
            QueryWrapper<PublicChat>()
                .like("content", keyword)
                .eq("deleted", 0)
                .orderByDesc("created_time")
        )
    }

}