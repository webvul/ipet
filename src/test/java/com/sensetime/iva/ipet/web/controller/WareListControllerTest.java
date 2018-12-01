package com.sensetime.iva.ipet.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sensetime.iva.ipet.entity.Project;
import com.sensetime.iva.ipet.entity.WareList;
import com.sensetime.iva.ipet.service.ProjectService;
import com.sensetime.iva.ipet.service.WareListService;
import com.sensetime.iva.ipet.vo.form.WareListForm;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WareListControllerTest extends AbstractRestTxController{
    @Autowired
    ProjectService projectService;
    @Autowired
    private WareListService wareListService;
    /**
     * 根据项目id查软硬件清单
     */
    @Test
    public void test0() throws Exception {
        MvcResult result = getMockMvc().perform(get("/wareList/3"))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"), "获取软硬件列表");
    }
    /**
     * 根据项目id新增软硬件清单
     */
    @Test
    public void test1() throws Exception {
        Project project = new Project();
        project.setName("wareList test");
        projectService.insert(project);
        ArrayList<WareList> list = new ArrayList<>();
        WareList wareList = new WareList();
        wareList.setConfigCode("test wareList");
        wareList.setProjectId(project.getId());
        list.add(wareList);
        WareListForm wareListForm = new WareListForm();
        wareListForm.setWareLists(list);
        String json = JSON.toJSONString(wareListForm);
        MvcResult result = getMockMvc().perform(post("/wareList")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"), "新增或更新软硬件信息成功");
    }

    /**
     * 根据项目idg更新交付物清单
     */
    @Test
    public void test2() throws Exception {
        String json = JSON.toJSONString(new WareListForm());
        MvcResult result = getMockMvc().perform(post("/wareList")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"), "新增或更新软硬件信息失败");
    }

    /**
     * 根据项目idg更新软硬件清单
     */
    @Test
    public void test3() throws Exception {
        Project project = new Project();
        project.setName("deliverList test");
        projectService.insert(project);
        WareList wareList = new WareList();
        wareList.setConfigCode("test wareList create");
        wareList.setProjectId(project.getId());
        wareListService.insert(wareList);
        List<WareList> list = wareListService.selectByProjectId(project.getId());
        list.get(0).setConfigCode("test wareList update");
        list.add(wareList);
        WareListForm wareListForm = new WareListForm();
        wareListForm.setWareLists(list);
        String json = JSON.toJSONString(wareListForm);
        MvcResult result = getMockMvc().perform(post("/wareList")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"), "新增或更新软硬件信息成功");
        List<WareList> wareLists = wareListService.selectAll();
        if(wareLists!=null&&wareLists.size()>0){
            for (WareList w: wareLists) {
                wareListService.deleteByPrimaryKey(w.getId());
                projectService.deleteByPrimaryKey(w.getProjectId());
            }
        }
    }

}
