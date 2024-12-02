package com.tml.otowbackend.util;

import com.tml.otowbackend.constants.RedisConstant;
import com.tml.otowbackend.core.exception.ServerException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author welsir
 * @Date 2024/10/27 22:06
 */
@Component
public class CacheUtil {
    public static final long EMAIL_COOL_DOWN = 60;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public String getCaptcha(String key){
        return stringRedisTemplate.opsForValue().get(RedisConstant.PIC_CAPTCHA + key);
    }

    public void putCaptcha(String key,String value){
        stringRedisTemplate.opsForValue().set(RedisConstant.PIC_CAPTCHA + key, value, 60, TimeUnit.SECONDS);
    }

    public void removeCaptcha(String key){
        stringRedisTemplate.delete(RedisConstant.PIC_CAPTCHA + key);
    }

    public String getCaptchaAndRemove(String key) {
        String code = stringRedisTemplate.opsForValue().get(RedisConstant.PIC_CAPTCHA + key);
        if (code != null) {
            stringRedisTemplate.delete(RedisConstant.PIC_CAPTCHA + key);
        }
        return code;
    }

    public String getEmail(String key){
        return stringRedisTemplate.opsForValue().get(RedisConstant.EMAIL_CAPTCHA + key);
    }

    public void putEmail(String key,String value){
        stringRedisTemplate.opsForValue().set(RedisConstant.EMAIL_CAPTCHA + key, value, EMAIL_COOL_DOWN, TimeUnit.SECONDS);
    }

    public void removeEmail(String key){
        stringRedisTemplate.delete(RedisConstant.EMAIL_CAPTCHA + key);
    }

    public String getEmailAndRemove(String key) {
        String code = stringRedisTemplate.opsForValue().get(RedisConstant.EMAIL_CAPTCHA + key);
        if (code == null) {
            throw new ServerException("验证码错误");
        }
        return code;
    }


    public boolean emailCodeIsExpired(String key) {
        return Boolean.TRUE.equals(stringRedisTemplate.hasKey(RedisConstant.EMAIL_CAPTCHA + key));
    }

    public boolean captchaCodeIsExpired(String key) {
        return Boolean.TRUE.equals(stringRedisTemplate.hasKey(RedisConstant.PIC_CAPTCHA + key));
    }

    public void put(String key,String value,Integer time){
        stringRedisTemplate.opsForValue().set(key,value,time,TimeUnit.SECONDS);
    }

    public void put(String key,String value){
        stringRedisTemplate.opsForValue().set(key,value);
    }

    public String get(String key){
        return stringRedisTemplate.opsForValue().get(key);
    }
    public void remove(String key){
        stringRedisTemplate.delete(key);
    }
    public Boolean checkCaptchaExpired(String key){
        return !Boolean.TRUE.equals(stringRedisTemplate.hasKey(key));
    }
}
