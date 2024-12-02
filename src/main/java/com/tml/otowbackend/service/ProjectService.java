package com.tml.otowbackend.service;

import com.tml.otowbackend.pojo.DTO.CreateProjectRequestDTO;
import com.tml.otowbackend.pojo.DTO.QueryProjectRequestDTO;
import com.tml.otowbackend.pojo.DTO.QueryProjectsResponseDTO;
import com.tml.otowbackend.pojo.DTO.UpdateProjectRequestDTO;

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
    List<QueryProjectsResponseDTO> queryProject(QueryProjectRequestDTO requestDTO);

}
