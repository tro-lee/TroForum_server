package com.troForum_server.infrastructure.config

import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.*

@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig : WebSocketMessageBrokerConfigurer {
    //注册stomp端点
    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        //允许使用socketJs访问，访问节点为ws，允许跨域
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS()
        //设置一个端点，可以进行websocket通信连接
    }

    //配置消息代理
    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
        //订阅Broker名称，可以订阅的地址,queue为指定用户，topic为群发
        registry.enableSimpleBroker("/queue", "/topic", "/user")
        //全局使用的消息前缀（客户端订阅路径上会体现出来）
        registry.setApplicationDestinationPrefixes("/app")
        //点对点使用的前缀（客户端订阅路径上会体现出来）
        registry.setUserDestinationPrefix("/user")
    }
}