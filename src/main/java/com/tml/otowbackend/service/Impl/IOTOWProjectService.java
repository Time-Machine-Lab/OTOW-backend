package com.tml.otowbackend.service.Impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.tml.otowbackend.constants.ProjectConstants;
import com.tml.otowbackend.engine.otow.OTOWCacheService;
import com.tml.otowbackend.engine.otow.ProjectValidator;
import com.tml.otowbackend.engine.tree.common.R;
import com.tml.otowbackend.engine.tree.common.ServeException;
import com.tml.otowbackend.mapper.OTOWProjectMapper;
import com.tml.otowbackend.pojo.DO.OTOWProject;
import com.tml.otowbackend.pojo.VO.ProjectDetailsVO;
import com.tml.otowbackend.pojo.VO.UserProjectVO;
import com.tml.otowbackend.service.OTOWProjectService;
import com.tml.otowbackend.util.UserThread;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static com.tml.otowbackend.constants.ProjectConstants.*;

@Service
public class IOTOWProjectService implements OTOWProjectService {

    @Resource
    private OTOWProjectMapper otowProjectMapper;

    @Resource
    private OTOWCacheService cacheService;

    @Override
    public R<Long> manageProject(Long projectId, Map<String, Object> params) {
        // 校验参数
        ProjectValidator.validateParams(params);

        if (projectId == null) {
            // 如果没有传递ID，则初始化项目
            return R.success("项目初始化成功", initializeProject(params));
        } else {
            // 如果传递了ID，则更新项目参数
            return R.success("更新项目参数成功", updateProject(projectId, params));
        }
    }

    /**
     * 初始化项目
     *
     * @param params 参数Map
     * @return 返回生成的项目ID
     */
    private Long initializeProject(Map<String, Object> params) {
        // 1. 生成唯一项目ID
        Long projectId = IdWorker.getId();

        // 2. 将参数存入缓存
        params.forEach((key, value) -> cacheService.put(projectId, key, value));

        // 3. 初始化默认值
        cacheService.put(projectId, UID, UserThread.getUid());
        cacheService.put(projectId, TITLE, params.getOrDefault(TITLE, ""));
        cacheService.put(projectId, DESCRIPTION, params.getOrDefault(DESCRIPTION, ""));
        cacheService.put(projectId, LANGUAGE, params.getOrDefault(LANGUAGE, ""));
        cacheService.put(projectId, ProjectConstants.TYPE, params.getOrDefault(ProjectConstants.TYPE, ""));

        return projectId;
    }

    /**
     * 更新项目参数
     *
     * @param projectId 项目ID
     * @param params    参数Map
     * @return 返回项目ID
     */
    private Long updateProject(Long projectId, Map<String, Object> params) {
        // 检查项目是否存在
        if (cacheService.getAll(projectId).isEmpty()) {
            throw new ServeException("项目不存在，ID: " + projectId);
        }

        // 更新缓存中的参数
        params.forEach((key, value) -> cacheService.put(projectId, key, value));

        return projectId;
    }

    @Override
    public void saveProject(Long projectId) {
        // 检查项目是否存在
        Map<String, Object> cachedParams = cacheService.getAll(projectId);
        if (cachedParams.isEmpty()) {
            throw new ServeException("项目不存在，无法保存，ID: " + projectId);
        }

        // 组装项目对象
        OTOWProject project = new OTOWProject();
        project.setId(projectId);
        project.setUid(UserThread.getUid());
        project.setTitle((String) cachedParams.get(TITLE));
        project.setDescription((String) cachedParams.get(DESCRIPTION));
        project.setLanguage((String) cachedParams.get(LANGUAGE));
        project.setType((String) cachedParams.get(TYPE));
        project.setStatus("SAVED");
        project.setMetadata("{}");

        // 保存到数据库
        if (otowProjectMapper.selectById(projectId) == null) {
            otowProjectMapper.insert(project);
        } else {
            otowProjectMapper.updateById(project);
        }
    }

    @Override
    public R<List<UserProjectVO>> getUserProjects(String userId) {
        // 从数据库获取用户的项目列表
        List<OTOWProject> projects = otowProjectMapper.getProjectsByUserId(userId);

        // 使用 VO 的静态方法转换
        List<UserProjectVO> result = UserProjectVO.fromEntityList(projects);

        return R.success("获取项目列表成功", result);
    }

    @Override
    public R<ProjectDetailsVO> getProjectDetails(Long projectId) {
        // 检查缓存中是否已有项目详情
        Map<String, Object> cachedParams = cacheService.getAll(projectId);
        if (!cachedParams.isEmpty()) {
            // 从缓存中构造 VO 对象
            ProjectDetailsVO vo = ProjectDetailsVO.fromCache(projectId, cachedParams);
            String uid = (String) cacheService.get(projectId, UID);
            if (!uid.equals(UserThread.getUid())) throw new ServeException("您没有此权限");
            return R.success("从缓存中获取项目详情成功", vo);
        }

        // 如果缓存中没有，从数据库加载
        OTOWProject project = otowProjectMapper.getProjectById(projectId);
        if (project == null) {
            throw new ServeException("项目不存在，ID: " + projectId);
        }

        // 将项目详情存入缓存
        cacheService.put(projectId, UID, project.getUid());
        cacheService.put(projectId, TITLE, project.getTitle());
        cacheService.put(projectId, DESCRIPTION, project.getDescription());
        cacheService.put(projectId, LANGUAGE, project.getLanguage());
        cacheService.put(projectId, TYPE, project.getType());
        cacheService.put(projectId, "status", project.getStatus());
        cacheService.put(projectId, "metadata", project.getMetadata());
        cacheService.put(projectId, "createTime", project.getCreateTime());
        cacheService.put(projectId, "updateTime", project.getUpdateTime());

        // 使用 VO 的静态方法构造
        ProjectDetailsVO vo = ProjectDetailsVO.fromEntity(project);
        return R.success("获取项目详情成功", vo);
    }
}