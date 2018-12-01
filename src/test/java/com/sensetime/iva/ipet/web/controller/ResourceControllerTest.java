package com.sensetime.iva.ipet.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sensetime.iva.ipet.common.IConstant;
import com.sensetime.iva.ipet.entity.AccountEntity;
import com.sensetime.iva.ipet.entity.Resource;
import com.sensetime.iva.ipet.entity.RoleEntity;
import com.sensetime.iva.ipet.entity.TreeNode;
import com.sensetime.iva.ipet.service.AccountService;
import com.sensetime.iva.ipet.service.ResourceService;
import com.sensetime.iva.ipet.service.RoleService;
import com.sensetime.iva.ipet.vo.form.ResourceForm;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author : ChaiLongLong
 * @date : 2018/9/3 17:05
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ResourceControllerTest extends AbstractRestTxController {

    @Autowired
    AccountService accountService;
    @Autowired
    RoleService roleService;
    @Autowired
    ResourceService resourceService;

    /**
     * 获取用户资源权限
     * @throws Exception
     */
    @Test
    public void test1GetAccountResource() throws Exception {
        AccountEntity accountEntity = accountService.loadAccountByUsername("admin");
        MvcResult result = getMockMvc().perform(get("/resource/"+accountEntity.getId()))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        List<TreeNode> resultTreeNode = JSON.parseArray(jsonObject.get("data").toString(), TreeNode.class);
        Assert.assertEquals(jsonObject.get("desc"),"获取用户资源权限成功");
        Assert.assertTrue(resultTreeNode.size()>0);
    }

    /**
     * 获取所有资源权限
     * @throws Exception
     */
    @Test
    public void test2GetAllResource() throws Exception {
        MvcResult result = getMockMvc().perform(get("/resource"))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        List<TreeNode> resultTreeNode = JSON.parseArray(jsonObject.get("data").toString(), TreeNode.class);
        Assert.assertEquals(jsonObject.get("desc"),"获取所有资源权限成功");
        Assert.assertTrue(resultTreeNode.size()>0);
    }

    /**
     * 修改角色资源权限
     * @throws Exception
     */
    @Test
    @Transactional
    public void test3UpdateRoleResource() throws Exception {
        RoleEntity roleEntity = roleService.queryRoleByRoleName(IConstant.ROLE_MANAGER);
        List<Resource> resources = resourceService.getResourcesByRoleId(roleEntity.getId());
        int oldResourceSize = resources.size();
        ResourceForm resourceForm = new ResourceForm();
        resourceForm.setRoleId(roleEntity.getId());
        resources.remove(0);
        List<Integer> integers = new ArrayList<>();
        for (Resource resource : resources) {
            integers.add(resource.getId());
        }
        resourceForm.setResources(integers);

        String json = JSON.toJSONString(resourceForm);

        MvcResult result = getMockMvc().perform(put("/resource")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"),"修改角色资源权限成功");
        List<Resource> newResources = resourceService.getResourcesByRoleId(roleEntity.getId());
        Assert.assertTrue(newResources.size() == (oldResourceSize -1));
    }

}
