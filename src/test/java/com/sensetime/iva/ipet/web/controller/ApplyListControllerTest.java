package com.sensetime.iva.ipet.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sensetime.iva.ipet.common.ReturnMsg;
import com.sensetime.iva.ipet.entity.ApplyList;
import com.sensetime.iva.ipet.entity.Project;
import com.sensetime.iva.ipet.service.ApplyListService;
import com.sensetime.iva.ipet.service.ProjectService;
import com.sensetime.iva.ipet.vo.form.ApplyListForm;
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
public class ApplyListControllerTest extends AbstractRestTxController{
    @Autowired
    ProjectService projectService;
    @Autowired
    private ApplyListService applyListService;
    /**
     * 根据项目id查实施清单
     */
    @Test
    public void test0() throws Exception {
        MvcResult result = getMockMvc().perform(get("/applyList/3"))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"), ReturnMsg.APPLY_LIST);
    }
    /**
     * 根据项目id新增实施清单
     */
    @Test
    public void test1() throws Exception {
        Project project = new Project();
        project.setName("applyList test");
        projectService.insert(project);
        ArrayList<ApplyList> list = new ArrayList<>();
        ApplyList applyList = new ApplyList();
        applyList.setDetailJob("test applyList");
        applyList.setProjectId(project.getId());
        list.add(applyList);
        ApplyListForm applyListForm = new ApplyListForm();
        applyListForm.setApplyLists(list);
        String json = JSON.toJSONString(applyListForm);
        MvcResult result = getMockMvc().perform(post("/applyList")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"), ReturnMsg.CREATE_APPLY_LIST);
    }

    /**
     * 根据项目idg更新实施清单
     */
    @Test
    public void test2() throws Exception {
        String json = JSON.toJSONString(new ApplyListForm());
        MvcResult result = getMockMvc().perform(post("/applyList")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"), ReturnMsg.CREATE_APPLY_LIST_FAIL);
    }

    /**
     * 根据项目idg更新实施清单
     */
    @Test
    public void test3() throws Exception {
        Project project = new Project();
        project.setName("applyList test");
        projectService.insert(project);
        ApplyList applyList = new ApplyList();
        applyList.setDetailJob("test applyList create");
        applyList.setProjectId(project.getId());
        applyListService.insert(applyList);
        List<ApplyList> list = applyListService.selectByProjectId(project.getId());
        list.get(0).setDetailJob("test applyList update");
        list.add(applyList);
        ApplyListForm applyListForm = new ApplyListForm();
        applyListForm.setApplyLists(list);
        String json = JSON.toJSONString(applyListForm);
        MvcResult result = getMockMvc().perform(post("/applyList")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"), ReturnMsg.CREATE_APPLY_LIST);
    }
    /**
     * 导出项目验收相关信息的Excel直接访问
     * http://ip:port/ipet/v1/applyList/export/3
     * 3换成表apply_list对应的字段project_id的值即可
     */
}
