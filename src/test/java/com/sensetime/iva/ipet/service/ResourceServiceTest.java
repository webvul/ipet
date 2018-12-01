package com.sensetime.iva.ipet.service;

import com.alibaba.fastjson.JSON;
import com.sensetime.iva.ipet.common.PredefineResource;
import com.sensetime.iva.ipet.entity.*;
import com.sensetime.iva.ipet.entity.enumeration.RequestMethod;
import com.sensetime.iva.ipet.entity.enumeration.ResourceType;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ChaiLongLong
 * @date : 2018/8/1 11:22
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ResourceServiceTest extends AbstractServiceTxTest{

    @Autowired
    ResourceService resourceService;
    @Autowired
    RoleService roleService;
    @Autowired
    AccountService accountService;

    @Test
    public void test1GetAll(){
        List<Resource> resourceList  = resourceService.getAll();
        System.out.println(JSON.toJSONString(resourceList));
    }

    @Test
    @Transactional
    public void test2AddResource(){
        resourceService.addResource(new Resource("个人信息","test_code", ResourceType.NODE, PredefineResource.ACCOUNT_MANAGEMENT_NODE_CODE,PredefineResource.MESSAGE_NODE_CODE, 4, RequestMethod.GET));
        System.out.println(resourceService.queryByCode("test_code"));
    }

    @Test
    @Transactional
    public void test3UpdateResource(){
        resourceService.addResource(new Resource("个人信息","test_code", ResourceType.NODE, PredefineResource.ACCOUNT_MANAGEMENT_NODE_CODE,PredefineResource.MESSAGE_NODE_CODE, 4,RequestMethod.GET));
        Resource resource = resourceService.queryByCode("test_code");
        Assert.assertNotNull(resource);
        resource.setPath("test_path");
        resourceService.updateResource(resource);
        Assert.assertEquals("test_path",resourceService.queryByCode("test_code").getPath());
    }

    @Test
    @Transactional
    public void test4AddResourceForRole(){
        RoleEntity roleEntity = roleService.queryRoleByRoleName("ROLE_admin");
        Resource resource = new Resource("test_name","test_code", ResourceType.NODE,  PredefineResource.ACCOUNT_MANAGEMENT_NODE_CODE,PredefineResource.MESSAGE_NODE_CODE, 4, RequestMethod.GET);
        resourceService.addResource(resource);
        resourceService.addResourceForRole(roleEntity.getId(),resource.getId());
    }

    @Test
    @Transactional
    public void test6QueryResourceAndRolesByPathAndMethod(){
        List<Resource> allResource = resourceService.queryResourceAndRolesByPathAndMethod("/account", "GET");
        Assert.assertNotNull(allResource);
    }

    @Test
    @Transactional
    public void test7GetRoleResourceIdListByRoleId(){
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRoleName("resource_test7");
        roleEntity.setName("resource_test7");
        roleService.addRole(roleEntity);
        List<Integer> roleResources = resourceService.getRoleResourceIdListByRoleId(roleEntity.getId());
        List<Integer> formResources = new ArrayList<>();
        formResources.add(344);
        formResources.add(345);
        formResources.add(346);
        formResources.add(347);
        formResources.add(348);
        List<Integer> formResourcesTemp = formResources;
        //求差集获取新增权限
        formResourcesTemp.removeAll(roleResources);
        //求差集获取删除权限
        roleResources.removeAll(formResources);
        Assert.assertTrue(formResourcesTemp.size() == 5);
        Assert.assertTrue(roleResources.size() == 0);

    }


}
