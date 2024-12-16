package com.tml.otowbackend.engine.generator.engine;


import com.tml.otowbackend.engine.generator.template.OTOWTemplate;

public abstract class Engine<T extends OTOWTemplate, R> {

    public abstract R generate(T template);

}
