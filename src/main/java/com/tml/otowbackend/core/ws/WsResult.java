package com.tml.otowbackend.core.ws;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class WsResult {
    private Integer status;

    private String content;
}
