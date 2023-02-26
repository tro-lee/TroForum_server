package com.troForum_server.infrastructure.common

import cn.dev33.satoken.exception.NotLoginException
import cn.dev33.satoken.util.SaResult
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.lang.Exception

//全局异常拦截
@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler
    fun handlerException(exception: Exception): SaResult? {
        exception.printStackTrace()
        if ( exception is NotLoginException ) {
            return SaResult.code(501)
        }
        if ( exception.message === "返回主页" ) {
            return SaResult.code(502)
        }
        return SaResult.error(exception.message)
    }
}