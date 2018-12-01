package com.sensetime.iva.ipet.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sensetime.iva.ipet.common.Code;
import com.sensetime.iva.ipet.entity.ConvertTree;
import com.sensetime.iva.ipet.entity.TreeNode;
import com.sensetime.iva.ipet.service.LoginInfoService;
import com.sensetime.iva.ipet.service.ResourceService;
import com.sensetime.iva.ipet.service.UserService;
import com.sensetime.iva.ipet.util.AccountUtils;
import com.sensetime.iva.ipet.util.ReturnUtil;
import com.sensetime.iva.ipet.vo.form.AccountForm;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.cors.CorsUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author : ChaiLongLong
 * @date : 2018/7/31 15:51
 * Spring Security默认是禁用注解的，要想开启注解，需要在继承WebSecurityConfigurerAdapter
 * 的类上加@EnableGlobalMethodSecurity注解，并在该类中将AuthenticationManager定义为Bean
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    MyAuthenticationProvider authenticationProvider;
    @Autowired
    UrlFilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource;
    @Autowired
    UrlAccessDecisionManager urlAccessDecisionManager;
    @Autowired
    AuthenticationAccessDeniedHandler authenticationAccessDeniedHandler;
    @Autowired
    ResourceService resourceService;
    @Autowired
    DataSource dataSource;
    @Autowired
    UserService userService;
    @Autowired
    LoginInfoService loginInfoService;
    @Autowired
    UnAuthenticationExceptionEntryPoint unAuthenticationExceptionEntryPoint;
    @Value("${user.rememberMeTimeout}")
    private int rememberMeTimeout;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    public void configure(WebSecurity web) {
        //
        web.ignoring().antMatchers(
                /** 放行测试websocket、remember_me 等需要的静态资源获取请求 */
                "/queue.html","/socket/**","/testlogin.html","/static/**",
                /** 放行下载项目相关附件请求 */
                "/load/**",
                /** 放行登陆异常检测 */
                "/login_p",
                /** 放行注销请求 */
                "/logout",
                /** 放行websocket连接和describe 请求 */
                "/queueServer/**","/user/**/message",
                /** 放行swagger请求 */
                "/v2/api-docs","/swagger-resources/configuration/ui",
                "/swagger-resources","/swagger-resources/configuration/security",
                "/swagger-ui.html");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.rememberMe()
                .tokenValiditySeconds(60*60*24*rememberMeTimeout)
                .tokenRepository(persistentTokenRepository())
                .userDetailsService(userService);

        http.exceptionHandling().authenticationEntryPoint(unAuthenticationExceptionEntryPoint);

        http.authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setSecurityMetadataSource(urlFilterInvocationSecurityMetadataSource);
                        o.setAccessDecisionManager(urlAccessDecisionManager);
                        return o;
                    }
                }).and().formLogin().loginPage("/login_p").loginProcessingUrl("/login").usernameParameter("username").passwordParameter("password").permitAll().failureHandler(new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                httpServletResponse.setContentType("application/json;charset=utf-8");
                PrintWriter out = httpServletResponse.getWriter();
                String errorResult;
                if (e instanceof UsernameNotFoundException || e instanceof BadCredentialsException) {
                    errorResult = ReturnUtil.error(Code.LOGIN_FAIL,"用户名或密码输入错误，登录失败").toString();
                } else if (e instanceof AccountExpiredException || e instanceof CredentialsExpiredException){
                    errorResult = ReturnUtil.error(Code.LOGIN_FAIL,"登陆超时！").toString();
                } else if (e instanceof DisabledException) {
                    errorResult = ReturnUtil.error(Code.LOGIN_FAIL,"账户未启用，登录失败，请联系管理员!").toString();
                } else {
                    errorResult = ReturnUtil.error(Code.LOGIN_FAIL,"登录失败!").toString();
                }
                out.write(errorResult);
                out.flush();
                out.close();
            }
        }).successHandler(new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                httpServletResponse.setContentType("application/json;charset=utf-8");
                httpServletResponse.setHeader("Access-Control-Allow-Origin", httpServletRequest.getHeader("Origin"));
                httpServletResponse.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, If-Modified-Since");
                httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
                httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
                httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
                PrintWriter out = httpServletResponse.getWriter();
                ObjectMapper objectMapper = new ObjectMapper();
                AccountForm accountForm = new AccountForm();
                BeanUtils.copyProperties(AccountUtils.getCurrentHr(),accountForm);

                List<com.sensetime.iva.ipet.entity.Resource> resourceList = resourceService.getAccountResourceByAccId(accountForm.getId());
                ConvertTree convertTree = new ConvertTree<>();
                List<TreeNode> result= convertTree.getForest(resourceList,"code", "parent");
                accountForm.setResources(result);
                String s = ReturnUtil.success("登陆成功",objectMapper.writeValueAsString(accountForm)).toString();
                out.write(s);
                out.flush();
                out.close();
            }
        }).and().logout().logoutUrl("/logout").logoutSuccessUrl("/login_p").invalidateHttpSession(true).deleteCookies("JSESSIONID").permitAll().and().cors().and().csrf().disable().exceptionHandling().accessDeniedHandler(authenticationAccessDeniedHandler);
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    /**
     * 记住我功能
     * @return
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        //自动创建数据库表，使用一次后注释掉，不然会报错
        //ipet.sql脚本中已添加建表脚本，此处不需要再使用
        //jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }


}