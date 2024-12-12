package com.tml.otowbackend.engine.tree.common;

import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class R<T> {

    private String code;

    private String message;

    private T data;

    public static <T> R<T> success(String message, T data) {
        return new R<T>(RCode.SUCCESS.getCode(), message, data);
    }

    public static <T> R<T> success(String message) {
        return success(message, null);
    }

    public static <T> R<T> success() {
        return success(RCode.SUCCESS.getMessage());
    }

    public static <T> R<T> success(T data) {
        return success(RCode.SUCCESS.getMessage(), data);
    }

    public static <T> R<T> error(String code, String message) {
        return new R<>(code, message, null);
    }

    public static <T> R<T> error(String message) {
        return error(RCode.ERROR.getCode(), message);
    }

    public static <T> R<T> error() {
        return error(RCode.ERROR.getMessage());
    }

    public static <T> R<T> error(RCode rCode) {
        return error(rCode.getCode(), rCode.getMessage());
    }
}