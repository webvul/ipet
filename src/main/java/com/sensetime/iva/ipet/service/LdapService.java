package com.sensetime.iva.ipet.service;

import com.sensetime.iva.ipet.entity.LdapUser;

/**
 * @author yore
 */
public interface LdapService {

    /**
     * 进行LDAP认证
     * 成功返回 LDAP用户信息
     * 失败返回 null (代表用户名或密码错误)
     *
     * @param username String LDAP账号
     * @param password String LDAP密码
     * @return LdapUser LDAP用户信息
     */
    LdapUser authenticate(String username, String password);

}
