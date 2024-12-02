package tml.otow.user.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import tml.otow.user.pojo.DO.UserDO;

/**
 * @Description
 * @Author welsir
 * @Date 2024/10/27 22:00
 */
@Mapper
public interface UserMapper extends BaseMapper<UserDO> {
}
