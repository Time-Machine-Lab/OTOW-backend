package com.tml.otowbackend.core.ws.strategy;

import com.tml.otowbackend.core.ws.WsMessage;

public interface ExecuteStrategy {
    void execute(WsMessage wsMessage);
}