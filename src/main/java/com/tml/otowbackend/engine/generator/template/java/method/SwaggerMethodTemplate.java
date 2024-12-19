package com.tml.otowbackend.engine.generator.template.java.method;

import com.tml.otowbackend.engine.generator.template.java.MethodTemplate;
import com.tml.otowbackend.engine.generator.template.meta.MetaMethod;
import com.tml.otowbackend.pojo.DTO.SwaggerInfo;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.apache.velocity.VelocityContext;

public class SwaggerMethodTemplate extends MethodTemplate {

    private SwaggerInfo swaggerInfo;

    public SwaggerMethodTemplate(SwaggerInfo swaggerInfo) {
        super("swagger.method.java.vm");
        this.swaggerInfo = swaggerInfo;
    }

    @Override
    public MetaMethod generateMethod() {
        MetaMethod metaMethod = new MetaMethod("customOpenAPI", generateMethodBody());
        metaMethod.setReturnRes(OpenAPI.class);
        metaMethod.addImportClazz(Info.class);
        metaMethod.addImportClazz(Contact.class);
        metaMethod.addImportClazz(License.class);
        return metaMethod;
    }

    @Override
    public VelocityContext getContext() {
        VelocityContext context = new VelocityContext();
        context.put("title", swaggerInfo.getTitle());
        context.put("version", swaggerInfo.getVersion());
        context.put("description", swaggerInfo.getDescription());
        context.put("authorName", swaggerInfo.getAuthorName());
        context.put("authorEmail", swaggerInfo.getAuthorEmail());
        context.put("authorUrl", swaggerInfo.getAuthorUrl());
        return context;
    }
}
