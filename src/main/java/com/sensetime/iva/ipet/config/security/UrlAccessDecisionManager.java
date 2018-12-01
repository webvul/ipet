package com.sensetime.iva.ipet.config.security;

import com.sensetime.iva.ipet.common.IConstant;
import com.sensetime.iva.ipet.entity.AccountEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/**
 * @author : ChaiLongLong
 * @date :  2018/7/31 15:51
 */
@Component
public class UrlAccessDecisionManager implements AccessDecisionManager {
    private static final Logger logger = LoggerFactory.getLogger(UrlAccessDecisionManager.class);

    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    RedisTemplate redisTemplate;

    @Value("${isOpenSecurity}")
    private boolean isOpenSecurity;

    @Value("${user.redisTimeout}")
    private int redisTimeout;


    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, AuthenticationException {
        Iterator<ConfigAttribute> iterator = collection.iterator();

        Object object = authentication.getPrincipal();
        if(isOpenSecurity){
            if(!IConstant.UNLOGINPRINCIPAL.equals(authentication.getPrincipal())){
                AccountEntity accountEntity = (AccountEntity) object;
                AccountEntity redisAccount = (AccountEntity) redisTemplate.boundValueOps(accountEntity.getUsername()).get();
                if(redisAccount == null){
                    logger.error("登陆超时");
                    throw new BadCredentialsException("登陆超时！");
                }
            }else {
                logger.error("未登录");
                throw new BadCredentialsException("未登录！");
            }

            while (iterator.hasNext()) {
                ConfigAttribute ca = iterator.next();
                //当前请求需要的权限
                String needRole = ca.getAttribute();
                if ("ROLE_NOT_MATCH".equals(needRole)) {
                    if (authentication instanceof AnonymousAuthenticationToken) {
                        logger.error("请求需要的权限时未登录");
                        throw new BadCredentialsException("未登录");
                    } else {
                        logger.error("请求需要的权限时无访问资源");
                        throw new AccessDeniedException("无访问资源!");
                    }
                }
                //当前用户所具有的权限
                Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
                for (GrantedAuthority authority : authorities) {
                    if (authority.getAuthority().equals(needRole)) {
                        //每次成功访问 刷新redis的失活时间
                        AccountEntity accountEntity = (AccountEntity) object;
                        redisTemplate.expire(accountEntity.getUsername(),redisTimeout, TimeUnit.MINUTES);
                        return;
                    }
                }
            }
            logger.error("请求需要的权限时权限不足");
            throw new AccessDeniedException("权限不足!");
        }
        return;
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}