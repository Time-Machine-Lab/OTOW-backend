package com.tml.otowbackend.service;

import com.tml.otowbackend.pojo.DTO.CreateProjectRequestDTO;
import com.tml.otowbackend.pojo.DTO.QueryProjectPageResponseDTO;
import com.tml.otowbackend.pojo.DTO.QueryProjectRequestDTO;
import com.tml.otowbackend.pojo.DTO.UpdateProjectRequestDTO;
import com.tml.otowbackend.pojo.VO.QueryProjectResponseVO;

import java.util.List;

/**
 * @Description
 * @Author welsir
 * @Date 2024/12/2 19:35
 */
public interface ProjectService {
    void create(CreateProjectRequestDTO requestDTO);
    void update(UpdateProjectRequestDTO requestDTO);
    void removeById(String projectId);
    QueryProjectPageResponseDTO queryProject(QueryProjectRequestDTO requestDTO);
    QueryProjectResponseVO queryOne(String id);
    String download(String id);
}
