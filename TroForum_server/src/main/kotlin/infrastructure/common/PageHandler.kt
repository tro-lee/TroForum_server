package com.troForum_server.infrastructure.common

import com.alibaba.fastjson.JSONObject
import com.baomidou.mybatisplus.extension.plugins.pagination.Page

fun <T> Page<T>.toJsonWrapper(): JSONObject {
    val res = JSONObject()
    res["offset"] = offset()
    res["page"] = current
    res["size"] = size
    res["page_num"] = pages
    res["total"] = total
    return res
}