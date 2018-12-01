package com.sensetime.iva.ipet.config;

import com.sensetime.iva.ipet.web.interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 *
 * @author yore
 */
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {
    private final AuthInterceptor authInterceptor;

    @Autowired
    public MvcConfig(AuthInterceptor authInterceptor) {
        this.authInterceptor = authInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 鉴权
        registry.addInterceptor(authInterceptor);
    }

}
