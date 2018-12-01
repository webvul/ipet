package com.sensetime.iva.ipet.service;

import com.sensetime.iva.ipet.entity.Project;
import com.sensetime.iva.ipet.entity.ProjectConclusionEntity;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author : ChaiLongLong
 * @date : 2018/8/13 14:18
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProjectConclusionServiceTest extends AbstractServiceTxTest{

    @Autowired
    ProjectConclusionService projectConclusionService;
    @Autowired
    ProjectService projectService;

    private static int id;

    private static int projectId;

    @Test
    public void test1Add(){
        Project project = new Project();
        project.setName("testProjectConclusion");
        projectService.insert(project);
        projectId = project.getId();

        ProjectConclusionEntity projectConclusionEntity = new ProjectConclusionEntity();
        projectConclusionEntity.setAcceptanceCycle(1);
        projectConclusionEntity.setAcceptanceDescribe("acceptanceDescribe");
        projectConclusionEntity.setActionEvent("actionEvent");
        projectConclusionEntity.setAfterSaleCycle(1);
        projectConclusionEntity.setAfterSaleDescribe("afterSaleDescribe");
        projectConclusionEntity.setCustomizationDevelopmentCycle(1);
        projectConclusionEntity.setCustomizationDevelopmentDescribe("customizationDevelopmentDescribe");
        projectConclusionEntity.setCycle("cycle");
        projectConclusionEntity.setDeliverCycle(1);
        projectConclusionEntity.setDeliverDescribe("deliverDescribe");
        projectConclusionEntity.setExperienceSummary("experienceSummary");
        projectConclusionEntity.setFaultNum(1);
        projectConclusionEntity.setFaultNumDescribe("faultNumDescribe");
        projectConclusionEntity.setFriendsStrengthsWeaknesses("friendsStrengthsWeaknesses");
        projectConclusionEntity.setIdentity(1);
        projectConclusionEntity.setImplementNum(1);
        projectConclusionEntity.setImplementDescribe("implementDescribe");
        projectConclusionEntity.setImprovement("improvement");
        projectConclusionEntity.setTotal(10);
        projectConclusionEntity.setTotalDescribe("总结说明");
        projectConclusionEntity.setLegacy("legacy");
        projectConclusionEntity.setMaintenanceCycle(1);
        projectConclusionEntity.setMaintenanceDescribe("maintenanceDescribe");
        projectConclusionEntity.setOurStrengthsWeaknesses("ourStrengthsWeaknesses");
        projectConclusionEntity.setPhaseConclusion("phaseConclusion");
        projectConclusionEntity.setPlan("plan");
        projectConclusionEntity.setPlanDesignCycle(1);
        projectConclusionEntity.setPlanDesignDescribe("planDesignDescribe");
        projectConclusionEntity.setPreDeliverCycle(1);
        projectConclusionEntity.setPreDeliverDescribe("preDeliverDescribe");
        projectConclusionEntity.setProjectId(projectId);
        projectConclusionEntity.setRemark("remark");
        projectConclusionEntity.setStatus(1);
        projectConclusionEntity.setTarget("target");
        projectConclusionEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));

        projectConclusionService.addProjectConclusion(projectConclusionEntity);
        id = projectConclusionEntity.getId();
        Assert.assertTrue(id != 0);
    }

    @Test
    public void test2QueryAndUpdate(){
        ProjectConclusionEntity projectConclusionEntity = projectConclusionService.queryByProject(projectId);
        projectConclusionEntity.setPlan("updatePlan");
        projectConclusionService.updateProjectConclusion(projectConclusionEntity);

        ProjectConclusionEntity queryResult = projectConclusionService.queryById(id);
        Assert.assertEquals(queryResult.getPlan(),"updatePlan");

    }

    @Test
    public void test3UpdateStatus(){
        ProjectConclusionEntity projectConclusionEntity = projectConclusionService.queryById(id);
        projectConclusionEntity.setStatus(2);
        projectConclusionService.updateStatus(id, 2, new Timestamp(System.currentTimeMillis()));

        ProjectConclusionEntity queryResult = projectConclusionService.queryById(id);
        Assert.assertEquals(queryResult.getStatus(),2);
    }

    @Test
    public void test4Delete(){
        projectConclusionService.deleteById(id);
        projectService.deleteByPrimaryKey(projectId);
        Assert.assertTrue( projectConclusionService.queryById(id) == null);


    }



}
