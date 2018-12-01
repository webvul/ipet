package com.sensetime.iva.ipet.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sensetime.iva.ipet.entity.AccountEntity;
import com.sensetime.iva.ipet.entity.AreaEntity;
import com.sensetime.iva.ipet.service.AreaService;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author : ChaiLongLong
 * @date : 2018/8/6 15:43
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AreaControllerTest extends AbstractRestTxController{

    @Autowired
    AreaService areaService;

    /**
     * 测试添加区域   区域名称不能为空
     * @throws Exception
     */
    @Test
    public void test1AddAreaFail1() throws Exception {
        AreaEntity areaEntity = new AreaEntity();
        String json = JSON.toJSONString(areaEntity);

        MvcResult result = getMockMvc().perform(post("/area")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"),"区域名称不能为空");

    }

    /**
     * 测试添加区域   区域已存在
     * @throws Exception
     */
    @Test
    public void test2AddAreaFail2() throws Exception {
        List<AreaEntity> areaEntityList = areaService.getAll();
        String json = JSON.toJSONString(areaEntityList.get(0));

        MvcResult result = getMockMvc().perform(post("/area")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"),"区域已存在");

    }

    /**
     * 测试添加区域   成功
     * @throws Exception
     */
    @Test
    public void test3AddAreaSuccess() throws Exception {
        AreaEntity areaEntity = new AreaEntity();
        areaEntity.setName("testAddArea");
        areaEntity.setNote("testNote");

        String json = JSON.toJSONString(areaEntity);

        MvcResult result = getMockMvc().perform(post("/area")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"),"新增区域成功");

    }

    /**
     * 测试获取区域列表   成功
     * @throws Exception
     */
    @Test
    public void test4GetAreas() throws Exception {

        MvcResult result = getMockMvc().perform(get("/area?page=1&size=20"))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"),"获取区域列表");

    }

    /**
     * 测试更新区域   失败
     * @throws Exception
     */
    @Test
    public void test5UpdateAreaFail1() throws Exception {
        AreaEntity areaEntity = areaService.queryByName("testAddArea");
        areaEntity.setName("");
        String json = JSON.toJSONString(areaEntity);

        MvcResult result = getMockMvc().perform(put("/area")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"),"区域名称不能为空");

    }

    /**
     * 测试更新区域   失败
     * @throws Exception
     */
    @Test
    public void test6UpdateAreaFail2() throws Exception {
        AreaEntity areaEntity = areaService.queryByName("testAddArea");
        areaEntity.setName("test");
        String json = JSON.toJSONString(areaEntity);

        AreaEntity testArea = new AreaEntity();
        testArea.setName("test");
        testArea.setNote("test");
        areaService.addArea(testArea);

        MvcResult result = getMockMvc().perform(put("/area")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"),"区域已存在");
        areaService.deleteArea(testArea.getId());
    }

    /**
     * 测试更新区域   成功
     * @throws Exception
     */
    @Test
    public void test7UpdateAreaSuccess() throws Exception {
        AreaEntity areaEntity = areaService.queryByName("testAddArea");
        areaEntity.setName("testUpdateArea");
        String json = JSON.toJSONString(areaEntity);

        MvcResult result = getMockMvc().perform(put("/area")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"),"更新区域成功");

    }

    /**
     * 测试删除区域
     * @throws Exception
     */
    @Test
    public void test8DeleteArea() throws Exception {
        AreaEntity areaEntity = areaService.queryByName("testUpdateArea");
        MvcResult result = getMockMvc().perform(delete("/area/"+areaEntity.getId()))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"),"删除区域成功");
    }

}
