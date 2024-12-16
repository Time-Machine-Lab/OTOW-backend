package com.tml.otowbackend.core.ws.chat.tongyi;

import lombok.Data;

@Data
public class TongYiMessage {
    String role;
    String content;

    public TongYiMessage(String role, String content) {
        this.role = role;
        this.content = content;
    }
}