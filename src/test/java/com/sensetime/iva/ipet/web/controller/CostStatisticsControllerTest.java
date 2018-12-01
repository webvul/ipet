package com.sensetime.iva.ipet.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sensetime.iva.ipet.config.dozer.EjbGenerator;
import com.sensetime.iva.ipet.entity.BusinessTripEntity;
import com.sensetime.iva.ipet.entity.EquipmentEntity;
import com.sensetime.iva.ipet.entity.Project;
import com.sensetime.iva.ipet.service.*;
import com.sensetime.iva.ipet.vo.form.*;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author : ChaiLongLong
 * @date : 2018/8/9 12:48
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CostStatisticsControllerTest extends AbstractRestTxController {

    @Autowired
    WorkTimeService workTimeService;
    @Autowired
    BusinessTripService businessTripService;
    @Autowired
    EquipmentService equipmentService;
    @Autowired
    StageService stageService;
    @Autowired
    ProjectService projectService;
    @Autowired
    protected EjbGenerator ejbGenerator = new EjbGenerator();

    private static int projectId;

    /**
     * 该测试project表必须存在数据
     */
    @Test
    public void test0Init(){
        Project project = new Project();
        project.setName("testCostStatistics");
        projectService.insert(project);
        projectId = project.getId();
    }

    @Test
    public void test1SubmitCostStatisticsData() throws Exception{
        CostStatisticsForm costStatisticsForm = new CostStatisticsForm();
        List<StageForm> stageForms =new ArrayList<>();
        StageForm stageForm = new StageForm();

        stageForm.setProjectId(projectId);
        stageForm.setStartDate(new Date());
        stageForm.setEndDate(new Date());

        List<WorkTimeForm> workTimeForms = new ArrayList<>();
        WorkTimeForm workTimeForm = new WorkTimeForm();
        workTimeForm.setName("testName");
        workTimeForm.setWeekTotalHour(22.22F);
        workTimeForm.setWorkDesc("实施");
        workTimeForm.setWorkHour1(11.11F);
        workTimeForm.setWorkHour3(11.11F);
        workTimeForms.add(workTimeForm);
        stageForm.setWorkTimeForms(workTimeForms);
        stageForms.add(stageForm);
        costStatisticsForm.setStageForms(stageForms);

        List<BusinessTripForm> businessTripForms = new ArrayList<>();
        BusinessTripEntity businessTripEntity = new BusinessTripEntity();
        businessTripEntity.setStartDate("2018-01-01");
        businessTripEntity.setTotal(2222.22F);
        businessTripEntity.setOther(1200.11F);
        businessTripEntity.setTraffic(22.11F);
        businessTripEntity.setAccommodation(1000F);
        businessTripEntity.setEndDate("2018-01-03");
        businessTripEntity.setDestination("深圳");
        businessTripEntity.setName("test");
        businessTripEntity.setWorkDesc("开发");
        businessTripEntity.setType(2);
        businessTripEntity.setProjectId(projectId);
        BusinessTripForm businessTripForm = ejbGenerator.convert(businessTripEntity,BusinessTripForm.class);
        businessTripForms.add(businessTripForm);
        costStatisticsForm.setBusinessTripForms(businessTripForms);

        List<EquipmentForm> equipmentForms = new ArrayList<>();
        EquipmentEntity equipmentEntity = new EquipmentEntity();
        equipmentEntity.setGraphicsCardNum(3);
        equipmentEntity.setGraphicsCardSerial("1080");
        equipmentEntity.setDeviceNum(3);
        equipmentEntity.setDeviceType("hello");
        equipmentEntity.setSoftwareVersion("1.1.10");
        equipmentEntity.setProjectId(projectId);
        equipmentEntity.setProType("2.1");
        EquipmentForm equipmentForm = ejbGenerator.convert(equipmentEntity,EquipmentForm.class);
        equipmentForms.add(equipmentForm);
        costStatisticsForm.setEquipmentForms(equipmentForms);

        String json = JSON.toJSONString(costStatisticsForm);
        MvcResult result = getMockMvc().perform(post("/costStatistics")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();

        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"),"提交成本统计数据成功");
    }

    @Test
    public void test2InitCostStatisticsData() throws Exception{
        MvcResult result = getMockMvc().perform(get("/costStatistics?projectId="+projectId))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();

        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"),"成本统计数据获取成功");
        CostStatisticsForm costStatisticsForm = JSON.parseObject(jsonObject.getString("data"),CostStatisticsForm.class);
        List<StageForm> stageForms = costStatisticsForm.getStageForms();
        Assert.assertTrue(stageForms.size() == 1);
        Assert.assertTrue("testName".equals(stageForms.get(0).getWorkTimeForms().get(0).getName()));

        workTimeService.deleteById(stageForms.get(0).getWorkTimeForms().get(0).getId());
        stageService.deleteById(stageForms.get(0).getId());
        businessTripService.deleteById(costStatisticsForm.getBusinessTripForms().get(0).getId());
        equipmentService.deleteById(costStatisticsForm.getEquipmentForms().get(0).getId());
        projectService.deleteByPrimaryKey(projectId);
    }
}
