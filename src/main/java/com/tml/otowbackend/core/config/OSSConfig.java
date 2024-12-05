package com.tml.otowbackend.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @Description
 * @Author welsir
 * @Date 2024/12/3 22:48
 */
@Configuration
@Data
@ConfigurationProperties("oss")
public class OSSConfig {

    private String stsEndPoint;
    private String bucket;
    private String accesskeyId;
    private String accesskeySecret;
    private String roleArn;
    private String endpoint;
    private Integer stsExpire;
    private Integer signedExpire;

}
