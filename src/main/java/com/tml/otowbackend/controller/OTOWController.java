package com.tml.otowbackend.controller;

import com.tml.otowbackend.engine.tree.common.R;
import com.tml.otowbackend.pojo.DTO.InitializeProjectRequest;
import com.tml.otowbackend.service.OTOWProjectService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 描述: 项目生成
 * @author suifeng
 * 日期: 2024/12/16
 */
@RequestMapping("/otow")
@RestController
@Validated
public class OTOWController {

    @Resource
    private OTOWProjectService projectService;

    /**
     * 初始化一个新项目
     * @param request 包含项目标题和描述
     * @return 返回生成的项目ID
     */
    @PostMapping("/initializeProject")
    public R<Long> initializeProject(@Validated @RequestBody InitializeProjectRequest request) {
        Long projectId = projectService.initializeProject(request.getTitle(), request.getDescription());
        return R.success("项目初始化成功", projectId);
    }
}
