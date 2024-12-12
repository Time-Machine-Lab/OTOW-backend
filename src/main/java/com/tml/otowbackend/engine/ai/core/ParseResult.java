package com.tml.otowbackend.engine.ai.core;

import lombok.Data;

/**
 * 描述: 通用结果包装类
 * @author suifeng
 * 日期: 2024/12/12
 */
@Data
public class ParseResult<T> {

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 解析成功时的数据
     */
    private T data;

    /**
     * 错误信息
     */
    private String errorMessage;

    /**
     * 静态方法：成功结果
     */
    public static <T> ParseResult<T> success(T data) {
        ParseResult<T> result = new ParseResult<>();
        result.setSuccess(true);
        result.setData(data);
        return result;
    }

    /**
     * 静态方法：失败结果
     */
    public static <T> ParseResult<T> failure(String errorMessage) {
        ParseResult<T> result = new ParseResult<>();
        result.setSuccess(false);
        result.setErrorMessage(errorMessage);
        return result;
    }
}