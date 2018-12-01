package com.sensetime.iva.ipet.service;

import com.sensetime.iva.ipet.entity.AccountEntity;
import com.sensetime.iva.ipet.entity.RoleEntity;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : ChaiLongLong
 * @date : 2018/8/2 16:59
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RoleServiceTest extends AbstractServiceTxTest{

    @Autowired
    RoleService roleService;
    @Autowired
    AccountService accountService;

    private static Integer id;
    private static Integer accountId;

    @Test
    public void test1AddRole(){
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName("testAdd");
        roleEntity.setRoleName("Role_test");
        roleService.addRole(roleEntity);
        id = roleEntity.getId();
        Assert.assertTrue(id >=0);
    }

    @Test
    public void test2QueryRoleByNameOrRoleName(){
        List<RoleEntity> roleEntities = roleService.queryRoleByNameOrRoleName("testAdd","Role_test");
        Assert.assertTrue(roleEntities.size() >=0);
    }

    @Test
    public void test3FindAll(){
        List<RoleEntity> roleEntities = roleService.getAll();
        Assert.assertTrue(roleEntities.size()>=1);
    }

    @Test
    public void test4UpdateRoleAndQueryById(){
        RoleEntity roleEntity = roleService.queryById(id);
        roleEntity.setName("testRoleUpdate");
        roleService.updateRole(roleEntity);
        RoleEntity role = roleService.queryById(id);
        Assert.assertEquals(role.getName(),"testRoleUpdate");
    }

    @Test
    public void test5DeleteRole(){
        roleService.deleteRole(id);
        Assert.assertNull(roleService.queryById(id));
    }

    @Test
    public void test6AddRolesForAccount(){
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUsername("testAccountUsername");
        accountEntity.setName("testAccountName");
        accountEntity.setType(1);
        accountService.addAccount(accountEntity);

        accountId = accountEntity.getId();
        List<RoleEntity> roleEntities = roleService.getAll();
        roleService.addRolesForAccount(accountId,roleEntities);
        Assert.assertTrue("testAccountUsername".equals(accountService.queryAccountById(accountId).getUsername()));
    }

    @Test
    public void test7DeleteAccountRoleByAccId(){
        roleService.deleteAccountRoleByAccId(accountId);
        accountService.deleteAccountById(accountId);
        Assert.assertNull(accountService.queryAccountById(accountId));
    }

}
