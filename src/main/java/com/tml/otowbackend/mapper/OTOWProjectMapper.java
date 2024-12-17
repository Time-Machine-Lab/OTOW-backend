package com.tml.otowbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tml.otowbackend.pojo.DO.OTOWProject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OTOWProjectMapper extends BaseMapper<OTOWProject> {

    /**
     * 根据用户ID获取项目列表
     *
     * @param userId 用户ID
     * @return 项目列表
     */
    @Select("SELECT id, title, language, type FROM otow_project WHERE uid = #{userId}")
    List<OTOWProject> getProjectsByUserId(@Param("userId") String userId);

    /**
     * 根据项目ID获取项目详情
     *
     * @param projectId 项目ID
     * @return 项目详情
     */
    @Select("SELECT * FROM otow_project WHERE id = #{projectId}")
    OTOWProject getProjectById(@Param("projectId") Long projectId);
}
