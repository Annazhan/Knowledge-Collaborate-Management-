package com.example.knw.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

/**
 * 消息拦截器
 *
 * @author qanna
 * @date 2021-04-21
 */
@Component
@Slf4j
public class MsgInterceptor implements HandshakeInterceptor {
    private String attributeName = "sessionID";

    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        log.info("开始握手");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userID = authentication.getName();
        if(userID != null){
            map.put(attributeName, userID);
            log.info("握手成功");
            return true;
        }
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
        log.info("握手完成");
    }
}
