package com.example.knw.config;

import com.example.knw.handler.HttpSocketHandler;
import com.example.knw.handler.MsgInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * websocket的相关配置
 *
 * @author qanna
 * @date 2021-04-21
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Autowired
    HttpSocketHandler handler;

    @Autowired
    MsgInterceptor interceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry
                .addHandler(handler, "msg")
                .addInterceptors(interceptor)
                .setAllowedOrigins("*");
    }
}
