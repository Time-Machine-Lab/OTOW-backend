package com.tml.otowbackend.core.ws.chat.tongyi;

import lombok.Data;

@Data
public class TongYiRequestBody {
    String model;
    TongYiMessage[] messages;
    Boolean stream;

    public TongYiRequestBody(String model, TongYiMessage[] messages, Boolean stream) {
        this.model = model;
        this.messages = messages;
        this.stream = stream;
    }
}