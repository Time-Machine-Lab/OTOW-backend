package com.tml.otowbackend.controller;

import com.tml.otowbackend.core.anno.TokenRequire;
import com.tml.otowbackend.pojo.DTO.CreateProjectRequestDTO;
import com.tml.otowbackend.pojo.DTO.QueryProjectRequestDTO;
import com.tml.otowbackend.pojo.DTO.UpdateProjectRequestDTO;
import com.tml.otowbackend.service.ProjectService;
import io.github.common.web.Result;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Description
 * @Author welsir
 * @Date 2024/12/2 19:23
 */
@RestController
@Validated
@RequestMapping("/project")
public class ProjectController {

    @Resource
    ProjectService projectService;

    @PostMapping("/create")
    @TokenRequire
    public Result<?> createProject(@RequestBody @Validated
                                   CreateProjectRequestDTO requestDTO){
        projectService.create(requestDTO);
        return Result.success();
    }

    @PostMapping("/edit")
    @TokenRequire
    public Result<?> updateProject(@RequestBody @Validated
                                   UpdateProjectRequestDTO requestDTO){
        projectService.update(requestDTO);
        return Result.success();
    }

    @GetMapping("/delete")
    @TokenRequire
    public Result<?> removeProject(@RequestParam(value = "id") String id){
        projectService.removeById(id);
        return Result.success();
    }

    @PostMapping("/search")
    public Result<?> queryProject(@RequestBody @Validated
                                  QueryProjectRequestDTO requestDTO){
        return Result.success(projectService.queryProject(requestDTO));
    }

    @GetMapping("/searchOne")
    public Result<?> queryProject(@RequestParam("id")String id){
        return Result.success(projectService.queryOne(id));
    }

    @GetMapping("/download")
    @TokenRequire
    public Result<?> downloadProject(@RequestParam("id") String id){
        return Result.success(projectService.download(id));
    }


}
