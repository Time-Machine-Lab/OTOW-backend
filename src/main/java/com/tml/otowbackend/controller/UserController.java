package com.tml.otowbackend.controller;

import com.tml.otowbackend.core.anno.TokenRequire;
import com.tml.otowbackend.pojo.DTO.LoginRequestDTO;
import com.tml.otowbackend.pojo.DTO.RegisterRequestDTO;
import com.tml.otowbackend.service.UserService;
import io.github.common.web.Result;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @Description
 * @Author welsir
 * @Date 2024/10/27 21:34
 */

@RestController
@Validated
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/captcha")
    public Result<?> queryCaptcha(){
        return userService.generateCaptcha();
    }

    @PostMapping("/login")
    public Result<?> login(@RequestBody
                               @Valid
                           LoginRequestDTO req){
        return userService.login(req);
    }

    @PostMapping("/logout")
    @TokenRequire
    public Result<?> logout(){
        return userService.logout();
    }

    @GetMapping("/getInfo")
    @TokenRequire
    public Result<?> getUserInfo(){
        return userService.getUserInfo();
    }

    @GetMapping("/sendCaptcha")
    public Result<?> preEmail(        @Valid
                                      @NotNull(message = "邮箱不能为空")
                                      String email,
                                      @Valid
                                      @NotNull(message = "pid不能为空")
                                      String pid,
                                      @Valid
                                      @NotNull(message = "验证码不能为空")
                                      String code){
        return userService.generateEmailCode(email,pid,code);
    }

    @GetMapping("/purchase")
    @TokenRequire
    public Result<?> purchaseProject(@RequestParam("projectId") String projectId){
        return userService.purchase(projectId);
    }
}
