package com.tml.otowbackend.core.generator.template.java.service;

import com.tml.otowbackend.core.generator.template.java.InterfaceTemplate;

public class ServiceTemplate extends InterfaceTemplate {

    public ServiceTemplate(String packagePath, String className) {
        super(packagePath, getServiceClassName(className));
    }

    public static String getServiceClassName(String className){
        return className+"Service";
    }


}
