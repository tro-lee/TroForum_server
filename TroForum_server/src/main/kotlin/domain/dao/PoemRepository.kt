package com.troForum_server.domain.dao

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.troForum_server.domain.entity.poem.Poem
import com.troForum_server.domain.entity.poem.PoemMapper
import com.troForum_server.domain.entity.post.*
import org.springframework.stereotype.Repository

//帖子的仓库类，提供处理帖子数据的服务
@Repository
class PoemRepository(
    val poemMapper: PoemMapper,
) {
    //插入诗
    fun insertPoem(poem: Poem) {
        try {
            poemMapper.insert(poem)
        } catch (e: Exception) {
            throw e
        }
    }

    //获得诗页
    fun getPoemPage(page: Page<Poem>, keyword: String): Page<Poem> {
        return poemMapper.selectPage(
            page,
            QueryWrapper<Poem>()
                .like("title", keyword)
                .orderByDesc("likes")
                .orderByDesc("created_time")
                .eq("deleted", 0)
        )
    }

    //获取作者的诗页
    fun getPoemPageByAuthor(page: Page<Poem>, authorId: String): Page<Poem> {
        return poemMapper.selectPage(
            page,
            QueryWrapper<Poem>()
                .eq("author_id", authorId)
                .orderByDesc("likes")
                .orderByDesc("created_time")
                .eq("deleted", 0)
        )
    }

    //获取诗
    fun getPoem(postId: String): Poem? {
        return poemMapper.selectOne(
                QueryWrapper<Poem>()
                    .eq("poem_id", postId)
            )
    }
}
