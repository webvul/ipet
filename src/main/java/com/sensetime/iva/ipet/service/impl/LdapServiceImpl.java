package com.sensetime.iva.ipet.service.impl;

import com.sensetime.iva.ipet.entity.LdapUser;
import com.sensetime.iva.ipet.service.LdapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;

import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;

/**
 * @author yore
 */
@Service("LdapService")
public class LdapServiceImpl implements LdapService {

    private static final Logger logger = LoggerFactory.getLogger(LdapServiceImpl.class);
    private static final String LDAP_NAME = "name";
    private static final String LDAP_DISPLAY_NAME = "displayName";
    private static final String LDAP_MAIL = "mail";
    private static final String QUERY_PREFIX = "cn=";

    @Value("${ldap.domain:@domain.sensetime.com}")
    private String ldapDomain;

    private final LdapTemplate ldapTemplate;

    @Autowired
    public LdapServiceImpl(LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }

    @Override
    public LdapUser authenticate(String username, String password) {
        logger.debug("ldap authenticate param: username={}, password={}", username, password);
        String principle = username + ldapDomain;
        try {
            logger.debug("ldap authenticating...");
            logger.info("ldap authenticate: principle={}", principle);
            // 登录
            DirContext context = ldapTemplate.getContextSource().getContext(principle, password);
            // 获取用户信息
            String[] attrIds = new String[] {LDAP_NAME, LDAP_DISPLAY_NAME, LDAP_MAIL};
            Attributes info = context.getAttributes(QUERY_PREFIX + username, attrIds);
            // 用户信息封装
            Attribute name = info.get(LDAP_NAME);
            Attribute mail = info.get(LDAP_MAIL);
            Attribute displayName = info.get(LDAP_DISPLAY_NAME);
            LdapUser user = new LdapUser();
            if (name != null) {
                user.setName(name.toString());
            }
            if (mail != null) {
                user.setMail(mail.toString());
            }
            if (displayName != null) {
                user.setDisplayName(displayName.toString());
            }
            return user;
        } catch (Exception e) {
            logger.error("[{}] login error [{}]",principle,e.getMessage());
            return null;
        }
    }
}
