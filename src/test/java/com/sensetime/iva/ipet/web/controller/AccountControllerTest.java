package com.sensetime.iva.ipet.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.sensetime.iva.ipet.config.dozer.EjbGenerator;
import com.sensetime.iva.ipet.entity.AccountEntity;
import com.sensetime.iva.ipet.entity.RoleEntity;
import com.sensetime.iva.ipet.service.AccountService;
import com.sensetime.iva.ipet.service.AreaService;
import com.sensetime.iva.ipet.service.RoleService;
import com.sensetime.iva.ipet.util.Md5Util;
import com.sensetime.iva.ipet.vo.form.AccountEditForm;
import com.sensetime.iva.ipet.vo.form.AccountForm;
import com.sensetime.iva.ipet.vo.form.RoleForm;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author : ChaiLongLong
 * @date ate: 2018/8/3 17:29
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AccountControllerTest extends AbstractRestTxController{
    @Autowired
    AccountService accountService;
    @Autowired
    AreaService areaService;
    @Autowired
    RoleService roleService;
    @Autowired
    protected EjbGenerator ejbGenerator = new EjbGenerator();

    /**
     * 初始化SecurityContextHolder参数，用于使用AccountUtils.getCurrentHr()功能模块的测试初始化数据
     */
    public void initSecurityContextDate(){
        //根据用户名username加载userDetails
        UserDetails userDetails = accountService.loadAccountByUsername("admin");

        //根据userDetails构建新的Authentication,这里使用了
        //PreAuthenticatedAuthenticationToken当然可以用其他token,如UsernamePasswordAuthenticationToken
        PreAuthenticatedAuthenticationToken authentication =
                new PreAuthenticatedAuthenticationToken(userDetails, userDetails.getPassword(),userDetails.getAuthorities());

        //设置authentication中details
//            authentication.setDetails(new WebAuthenticationDetails(request));

        //存放authentication到SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(authentication);
//            HttpSession session = request.getSession(true);
        //在session中存放security context,方便同一个session中控制用户的其他操作
//            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

    }

    /**
     * 测试获取用户列表 查询不传分页参数
     * @throws Exception
     */
    @Test
    public void test10GetAccountList() throws Exception {

        MvcResult result = getMockMvc().perform(get("/account"))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"),"获取用户列表成功");
    }

    /**
     * 测试获取用户列表 查询传递分页参数
     * @throws Exception
     */
    @Test
    public void test11GetAccountList() throws Exception {

        MvcResult result = getMockMvc().perform(get("/account?page=1&size=20"))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"),"获取用户列表成功");
    }

    /**
     *  测试获取用户列表 查询模糊查询
     * @throws Exception
     */
    @Test
    public void test12GetAccountList() throws Exception {
        MvcResult result = getMockMvc().perform(get("/account?username=test"))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"),"获取用户列表成功");
    }

    /**
     * 测试获取用户信息
     * @throws Exception
     */
    @Test
    public void test13GetAccount() throws Exception {
        AccountEntity accountEntity = accountService.loadAccountByUsername("admin");
        MvcResult result = getMockMvc().perform(get("/account/"+accountEntity.getId()))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        AccountForm account = JSON.parseObject(jsonObject.get("data").toString(), new TypeReference<AccountForm>() {});
        Assert.assertEquals(jsonObject.get("desc"),"获取用户信息成功");
        Assert.assertEquals(account.getUsername(),"admin");
    }

    /**
     * 测试新增用户 用户名已存在
     * @throws Exception
     */
    @Test
    public void test14AddAccountFail1() throws Exception {
        AccountEditForm accountForm = new AccountEditForm();
        accountForm.setUsername("admin");
        accountForm.setName("testAccountName");
        accountForm.setType(1);
        String json = JSON.toJSONString(accountForm);

        MvcResult result = getMockMvc().perform(post("/account")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"),"用户名已存在");

    }

    /**
     * 测试新增用户 类型不能为空
     * @throws Exception
     */
    @Test
    public void test15AddAccountFail2() throws Exception {
        AccountEditForm accountForm = new AccountEditForm();
        accountForm.setUsername("testUsername");
        accountForm.setName("testAccountName");
        String json = JSON.toJSONString(accountForm);

        MvcResult result = getMockMvc().perform(post("/account")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"),"类型不能为空");

    }

    /**
     * 测试新增用户 普通用户密码不能为空
     * @throws Exception
     */
    @Test
    public void test16AddAccountFail3() throws Exception {
        AccountEditForm accountForm = new AccountEditForm();
        accountForm.setUsername("testUsername");
        accountForm.setName("testAccountName");
        accountForm.setType(1);
        String json = JSON.toJSONString(accountForm);

        MvcResult result = getMockMvc().perform(post("/account")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"),"普通用户密码不能为空");

    }

    /**
     * 测试新增用户 区域不存在
     * @throws Exception
     */
    @Test
    public void test17AddAccountFail4() throws Exception {
        AccountEditForm accountForm = new AccountEditForm();
        accountForm.setUsername("testUsername");
        accountForm.setPassword("test");
        accountForm.setName("testAccountName");
        accountForm.setType(1);
        accountForm.setAreaId(999);
        String json = JSON.toJSONString(accountForm);

        MvcResult result = getMockMvc().perform(post("/account")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"),"区域不存在");

    }

    /**
     * 测试新增用户 角色不存在
     * @throws Exception
     */
    @Test
    @Transactional
    public void test18AddAccountFail5() throws Exception {
        AccountEditForm accountForm = new AccountEditForm();
        accountForm.setUsername("testUsername");
        accountForm.setPassword("test");
        accountForm.setName("testAccountName");
        accountForm.setType(1);
        accountForm.setAreaId(areaService.getAll().get(0).getId());
        List<RoleForm> roleForms = new ArrayList<>();
        RoleForm roleForm = new RoleForm();
        roleForm.setName("testRoleName");
        roleForm.setRoleName("Role_test");
        roleForms.add(roleForm);
        accountForm.setRoles(roleForms);
        String json = JSON.toJSONString(accountForm);

        MvcResult result = getMockMvc().perform(post("/account")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"),"角色不存在");

    }

    /**
     * 测试新增用户 成功
     * @throws Exception
     */
    @Test
    public void test19AddAccountSuccess() throws Exception {
        AccountEditForm accountEditForm = new AccountEditForm();
        accountEditForm.setUsername("testUsername");
        accountEditForm.setPassword("test");
        accountEditForm.setName("testAccountName");
        accountEditForm.setType(1);
        accountEditForm.setAreaId(areaService.getAll().get(0).getId());

        List<RoleEntity> roleEntities = roleService.getAll();
        List<RoleForm> roleForms = ejbGenerator.convert(roleEntities,RoleForm.class);
        accountEditForm.setRoles(roleForms);
        String json = JSON.toJSONString(accountEditForm);

        initSecurityContextDate();

        MvcResult result = getMockMvc().perform(post("/account")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"),"新增用户成功");
    }

    /**
     * 测试更新用户  区域不存在
     * @throws Exception
     */
    @Test
    public void test20TestUpdateAccountFail() throws Exception {

        AccountEntity accountEntity = accountService.loadAccountByUsername("testUsername");
        AccountEditForm accountEditForm = new AccountEditForm();
        BeanUtils.copyProperties(accountEntity,accountEditForm);
        accountEditForm.setAreaId(999);
        String json = JSON.toJSONString(accountEditForm);
        MvcResult result = getMockMvc().perform(put("/account")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"),"区域不存在");

    }

    /**
     * 测试更新用户  区域不存在
     * @throws Exception
     */
    @Test
    @Transactional
    public void test21TestUpdateAccountFail2() throws Exception {

        AccountEntity accountEntity = accountService.loadAccountByUsername("testUsername");
        AccountEditForm accountEditForm = new AccountEditForm();
        BeanUtils.copyProperties(accountEntity,accountEditForm);
        List<RoleForm> roleForms = new ArrayList<>();
        RoleForm roleForm = new RoleForm();
        roleForm.setName("testRoleName");
        roleForm.setRoleName("Role_test");
        roleForms.add(roleForm);
        accountEditForm.setRoles(roleForms);
        String json = JSON.toJSONString(accountEditForm);
        MvcResult result = getMockMvc().perform(put("/account")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"),"角色不存在");
    }

    /**
     * 测试更新用户 成功
     * @throws Exception
     */
    @Test
    @Transactional
    public void test22TestUpdateAccountSuccess() throws Exception {

        AccountEntity accountEntity = accountService.loadAccountByUsername("testUsername");
        AccountEditForm accountEditForm = new AccountEditForm();
        BeanUtils.copyProperties(accountEntity, accountEditForm);
        List<RoleForm> roleForms = ejbGenerator.convert(accountEntity.getRoles(),RoleForm.class);
        accountEditForm.setRoles(roleForms);
        accountEditForm.setPassword("12345");
        String json = JSON.toJSONString(accountEditForm);
        MvcResult result = getMockMvc().perform(put("/account")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"),"更新增用户成功");
        AccountEntity account = accountService.loadAccountByUsername("testUsername");
        Assert.assertEquals(account.getPassword(),Md5Util.encode("12345"));
    }

    /**
     * 测试删除用户信息
     * @throws Exception
     */
    @Test
    public void test23DeleteAccount() throws Exception {
        AccountEntity accountEntity = accountService.loadAccountByUsername("testUsername");
        MvcResult result = getMockMvc().perform(delete("/account/"+accountEntity.getId()))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"),"删除账号成功");
        Assert.assertNull(accountService.loadAccountByUsername("testUsername"));
    }

}
