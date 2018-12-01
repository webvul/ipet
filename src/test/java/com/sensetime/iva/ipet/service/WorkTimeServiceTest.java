package com.sensetime.iva.ipet.service;

import com.sensetime.iva.ipet.entity.Project;
import com.sensetime.iva.ipet.entity.StageEntity;
import com.sensetime.iva.ipet.entity.WorkTimeEntity;
import com.sensetime.iva.ipet.vo.form.ReportBoardFrom;
import com.sensetime.iva.ipet.vo.form.ReportDelayProjectForm;
import com.sensetime.iva.ipet.vo.form.ReportIndexForm;
import com.sensetime.iva.ipet.vo.form.ReportProjectTypeForm;
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: ChaiLongLong
 * @date : 2018/8/7 16:36
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WorkTimeServiceTest extends AbstractServiceTxTest{

    @Autowired
    WorkTimeService workTimeService;
    @Autowired
    StageService stageService;
    @Autowired
    ProjectService projectService;
    @Autowired
    ReportService reportService;
    private static int id;
    private static int stageId;
    private static int projectId;

    /**
     * 该测试project表必须存在数据
     */
    @Test
    public void test0Init(){
        Project project = new Project();
        project.setName("testWorkTime");
        projectService.insert(project);
        projectId = project.getId();

        StageEntity stageEntity = new StageEntity();
        stageEntity.setProjectId(projectId);
        stageEntity.setType(2);
        stageEntity.setStartDate(new Date());
        stageService.addStage(stageEntity);
        stageId = stageEntity.getId();
    }


    @Test
    public void test1AddWorkTime(){
        WorkTimeEntity workTimeEntity = new WorkTimeEntity();
        workTimeEntity.setName("test");
        workTimeEntity.setStageId(stageId);
        workTimeEntity.setWeekTotalHour(22.2f);
        workTimeEntity.setWorkDesc("testdesc");
        workTimeEntity.setWorkHour4(12f);
        workTimeEntity.setWorkHour5(12f);
        workTimeEntity.setWorkHour6(12f);
        workTimeEntity.setWorkHour7(12f);
        workTimeEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        workTimeService.addWorkTime(workTimeEntity);
        id = workTimeEntity.getId();
        Assert.assertTrue(id != 0);
    }

    @Test
    public void test2UpdateWorkTime(){
        WorkTimeEntity workTimeEntity = workTimeService.queryById(id);
        workTimeEntity.setWeekTotalHour(22.2f);
        workTimeEntity.setWorkDesc("testupdatedesc");
        workTimeEntity.setWorkHour4(1.2f);
        workTimeEntity.setWorkHour5(1.2f);
        workTimeEntity.setWorkHour6(1.2f);
        workTimeEntity.setWorkHour7(1.2f);
        workTimeEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        workTimeService.updateWorkTime(workTimeEntity);
        WorkTimeEntity updateResult = workTimeService.queryById(id);
        Assert.assertEquals(updateResult.getWorkDesc(),"testupdatedesc");
    }

    @Test
    public void test3DeleteWorkTimeById(){
        workTimeService.deleteById(id);
        stageService.deleteById(stageId);
        projectService.deleteByPrimaryKey(projectId);
        Assert.assertTrue(workTimeService.queryById(id) == null );
    }
    @Test
    public void aa() throws Exception {
       
        List<ReportDelayProjectForm> a = reportService.getReportDelayProjectForm(null, null);
        ArrayList<Integer > ids = new ArrayList<>();
        ids.add(2);
        reportService.getReportProjectTypeForm(ids);
        List<ReportDelayProjectForm> b = reportService.getReportDelayProjectForm(ids, null);
        ArrayList<Integer > status = new ArrayList<>();
        status.add(6);
        List<ReportDelayProjectForm>c = reportService.getReportDelayProjectForm(null, status);
        ids.add(1);
        status.add(5);
        List<ReportDelayProjectForm>d = reportService.getReportDelayProjectForm(ids, status);
        System.out.println(a);
    }
}
