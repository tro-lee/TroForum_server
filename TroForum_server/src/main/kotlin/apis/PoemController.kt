package com.troForum_server.apis

import cn.dev33.satoken.annotation.SaCheckLogin
import com.troForum_server.application.poem.PoemService
import com.troForum_server.infrastructure.common.result
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@SaCheckLogin
@RequestMapping("/poem")
class PoemController {
    @Autowired
    private lateinit var poemService: PoemService

    data class InsertPoemReq(
        val authorId: String,
        val title: String,
        val content: String,
    )

    @PostMapping("/insPoem")
    fun insertPoem(@RequestBody req: InsertPoemReq) {
        //判断是否违规
        if (req.content.length > 500) {
            throw Exception("字数超出500字")
        }
        if (req.title.length > 32) {
            throw Exception("标题过长")
        }
        poemService.insertPoem(
            req.authorId, req.title, req.content
        )
    }

    data class GetPoemPageReq(
        val page: Long,
        val size: Long,
        val keyword: String
    )

    @PostMapping("/getPoemPage")
    fun getPoemPage(@RequestBody req: GetPoemPageReq) = result {
        return@result poemService.getPoemPage(req.page, req.size, req.keyword)
    }

    data class GetPoemPageByAuthorReq(
        val page: Long,
        val size: Long,
        val authorId: String
    )

    @PostMapping("/getPoemPageByAuthor")
    fun getPoemPageByAuthor(@RequestBody req: GetPoemPageByAuthorReq) = result {
        return@result poemService.getPoemPageByAuthorId(req.page, req.size, req.authorId)
    }
}
