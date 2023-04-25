package com.troForum_server.apis

import cn.dev33.satoken.annotation.SaCheckLogin
import com.troForum_server.application.ocr.OcrService
import com.troForum_server.infrastructure.common.result
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
@SaCheckLogin
class OcrController {
    @Autowired
    private lateinit var ocrService: OcrService

    class ocrReq {
        var img: String = ""
    }

    @PostMapping("/ocr")
    fun test(@RequestBody req: ocrReq) = result {
        return@result ocrService.ocr(req.img)
    }
}
