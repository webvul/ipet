package com.sensetime.iva.ipet.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sensetime.iva.ipet.common.ReturnMsg;
import com.sensetime.iva.ipet.entity.DeliverList;
import com.sensetime.iva.ipet.entity.Project;
import com.sensetime.iva.ipet.service.DeliverListService;
import com.sensetime.iva.ipet.service.ProjectService;
import com.sensetime.iva.ipet.vo.form.DeliverListForm;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DeliverListControllerTest extends AbstractRestTxController{
    @Autowired
    ProjectService projectService;
    @Autowired
    private DeliverListService deliverListService;
    /**
     * 根据项目id查交付清单
     */
    @Test
    public void test0() throws Exception {
        MvcResult result = getMockMvc().perform(get("/deliverList/3"))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"), ReturnMsg.SUCCESS);
    }
    /**
     * 根据项目id新增交付物清单
     */
    @Test
    public void test1() throws Exception {
        Project project = new Project();
        project.setName("DeliverList test");
        projectService.insert(project);
        ArrayList<DeliverList> list = new ArrayList<>();
        DeliverList deliverList = new DeliverList();
        deliverList.setTarget("test DeliverList");
        deliverList.setProjectId(project.getId());
        list.add(deliverList);
        DeliverListForm deliverListForm = new DeliverListForm();
        deliverListForm.setDeliverLists(list);
        String json = JSON.toJSONString(deliverListForm);
        MvcResult result = getMockMvc().perform(post("/deliverList")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"), ReturnMsg.ADD_OR_UPDATE_DELIVER_LIST);
    }

    /**
     * 根据项目idg更新交付物清单
     */
    @Test
    public void test2() throws Exception {
        String json = JSON.toJSONString(new DeliverListForm());
        MvcResult result = getMockMvc().perform(post("/deliverList")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"), ReturnMsg.DELIVER_LIST_EMPTY);
    }

    /**
     * 根据项目idg更新交付物清单
     */
    @Test
    public void test3() throws Exception {
        Project project = new Project();
        project.setName("deliverList test");
        projectService.insert(project);
        DeliverList deliverList = new DeliverList();
        deliverList.setTarget("TEST deliverList CREATE");
        deliverList.setProjectId(project.getId());
        deliverListService.insert(deliverList);
        List<DeliverList> list = deliverListService.selectByProjectId(project.getId());
        list.get(0).setTarget("test deliverList update");
        list.add(deliverList);
        DeliverListForm deliverListForm = new DeliverListForm();
        deliverListForm.setDeliverLists(list);
        String json = JSON.toJSONString(deliverListForm);
        MvcResult result = getMockMvc().perform(post("/deliverList")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"), ReturnMsg.ADD_OR_UPDATE_DELIVER_LIST);
    }
}
