package tml.otow.user.core.exception;

import io.github.common.web.Result;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

/**
 * 描述: 全局异常捕获
 * @author suifeng
 * 日期: 2024/7/5
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandle {

    /**
     * 自定义异常类
     */
    @ExceptionHandler(ServeException.class)
    public Result<?> handleServeException(final ServeException e) {
        log.error(e.getMessage(), e);
        return Result.error("500",e.getMessage());
    }

    /**
     * 运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Result<?> runtimeExceptionHandler(final Exception e) {
        e.printStackTrace();
        log.error("全局异常[RuntimeException]: {}:", e.getMessage());
        return Result.error("500",e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        log.error(e.getMessage());
        // 获取校验错误信息
        return Result.error("500",Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result<?> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException e) {
        log.error(e.getMessage());
        return Result.error("500",e.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    public Result<?> illegalStateExceptionHandler(IllegalStateException e) {
        log.error(e.getMessage());
        return Result.error("500",e.getMessage());
    }
}
