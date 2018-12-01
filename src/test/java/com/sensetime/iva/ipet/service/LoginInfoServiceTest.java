package com.sensetime.iva.ipet.service;

import com.sensetime.iva.ipet.common.IConstant;
import com.sensetime.iva.ipet.entity.LoginInfoEntity;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author : ChaiLongLong
 * @date : 2018/9/12 17:21
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LoginInfoServiceTest extends AbstractServiceTxTest {

    @Autowired
    LoginInfoService loginInfoService;

    private static int id = 0;
    private static String name = "admin";

    @Test
    public void test1AddLoginInfo(){
        LoginInfoEntity loginInfoEntity = new LoginInfoEntity();
        loginInfoEntity.setName(name);
        loginInfoEntity.setType(IConstant.LOGIN_INFO_TYPE_LOGIN);
        loginInfoEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        loginInfoService.addLoginInfo(loginInfoEntity);
        id = loginInfoEntity.getId();
        Assert.assertTrue(id != 0);
    }

    @Test
    public void test2QueryById(){
        LoginInfoEntity loginInfoEntity = loginInfoService.queryById(id);
        Assert.assertNotNull(loginInfoEntity);
    }

    @Test
    public void test3QueryByName(){
        List<LoginInfoEntity> loginInfoEntityList = loginInfoService.queryByName(name);
        Assert.assertNotNull(loginInfoEntityList);
        Assert.assertTrue(loginInfoEntityList.size() > 0);
    }

    @Test
    public void test4GetAll(){
        List<LoginInfoEntity> loginInfoEntityList = loginInfoService.getAll();
        Assert.assertNotNull(loginInfoEntityList);
        Assert.assertTrue(loginInfoEntityList.size() > 0);
    }

    @Test
    public void test5DeleteById(){
        loginInfoService.deleteLoginInfoById(id);
        LoginInfoEntity loginInfoEntity = loginInfoService.queryById(id);
        Assert.assertNull(loginInfoEntity);
    }



}
