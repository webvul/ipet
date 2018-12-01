package com.sensetime.iva.ipet.config.security;

import com.sensetime.iva.ipet.common.IConstant;
import com.sensetime.iva.ipet.entity.AccountEntity;
import com.sensetime.iva.ipet.entity.LoginInfoEntity;
import com.sensetime.iva.ipet.service.LoginInfoService;
import com.sensetime.iva.ipet.web.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.event.AuthorizationFailureEvent;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationFailureDisabledEvent;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

/**
 * @author : ChaiLongLong
 * @date : 2018/9/13 15:50
 * 监听内部校验成功事件，用于登陆记录（包含登陆以及remember-me）
 */
@Component
public class AuthenticationApplicationListener{

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationApplicationListener.class);

    @Autowired
    LoginInfoService loginInfoService;
    @Autowired
    RedisTemplate redisTemplate;
    @Value("${user.redisTimeout}")
    private int redisTimeout;

    @EventListener
    public void handleInteractiveAuthenticationSuccess(InteractiveAuthenticationSuccessEvent event) {
        logger.info("AuthenticationApplicationListener "+event.getAuthentication().getClass().getName());

        AccountEntity accountEntity = (AccountEntity) event.getAuthentication().getPrincipal();
        logger.info("get accountEntity [{}]",accountEntity);
        LoginInfoEntity loginInfoEntity = new LoginInfoEntity();
        loginInfoEntity.setName(accountEntity.getUsername());

        loginInfoEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));

        if (RememberMeAuthenticationToken.class.isAssignableFrom(event.getAuthentication().getClass())) {
            redisTemplate.opsForValue().set(accountEntity.getUsername(),accountEntity,redisTimeout, TimeUnit.MINUTES);
            loginInfoEntity.setType(IConstant.LOGIN_INFO_TYPE_REMEMBER_ME);
        }
        if(UsernamePasswordAuthenticationToken.class.isAssignableFrom(event.getAuthentication().getClass())){
            loginInfoEntity.setType(IConstant.LOGIN_INFO_TYPE_LOGIN);
        }
        loginInfoService.addLoginInfo(loginInfoEntity);
    }


}
