package com.tml.otowbackend.core.interceptor;

import cn.dev33.satoken.stp.StpUtil;
import com.tml.otowbackend.core.anno.TokenRequire;
import com.tml.otowbackend.util.UserThread;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description
 * @Author welsir
 * @Date 2024/10/28 14:55
 */
@Component
public class TokenInterceptor implements HandlerInterceptor, Ordered {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            if(handlerMethod.hasMethodAnnotation(TokenRequire.class)){
                String token = request.getHeader("Authorization");
                token = token==null? (String) request.getAttribute("Authorization") :token;
                String tokenObject = (String) StpUtil.getLoginIdByToken(token);
                if(tokenObject == null){
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                    return false;
                }
                String[] strs = tokenObject.split("\\|");
                UserThread.set("uid",strs[0]);
                UserThread.set("email",strs[1]);
                return true;
            }
        }
        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        UserThread.remove();
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
