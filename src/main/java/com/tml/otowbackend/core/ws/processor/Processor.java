package com.tml.otowbackend.core.ws.processor;

public interface Processor<T>{
    void process(String key, T message);
}
