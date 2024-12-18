package com.tml.otowbackend.controller.generate;

import com.tml.otowbackend.core.anno.TokenRequire;
import com.tml.otowbackend.engine.tree.common.R;
import com.tml.otowbackend.pojo.VO.ProjectDetailsVO;
import com.tml.otowbackend.pojo.VO.UserProjectVO;
import com.tml.otowbackend.service.OTOWProjectService;
import com.tml.otowbackend.util.UserThread;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 描述: 项目生成
 * @author suifeng
 * 日期: 2024/12/16
 */
@RequiredArgsConstructor
@RequestMapping("/otow/project")
@RestController
@Validated
public class OTOWController {

    private final OTOWProjectService projectService;

    /**
     * 初始化或更新项目
     *
     * @param projectId 项目ID（可选）
     * @param params    参数Map
     * @return 返回项目ID
     */
    @PostMapping("/manageProject")
    @TokenRequire
    public R<Long> manageProject(@RequestParam(required = false) Long projectId, @RequestBody Map<String, Object> params) {
        return projectService.manageProject(projectId, params);
    }

    /**
     * 保存项目
     *
     * @param projectId 项目ID
     * @return 操作结果
     */
    @TokenRequire
    @PostMapping("/saveProject")
    public R<Void> saveProject(@RequestParam Long projectId) {
        projectService.saveProject(projectId);
        return R.success("项目保存成功");
    }

    /**
     * 获取当前用户的项目列表
     *
     * @return 用户项目列表
     */
    @GetMapping("/userProjects")
    @TokenRequire
    public R<List<UserProjectVO>> getUserProjects() {
        return projectService.getUserProjects(UserThread.getUid());
    }

    /**
     * 获取项目详情
     *
     * @param projectId 项目ID
     * @return 项目详情
     */
    @GetMapping("/projectDetails")
    @TokenRequire
    public R<ProjectDetailsVO> getProjectDetails(@RequestParam Long projectId) {
        return projectService.getProjectDetails(projectId);
    }
}
