package com.sensetime.iva.ipet.config.security;

import com.sensetime.iva.ipet.common.IConstant;
import com.sensetime.iva.ipet.entity.AccountEntity;
import com.sensetime.iva.ipet.entity.LdapUser;
import com.sensetime.iva.ipet.service.AccountService;
import com.sensetime.iva.ipet.service.LdapService;
import com.sensetime.iva.ipet.util.Md5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * @author : ChaiLongLong
 * @date : 2018/8/2 10:32
 */
@Component
public class MyAuthenticationProvider implements AuthenticationProvider {
    private static final Logger logger = LoggerFactory.getLogger(MyAuthenticationProvider.class);

    @Autowired
    AccountService accountService;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    LdapService ldapService;

    @Value("${user.redisTimeout}")
    private int redisTimeout;

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        AccountEntity user = accountService.loadAccountByUsername(username);
        if(user == null){
            logger.error("Username not found");
            throw new BadCredentialsException("Username not found.");
        }
        //普通用户
        if(user.getType() == 1 && !(Md5Util.encode(password)).equals(user.getPassword())){
            logger.error("Wrong password");
            throw new BadCredentialsException("Wrong password.");
        }
        //LDAP用户
        if(user.getType() == IConstant.LDAPACCOUNTTYPE){
            LdapUser ldapUser = ldapService.authenticate(username, password);
            if(ldapUser == null){
                throw new BadCredentialsException("Username not found.");
            }
        }

        if(!user.isEnabled()){
            logger.error("账号未启用");
            throw new DisabledException("账号未启用");
        }

        redisTemplate.opsForValue().set(user.getUsername(),user,redisTimeout, TimeUnit.MINUTES);

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        return new UsernamePasswordAuthenticationToken(user, password, authorities);
    }
}
