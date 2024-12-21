package com.tml.otowbackend.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tml.otowbackend.constants.CodeLanguageEnum;
import com.tml.otowbackend.core.exception.ResultCode;
import com.tml.otowbackend.core.exception.ServerException;
import com.tml.otowbackend.mapper.ProjectMapper;
import com.tml.otowbackend.pojo.DO.Project;
import com.tml.otowbackend.pojo.DTO.CreateProjectRequestDTO;
import com.tml.otowbackend.pojo.DTO.QueryProjectPageResponseDTO;
import com.tml.otowbackend.pojo.DTO.QueryProjectRequestDTO;
import com.tml.otowbackend.pojo.DTO.UpdateProjectRequestDTO;
import com.tml.otowbackend.pojo.VO.QueryProjectResponseVO;
import com.tml.otowbackend.service.ProjectService;
import com.tml.otowbackend.util.OSSUtil;
import com.tml.otowbackend.util.UserThread;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author welsir
 * @Date 2024/12/2 19:37
 */
@Service
public class IProjectServiceImpl implements ProjectService {
    final int DEFAULT_MAX_LIMIT = 20;
    @Resource
    ProjectMapper projectMapper;
    @Resource
    OSSUtil ossUtil;
    @Override
    public void create(CreateProjectRequestDTO requestDTO) {
        Project project = Project.convert(requestDTO);
        String uid = UserThread.getUid();
        project.setShareUid(uid);
        projectMapper.insert(project);
    }

    @Override
    public void update(UpdateProjectRequestDTO requestDTO) {
        LambdaUpdateWrapper<Project> wrapper = new LambdaUpdateWrapper<>();
        String uid = UserThread.getUid();
        Integer language = CodeLanguageEnum.queryCodeByLanguage(requestDTO.getCodeLanguage());
        wrapper.eq(Project::getId,requestDTO.getId()).eq(Project::getShareUid,uid);
        if(!StringUtils.isBlank(requestDTO.getCover())){
            wrapper.set(Project::getCover, requestDTO.getCover());
        }
        if(!StringUtils.isBlank(requestDTO.getDescription())){
            wrapper.set(Project::getCover, requestDTO.getDescription());
        }
        if(!StringUtils.isBlank(requestDTO.getIntroduce())){
            wrapper.set(Project::getIntroduce, requestDTO.getIntroduce());
        }
        if(!StringUtils.isBlank(requestDTO.getName())){
            wrapper.set(Project::getName,requestDTO.getName());
        }
        if(Objects.nonNull(requestDTO.getCodeLanguage())){
            wrapper.set(Project::getCodeLanguage, language);
        }
        if(Objects.nonNull(requestDTO.getPrice())){
            wrapper.set(Project::getPrice, requestDTO.getPrice());
        }
        projectMapper.update(null,wrapper);
    }

    @Override
    public void removeById(String projectId) {
        projectMapper.deleteById(projectId);
    }


    @Override
    public QueryProjectPageResponseDTO queryProject(QueryProjectRequestDTO requestDTO) {
        int limit = Math.min((requestDTO.getLimit()),DEFAULT_MAX_LIMIT);
        int page = Math.max(requestDTO.getPage(), 1);
        List<QueryProjectResponseVO> resLists;
        LambdaUpdateWrapper<Project> wrapper = new LambdaUpdateWrapper<>();
        if(!StringUtils.isBlank(requestDTO.getName())){
            wrapper.like(Project::getName, requestDTO.getName());
        }
        if(!StringUtils.isBlank(requestDTO.getCodeLanguage())){
            wrapper.eq(Project::getCodeLanguage,CodeLanguageEnum.queryCodeByLanguage(requestDTO.getCodeLanguage()));
        }
        if(Objects.nonNull(requestDTO.getAmountMax())){
            wrapper.le(Project::getPrice, requestDTO.getAmountMax());
        }
        if (Objects.nonNull(requestDTO.getAmountMin())) {
            wrapper.ge(Project::getPrice, requestDTO.getAmountMin());
        }
        try {
            Page<Project> projectPage = projectMapper.selectPage(new Page<>(page, limit), wrapper);
            List<Project> records = projectPage.getRecords();
            resLists = records.stream().map((QueryProjectResponseVO::convertWithoutDetail)).collect(Collectors.toList());
            return QueryProjectPageResponseDTO.builder().total((int) projectPage.getTotal()).respList(resLists).build();
        }catch (RuntimeException e){
            throw new ServerException(ResultCode.QUERY_PROJECT_FAIL);
        }
    }

    @Override
    public QueryProjectResponseVO queryOne(String id) {
        Project one = projectMapper.selectOne(new QueryWrapper<Project>().eq("id", id));
        return QueryProjectResponseVO.convert(one);
    }

    @Override
    public String download(String id) {
        String uid = UserThread.getUid();
        boolean flag = projectMapper.checkRecord(uid, Long.valueOf(id))==1;
        if(flag){
            Project one = projectMapper.selectOne(new QueryWrapper<Project>().eq("id", id));
            return ossUtil.getObjectURL(one.getDownloadUrl());
        }
        throw new ServerException(ResultCode.NOT_PURCHASE_RECORD);
    }

    @Override
    public List<String> queryCodeLanguage() {
        List<String> res = new ArrayList<>();
        for (CodeLanguageEnum value : CodeLanguageEnum.values()) {
            res.add(value.getLanguage());
        }
        return res;
    }

}
