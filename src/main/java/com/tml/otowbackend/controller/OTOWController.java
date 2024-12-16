package com.tml.otowbackend.controller;

import com.tml.otowbackend.core.anno.TokenRequire;
import com.tml.otowbackend.engine.ai.result.EntityClassDefinition;
import com.tml.otowbackend.engine.otow.SupportedLanguages;
import com.tml.otowbackend.engine.tree.common.R;
import com.tml.otowbackend.pojo.VO.ProjectDetailsVO;
import com.tml.otowbackend.pojo.VO.UserProjectVO;
import com.tml.otowbackend.service.AIService;
import com.tml.otowbackend.service.EntityClassService;
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
@RequestMapping("/otow")
@RestController
@Validated
public class OTOWController {

    private final OTOWProjectService projectService;
    private final EntityClassService entityClassService;
    private final AIService aiService;

    /**
     * 初始化或更新项目
     *
     * @param projectId 项目ID（可选）
     * @param params    参数Map
     * @return 返回项目ID
     */
    @PostMapping("/manageProject")
    @TokenRequire
    public R<Long> manageProject(@RequestParam(required = false) Long projectId,
                                 @RequestBody Map<String, Object> params) {
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
     * 获取支持的语言及其项目类型
     *
     * @return 返回支持的语言及类型
     */
    @GetMapping("/supportedLanguages")
    public R<Map<String, List<String>>> getSupportedLanguages() {
        return R.success("获取支持的语言和项目类型成功", SupportedLanguages.LANGUAGE_TO_TYPES);
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

    /**
     * AI 根据项目标题生成项目大纲描述
     *
     * @param projectId 项目ID
     * @return 项目描述大纲
     */
    @PostMapping("/generateProjectOutline")
    @TokenRequire
    public R<String> generateProjectOutline(@RequestParam Long projectId) {
        return aiService.generateProjectOutline(projectId);
    }

    @PostMapping("/addEntityClass")
    @TokenRequire
    public R<Void> addEntityClass(@RequestParam Long projectId, @RequestBody EntityClassDefinition entity) {
        return entityClassService.addEntityClass(projectId, entity);
    }

    @PutMapping("/updateEntityClass")
    @TokenRequire
    public R<Void> updateEntityClass(@RequestParam Long projectId, @RequestParam String className, @RequestBody EntityClassDefinition entity) {
        return entityClassService.updateEntityClass(projectId, className, entity);
    }

    @DeleteMapping("/deleteEntityClass")
    @TokenRequire
    public R<Void> deleteEntityClass(@RequestParam Long projectId, @RequestParam String className) {
        return entityClassService.deleteEntityClass(projectId, className);
    }

    @GetMapping("/getEntityClasses")
    @TokenRequire
    public R<List<EntityClassDefinition>> getEntityClasses(@RequestParam Long projectId) {
        return entityClassService.getEntityClasses(projectId);
    }
}
