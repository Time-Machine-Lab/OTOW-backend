package com.example.framepack.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private Integer code;//200成功，405失败
    private String msg;
    private Object data;

    public static Result success(String msg){
        return new Result(200,msg,null);
    }

    public static Result success(String msg, Object data){
        return new Result(200,msg,data);
    }

    public static Result error(String msg){
        return new Result(405,msg,null);
    }
}