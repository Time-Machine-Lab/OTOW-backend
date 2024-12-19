package com.tml.otowbackend.engine.generator.core;

public interface FunctionPack {
    void handleController(Object object);
    void handleService(Object object);
    void handleServiceImpl(Object object);
    void handleMapper(Object object);
    void handleApplicationConfig(Object object);
    void handlePomConfig(Object object);
}
