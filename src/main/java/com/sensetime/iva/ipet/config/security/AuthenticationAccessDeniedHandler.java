package com.sensetime.iva.ipet.config.security;

import com.sensetime.iva.ipet.common.Code;
import com.sensetime.iva.ipet.util.ReturnUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author : ChaiLongLong
 * @date : 2018/7/31 15:51
 */
@Component
public class AuthenticationAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse resp, AccessDeniedException e) throws IOException{
        resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.write(ReturnUtil.error(Code.UNAUTHORIZED,"权限不足，请联系管理员!").toString());
        out.flush();
        out.close();
    }
}
