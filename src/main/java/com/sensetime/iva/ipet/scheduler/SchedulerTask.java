package com.sensetime.iva.ipet.scheduler;

import com.sensetime.iva.ipet.common.DatePattern;
import com.sensetime.iva.ipet.common.ProjectArgs;
import com.sensetime.iva.ipet.entity.CompareDate;
import com.sensetime.iva.ipet.entity.Project;
import com.sensetime.iva.ipet.entity.ProjectPlan;
import com.sensetime.iva.ipet.entity.StageEntity;
import com.sensetime.iva.ipet.service.CompareDateService;
import com.sensetime.iva.ipet.service.ProjectPlanService;
import com.sensetime.iva.ipet.service.ProjectService;
import com.sensetime.iva.ipet.service.StageService;
import com.sensetime.iva.ipet.util.DateUtil;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Author: ChaiLongLong
 * @date : 2018/8/9 12:30
 */
@Component
public class SchedulerTask {
    @Autowired
    StageService stageService;
    @Autowired
    ProjectPlanService projectPlanService;
    @Autowired
    ProjectService projectService;
    @Autowired
    CompareDateService compareDateService;

    /**
     * 每周1 凌晨2点执行
     * 当项目已存在每周看板、工时的阶段时，创建新一周的阶段
     */
    @Scheduled(cron="0 0 2 ? * MON")
    private void process(){
        //查找未结项的项目
        List<Project> projects = projectService.selectNotInStatus(ProjectArgs.PROJECT_STATUS_JUNCTIONS_NUM);
       if(projects!=null&&projects.size()>0){
           for (Project p: projects) {

               List<StageEntity> boardStages = stageService.queryProjectStageByProIdAndType(p.getId(), ProjectArgs.STAGE_PROJECT_PLAN_TYPE);
               List<StageEntity> workTimeStages = stageService.queryProjectStageByProIdAndType(p.getId(),ProjectArgs.STAGE_PROJECT_MAN_HOUR_TYPE);
               if(boardStages != null && boardStages.size() > 0){
                   //新的项目时间阶段
                   StageEntity stageEntity = new StageEntity();
                   stageEntity.setProjectId(p.getId());
                   stageEntity.setStartDate(DateUtil.getThisDate());
                   stageEntity.setEndDate(DateUtil.getThisWeekDate(Calendar.SUNDAY));
                   stageEntity.setType(ProjectArgs.STAGE_PROJECT_PLAN_TYPE);
                   stageService.addStage(stageEntity);
                   //新的项目计划
                   ProjectPlan projectPlan = new ProjectPlan();
                   projectPlan.setProjectId(p.getId());
                   projectPlan.setStageId(stageEntity.getId());
                   projectPlanService.insert(projectPlan);
               }

               if(workTimeStages != null && workTimeStages.size() > 0){
                   //新的项目时间阶段
                   StageEntity stageEntity = new StageEntity();
                   stageEntity.setProjectId(p.getId());
                   stageEntity.setStartDate(new Date());
                   stageEntity.setEndDate(DateUtil.getThisWeekDate(Calendar.SUNDAY));
                   stageEntity.setType(ProjectArgs.STAGE_PROJECT_MAN_HOUR_TYPE);
                   stageService.addStage(stageEntity);
               }

           }
       }
    }

    /**
     * 获取每月的时间用于报表时间对比
     */
    @Scheduled(cron="0 0 0 1 * ?")
    private void process1(){
        CompareDate compareDate = new CompareDate();
        compareDate.setDate(DateUtil.dateToString(new Date(), DatePattern.yyyy_MM));
        compareDateService.insert(compareDate);
    }
}
