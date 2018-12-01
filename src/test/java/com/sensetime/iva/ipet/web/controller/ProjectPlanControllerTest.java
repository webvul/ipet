package com.sensetime.iva.ipet.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sensetime.iva.ipet.common.ReturnMsg;
import com.sensetime.iva.ipet.entity.Project;
import com.sensetime.iva.ipet.entity.ProjectPlan;
import com.sensetime.iva.ipet.entity.StageEntity;
import com.sensetime.iva.ipet.entity.WeeklyBoard;
import com.sensetime.iva.ipet.service.ProjectPlanService;
import com.sensetime.iva.ipet.service.ProjectService;
import com.sensetime.iva.ipet.service.StageService;
import com.sensetime.iva.ipet.service.WeeklyBoardService;
import com.sensetime.iva.ipet.util.DateUtil;
import com.sensetime.iva.ipet.vo.form.ProjectPlanForm;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author  gongchao
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProjectPlanControllerTest extends AbstractRestTxController{
    @Autowired
    ProjectPlanService projectPlanService;
    @Autowired
    ProjectService projectService;
    @Autowired
    WeeklyBoardService weeklyBoardService;
    @Autowired
    StageService stageService;
    private static Integer projectId=-1;

    /**
     * 查询项目id查询周报
     */
    @Test
    public void test0() throws Exception {
        MvcResult result = getMockMvc().perform(get("/projectPlan/1"))
                .andExpect(status().isOk())// 模拟发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"), ReturnMsg.PROJECT_PLAN_LIST);
    }

    /**
     * 新增周报信息
     * @throws Exception
     */
    @Test
    public void test1() throws Exception {
        ProjectPlanForm projectPlanVo = new ProjectPlanForm();
        //项目必须提前存在不然数据库外键报错
        Project project = new Project();
        project.setName("projectName");
        projectPlanVo.setProject(project);
        projectService.insert(project);
        projectId=project.getId();

         List<ProjectPlan> plans=new ArrayList<>();
        StageEntity stageEntity = new StageEntity();
        ProjectPlan projectPlan = new ProjectPlan();

        List<WeeklyBoard> tasks=new ArrayList<>();
        WeeklyBoard weeklyBoard = new WeeklyBoard();

        weeklyBoard.setPersonLiable("create weeklyBoard");
        weeklyBoard.setProjectId(project.getId());

        stageEntity.setStartDate(DateUtil.getThisDate());
        stageEntity.setEndDate(DateUtil.getSundayOfThisWeek());
        stageEntity.setType(1);
        stageEntity.setProjectId(project.getId());

        projectPlan.setProjectId(project.getId());
        projectPlan.setProjectProgress("create projectPlan");
        plans.add(projectPlan);
        tasks.add(weeklyBoard);
        projectPlanVo.setTasks(tasks);
        projectPlanVo.setProjectPlans(plans);
        String json = JSON.toJSONString(projectPlanVo);
        MvcResult result = getMockMvc().perform(post("/projectPlan")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"), ReturnMsg.CREATE_PROJECT_PLAN_SUCCESS);
    }

    /**
     * 更新周报信息
     * @throws Exception
     */
    @Test
    public void test2() throws Exception {
        ProjectPlanForm projectPlanVo = new ProjectPlanForm();
        //项目必须存在可将test1创建的projectid填上
        Project project = projectService.selectByPrimaryKey(projectId);
        projectPlanVo.setProject(project);
        List<ProjectPlan> plans = projectPlanService.selectByProjectId(project.getId());
        for (ProjectPlan p: plans ) {
            List<StageEntity> stageEntities = stageService.selectByIdAndType(p.getStageId(), 1);
            if(stageEntities!=null&&stageEntities.size()>0){
                p.setStageEntity(stageEntities.get(0));
            }
            p.setWeekProgress("update projectPlan");
        }
        projectPlanVo.setProjectPlans(plans);
        List<WeeklyBoard> weeklyBoards = weeklyBoardService.selectByProjectId(project.getId());
        WeeklyBoard weeklyBoard = weeklyBoards.get(0);
        weeklyBoard.setPersonLiable("update weeklyBoard");

        projectPlanVo.setTasks(weeklyBoards);
        String json = JSON.toJSONString(projectPlanVo);
        MvcResult result = getMockMvc().perform(post("/projectPlan")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"), ReturnMsg.CREATE_PROJECT_PLAN_SUCCESS);
        weeklyBoardService.deleteByPrimaryKey(weeklyBoard.getId());
        List<ProjectPlan> projectPlans = projectPlanService.selectByProjectId(projectId);
        if(projectPlans!=null&&projectPlans.size()>0){
            for (ProjectPlan p: projectPlans) {
                projectPlanService.deleteByPrimaryKey(p.getId());
            }
        }
        List<StageEntity> stageEntities = stageService.queryProjectStageByProIdAndType(projectId, 1);
        if(stageEntities!=null&&stageEntities.size()>0){
            for (StageEntity p: stageEntities) {
                stageService.deleteById(p.getId());
            }
        }
        projectService.deleteByPrimaryKey(projectId);

    }
}
