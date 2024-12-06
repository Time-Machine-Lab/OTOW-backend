package com.tml.otowbackend.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tml.otowbackend.constants.CodeLanguage;
import com.tml.otowbackend.core.exception.ResultCode;
import com.tml.otowbackend.core.exception.ServerException;
import com.tml.otowbackend.mapper.ProjectMapper;
import com.tml.otowbackend.pojo.DO.Project;
import com.tml.otowbackend.pojo.DTO.CreateProjectRequestDTO;
import com.tml.otowbackend.pojo.DTO.QueryProjectRequestDTO;
import com.tml.otowbackend.pojo.DTO.QueryProjectResponseDTO;
import com.tml.otowbackend.pojo.DTO.UpdateProjectRequestDTO;
import com.tml.otowbackend.service.ProjectService;
import com.tml.otowbackend.util.OSSUtil;
import com.tml.otowbackend.util.ThreadUtil;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author welsir
 * @Date 2024/12/2 19:37
 */
@Component
public class IProjectServiceImpl implements ProjectService {
    final int DEFAULT_MAX_LIMIT = 20;
    @Resource
    ProjectMapper projectMapper;
    @Resource
    OSSUtil ossUtil;
    @Override
    public void create(CreateProjectRequestDTO requestDTO) {
        Project project = Project.convert(requestDTO);
        String uid = ThreadUtil.getUid();
        project.setShareUid(uid);
        projectMapper.insert(project);
    }

    @Override
    public void update(UpdateProjectRequestDTO requestDTO) {
        LambdaUpdateWrapper<Project> wrapper = new LambdaUpdateWrapper<>();
        String uid = ThreadUtil.getUid();
        Integer language = CodeLanguage.queryCodeByLanguage(requestDTO.getCodeLanguage());
        wrapper.eq(Project::getId,requestDTO.getId()).eq(Project::getShareUid,uid);
        Optional.ofNullable(requestDTO.getCover())
                .ifPresent(cover -> wrapper.set(Project::getCover, cover));
        Optional.ofNullable(requestDTO.getDescription())
                .ifPresent(description -> wrapper.set(Project::getDescription, description));
        Optional.ofNullable(requestDTO.getIntroduce())
                .ifPresent(introduce -> wrapper.set(Project::getIntroduce, introduce));
        Optional.ofNullable(requestDTO.getPrice())
                .ifPresent(price -> wrapper.set(Project::getPrice, price));
        Optional.ofNullable(language)
                .ifPresent(lang -> wrapper.set(Project::getCodeLanguage, lang));
        Optional.ofNullable(requestDTO.getName())
                .ifPresent(name -> wrapper.set(Project::getName, name));
        projectMapper.update(null,wrapper);
    }

    @Override
    public void removeById(String projectId) {
        projectMapper.deleteById(projectId);
    }

    @Override
    public List<QueryProjectResponseDTO> queryProject(QueryProjectRequestDTO requestDTO) {
        int limit = requestDTO.getLimit();
        int page = requestDTO.getPage();
        int cmd = requestDTO.getCmd();
        List<QueryProjectResponseDTO> resLists;
        limit = Math.min(limit,DEFAULT_MAX_LIMIT);
        Page<Project> projectPage;
        page = page-1>=0?page:0;
        switch (cmd){
            case 2:{
                projectPage = projectMapper.selectPage(new Page<>(page, limit), new LambdaQueryWrapper<Project>()
                        .orderByAsc(Project::getDownloadNum));
                break;
            }
            case 3:{
                projectPage = projectMapper.selectPage(new Page<>(page, limit), new LambdaQueryWrapper<Project>()
                        .like(Project::getName,requestDTO.getName()));
                break;
            }
            default:{
                projectPage = projectMapper.selectPage(new Page<>(page - 1, limit), new LambdaQueryWrapper<Project>()
                        .orderByDesc(Project::getCreateTime));
            }
        }
        resLists = projectPage.getRecords().stream().map((QueryProjectResponseDTO::convertWithoutDetail)).collect(Collectors.toList());
        return resLists;
    }

    @Override
    public QueryProjectResponseDTO queryOne(String id) {
        Project one = projectMapper.selectOne(new QueryWrapper<Project>().eq("id", id));
        return QueryProjectResponseDTO.convert(one);
    }

    @Override
    public String download(String id) {
        String uid = ThreadUtil.getUid();
        boolean flag = projectMapper.checkRecord(uid, Long.valueOf(id))==1;
        if(flag){
            Project one = projectMapper.selectOne(new QueryWrapper<Project>().eq("id", id));
            return ossUtil.getObjectURL(one.getDownloadUrl());
        }
        throw new ServerException(ResultCode.NOT_PURCHASE_RECORD);
    }
}
