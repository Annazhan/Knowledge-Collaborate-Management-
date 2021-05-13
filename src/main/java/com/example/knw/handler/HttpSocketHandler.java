package com.example.knw.handler;

import com.example.knw.dao.MsgMapper;
import com.example.knw.pojo.Msg;
import com.example.knw.service.MsgService;
import com.example.knw.utils.JsonUtils;
import com.example.knw.websocket.WsSessionManager;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.ObjectTypeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 处理websocket事件类
 *
 * @author qanna
 * @date 2021-03-18
 */
@Component
@Slf4j
public class HttpSocketHandler extends AbstractWebSocketHandler {

    //就是userID,如果是系统消息则为0
    private String attributeName = "sessionID";
    private JsonUtils json = new JsonUtils();

    @Autowired
    MsgService msgService;

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        Object token = webSocketSession.getAttributes().get(attributeName);
        if(token != null){
            WsSessionManager.add(token.toString(), webSocketSession);
        }
        else{
            throw new RuntimeException("用户登录已经失效");
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        Msg msg = json.jsonString2Object(payload,null, Msg.class);
        msgService.sendTextToReceiver(msg);
    }


    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        Object sessionID = webSocketSession.getAttributes().get(attributeName);
        if(sessionID != null){
            WsSessionManager.remove(sessionID.toString());
        }
    }


}
