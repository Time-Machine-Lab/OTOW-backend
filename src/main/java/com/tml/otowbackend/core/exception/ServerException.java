package com.tml.otowbackend.core.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 描述: 全局业务异常类
 *
 * @author suifeng
 * 日期: 2024/7/5
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ServerException extends RuntimeException {

    /**
     * 状态码
     */
    private int code = ResultCode.ERROR.getCode();

    /**
     * 异常消息
     */
    private String message = ResultCode.ERROR.getMessage();


    public ServerException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ServerException(String message) {
        this.message = message;
    }

    public ServerException(int code) {
        this.code = code;
    }

    public ServerException(ResultCode rCode) {
        this.code = rCode.getCode();
        this.message = rCode.getMessage();
    }

    public ServerException(ResultCode rCode, String addMessage) {
        this.code = rCode.getCode();
        this.message = rCode.getMessage() + addMessage;
    }

    public static ServerException of(int code, String msg) {
        return new ServerException(code, msg);
    }

    public static ServerException of(String msg) {
        return new ServerException(ResultCode.ERROR.getCode(), msg);
    }

    public static ServerException of(ServerCode serverCode) {
        return ServerException.of(Integer.parseInt(serverCode.getCode()), serverCode.getMsg());
    }

    public static ServerException of(ServerCode serverCode, String msg) {
        String newMsg = String.format(serverCode.getMsg(), msg);
        return ServerException.of(Integer.parseInt(serverCode.getCode()), newMsg);
    }

    public static ServerException SystemError(String msg) {
        return ServerException.of(ResultCode.ERROR.getCode(), msg);
    }
}
