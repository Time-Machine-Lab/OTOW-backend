package tml.otow.user.service;

import io.github.common.web.Result;
import tml.otow.user.pojo.DO.UserDO;
import tml.otow.user.pojo.dto.LoginRequestDTO;
import tml.otow.user.pojo.dto.RegisterRequestDTO;
import tml.otow.user.pojo.vo.LoginVO;
import tml.otow.user.pojo.vo.UserVO;


import java.util.Map;

/**
 * @Description
 * @Author welsir
 * @Date 2024/10/27 21:40
 */
public interface UserService {

    UserVO queryUserById(String uid);

    Result<UserVO> getUserInfo();

    LoginVO login(LoginRequestDTO req);

    void logout();

    LoginVO register(RegisterRequestDTO req);

    Map<String,String> generateCaptcha();

    Result<?> generateEmailCode(String email,String pid,String code);

    UserVO queryUserByEmail(String email);

}
