package com.example.knw.websocket;

import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 实现session池
 *
 * @author qanna
 * @date 2021-03-18
 */
public class WsSessionManager {
    //所有的session
    private static ConcurrentHashMap<String, WebSocketSession> SESSION_POOL = new ConcurrentHashMap<>();

    /**
     * 添加Session
     * @param key
     * @param session
     */
    public static void add(String key, WebSocketSession session){
        SESSION_POOL.put(key, session);
    }

    public static WebSocketSession remove(String key){
        return SESSION_POOL.remove(key);
    }

    public static void removeAndClose(String key){
        WebSocketSession socketSession = remove(key);
        if(socketSession != null){
            try{
                socketSession.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public static WebSocketSession get(String key){
        return SESSION_POOL.get(key);
    }

    public static boolean isInSession(String key){
        return SESSION_POOL.contains(key);
    }
}
