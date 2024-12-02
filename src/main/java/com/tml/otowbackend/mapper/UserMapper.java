package com.tml.otowbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tml.otowbackend.pojo.DO.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
