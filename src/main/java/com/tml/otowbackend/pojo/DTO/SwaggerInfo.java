package com.tml.otowbackend.pojo.DTO;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SwaggerInfo {
    private String title;
    private String version;
    private String description;
    private String authorName;
    private String authorEmail;
    private String authorUrl;
}