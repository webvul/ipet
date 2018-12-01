package com.sensetime.iva.ipet.web.controller;

import com.sensetime.iva.ipet.common.Code;
import com.sensetime.iva.ipet.service.AccountService;
import com.sensetime.iva.ipet.service.ResourceService;
import com.sensetime.iva.ipet.util.ReturnUtil;
import com.sensetime.iva.ipet.vo.form.AccountForm;
import com.sensetime.iva.ipet.vo.response.ResponseBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author: ChaiLongLong
 */
@Api(description  = "登陆、登出")
@RestController
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    AccountService accountService;
    @Autowired
    ResourceService resourceService;

    /**
     * 当权限验证失败，则跳转到 该处
     */
    @GetMapping("/login_p")
    public void login(HttpServletResponse resp) throws IOException {
        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.write(ReturnUtil.error(Code.NOT_LOGIN,"尚未登录，请登录!").toString());
        out.flush();
        out.close();
    }

    /**
     * 此处无实际作用，登陆相关功能由security完成，此处只为在swagger上提供API
     * @param username 登陆username
     * @param password 登陆 password
     * @return 登陆return
     */
    @PostMapping(value = "/login",produces="application/json;charset=UTF-8")
    public ResponseBody<AccountForm> loginMethod(@RequestParam(defaultValue = "") @ApiParam(name="username",value="账号",required=true)  String username,
                                                 @RequestParam(defaultValue = "") @ApiParam(name="password",value="密码",required=true) String password,
                                                 @RequestParam(defaultValue = "") @ApiParam(name="remember-me",value="记住我") String rememberMe) {
        return ReturnUtil.success("登录",null);
    }

    /**
     * 此处无实际作用，登陆相关功能由security完成，此处只为在swagger上提供API
     * @return 登出
     */
    @GetMapping("/logout")
    public ResponseBody logoutMethod(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Cookie[] cookies = request.getCookies();
        if(cookies!=null&&cookies.length>0){
            for(Cookie cookie: cookies){
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return ReturnUtil.success("注销成功");
    }

}
