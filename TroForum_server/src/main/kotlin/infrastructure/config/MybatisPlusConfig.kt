package com.troForum_server.infrastructure.config

import com.baomidou.mybatisplus.annotation.DbType
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MybatisPlusConfig {
    @Bean
    fun paginationInterceptor(): MybatisPlusInterceptor {
        val interceptor = MybatisPlusInterceptor()
        val paginationInnerInterceptor = PaginationInnerInterceptor(DbType.MYSQL)
        interceptor.addInnerInterceptor(paginationInnerInterceptor)
        return interceptor
    }
}