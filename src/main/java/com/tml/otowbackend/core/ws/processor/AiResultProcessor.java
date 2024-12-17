package com.tml.otowbackend.core.ws.processor;

import com.tml.otowbackend.core.ws.WebSocketManager;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class AiResultProcessor implements Processor<String> {
    @Resource
    private WebSocketManager webSocketManager;
    @Override
    public void process(String key, String message) {
        if(message != null && !message.isBlank()){
            webSocketManager.sendStringMessage(key, message);
        }
    }
}
