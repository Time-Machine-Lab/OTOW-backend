package com.tml.otowbackend.pojo.DTO;

import com.tml.otowbackend.pojo.VO.QueryProjectResponseVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description
 * @Author welsir
 * @Date 2024/12/2 19:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QueryProjectPageResponseDTO {

    List<QueryProjectResponseVO> respList;

    Integer total;
}
