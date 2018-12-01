package com.sensetime.iva.ipet.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sensetime.iva.ipet.common.ProjectArgs;
import com.sensetime.iva.ipet.common.ReturnMsg;
import com.sensetime.iva.ipet.entity.*;
import com.sensetime.iva.ipet.service.*;
import com.sensetime.iva.ipet.util.AccountUtils;
import com.sensetime.iva.ipet.vo.form.ProjectFilterForm;
import com.sensetime.iva.ipet.vo.form.ProjectInitFilterForm;
import io.swagger.models.auth.In;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author  gongchao
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProjectControllerTest extends AbstractRestTxController{
    @Autowired
    ProjectService projectService;
    @Autowired
    ProjectStageService projectStageService;
    @Autowired
    AccountService accountService;
    @Autowired
    MessageService messageService;
    @Autowired
    FileService fileService;
    @Autowired
    EquipmentService equipmentService;
    @Autowired
    WeeklyBoardService weeklyBoardService;
    @Autowired
    ProjectPlanService projectPlanService;
    @Autowired
    RiskProblemService riskProblemService;
    @Autowired
    StageService stageService;
    @Autowired
    WorkTimeService workTimeService;
    @Autowired
    BusinessTripService businessTripService;
    private  Integer userId=-1;
    private  Integer projectId=-1;

    public void initSecurityContextDate(){
        UserDetails userDetails = accountService.loadAccountByUsername("admin");
        PreAuthenticatedAuthenticationToken authentication =
                new PreAuthenticatedAuthenticationToken(userDetails, userDetails.getPassword(),userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    /**
     * 查询项目
     */
    @Test
    public void test0() throws Exception {
        MvcResult result = getMockMvc().perform(get("/project/init"))
                .andExpect(status().isOk())// 模拟发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"), ReturnMsg.PROJECT_INIT);
    }
    /**
     * 新建立项信息 用excel的信息直接插入数据库
     */
    @Test
    public void test1() throws Exception {
        this.initSecurityContextDate();
        //由于需要用户登陆获取用户id保存数据库，先将这部分代码注释
        final FileInputStream fis = new FileInputStream("src/test/resources/projects/project.xlsx");
        MockMultipartFile multipartFile = new MockMultipartFile("file", "project.xlsx", "application/vnd.ms-excel", fis);
        String result = getMockMvc().perform(fileUpload("/project/doc").file(multipartFile))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn().getResponse().getContentAsString();
        JSONObject jsonObject = JSONObject.parseObject(result);
        Object data = jsonObject.get("data");
        JSONObject jsonObject1 = JSONObject.parseObject(data.toString());
        Project project = JSONObject.toJavaObject(jsonObject1, Project.class);
        AccountEntity currentHr = AccountUtils.getCurrentHr();
        project.setCreateUserId(currentHr.getId());
        userId=currentHr.getId();
        projectService.createProject(project, project.getProjectStages());
        projectId=project.getId();
        Assert.assertEquals(jsonObject.get("desc"), ReturnMsg.SUCCESS);

    }
    /**
     * 获取项目数据
     */
    @Test
    public void test2() throws Exception {
        //过滤条件
        ProjectFilterForm form = new ProjectFilterForm();
        //项目经理
        List<Integer> ids = new ArrayList<>();
        ids.add(1);
        form.setCreateUserIds(ids);
        form.setLevel(1);
        String json = JSON.toJSONString(form);
        MvcResult result = getMockMvc().perform(post("/project/list")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"), ReturnMsg.PROJECT_LIST);
    }
    /**
     * 结项项目激活失败
     */
    @Test
    public void test3() throws Exception {
        MvcResult result = getMockMvc().perform(get("/project/active/"+projectId))
                .andExpect(status().isOk())// 模拟发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"), "激活项目不存在");

    }
    /**
     * 结项项目激活成功
     */
    @Test
    public void test4() throws Exception {
        AccountEntity accountEntity = accountService.loadAccountByUsername("admin");
        Project project = new Project();
        project.setName("active project");
        project.setLastStatus(ProjectArgs.PROJECT_STATUS_ACCEPTED_NUM);
        project.setStatus(ProjectArgs.PROJECT_STATUS_JUNCTIONS_NUM);
        project.setCreateUserId(accountEntity.getId());
        projectId=project.getId();
        projectService.insert(project);
        MvcResult result = getMockMvc().perform(get("/project/active/"+project.getId()))
                .andExpect(status().isOk())// 模拟发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"), ReturnMsg.ACTIVE_PROJECT_SUCCESS);
        List<Message> message = messageService.getUnReadByRecId(accountEntity.getId());
        Assert.assertTrue(message.size() >=1 );
    }
    /**
     * 上传立项文件
     */
    @Test
    public void test5() throws Exception {
        initSecurityContextDate();
        //需要提前添加系统和平台基础数据
        final FileInputStream fis = new FileInputStream("src/test/resources/projects/project.xlsx");
        MockMultipartFile multipartFile = new MockMultipartFile("file", "project.xlsx", "application/vnd.ms-excel", fis);
        MvcResult result = getMockMvc().perform(fileUpload("/project/doc").file(multipartFile))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"), ReturnMsg.SUCCESS);
    }

    /**
     * 获取项目信息的过滤条件
     */
    @Test
    public void test6() throws Exception {
        initSecurityContextDate();
        MvcResult result = getMockMvc().perform(get("/project/filter?isActive=true&isPm=true&isArea=false"))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"), ReturnMsg.PROJECT_FILTER_SUCCESS);
    }
}
