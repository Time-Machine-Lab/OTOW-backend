package com.tml.otowbackend.pojo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotNull;

/**
 * @Description
 * @Author welsir
 * @Date 2024/12/2 22:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryProjectRequestDTO {
    @NotNull
    private Integer cmd;
    private String name;
    @NonNull
    private Integer limit;
    @NonNull
    private Integer page;

}
