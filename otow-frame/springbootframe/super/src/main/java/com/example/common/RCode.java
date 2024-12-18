package com.example.common;

import lombok.Getter;

@Getter
public enum RCode {

    // 成功响应
    SUCCESS("200", "成功"),

    /**
     * 失败返回码
     */
    ERROR("400", "服务器繁忙，请稍后重试"),

    NUMBER_OUT("402", "数量上限超出"),

    /**
     * 参数异常
     */
    PARAMS_ERROR("20001", "参数异常或者格式错误");

    /**
     * 自定义状态码
     **/
    private final String code;

    /**
     * 自定义描述
     **/
    private final String message;

    RCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

