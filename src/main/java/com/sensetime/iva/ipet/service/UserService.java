package com.sensetime.iva.ipet.service;

import com.sensetime.iva.ipet.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @author : ChaiLongLong
 * @date : 2018/9/12 10:36
 */
@Component
public class UserService implements UserDetailsService {

    @Autowired
    AccountMapper accountMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return accountMapper.loadUserByUsername(s);
    }

}
