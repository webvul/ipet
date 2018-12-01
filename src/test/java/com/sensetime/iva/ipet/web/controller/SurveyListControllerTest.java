package com.sensetime.iva.ipet.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sensetime.iva.ipet.common.ReturnMsg;
import com.sensetime.iva.ipet.entity.Project;
import com.sensetime.iva.ipet.entity.SurveyList;
import com.sensetime.iva.ipet.entity.WareList;
import com.sensetime.iva.ipet.service.ProjectService;
import com.sensetime.iva.ipet.service.SurveyListService;
import com.sensetime.iva.ipet.vo.form.SurveyListForm;
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
public class SurveyListControllerTest extends AbstractRestTxController{
    @Autowired
    ProjectService projectService;
    @Autowired
    private SurveyListService surveyListService;
    /**
     * 根据项目id查工勘清单
     */
    @Test
    public void test0() throws Exception {
        MvcResult result = getMockMvc().perform(get("/surveyList/3"))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"), ReturnMsg.SUCCESS);
    }
    /**
     * 根据项目id新增工勘清单
     */
    @Test
    public void test1() throws Exception {
        Project project = new Project();
        project.setName("surveyList test");
        projectService.insert(project);
        ArrayList<SurveyList> list = new ArrayList<>();
        SurveyList surveyList = new SurveyList();
        surveyList.setDc("test surveyList");
        surveyList.setProjectId(project.getId());
        list.add(surveyList);
        SurveyListForm surveyListForm = new SurveyListForm();
        surveyListForm.setSurveyLists(list);
        String json = JSON.toJSONString(surveyListForm);
        MvcResult result = getMockMvc().perform(post("/surveyList")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"), ReturnMsg.ADD_OR_UPDATE_SURVEY_LIST);
    }

    /**
     * 根据项目idg更新工勘清单
     */
    @Test
    public void test2() throws Exception {
        String json = JSON.toJSONString(new SurveyListForm());
        MvcResult result = getMockMvc().perform(post("/surveyList")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"), "新增或更新工勘清单信息失败");
    }

    /**
     * 根据项目idg更新工勘清单
     */
    @Test
    public void test3() throws Exception {
        Project project = new Project();
        project.setName("surveyList test");
        projectService.insert(project);
        SurveyList surveyList = new SurveyList();
        surveyList.setDc("test surveyList create");
        surveyList.setProjectId(project.getId());
        surveyListService.insert(surveyList);
        List<SurveyList> list = surveyListService.selectByProjectId(project.getId());
        list.get(0).setDc("test wareList update");
        list.add(surveyList);
        SurveyListForm surveyListForm = new SurveyListForm();
        surveyListForm.setSurveyLists(list);
        String json = JSON.toJSONString(surveyListForm);
        MvcResult result = getMockMvc().perform(post("/surveyList")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"), ReturnMsg.ADD_OR_UPDATE_SURVEY_LIST);
        List<SurveyList> surveyLists = surveyListService.selectAll();
        if(surveyLists!=null&&surveyLists.size()>0){
            for (SurveyList w: surveyLists) {
                surveyListService.deleteByPrimaryKey(w.getId());
                projectService.deleteByPrimaryKey(w.getProjectId());
            }
        }
    }
}
