package com.tml.otowbackend.engine.tree.common;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ServeException extends RuntimeException {

    /**
     * 状态码
     */
    private String code = RCode.ERROR.getCode();

    /**
     * 异常消息
     */
    private String message = RCode.ERROR.getMessage();


    public ServeException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ServeException(String message) {
        this.message = message;
    }

    public ServeException(RCode rCode) {
        this.code = rCode.getCode();
        this.message = rCode.getMessage();
    }

    public ServeException(RCode rCode, String addMessage) {
        this.code = rCode.getCode();
        this.message = rCode.getMessage() + addMessage;
    }

    public static ServeException of(RCode rCode) {
        return new ServeException(rCode.getCode(), rCode.getMessage());
    }

    public static ServeException of(RCode rCode, String addMessage) {
        return new ServeException(rCode.getCode(), rCode.getMessage() + addMessage);
    }

    public static ServeException of(String code, String msg) {
        return new ServeException(code, msg);
    }

    public static ServeException of(String msg) {
        return new ServeException(RCode.ERROR.getCode(), msg);
    }
}