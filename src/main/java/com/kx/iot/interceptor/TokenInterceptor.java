package com.kx.iot.interceptor;

import com.kx.iot.util.TokenThreadLocal;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
public class TokenInterceptor {

    @Pointcut("within(com.kx.iot.controller..*)")
    public void pointCut() {
    }

    @Before("pointCut()")
    public void setToken() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String token = request.getHeader("token");
        TokenThreadLocal.setToken(token);
    }
}
