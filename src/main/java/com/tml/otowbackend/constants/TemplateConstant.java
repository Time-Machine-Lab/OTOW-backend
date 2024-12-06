package com.tml.otowbackend.constants;

import com.tml.otowbackend.core.generator.template.meta.MetaAnnotation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

public class TemplateConstant {

    public static final List<Class<?>> ENTITY_ANNOTATION = List.of(
            AllArgsConstructor.class,
            NoArgsConstructor.class,
            Data.class
    );

    public static final List<Class<?>> REQ_ANNOTATION = List.of(
            AllArgsConstructor.class,
            NoArgsConstructor.class,
            Data.class,
            Validated.class
    );

    public static final List<Class<?>> CONTROLLER_ANNOTATION = List.of(
            RestController.class,
            Validated.class
    );

    public static final MetaAnnotation REQUEST_BODY = new MetaAnnotation(RequestBody.class);

    public static final MetaAnnotation SPRING_BOOT_APPLICATION = new MetaAnnotation(SpringBootApplication.class);

    // 模板文件路径
    public static final String CLASS_TEMPLATE_PATH = "class.java.vm";

    public static final String INTERFACE_TEMPLATE_PATH = "interface.java.vm";
}
