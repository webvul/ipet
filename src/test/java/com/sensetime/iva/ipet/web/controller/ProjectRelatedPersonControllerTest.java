package com.sensetime.iva.ipet.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sensetime.iva.ipet.entity.Project;
import com.sensetime.iva.ipet.entity.ProjectRelatedPerson;
import com.sensetime.iva.ipet.service.ProjectRelatedPersonService;
import com.sensetime.iva.ipet.service.ProjectService;
import com.sensetime.iva.ipet.vo.form.ProjectFilterForm;
import com.sensetime.iva.ipet.vo.form.ProjectRelatedPersonForm;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author  gongchao
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProjectRelatedPersonControllerTest extends AbstractRestTxController{
    @Autowired
    ProjectRelatedPersonService projectRelatedPersonService;
    @Autowired
    ProjectService projectService;
    /**
     * 查询项目
     */
    @Test
    public void test0() throws Exception {
        MvcResult result = getMockMvc().perform(get("/projectRelatedPerson/1"))
                .andExpect(status().isOk())// 模拟发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"), "获取项目干系人列表");
    }
    /**
     * 新增干系人信息失败
     * @throws Exception
     */
    @Test
    public void test1() throws Exception {
        //项目不存在
        ProjectRelatedPerson person = new ProjectRelatedPerson();
        person.setCompanyName("create company");
        person.setName("create zhangsan");
        ArrayList<ProjectRelatedPerson> list = new ArrayList<>();
        ProjectRelatedPersonForm projectRelatedPersonForm = new ProjectRelatedPersonForm();
        list.add(person);
        projectRelatedPersonForm.setPersonLists(list);
        String json = JSON.toJSONString(projectRelatedPersonForm);
        MvcResult result = getMockMvc().perform(post("/projectRelatedPerson")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"), "所在项目不存在,不能添加干系人信息");
    }
    /**
     * 新增干系人信息成功
     * @throws Exception
     */
    @Test
    public void test2() throws Exception {
        ProjectRelatedPerson person = new ProjectRelatedPerson();
        Project project = new Project();
        project.setName("projectName");
        projectService.insert(project);
        person.setProjectId(project.getId());
        person.setCompanyName("company");
        person.setName("zhangsan");
        ArrayList<ProjectRelatedPerson> list = new ArrayList<>();
        ProjectRelatedPersonForm projectRelatedPersonForm = new ProjectRelatedPersonForm();
        list.add(person);
        projectRelatedPersonForm.setPersonLists(list);
        String json = JSON.toJSONString(projectRelatedPersonForm);
        MvcResult result = getMockMvc().perform(post("/projectRelatedPerson")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"), "新增或更新项目干系人成功");
    }

    /**
     * 更新干系人信息成功
     * @throws Exception
     */
    @Test
    public void test3() throws Exception {
        ProjectRelatedPerson person = new ProjectRelatedPerson();
        person.setId(-2);
        person.setCompanyName("update company");
        person.setName("update zhangsan");
        ArrayList<ProjectRelatedPerson> list = new ArrayList<>();
        ProjectRelatedPersonForm projectRelatedPersonForm = new ProjectRelatedPersonForm();
        list.add(person);
        projectRelatedPersonForm.setPersonLists(list);
        String json = JSON.toJSONString(projectRelatedPersonForm);
        MvcResult result = getMockMvc().perform(post("/projectRelatedPerson")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"), "所在项目不存在,不能添加干系人信息");
    }
    /**
     * 更新干系人信息成功
     * @throws Exception
     */
    @Test
    public void test4() throws Exception {
        //选要有项目数据
        Project project = new Project();
        project.setName("projectName");
        projectService.insert(project);
        ProjectRelatedPerson person = new ProjectRelatedPerson();
        person.setId(2);
        person.setProjectId(project.getId());
        person.setCompanyName("create company");
        person.setName("create zhangsan");
        projectRelatedPersonService.insert(person);

        person.setCompanyName("update company");
        person.setName("update zhangsan");
        ArrayList<ProjectRelatedPerson> list = new ArrayList<>();
        ProjectRelatedPersonForm projectRelatedPersonForm = new ProjectRelatedPersonForm();
        list.add(person);
        projectRelatedPersonForm.setPersonLists(list);
        String json = JSON.toJSONString(projectRelatedPersonForm);
        MvcResult result = getMockMvc().perform(post("/projectRelatedPerson")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"), "新增或更新项目干系人成功");
    }
}
