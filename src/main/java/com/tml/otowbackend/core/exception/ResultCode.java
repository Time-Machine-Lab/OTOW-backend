package com.tml.otowbackend.core.exception;

import lombok.Getter;

/**
 * 描述: 自定义状态码
 * @author suifeng
 * 日期: 2024/7/5
 */
@Getter
public enum ResultCode {

    // 成功响应
    SUCCESS(200, "成功"),

    /**
     * 失败返回码
     */
    ERROR(400, "服务器繁忙，请稍后重试"),

    NUMBER_OUT(402, "数量上限超出"),

    /**
     * 参数异常
     */
    PARAMS_ERROR(20001, "参数异常或者格式错误"),

    NOT_EXITS_LANGUAGE(1001,"错误的语言"),
    NOT_PURCHASE_RECORD(1002,"未查询到购买记录"),
    NOT_USER_EXITS(1003,"用户未登录"),
    GET_OSS_OBJECT_URL_FAIL(1004,"获取OSS资源失败"),
    QUERY_PROJECT_FAIL(1005,"查询项目列表失败");

    /**
     * 自定义状态码
     **/
    private final int code;

    /**
     * 自定义描述
     **/
    private final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}

