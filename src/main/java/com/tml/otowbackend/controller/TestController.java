package com.tml.otowbackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tml.otowbackend.constants.UserConstant;
import com.tml.otowbackend.mapper.UserMapper;
import com.tml.otowbackend.pojo.DO.User;
import com.tml.otowbackend.pojo.DTO.RegisterRequestDTO;
import com.tml.otowbackend.pojo.VO.LoginVO;
import com.tml.otowbackend.pojo.VO.UserVO;
import com.tml.otowbackend.util.RandomUtil;
import com.tml.otowbackend.util.TokenUtil;
import io.github.common.web.Result;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @Description
 * @Author welsir
 * @Date 2024/12/7 0:45
 */
@RequestMapping("/test")
@RestController
public class TestController {

    @Resource
    private UserMapper userMapper;

    @PostMapping("/register")
    public Result<?> registerUser(@RequestBody RegisterRequestDTO req){
        User user = User.builder()
                .nickname(UserConstant.OTOW + RandomUtil.generateRandomString(8))
                .uid(UUID.randomUUID().toString())
                .avatar("https://geniusserve.oss-cn-shanghai.aliyuncs.com/cybernomads/plant.ico")
                .email(req.getEmail())
                .password(DigestUtils.md5Hex(req.getPassword()))
                .build();
        userMapper.insert(user);
        String token = TokenUtil.getToken(user.getUid(), user.getEmail());
        return Result.success(LoginVO.builder().userVO(UserVO.convert(user)).token(token).build());
    }

    @GetMapping("/login")
    public Result<?> loginUser(@RequestParam String email){
        User user = userMapper.selectOne(new QueryWrapper<User>()
                .eq("email", email));
        if(user == null){
            throw new RuntimeException("用户未注册");
        }
        String token = TokenUtil.getToken(user.getUid(), user.getEmail());
        return Result.success(LoginVO.builder().userVO(UserVO.convert(user)).token(token).build());
    }

}
