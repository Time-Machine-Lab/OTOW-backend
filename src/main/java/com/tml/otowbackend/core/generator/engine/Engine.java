package com.tml.otowbackend.core.generator.engine;


import com.tml.otowbackend.core.generator.template.OTOWTemplate;

public abstract class Engine<T extends OTOWTemplate, R> {

    public abstract R generate(T template);

}
