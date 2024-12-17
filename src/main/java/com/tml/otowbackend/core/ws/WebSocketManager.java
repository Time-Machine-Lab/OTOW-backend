package com.tml.otowbackend.core.ws;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.websocket.Session;
import java.io.IOException;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class WebSocketManager {
    private final ConcurrentHashMap<String, WebSession> sessions = new ConcurrentHashMap<>();

    public void add(String key, Session session){
        sessions.put(key, new WebSession(session));
    }

    public void remove(String key){
        sessions.remove(key);
    }

    public void sendMessage(String key, Object o){
        Session session = sessions.get(key).getSession();
        if (session != null){
            session.getAsyncRemote().sendObject(o);
        }
    }

    public void sendStringMessage(String key, String message){
        Session session = sessions.get(key).getSession();
        if (session != null){
            session.getAsyncRemote().sendText(JSONObject.toJSONString(
                    WsResult.builder()
                            .status(200)
                            .content(message)
                            .build()
            ));
        }
    }

    public void error(String key, String message){
        Session session = sessions.get(key).getSession();
        if (session != null){
            session.getAsyncRemote().sendText(JSONObject.toJSONString(
                    WsResult.builder()
                            .status(500)
                            .content(message)
                            .build()
            ));
        }
    }

    public Boolean cooldown(String key){
        return sessions.get(key).status.compareAndSet(true, false);
    }

    public Boolean useAi(String key){
        return sessions.get(key).status.compareAndSet(false, true);
    }

    @Data
    static class WebSession{
        private Session session;

        private AtomicBoolean status;

        public WebSession(Session session) {
            this.session = session;
            this.status = new AtomicBoolean(false);
        }
    }
}
