package com.sensetime.iva.ipet.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sensetime.iva.ipet.entity.RoleEntity;
import com.sensetime.iva.ipet.service.RoleService;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author : ChaiLongLong
 * @date : 2018/8/6 17:35
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RoleControllerTest extends AbstractRestTxController {

    @Autowired
    RoleService roleService;

    /**
     * 测试添加区域   失败
     * @throws Exception Exception
     */
    @Test
    public void test1AddRoleFail1() throws Exception {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName("超级管理员");
        roleEntity.setRoleName("ROLE_admin");

        String json = JSON.toJSONString(roleEntity);

        MvcResult result = getMockMvc().perform(post("/role")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"),"角色已存在");

    }

    /**
     * 测试添加区域   失败
     * @throws Exception 捕获
     */
    @Test
    public void test2AddRoleFail2() throws Exception {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName("");
        roleEntity.setRoleName("");

        String json = JSON.toJSONString(roleEntity);

        MvcResult result = getMockMvc().perform(post("/role")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"),"角色名不能为空");

    }

    /**
     * 测试添加角色   成功
     * @throws Exception 捕获
     */
    @Test
    @Transactional
    public void test3AddRoleSuccess() throws Exception {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName("testAddRole");
        roleEntity.setRoleName("testRoleName");

        String json = JSON.toJSONString(roleEntity);

        MvcResult result = getMockMvc().perform(post("/role")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"),"新增角色成功");
    }

    /**
     * 测试更新角色   角色已存在
     * @throws Exception 捕获
     */
    @Test
    @Transactional
    public void test4UpdateRoleFail1() throws Exception {

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName("testAddRole");
        roleEntity.setRoleName("testRoleName");
        roleService.addRole(roleEntity);

        roleEntity.setRoleName("ROLE_admin");
        String json = JSON.toJSONString(roleEntity);

        MvcResult result = getMockMvc().perform(put("/role")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"),"角色已存在");
    }
    /**
     * 测试更新角色   成功
     * @throws Exception 捕获
     */
    @Test
    @Transactional
    public void test5UpdateRoleSuccess() throws Exception {
        List<RoleEntity> roleEntities = roleService.queryRoleByNameOrRoleName("超级管理员","ROLE_admin");
        RoleEntity roleEntity = roleEntities.get(0);
        roleEntity.setName("testUpdateRole");
        roleEntity.setRoleName("testRoleName");
        String json = JSON.toJSONString(roleEntity);

        MvcResult result = getMockMvc().perform(put("/role")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"),"更新角色成功");
    }


}
