package com.sensetime.iva.ipet.service;

import com.sensetime.iva.ipet.common.IConstant;
import com.sensetime.iva.ipet.config.dozer.EjbGenerator;
import com.sensetime.iva.ipet.entity.AccountEntity;
import com.sensetime.iva.ipet.entity.Resource;
import com.sensetime.iva.ipet.entity.RoleEntity;
import org.apache.commons.collections4.map.MultiKeyMap;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.redis.core.RedisTemplate;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author : ChaiLongLong
 * @date : 2018/8/1 10:36
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AccountServiceTest extends AbstractServiceTxTest{

    @Autowired
    AccountService accountService;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    RoleService roleService;
    @Autowired
    protected EjbGenerator ejbGenerator = new EjbGenerator();

    private static int id;

    @Test
    public void test1AddAccount(){
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUsername("testAccountUsername");
        accountEntity.setName("testAccountName");
        accountEntity.setType(1);
        List<RoleEntity> roleEntities = new ArrayList<>();
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName("testRoleName");
        roleEntity.setRoleName("ROLE_test");
        roleService.addRole(roleEntity);
        roleEntities.add(roleEntity);
        accountEntity.setRoles(roleEntities);
        accountService.addAccount(accountEntity);
        id = accountEntity.getId();
        AccountEntity accountEntity1 = accountService.loadAccountByUsername("testAccountUsername");
        Assert.assertEquals(accountEntity1.getName(),"testAccountName");
    }

    @Test
    public void test2GetRolesByAccountId(){
        List<RoleEntity> roleEntities = accountService.getRolesByAccountId(id);
        Assert.assertEquals(roleEntities.get(0).getRoleName(),"ROLE_test");
    }

    @Test
    public void test3FindAllUsernameNotNull(){
        String username="test";
        List<AccountEntity> accountEntities = accountService.getAll(username);
        Assert.assertTrue(accountEntities.size()>0);
    }

    @Test
    public void test4FindAllUsernameNull(){
        List<AccountEntity> accountEntities = accountService.getAll("");
        Assert.assertTrue(accountEntities.size()>0);
    }

    @Test
    public void test5UpdateAccount(){
        AccountEntity accountEntity = accountService.queryAccountById(id);
        accountEntity.setName("testUpdateName");
        accountService.updateAccount(accountEntity);
        AccountEntity newAccount = accountService.queryAccountById(id);
        Assert.assertEquals(newAccount.getName(),"testUpdateName");
    }

    @Test
    public void test6QueryAccountsByRoleId(){
        List<RoleEntity> roleEntities = accountService.getRolesByAccountId(id);
        List<AccountEntity> accountEntities = accountService.queryAccountsByRoleId(roleEntities.get(0).getId());
        Assert.assertTrue(accountEntities.size()>0);
    }

    @Test
    public void test7DeleteAccount(){
        List<RoleEntity> roleEntities = roleService.queryRoleByAccountId(id);

        accountService.deleteAccountById(id);
        AccountEntity accountEntity = accountService.queryAccountById(id);
        Assert.assertNull(accountEntity);

        //删除账号分配的角色测试数据，正常逻辑不可删除
        roleService.deleteRole(roleEntities.get(0).getId());
    }

    /**
     * 测试添加账号时 添加的角色不存在  异常回滚
     */
    @Test
    public void test8AddAccountRoleUnExist(){
        try {
            AccountEntity accountEntity = new AccountEntity();
            accountEntity.setUsername("testAccountUsername");
            accountEntity.setName("testAccountName");
            accountEntity.setType(1);
            List<RoleEntity> roleEntities = new ArrayList<>();
            RoleEntity roleEntity = new RoleEntity();
            roleEntity.setName("testRoleName");
            roleEntity.setRoleName("Role_test");
            roleEntities.add(roleEntity);
            accountEntity.setRoles(roleEntities);
            accountService.addAccount(accountEntity);
        }catch (Exception e){
            //捕获DataIntegrityViolationException异常，并验证回滚
            Assert.assertTrue(e instanceof DataIntegrityViolationException);
            AccountEntity accountEntity = accountService.loadAccountByUsername("testAccountUsername");
            Assert.assertNull(accountEntity);
        }
    }


    @Test
    public void testRedis(){
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setName("testRedis");
        accountEntity.setUsername("testRedisUsername");
        redisTemplate.opsForValue().set(accountEntity.getUsername(),accountEntity,100,TimeUnit.SECONDS);
        AccountEntity redisAccount = (AccountEntity) redisTemplate.boundValueOps(accountEntity.getUsername()).get();
        Assert.assertNotNull(redisAccount);
    }

    @Test
    public void testQueryAccountByResourceName(){
        List<AccountEntity> accountEntities = accountService.queryAccountByResourceCode(IConstant.PROJECT_CONCLUSION_APPROVAL_RESOURCE_CODE);
        System.out.println(accountEntities.size());
    }

    @Test
    public void testGetAllPMAccount(){
        List<AccountEntity> accountEntities = accountService.getAllPM(null);
        System.out.println(accountEntities.size());
    }
}
