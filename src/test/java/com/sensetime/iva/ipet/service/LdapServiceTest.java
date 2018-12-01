package com.sensetime.iva.ipet.service;

import com.sensetime.iva.ipet.entity.LdapUser;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author : ChaiLongLong
 * @date : 2018/8/15 11:00
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LdapServiceTest extends AbstractServiceTxTest{

    @Autowired
    LdapService ldapService;

    @Test
    public void test1LdapAuthSuccess(){
        String username = "chailonglong_vendor";
        String password = "Cll18082282337";
        LdapUser ldapUser = ldapService.authenticate(username,password);
        System.out.println(ldapUser.toString());
    }

    @Test
    public void test2LdapAuthFail(){
        String username = "chailonglong1";
        String password = "Cll18082282337";
        LdapUser ldapUser = ldapService.authenticate(username,password);
        Assert.assertNull(ldapUser);
    }



}
