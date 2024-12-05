package com.tml.otowbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tml.otowbackend.pojo.DO.Project;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @Description
 * @Author welsir
 * @Date 2024/12/2 19:39
 */
@Mapper
public interface ProjectMapper extends BaseMapper<Project> {

    @Select("SELECT COUNT(1)\n" +
            "FROM purchase p\n" +
            "WHERE p.uid = #{uid}\n" +
            "  AND p.project_id = #{projectId}\n" +
            "  AND EXISTS (\n" +
            "    SELECT 1\n" +
            "    FROM project pr\n" +
            "    WHERE pr.id = #{projectId}\n" +
            "  )")
    Integer checkRecord(String uid,Long projectId);
}
