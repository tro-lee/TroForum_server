package com.troForum_server.infrastructure.config

import cn.dev33.satoken.interceptor.SaAnnotationInterceptor
import org.springframework.stereotype.Component
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Component
class SaTokenConfigure: WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(SaAnnotationInterceptor())
            .addPathPatterns("/**")
    }
}