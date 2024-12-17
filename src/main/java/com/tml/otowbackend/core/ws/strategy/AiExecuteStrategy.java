package com.tml.otowbackend.core.ws.strategy;

import com.tml.otowbackend.core.ws.WebSocketManager;
import com.tml.otowbackend.core.ws.WsMessage;
import com.tml.otowbackend.core.ws.chat.AiGenerate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component("ai")
public class AiExecuteStrategy implements ExecuteStrategy{
    @Resource
    private AiGenerate aiGenerate;

    @Resource
    private WebSocketManager webSocketManager;

    @Override
    public void execute(WsMessage wsMessage) {
        if (!webSocketManager.useAi(wsMessage.getKey())) {
            webSocketManager.error(wsMessage.getKey(), "目前正在回答问题中");
            return;
        }
        aiGenerate.streamGenerate(wsMessage.getKey(), wsMessage.getMessage(), wsMessage.getNum());
        webSocketManager.cooldown(wsMessage.getKey());
    }
}
