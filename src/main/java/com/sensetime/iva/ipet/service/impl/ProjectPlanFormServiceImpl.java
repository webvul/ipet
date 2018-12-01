package com.sensetime.iva.ipet.service.impl;

import com.sensetime.iva.ipet.common.ProjectArgs;
import com.sensetime.iva.ipet.config.LoadFileConfig;
import com.sensetime.iva.ipet.entity.*;
import com.sensetime.iva.ipet.service.*;
import com.sensetime.iva.ipet.util.DateUtil;
import com.sensetime.iva.ipet.vo.form.FileForm;
import com.sensetime.iva.ipet.vo.form.ProjectPlanForm;
import com.sensetime.iva.ipet.web.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author  gongchao
 */
@Component
public class ProjectPlanFormServiceImpl implements ProjectPlanFormService {
    private static final Logger logger = LoggerFactory.getLogger(ProjectPlanFormServiceImpl.class);

    @Autowired
    ProjectService projectService;
    @Autowired
    ProjectPlanService projectPlanService;
    @Autowired
    StageService stageService;
    @Autowired
    WeeklyBoardService weeklyBoardService;
    @Autowired
    FileService fileService;
    @Autowired
    AccountService accountService;
    @Autowired
    LoadFileConfig loadFileConfig;
    @Autowired
    ServerProperties serverProperties;
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ProjectPlanForm getByProjectId(Integer projectId) throws Exception{
        logger.info("开始获取项目信息");
        ProjectPlanForm projectPlanVO = new ProjectPlanForm();
        //获取项目信息
        Project project = projectService.selectByPrimaryKey(projectId);
        //项目经理
        if(project!=null&&project.getCreateUserId()!=null){
            project.setAccount(accountService.queryAccountById(project.getCreateUserId()));
        }
        projectPlanVO.setProject(project);
        //获取所有项目计划信息
        List<ProjectPlan> plans = projectPlanService.selectByProjectId(projectId);
        logger.info("开始获取当前项目所有项目计划信息 [{}]",plans);
        for (ProjectPlan p: plans) {
            //获取每个项目计划的开始结束时间,由于是周报下的Stage的list的size最多只有1
            logger.info("开始获取每个项目计划的开始结束时间信息");
            if(p.getStageId()==null){
                logger.error("ProjectPlan stage id null error");
                throw new BusinessException("项目计划表的开始结束时间为空");
            }
            List<StageEntity> stageEntities = stageService.selectByIdAndType(p.getStageId(), ProjectArgs.STAGE_PROJECT_PLAN_TYPE);
            if(stageEntities!=null&&stageEntities.size()>0){
                StageEntity stageEntity = stageEntities.get(0);
                p.setStageEntity(stageEntity);
            }
        }
        projectPlanVO.setProjectPlans(plans);
        List<File> files = fileService.selectByProjectId(projectId);
        logger.info("get all load files [{}]",files);
        HashMap<Integer, String> fileMap = new HashMap<>(16);
        FileForm fileForm = new FileForm();
        if(files!=null&&files.size()>0){
            for (File file: files) {
                String[] pathArray = file.getPath().split("/");
                String url="http://"+loadFileConfig.getDownloadIp()+":"+loadFileConfig.getDownloadPort()+serverProperties.getContextPath()+"/"+pathArray[pathArray.length-3]+"/"+pathArray[pathArray.length-2]+"/"+pathArray[pathArray.length-1]+"/"+file.getName();
                fileMap.put(file.getId(),url);
            }
        }
        fileForm.setFileMap(fileMap);
        projectPlanVO.setFileForm(fileForm);
        logger.info("开始获取每周看板信息");
        List<WeeklyBoard> weeklyBoard = weeklyBoardService.selectByProjectId(projectId);
        logger.info("get weeklyBoard by projectId [{}] result [{}]",projectId,weeklyBoard);
        logger.info("每周看板信息:"+weeklyBoard);
        projectPlanVO.setTasks(weeklyBoard);
        logger.info("return web projectPlanVO [{}]",projectPlanVO);
        return projectPlanVO;
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ProjectPlanForm createOrUpdate(ProjectPlanForm projectPlanVo) {

        logger.info("开始更新或创建项目计划信息 [{}]",projectPlanVo);
        Project project = projectPlanVo.getProject();
        logger.info("get project from projectPlanVo [{}]",project);
        List<ProjectPlan> projectPlans = projectPlanVo.getProjectPlans();
        if(projectPlans!=null&&projectPlans.size()>0){
            //项目状态有更新就更新，没有沿用原来项目的状态
            Project project1 = projectService.selectByPrimaryKey(project.getId());
            Integer status = projectPlans.get(0).getProjectStatus();
            project1.setStatus(status==null?project1.getStatus():status);
            projectService.updateByPrimaryKey(project1);
        }
        //获取每周看板的时间及内容
        logger.info("获取每周看板的时间及内容");
        List<ProjectPlan> plans = projectPlanVo.getProjectPlans();
        logger.info("每周看板信息："+plans.toString());
        if(plans!=null&&plans.size()>0){
            //遍历每周的看板
            for (ProjectPlan projectPlan: plans) {
                StageEntity s = projectPlan.getStageEntity();
                if(s==null||s.getId()==null){
                    s= new StageEntity();
                    //新建周报的stage
                    //  获取当前时间和本周周末的时间 yyyy-MM-dd
                    s.setStartDate(DateUtil.getThisDate());
                    s.setProjectId(project.getId());
                    //如果今天周日那就从本周日到本周日，否则是本周到下周日
                    if(1== DateUtil.getWhicthDay(new Date())){
                        s.setEndDate(DateUtil.getThisDate());
                    }else{
                        s.setEndDate(DateUtil.getThisWeekDate(Calendar.SUNDAY));
                    }
                    //周报类型
                        s.setType(ProjectArgs.STAGE_PROJECT_PLAN_TYPE);
                        logger.info("新建项目周报的开始结束时间信息 [{}]",s);
                        stageService.addStage(s);
                        projectPlan.setStageEntity(s);
                        projectPlan.setStageId(s.getId());
                }
                //获取看板内容且不为空
                if(projectPlan!=null){
                    //更新
                    if(projectPlan.getId()!=null){
                        logger.info("update projectPlan [{}]",projectPlan);
                        projectPlanService.updateByPrimaryKey(projectPlan);
                    }else{
                        //新建
                        logger.info("insert projectPlan [{}]",projectPlan);
                        projectPlanService.insert(projectPlan);
                    }

                }
            }
        }
        //获取看板任务
        logger.info("获取每周看板的时间及内容");
       List<WeeklyBoard>  tasks = projectPlanVo.getTasks();
        logger.info("每周看板信息："+tasks);
        //看板详细信息更新或创建
        if(tasks!=null&&tasks.size()>0){
            for (WeeklyBoard task: tasks) {
                if(task.getId()!=null){
                    logger.info("新建每周看板");
                    weeklyBoardService.updateByPrimaryKey(task);
                }else{
                    logger.info("更新每周看板");
                    task.setProjectId(project.getId());
                    weeklyBoardService.insert(task);
                }
            }
        }
       logger.info("更新结束,返回最新的项目计划数据");
        return projectPlanVo;
    }
}
