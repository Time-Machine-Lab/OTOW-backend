package com.tml.otowbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tml.otowbackend.pojo.DO.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Insert("INSERT INTO purchase (uid, project_id) VALUES (#{uid}, #{projectId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer insertPurchase(String uid, String projectId);

}
