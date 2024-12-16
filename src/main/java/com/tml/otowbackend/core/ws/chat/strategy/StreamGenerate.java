package com.tml.otowbackend.core.ws.chat.strategy;

public interface StreamGenerate {
    void generate(String key, String text, String apiKey, Integer num);
}
