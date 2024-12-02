package com.tml.otowbackend.core.async;

import com.tml.otowbackend.util.EmailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class AsyncService {

    @Resource
    private EmailUtil emailUtil;

    @Async("taskExecutor")
    public void sendCodeToEmail(String to, String code) {
        log.info("{}验证码已发送", to);
        emailUtil.sendCode(to, code);
    }
}
