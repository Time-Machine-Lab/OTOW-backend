package com.tml.otowbackend.service.Impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.tml.otowbackend.engine.otow.OTOWCacheService;
import com.tml.otowbackend.engine.tree.common.ServeException;
import com.tml.otowbackend.mapper.OTOWProjectMapper;
import com.tml.otowbackend.pojo.DO.OTOWProject;
import com.tml.otowbackend.service.OTOWProjectService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import static com.tml.otowbackend.pojo.DO.OTOWProject.getOtowProject;

@Service
public class IOTOWProjectService implements OTOWProjectService {

    @Resource
    private OTOWProjectMapper otowProjectMapper;

    @Resource
    private OTOWCacheService cacheService;

    @Override
    public Long initializeProject(String title, String description) {

        // 1. 生成唯一的项目ID
        Long projectId = IdWorker.getId();

        // 2. 创建项目对象并设置初始数据
        OTOWProject project = getOtowProject(projectId, title, description);

        // 3. 保存到数据库
        if (otowProjectMapper.insert(project) <= 0) {
            throw new ServeException("项目初始化失败");
        }

        // 4. 将项目数据缓存到内存
        cacheService.put(projectId, "project", project);

        // 5. 返回项目ID
        return projectId;
    }
}