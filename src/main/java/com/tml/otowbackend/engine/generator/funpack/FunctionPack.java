package com.tml.otowbackend.engine.generator.funpack;


public interface FunctionPack {

    Object generateController(Object object);

    Object generateService(Object object);

    Object generateServiceImpl(Object object);

    Object generateMapper(Object object);

    Object generateApplication(Object object);
    Object generateApplicationConfig(Object object);
    Object generatePomConfig(Object object);
}
