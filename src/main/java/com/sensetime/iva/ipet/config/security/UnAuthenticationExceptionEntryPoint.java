package com.sensetime.iva.ipet.config.security;

import com.sensetime.iva.ipet.common.Code;
import com.sensetime.iva.ipet.util.ReturnUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author : ChaiLongLong
 * @date : 2018/9/14 11:19
 * 处理权限验证异常情况
 * 1.用户登陆成功后请求权限校验失败
 * 2.remember_me请求校验失败
 * 3.remember_me失效
 */
@Component
public class UnAuthenticationExceptionEntryPoint implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(UnAuthenticationExceptionEntryPoint.class);

    @Value("${server.context-path}")
    private String contextPath;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        logger.error("UnAuthenticationExceptionEntryPoint");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String errorResult;
        if (authException instanceof BadCredentialsException) {
            //此处添加 login_p跳转是因为前端统一对login_p响应做登陆跳转
            response.sendRedirect(contextPath+"/login_p");
            errorResult = ReturnUtil.error(Code.LOGIN_FAIL,"尚未登录，请登录!").toString();
        } else if (authException instanceof DisabledException) {
            errorResult = ReturnUtil.error(Code.LOGIN_FAIL,"账户未启用，登录失败，请联系管理员!").toString();
        } else if (authException instanceof InsufficientAuthenticationException){
            errorResult = ReturnUtil.error(Code.UNAUTHORIZED,"权限不足，请联系管理员!").toString();
        } else {
            response.sendRedirect(contextPath+"/login_p");
            errorResult = ReturnUtil.error(Code.LOGIN_FAIL,"验证失败，请重新登陆！").toString();
        }
        out.write(errorResult);
        out.flush();
        out.close();
    }
}
