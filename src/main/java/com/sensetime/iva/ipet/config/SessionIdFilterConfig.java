package com.sensetime.iva.ipet.config;

import com.sensetime.iva.ipet.config.filter.HttpRequestMDCFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author  gongchao
 */
@Configuration
public class SessionIdFilterConfig {
    @Bean
    public FilterRegistrationBean sessionIdFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean(new HttpRequestMDCFilter());
        registration.addUrlPatterns("/*");
        registration.setName("sessionIdFilter");
        return registration;
    }
}
