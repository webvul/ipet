package com.sensetime.iva.ipet.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sensetime.iva.ipet.common.IConstant;
import com.sensetime.iva.ipet.common.ProjectArgs;
import com.sensetime.iva.ipet.entity.AccountEntity;
import com.sensetime.iva.ipet.entity.Message;
import com.sensetime.iva.ipet.entity.Project;
import com.sensetime.iva.ipet.entity.ProjectConclusionEntity;
import com.sensetime.iva.ipet.service.AccountService;
import com.sensetime.iva.ipet.service.MessageService;
import com.sensetime.iva.ipet.service.ProjectConclusionService;
import com.sensetime.iva.ipet.service.ProjectService;
import com.sensetime.iva.ipet.vo.form.ProjectConclusionApprovalForm;
import com.sensetime.iva.ipet.vo.form.ProjectConclusionDataForm;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author : ChaiLongLong
 * @date : 2018/8/14 15:03
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProjectConclusionControllerTest extends AbstractRestTxController{

    @Autowired
    ProjectConclusionService projectConclusionService;
    @Autowired
    AccountService accountService;
    @Autowired
    ProjectService projectService;
    @Autowired
    MessageService messageService;

    private static int projectId;
    private static int id;

    @Test
    public void test1AddProjectConclusion(){
        AccountEntity accountEntity = accountService.loadAccountByUsername("test_pm");
        Project project = new Project();
        project.setName("测试项目");
        project.setFriends("友商");
        project.setCreateUserId(accountEntity.getId());
        project.setSerial("123456");
        projectService.insert(project);
        projectId = project.getId();

        ProjectConclusionDataForm projectConclusionDataForm = new ProjectConclusionDataForm();
        projectConclusionDataForm.setFriends(project.getFriends());
        projectConclusionDataForm.setProjectManager("项目经理");
        projectConclusionDataForm.setSerial(project.getSerial());
        projectConclusionDataForm.setName(project.getName());
        projectConclusionDataForm.setAcceptanceCycle(1);
        projectConclusionDataForm.setAcceptanceDescribe("acceptanceDescribe");
        projectConclusionDataForm.setActionEvent("actionEvent");
        projectConclusionDataForm.setAfterSaleCycle(1);
        projectConclusionDataForm.setAfterSaleDescribe("afterSaleDescribe");
        projectConclusionDataForm.setCustomizationDevelopmentCycle(1);
        projectConclusionDataForm.setCustomizationDevelopmentDescribe("customizationDevelopmentDescribe");
        projectConclusionDataForm.setCycle("2018-01-01~2018-01-02");
        projectConclusionDataForm.setDeliverCycle(1);
        projectConclusionDataForm.setDeliverDescribe("deliverDescribe");
        projectConclusionDataForm.setExperienceSummary("experienceSummary");
        projectConclusionDataForm.setFaultNum(1);
        projectConclusionDataForm.setFaultNumDescribe("faultDescribe");
        projectConclusionDataForm.setFriendsStrengthsWeaknesses("friendsStrengthsWeaknesses");
        projectConclusionDataForm.setIdentity(1);
        projectConclusionDataForm.setImplementDescribe("implementDescribe");
        projectConclusionDataForm.setImplementNum(1);
        projectConclusionDataForm.setImprovement("improvement");
        projectConclusionDataForm.setLegacy("legacy");
        projectConclusionDataForm.setMaintenanceCycle(1);
        projectConclusionDataForm.setMaintenanceDescribe("maintenanceDescribe");
        projectConclusionDataForm.setOurStrengthsWeaknesses("ourStrengthsWeaknesses");
        projectConclusionDataForm.setPhaseConclusion("phaseConclusion");
        projectConclusionDataForm.setPlan("plan");
        projectConclusionDataForm.setPlanDesignCycle(1);
        projectConclusionDataForm.setPlanDesignDescribe("planDesignDescribe");
        projectConclusionDataForm.setPreDeliverCycle(1);
        projectConclusionDataForm.setPreDeliverDescribe("preDeliverDescribe");
        projectConclusionDataForm.setRemark("remark");
        projectConclusionDataForm.setTarget("target");
        projectConclusionDataForm.setProjectId(project.getId());
        projectConclusionDataForm.setTotal(9);
        projectConclusionDataForm.setTotalDescribe("totalDescribe");
        projectConclusionDataForm.setStatus(1);

        String json = JSON.toJSONString(projectConclusionDataForm);

        MvcResult result;
        try {
            result = getMockMvc().perform(post("/projectConclusion")
                    .contentType(MediaType.APPLICATION_JSON).content(json))
                    .andExpect(status().isOk())// 模拟向testRest发送get请求
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                    .andReturn();
            JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
            Assert.assertEquals(jsonObject.get("desc"),"提交项目结项数据成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2UpdateProjectConclusion(){

        ProjectConclusionDataForm projectConclusionDataForm = new ProjectConclusionDataForm();
        ProjectConclusionEntity projectConclusionEntity = projectConclusionService.queryByProject(projectId);
        BeanUtils.copyProperties(projectConclusionEntity,projectConclusionDataForm);
        projectConclusionDataForm.setRemark("updateRemark");

        String json = JSON.toJSONString(projectConclusionDataForm);

        MvcResult result;
        try {
            result = getMockMvc().perform(post("/projectConclusion")
                    .contentType(MediaType.APPLICATION_JSON).content(json))
                    .andExpect(status().isOk())// 模拟向testRest发送get请求
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                    .andReturn();

            JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
            Assert.assertEquals(jsonObject.get("desc"),"提交项目结项数据成功");
            ProjectConclusionEntity updateResult = projectConclusionService.queryByProject(projectId);
            Assert.assertEquals(updateResult.getRemark(),"updateRemark");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test3InitProjectConclusion(){

        MvcResult result;
        try {
            result = getMockMvc().perform(get("/projectConclusion?projectId="+projectId))
                    .andExpect(status().isOk())// 模拟向testRest发送get请求
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                    .andReturn();

            JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
            Assert.assertEquals(jsonObject.get("desc"),"项目结项数据获取成功");
            ProjectConclusionDataForm projectConclusionDataForm = JSON.parseObject(jsonObject.getString("data"),ProjectConclusionDataForm.class);
            Assert.assertEquals(projectConclusionDataForm.getProjectManager(),"测试项目经理");
            id= projectConclusionDataForm.getId();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test4LaunchConclusion(){

        MvcResult result;
        try {
            result = getMockMvc().perform(get("/launchConclusion/"+id))
                    .andExpect(status().isOk())// 模拟向testRest发送get请求
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                    .andReturn();

            JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
            Assert.assertEquals(jsonObject.get("desc"),"发起结项成功");
            List<AccountEntity> accountEntities = accountService.queryAccountByResourceCode(IConstant.PROJECT_CONCLUSION_APPROVAL_RESOURCE_CODE);
            //删除结项申请的消息
            for (AccountEntity accountEntity : accountEntities ) {
                List<Message> message = messageService.getUnReadByRecId(accountEntity.getId());
                Assert.assertTrue(message.size() >0 );
                messageService.deleteById(message.get(0).getId());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test5ApprovalConclusion(){
        ProjectConclusionApprovalForm projectConclusionApprovalForm = new ProjectConclusionApprovalForm();
        projectConclusionApprovalForm.setId(id);
        projectConclusionApprovalForm.setStatus(ProjectArgs.REPULSE);
        projectConclusionApprovalForm.setReason("打回原因");

        String json = JSON.toJSONString(projectConclusionApprovalForm);

        MvcResult result;
        try {
            result = getMockMvc().perform(post("/approvalConclusion")
                    .contentType(MediaType.APPLICATION_JSON).content(json))
                    .andExpect(status().isOk())// 模拟向testRest发送get请求
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                    .andReturn();

            JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
            Assert.assertEquals(jsonObject.get("desc"),"审批结项成功");

            //删除审批结项生成的消息
            AccountEntity accountEntity = accountService.loadAccountByUsername("test_pm");
            List<Message>  messages = messageService.getUnReadByRecId(accountEntity.getId());
            Assert.assertTrue(messages.size() == 1);
            messageService.deleteById(messages.get(0).getId());

            projectConclusionService.deleteById(id);
            Assert.assertNull(projectConclusionService.queryById(id));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void clean6TestData(){
        projectService.deleteByPrimaryKey(projectId);
    }

}
