package com.tml.otowbackend.core.ws;

import java.io.IOException;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tml.otowbackend.core.ws.strategy.ExecuteStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@Slf4j
@Component
@ServerEndpoint(value = "/ws/test")
public class EchoChannel implements ApplicationContextAware {
    private Session session;

    private static WebSocketManager webSocketManager;

    private static ExecuteProcessor executeProcessor;

    private static final String BYE = "bye";

    // 收到消息
    @OnMessage
    public void onMessage(String message) throws IOException{
        WsMessage wsMessage = parseMessage(message);
        if (BYE.equals(wsMessage.getType())) {
            this.session.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "Closed"));;
            return;
        }
        ExecuteStrategy strategy = executeProcessor.getStrategy(wsMessage.getType());
        if(strategy == null){
            webSocketManager.error(wsMessage.getKey(), "消息类型错误");
            return;
        }
        strategy.execute(wsMessage);
    }

    // 连接打开
    @OnOpen
    public void onOpen(Session session, EndpointConfig endpointConfig){
        webSocketManager.add(session.getId(), session);
        this.session = session;
    }

    // 连接关闭
    @OnClose
    public void onClose(CloseReason closeReason){
        webSocketManager.remove(session.getId());
    }

    // 连接异常
    @OnError
    public void onError(Throwable throwable) throws IOException {
        log.error("[websocket] 连接异常：id={}，throwable={}", this.session.getId(), throwable.getMessage());
        // 关闭连接。状态码为 UNEXPECTED_CONDITION（意料之外的异常）
        this.session.close(new CloseReason(CloseReason.CloseCodes.UNEXPECTED_CONDITION, throwable.getMessage()));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        EchoChannel.webSocketManager = applicationContext.getBean(WebSocketManager.class);
        EchoChannel.executeProcessor = applicationContext.getBean(ExecuteProcessor.class);
    }

    private WsMessage parseMessage(String message){
        WsMessage wsMessage = JSONObject.parseObject(message, WsMessage.class);
        if(wsMessage.getNum() == null) wsMessage.setNum(100);
        wsMessage.setKey(session.getId());
        return wsMessage;
    }
}