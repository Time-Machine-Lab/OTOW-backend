package com.tml.otowbackend.service;

import com.tml.otowbackend.pojo.DO.User;
import com.tml.otowbackend.pojo.DTO.LoginRequestDTO;
import com.tml.otowbackend.pojo.DTO.RegisterRequestDTO;
import com.tml.otowbackend.pojo.VO.LoginVO;
import io.github.common.web.Result;

import java.util.Map;

/**
 * @Description
 * @Author welsir
 * @Date 2024/10/27 21:40
 */
public interface UserService {
    Result<?> getUserInfo();
    Result<?> login(LoginRequestDTO req);
    Result<?> logout();
    Result<?> generateCaptcha();
    Result<?> generateEmailCode(String email,String pid,String code);
    Result<?> purchase(String projectId);
}
