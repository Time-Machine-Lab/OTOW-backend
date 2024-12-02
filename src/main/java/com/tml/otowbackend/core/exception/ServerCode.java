package com.tml.otowbackend.core.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServerCode {
    
    private String code;
    
    private String msg;
    
    private String i18nMsg;
}
