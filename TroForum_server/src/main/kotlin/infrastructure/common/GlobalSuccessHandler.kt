package com.troForum_server.infrastructure.common

import cn.dev33.satoken.util.SaResult

inline fun <T> result(func: ()-> T) : SaResult? {
    return SaResult.data(func())
}