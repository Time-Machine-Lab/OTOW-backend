package com.tml.otowbackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.tml.otowbackend.mapper")
public class OtowBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(OtowBackendApplication.class, args);
    }

}
