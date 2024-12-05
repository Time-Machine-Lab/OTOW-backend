package com.tml.otowbackend.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tml.otowbackend.constants.UserConstant;
import com.tml.otowbackend.core.async.AsyncService;
import com.tml.otowbackend.core.exception.ServerException;
import com.tml.otowbackend.mapper.UserMapper;
import com.tml.otowbackend.pojo.DO.User;
import com.tml.otowbackend.pojo.DTO.LoginRequestDTO;
import com.tml.otowbackend.pojo.VO.LoginVO;
import com.tml.otowbackend.pojo.VO.UserVO;
import com.tml.otowbackend.service.UserService;
import com.tml.otowbackend.util.*;
import io.github.common.web.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.UUID;

import static com.tml.otowbackend.constants.UserConstant.DEFAULT_PWD;
import static com.tml.otowbackend.util.CacheUtil.EMAIL_COOL_DOWN;


/**
 * @Description
 * @Author welsir
 * @Date 2024/10/27 21:39
 */
@Slf4j
@Service
public class IUserService implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private ImageUtil imageUtil;
    @Resource
    private AsyncService asyncService;
    @Resource
    private CacheUtil cacheUtil;

    @Override
    public Result<?> getUserInfo() {
        String uid = ThreadUtil.getUid();
        User dto = userMapper.selectOne(new QueryWrapper<User>().eq("uid",uid));
        return Result.success(UserVO.convert(dto));
    }

    @Override
    public Result<?> login(LoginRequestDTO req) {
        String code = req.getCode();
        String emailCode = cacheUtil.getEmailAndRemove(req.getEmail());
        if(!StringUtils.equals(code.toLowerCase(), emailCode)){
            throw new ServerException("验证码错误");
        }

        User user = userMapper.selectOne(new QueryWrapper<User>()
                .eq("email",req.getEmail()));
        // 用户不存在则注册
        if(user == null){
            user = addNewUser(req.getEmail());
        }
        String token = TokenUtil.getToken(String.valueOf(user.getUid()), user.getEmail());
        return Result.success(LoginVO.builder().userVO(UserVO.convert(user)).token(token).build());
    }

    @Override
    public Result<?> logout() {
        TokenUtil.logout(ThreadUtil.getUid(), ThreadUtil.getEmail());
        return Result.success();
    }

    private User addNewUser(String email) {
        User user = User.builder()
                .nickname(UserConstant.OTOW + RandomUtil.generateRandomString(8))
                .uid(UUID.randomUUID().toString())
                .avatar("https://geniusserve.oss-cn-shanghai.aliyuncs.com/cybernomads/plant.ico")
                .email(email)
                .password(DigestUtils.md5Hex(DEFAULT_PWD))
                .build();
        try {
            userMapper.insert(user);
        }catch (Exception e){
            throw new ServerException("请勿频繁操作!");
        }
        return user;
    }

    @Override
    public Result<?> generateCaptcha() {
        String pid = UUID.randomUUID().toString();
        Map<String, String> code = imageUtil.generateCode();
        String seeCode = code.get("code").toLowerCase();
        cacheUtil.putCaptcha(pid, seeCode);
        return Result.success(Map.of("base64",code.get("base64"),"pid",pid));
    }

    @Override
    public Result<?> generateEmailCode(String email,String pid,String code) {
        if(cacheUtil.emailCodeIsExpired(email)){
            throw new ServerException("验证码冷却中");
        }
        if(!StringUtils.equals(cacheUtil.getCaptchaAndRemove(pid), code.toLowerCase())){
            throw new ServerException("验证码错误");
        }
        String emailCode = RandomUtil.generateRandomString(6);
        cacheUtil.putEmail(email, emailCode.toLowerCase());
        asyncService.sendCodeToEmail(email, emailCode);
        return Result.success(
                Map.of("email", email, "coolDown",EMAIL_COOL_DOWN)
        );
    }

    @Override
    public Result<?> purchase(String projectId) {
        String uid = ThreadUtil.getUid();
        Integer integer = userMapper.insertPurchase(uid, projectId);
        return Result.success(integer==1);
    }
}
