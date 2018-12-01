package com.sensetime.iva.ipet.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sensetime.iva.ipet.common.DatePattern;
import com.sensetime.iva.ipet.common.IConstant;
import com.sensetime.iva.ipet.common.ProjectArgs;
import com.sensetime.iva.ipet.common.ReturnMsg;
import com.sensetime.iva.ipet.config.dozer.EjbGenerator;
import com.sensetime.iva.ipet.entity.*;
import com.sensetime.iva.ipet.service.*;
import com.sensetime.iva.ipet.util.AccountUtils;
import com.sensetime.iva.ipet.util.ChooseUtil;
import com.sensetime.iva.ipet.util.DateUtil;
import com.sensetime.iva.ipet.vo.form.*;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author gongchao
 * @date 12:54 2018/9/3
 */
public class IpetAllTest extends AbstractRestTxController {
    private static final Logger logger = LoggerFactory.getLogger(IpetAllTest.class);
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
    @Autowired
    DeliverListService deliverListService;
    @Autowired
    PhysicalMapService physicalMapService;
    @Autowired
    ProjectConclusionService projectConclusionService;
    @Autowired
    ProjectRelatedPersonService projectRelatedPersonService;
    @Autowired
    SurveyListService surveyListService;
    @Autowired
    TopologicalGraphService topologicalGraphService;
    @Autowired
    WareListService wareListService;
    @Autowired
    ApplyListService applyListService;
    @Autowired
    protected EjbGenerator ejbGenerator = new EjbGenerator();
    @Autowired
    LoginInfoService loginInfoService;
    @Autowired

    /**项目id*/
    private  static Integer projectId;
    /**项目计划id*/
    private static Integer projectPlanId;
    /**出差id*/
    private static Integer tripId;
    /**设备id*/
    private static Integer equimentId;
    /**项目计划阶段id*/
    private static Integer stageId;
    /**项目干系人id*/
    private static Integer relatedPersonID;
    /**实施清单id*/
    private static Integer appId;
    /**工勘清单id*/
    private static Integer surId;
    /**软硬件清单id*/
    private static Integer wareId;
    /**交付清单id*/
    private static Integer deleverId;
    /**项目结项d*/
    private static int conclusionId;
    /**通知信息id*/
    private static Integer messageId;
    /**项目风险id*/
    private static Integer riskId;

    /**
     * 模拟用户登陆和清除所有数据
     */
    public void initSecurityContextDate(){
        UserDetails userDetails = accountService.loadAccountByUsername("admin");
        PreAuthenticatedAuthenticationToken authentication =
                new PreAuthenticatedAuthenticationToken(userDetails, userDetails.getPassword(),userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    @Test
    public void test0() {
        //清除所有业务数据
        clearData();
    }
    /**
     * @throws Exception
     */
    @Test
    public void test1() throws Exception {
        //第一次登陆加载缓存的项目信息
        initSecurityContextDate();
         getProjectInit();
         //上传项目立项信息的文件
        loadFile();
        //查看缓存是否存在
        getProjectInit();
        //成功创建项目信息
        createProject();
        //更新项目信息
        updateProjectLevelAndStage();
        //创建查询项目信息的过滤条件
        ProjectFilterForm projectFilter = getProjectFilter();
        //根据过滤条件查询项目
        getProjectByFilter(projectFilter);
    //=========================================//
        //获取当前项目计划信息
        getProjectPlan();
        //创建项目计划
        createProjectPlan();
        //更新项目计划
        updateProjectPlan();
        //获取更新后的项目计划信息
        getProjectPlan();
        //项目计划上传附件
        loadProjectPlanFile();
        //删除项目计划周报
        deleteProjectPlan(projectPlanId);
        //创建成本统计信息===========
        createCostStatisticsData();
        //查询成本统计信息
        CostStatisticsForm costStatisticsData = getCostStatisticsData();
        //更新成本统计信息
        updateCostStatisticsData(costStatisticsData);
        //删除成本统计相关信息
        deleteCostStatisticsData(tripId,equimentId,stageId);
        //创建风险=========
        RiskProblemListForm risk = createRisk();
        //更新风险
        updateRisk(risk);
        //获取风险
        getRisk();
        //删除风险
        deleteRisk();
        //创建项目干系人=========
        createProjectRelatedPerson();
        //查看项目干系人
        List<ProjectRelatedPerson> projectRelatedPerson = getProjectRelatedPerson();
        //更新项目干系人
        updateProjectRelatedPerson(projectRelatedPerson);
        //删除项目干系人
        deleteRelatedPerson(relatedPersonID);
     //=====================================//
        //项目验收相关信息
        //创建验收清单========
        createApplyList();
        //更新验收清单
        updateApplyList();
        //查询验收清单
        getApplyList();
        //删除gk清单
        deleteApplyList();
        //创建工勘清单=========
        createSurveyList();
        //更新工勘清单
        updateSurveyList();
        //查询工勘清单
        getSurveyList();
        //删除工勘清单
        deleteSurveyList();
        //创建交付物清单=========
        createDeliverList();
        //更新交付物清单
        updateDeliverList();
        //查询交付物清单
        getDeliverList();
        //删除交付物清单
        deleteDeliverList();
        //创建软硬件清单=========
        createWareList();
        //更新软硬件清单
        updateWareList();
        //查询软硬件清单
        getWareList();
        //删除软硬件清单
        deleteWareList();
        //======================================//
        //项目结项
        createProjectConclusion();
        //更新项目结项
        updateProjectConclusion();
        //获取结项信息
        getProjectConclusion();
        //发起结项申请
        launchConclusion(conclusionId);
        //审批结项 打回
        approvalConclusion(conclusionId,ProjectArgs.REPULSE);
        //项目激活失败因为项目未结项
        activeFailProject();
        //发起结项申请
        launchConclusion(conclusionId);
        //审批结项 通过
        approvalConclusion(conclusionId,ProjectArgs.ADOPT);
        //激活结项通过的项目
        activeSuccessProject();
        //获取消息
        getUnHandleMessages();
        //处理消息
        setMessageHandled();
        //pm查看首页
        getPmIndex();
        //admin查看首页
        getAdminIndex();
        //admin看板
        getAdminBoard();
        //pm看板
        getPmBoard();
        //admin活跃延期信息
        getAdminActive();
        //pm活跃延期信息
        getPmActive();
        //admin结项信息
        getAdminJunctions();
        //pm结项信息
        getPmJunctions();

        //清除所有业务数据
        clearData();
    }

    /**
     * 清除有关项目的所有数据
     */
    public void clearData(){
        logger.info("清除所有项目阶段信息");
        List<ProjectStage> byProjectId = projectStageService.selectAll();
        if(byProjectId!=null&&byProjectId.size()>0){
            for (ProjectStage s:byProjectId) {
                projectStageService.deleteByPrimaryKey(s.getId());
            }
        }
        logger.info("清除所有上传文件信息");
        List<File> files = fileService.selectAll();
        if(files!=null&&files.size()>0){
            for (File s: files) {
                fileService.deleteByPrimaryKey(s.getId());
            }
        }
        logger.info("清除所有项目设备信息");
        List<EquipmentEntity> equipmentEntities = equipmentService.selectAll();
        if(equipmentEntities!=null&&equipmentEntities.size()>0){
            for (EquipmentEntity s: equipmentEntities) {
                equipmentService.deleteById(s.getId());
            }
        }
        logger.info("清除所有看板信息");
        List<WeeklyBoard> weeklyBoards = weeklyBoardService.selectAll();
        if(weeklyBoards!=null&&weeklyBoards.size()>0){
            for (WeeklyBoard s: weeklyBoards) {
                weeklyBoardService.deleteByPrimaryKey(s.getId());
            }
        }
        logger.info("清除所有工时信息");
        List<WorkTimeEntity> workTimeEntities = workTimeService.selectAll();
        if(workTimeEntities!=null&&workTimeEntities.size()>0){
            for (WorkTimeEntity s: workTimeEntities) {
                workTimeService.deleteById(s.getId());
            }
        }
        logger.info("清除所有项目计划");
        List<ProjectPlan> projectPlans = projectPlanService.selectAll();
        if(projectPlans!=null&&projectPlans.size()>0){
            for (ProjectPlan s: projectPlans) {
                projectPlanService.deleteByPrimaryKey(s.getId());
            }
        }
        logger.info("清除所有旧项目计划阶段");
        List<StageEntity> stageEntities = stageService.selectAll();
        if(stageEntities!=null&&stageEntities.size()>0){
            for (StageEntity s: stageEntities) {
                stageService.deleteById(s.getId());
            }
        }
        logger.info("清除所有问题及风险项");
        List<RiskProblemEntity> riskProblemEntities = riskProblemService.selectAll();
        if(riskProblemEntities!=null&&riskProblemEntities.size()>0){
            for (RiskProblemEntity s: riskProblemEntities) {
                riskProblemService.deleteById(s.getId());
            }
        }

        logger.info("清除所有出差信息");
        List<BusinessTripEntity> businessTripEntities = businessTripService.selectAll();
        if(businessTripEntities!=null&&businessTripEntities.size()>0){
            for (BusinessTripEntity s: businessTripEntities) {
                businessTripService.deleteById(s.getId());
            }
        }
        logger.info("清除所有交付物信息");
        List<DeliverList> deliverLists = deliverListService.selectAll();
        if(deliverLists!=null&&deliverLists.size()>0){
            for (DeliverList s: deliverLists) {
                deliverListService.deleteByPrimaryKey(s.getId());
            }
        }
        logger.info("清除所有物理图");
        List<PhysicalMap> physicalMaps = physicalMapService.selectAll();
        if(physicalMaps!=null&&physicalMaps.size()>0){
            for (PhysicalMap s: physicalMaps) {
                physicalMapService.deleteByPrimaryKey(s.getId());
            }
        }
        logger.info("清除所有项目结项信息");
        List<ProjectConclusionEntity> projectConclusionEntities = projectConclusionService.selectAll();
        if(projectConclusionEntities!=null&&projectConclusionEntities.size()>0){
            for (ProjectConclusionEntity s: projectConclusionEntities) {
                projectConclusionService.deleteById(s.getId());
            }
        }
        logger.info("清除所有项目干系人");
        List<ProjectRelatedPerson> projectRelatedPeople = projectRelatedPersonService.selectAll();
        if(projectRelatedPeople!=null&&projectRelatedPeople.size()>0){
            for (ProjectRelatedPerson s: projectRelatedPeople) {
                projectRelatedPersonService.deleteByPrimaryKey(s.getId());
            }
        }
        logger.info("清除所有");
        List<SurveyList> surveyLists = surveyListService.selectAll();
        if(surveyLists!=null&&surveyLists.size()>0){
            for (SurveyList s: surveyLists) {
                surveyListService.deleteByPrimaryKey(s.getId());
            }
        }
        logger.info("清除所有拓扑图");
        List<TopologicalGraph> topologicalGraphs = topologicalGraphService.selectAll();
        if(topologicalGraphs!=null&&topologicalGraphs.size()>0){
            for (TopologicalGraph s: topologicalGraphs) {
                topologicalGraphService.deleteByPrimaryKey(s.getId());
            }
        }
        logger.info("清除所有软硬件");
        List<WareList> wareLists = wareListService.selectAll();
        if(wareLists!=null&&wareLists.size()>0){
            for (WareList s: wareLists) {
                wareListService.deleteByPrimaryKey(s.getId());
            }
        }
        logger.info("清除所有实施清单信息");
        List<ApplyList> applyLists = applyListService.selectAll();
        if(applyLists!=null&&applyLists.size()>0){
            for (ApplyList s: applyLists) {
                applyListService.deleteByPrimaryKey(s.getId());
            }
        }
        logger.info("清除所有项目信息");
        ProjectFilterForm projectFilterForm = new ProjectFilterForm();
        List<ProjectForm> projects = projectService.selectAll(projectFilterForm);
        if(projects!=null&&projects.size()>0){
            for (ProjectForm s: projects) {
                projectService.deleteByPrimaryKey(s.getId());
            }
        }
        logger.info("清除所有通知信息");
        List<Message> messages = messageService.selectAll();
        if(messages!=null&&messages.size()>0){
            for (Message s: messages) {
                messageService.deleteById(s.getId());
            }
        }
        logger.info("清除所有登陆信息");
        List<LoginInfoEntity> all = loginInfoService.getAll();
        if(all!=null&&all.size()>0){
            for (LoginInfoEntity s: all) {
                loginInfoService.deleteLoginInfoById(s.getId());
            }
        }
    }

    /**
     * 获取缓存的项目信息
     */
    public void getProjectInit() throws  Exception{
        logger.info("[start]get last init project values");
        MvcResult result = getMockMvc().perform(get("/project/init"))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        logger.info("[end]get last init project values [{}]",jsonObject.get("data").toString());
        Assert.assertEquals(jsonObject.get("desc"), ReturnMsg.PROJECT_INIT);
    }

    /**
     * 模拟上传文件并保到缓存文件保存到服务器
     * @throws Exception
     */
    public void loadFile () throws  Exception{
        logger.info("[start] upload file");
        final FileInputStream fis = new FileInputStream("src/test/resources/projects/project.xlsx");
        MockMultipartFile multipartFile = new MockMultipartFile("file", "project.xlsx", "application/vnd.ms-excel", fis);
        MvcResult result = getMockMvc().perform(fileUpload("/project/doc").file(multipartFile))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        logger.info("[end] upload file");
        Assert.assertEquals(jsonObject.get("desc"), "success");
    }

    /**
     * 成功创建项目信息
     * @throws Exception
     */
    public void createProject() throws Exception {
        logger.info("[start] createProject ");
        initSecurityContextDate();
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
        logger.info("create project data {}",project);
        projectService.createProject(project, project.getProjectStages());
        projectId=project.getId();
        logger.info("create projectStage data {}",project.getProjectStages());
        Assert.assertEquals(jsonObject.get("desc"), "success");
        logger.info("[end] createProject ");
    }

    /**
     * 更新项目级别及项目阶段
     * @throws Exception
     */
    public void updateProjectLevelAndStage() throws Exception {
        Project project = projectService.selectByPrimaryKey(projectId);
        project.setLevel(ProjectArgs.PROJECT_LEVEL_NUM_P1);

        logger.info("当前项目新增一条项目信息");
        ProjectStage stage = new ProjectStage();
        stage.setDeliveryDate(DateUtil.dateToString(new Date(), DatePattern.yyyy_MM_dd));
        stage.setCustomization(3);
        stage.setExpectedScale("ExpectedScale");
        stage.setStageScale("StageScale");
        stage.setDeliveryDate("2018-09-24");
        stage.setPlatformId(2);
        stage.setBusinessSystemId(2);

        List<ProjectStage> projectStages = project.getProjectStages();
        projectStages.add(stage);
        for (ProjectStage s: projectStages) {
            s.setExpectedScale("update" +s.getStageScale());
            s.setCustomization(1);
            s.setTarget("update"+s.getTarget());
            s.setStageScale("update"+s.getStageScale());
        }
        String json = JSON.toJSONString(project);
        MvcResult result = getMockMvc().perform(put("/project")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"), ReturnMsg.UPDATE_PROJECT_SUCCESS);
        logger.info("[end] update project  {} ",project);
        logger.info("[end] update project  {} ",project.getProjectStages());
    }

    public void createProjectPlan() throws Exception {
        logger.info("[start] create projectPlan ");
        ProjectPlanForm projectPlanVo = new ProjectPlanForm();
        Project project = projectService.selectByPrimaryKey(projectId);

        List<ProjectPlan> plans=new ArrayList<>();
        StageEntity stageEntity = new StageEntity();
        ProjectPlan projectPlan = new ProjectPlan();
        List<WeeklyBoard> tasks=new ArrayList<>();
        WeeklyBoard weeklyBoard = new WeeklyBoard();
        weeklyBoard.setPersonLiable("create weeklyBoard");
        weeklyBoard.setProjectId(project.getId());
        weeklyBoard.setOutput("create output");
        weeklyBoard.setCompletionRate(50f);
        weeklyBoard.setRealStartDate("2018-09-01");
        weeklyBoard.setRealStartDate("2018-09-05");
        weeklyBoard.setPlanFinishDate("2018-09-04");
        weeklyBoard.setPlanStartDate("2018-09-03");
        weeklyBoard.setWorkDesc("WorkDesc");
        weeklyBoard.setRemark("Remark");
        weeklyBoard.setStage(0);
        weeklyBoard.setTask(0);
        stageEntity.setType(ProjectArgs.STAGE_PROJECT_PLAN_TYPE);
        stageEntity.setProjectId(project.getId());

        projectPlan.setProjectId(project.getId());
        projectPlan.setProjectProgress("create projectPlan");
        projectPlan.setWeekProgress("create WeekProgress");
        projectPlan.setRiskAndHelp("create RiskAndHelp");
        projectPlan.setStartDate("create StartDate");
        projectPlan.setReportCycle("create ReportCycle");

        plans.add(projectPlan);
        tasks.add(weeklyBoard);
        projectPlanVo.setTasks(tasks);
        projectPlanVo.setProjectPlans(plans);
        projectPlanVo.setProject(project);
        String json = JSON.toJSONString(projectPlanVo);
        MvcResult result = getMockMvc().perform(post("/projectPlan")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"), ReturnMsg.CREATE_PROJECT_PLAN_SUCCESS);
        Object data = jsonObject.get("data");
        JSONObject jsonObject1 = JSONObject.parseObject(data.toString());
        ProjectPlanForm planForm = JSONObject.toJavaObject(jsonObject1, ProjectPlanForm.class);
        logger.info("[end]项目计划信息 {}",planForm.getProjectPlans());
        logger.info("[end]项目计划看板信息 {}",planForm.getTasks());
        projectPlanId=planForm.getProjectPlans().get(0).getId();
    }

    public void updateProjectPlan() throws  Exception{
        logger.info("[start] update projectPlan ");
        ProjectPlanForm projectPlanVo = new ProjectPlanForm();
        Project project = projectService.selectByPrimaryKey(projectId);
        project.setStatus(ProjectArgs.PROJECT_STATUS_ONLINE_NUM);

        List<ProjectPlan> plans=new ArrayList<>();

        List<ProjectPlan> projectPlans = projectPlanService.selectByProjectId(projectId);
        for (ProjectPlan projectPlan:projectPlans) {
            projectPlan.setProjectProgress("update projectPlan");
            projectPlan.setWeekProgress("update WeekProgress");
            projectPlan.setRiskAndHelp("update RiskAndHelp");
            projectPlan.setStartDate("update StartDate");
            projectPlan.setReportCycle("update ReportCycle");
            plans.add(projectPlan);
            List<StageEntity> stageEntities = stageService.selectByIdAndType(projectPlan.getStageId(), ProjectArgs.STAGE_PROJECT_PLAN_TYPE);
            projectPlan.setStageEntity(stageEntities.get(0));
        }
        List<WeeklyBoard> tasks=new ArrayList<>();
        List<WeeklyBoard> weeklyBoards = weeklyBoardService.selectByProjectId(projectId);
        for (WeeklyBoard weeklyBoard: weeklyBoards ) {
            weeklyBoard.setPersonLiable("update weeklyBoard");
            weeklyBoard.setOutput("update output");
            weeklyBoard.setCompletionRate(90f);
            weeklyBoard.setRealStartDate("2019-09-01");
            weeklyBoard.setRealStartDate("2019-09-05");
            weeklyBoard.setPlanFinishDate("2019-09-04");
            weeklyBoard.setPlanStartDate("2018-09-03");
            weeklyBoard.setWorkDesc("update WorkDesc");
            weeklyBoard.setRemark("updateRemark");
            weeklyBoard.setStage(0);
            weeklyBoard.setTask(0);
            tasks.add(weeklyBoard);
        }

        projectPlanVo.setTasks(tasks);
        projectPlanVo.setProjectPlans(plans);
        projectPlanVo.setProject(project);
        String json = JSON.toJSONString(projectPlanVo);
        MvcResult result = getMockMvc().perform(post("/projectPlan")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"), ReturnMsg.CREATE_PROJECT_PLAN_SUCCESS);
        Object data = jsonObject.get("data");
        JSONObject jsonObject1 = JSONObject.parseObject(data.toString());
        ProjectPlanForm planForm = JSONObject.toJavaObject(jsonObject1, ProjectPlanForm.class);
        logger.info("[end]更新项目计划信息 {}",planForm.getProjectPlans());
        logger.info("[end]更新项目计划看板信息 {}",planForm.getTasks());
    }

    public void loadProjectPlanFile() throws Exception{
        logger.info("[start] load [first] file to projectPlan");
        final FileInputStream fis = new FileInputStream("src/test/resources/projects/projectPlan.xlsx");
        MockMultipartFile multipartFile = new MockMultipartFile("file", "projectPlan.xlsx", "application/vnd.ms-excel", fis);
        getMockMvc().perform(fileUpload("/projectPlan/doc").file(multipartFile)
                .param("projectId",projectId.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn().getResponse().getContentAsString();
         logger.info("load [second] file to projectPlan");
    final FileInputStream fis1 = new FileInputStream("src/test/resources/projects/projectPlan.jpg");
    MockMultipartFile multipartFile1 = new MockMultipartFile("file", "projectPlan.jpg", "image/jpeg", fis1);
    getMockMvc().perform(fileUpload("/projectPlan/doc").file(multipartFile1)
            .param("projectId",projectId.toString()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andReturn().getResponse().getContentAsString();
         List<File> files = fileService.selectByProjectId(projectId);
        logger.info("[end] projectId {} contain fileList info {}",projectId,files);
}

    /**
     * 删除项目计划上传的附件
     * @param projectPlanId
     */
    public void deleteProjectPlan(Integer projectPlanId) throws Exception{
        logger.info("[start] 删除周报信息");
        MvcResult result = getMockMvc().perform(delete("/projectPlan/"+projectPlanId))
                .andExpect(status().isOk())// 模拟发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"), ReturnMsg.DELETE_PROJECT_PLAN_SUCCESS);
        logger.info("[end] 删除周报信息 success projectPlanId ={}",projectPlanId);
    }
    public void getProjectPlan() throws Exception {
        logger.info("[start] 获取项目计划信息");
        MvcResult result = getMockMvc().perform(get("/projectPlan/"+projectId))
                .andExpect(status().isOk())// 模拟发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"), ReturnMsg.PROJECT_PLAN_LIST);
        logger.info("[end] 获取项目计划信息 {}",jsonObject.get("data").toString());
    }

    /**
     * 获取项目信息的过滤条件
     */
    public ProjectFilterForm getProjectFilter() throws Exception {
        logger.info("[start] get PM project filter From");
        MvcResult result = getMockMvc().perform(get("/project/filter?isActive=true&isPm=true&isArea=false"))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"), ReturnMsg.PROJECT_FILTER_SUCCESS);
        JSONObject jsonObject1 = JSONObject.parseObject(jsonObject.get("data").toString());
        ProjectInitFilterForm planForm = JSONObject.toJavaObject(jsonObject1, ProjectInitFilterForm.class);
        logger.info(" get PM project all filters From {}",planForm);

        ProjectFilterForm projectFilterForm = new ProjectFilterForm();
        initSecurityContextDate();
        AccountEntity currentHr = AccountUtils.getCurrentHr();
        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(currentHr.getId());
        projectFilterForm.setCreateUserIds(ids);
        logger.info("[end]create PM project one filter From {}",planForm);
        return projectFilterForm;
    }
    /**
     * 获取项目数据
     */
    public void getProjectByFilter(ProjectFilterForm form) throws Exception {
        logger.info("[start] get project by ProjectFilterForm {}",form);
        String json = JSON.toJSONString(form);
        MvcResult result = getMockMvc().perform(post("/project/list")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"), ReturnMsg.PROJECT_LIST);
        logger.info("[end] get project success", jsonObject.get("data").toString());
    }

    /**
     * 创建项目计划的成本信息
     * @throws Exception
     */
    public void createCostStatisticsData() throws Exception{
        logger.info("[start] create CostStatisticsData");
        CostStatisticsForm costStatisticsForm = new CostStatisticsForm();
        List<StageForm> stageForms =new ArrayList<>();
        StageForm stageForm = new StageForm();

        stageForm.setProjectId(projectId);
        stageForm.setStartDate(new Date());
        stageForm.setEndDate(new Date());

        List<WorkTimeForm> workTimeForms = new ArrayList<>();
        WorkTimeForm workTimeForm = new WorkTimeForm();
        workTimeForm.setName("WorkTimeForm");
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
        BusinessTripForm businessTripForm = ejbGenerator.convert(businessTripEntity, BusinessTripForm.class);
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
        EquipmentForm equipmentForm = ejbGenerator.convert(equipmentEntity, EquipmentForm.class);
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
        logger.info("[end] create CostStatisticsData success {}",costStatisticsForm);
    }
    /**
     * 删除成本信息
     */
    public  void deleteCostStatisticsData(Integer tripId,Integer equimentId,Integer stageId) throws Exception{
        logger.info("[start] 删除成本统计相关信息");
        MvcResult result = getMockMvc().perform(delete("/costStatistics/trip/"+tripId))
                .andExpect(status().isOk())// 模拟发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"), "删除出差成功");
        logger.info("删除出差成功 success tripId ={}",tripId);

        MvcResult result1 = getMockMvc().perform(delete("/costStatistics/equipment/"+equimentId))
                .andExpect(status().isOk())// 模拟发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        JSONObject jsonObject1 = JSON.parseObject(result1.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject1.get("desc"), "删除设备成功");
        logger.info("删除设备成功 success equimentId ={}",equimentId);

        logger.info("[start] 删除成本统计相关信息");
        MvcResult result2 = getMockMvc().perform(delete("/costStatistics/workTime/"+stageId))
                .andExpect(status().isOk())// 模拟发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        JSONObject jsonObject2 = JSON.parseObject(result2.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject2.get("desc"), "删除工时成功");
        logger.info("删除工时成功 success stageId ={}",stageId);

    }
    /**
     * 更新成本统计数据
     */
    public void updateCostStatisticsData(CostStatisticsForm costStatisticsForm) throws  Exception{
        List<BusinessTripForm> businessTripForms = costStatisticsForm.getBusinessTripForms();
        for (BusinessTripForm businessTripForm: businessTripForms) {
            businessTripForm.setDestination("update" +businessTripForm.getDestination());
        }
        List<EquipmentForm> equipmentForms = costStatisticsForm.getEquipmentForms();
        for (EquipmentForm equipmentForm: equipmentForms) {
            equipmentForm.setDeviceType("update"+equipmentForm.getDeviceType());
        }
        String json = JSON.toJSONString(costStatisticsForm);
        MvcResult result = getMockMvc().perform(post("/costStatistics")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();

        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"),"提交成本统计数据成功");
        logger.info("[end] update CostStatisticsData success {}",costStatisticsForm);
    }

    /**
     * 获取成本统计信息
     * @throws Exception
     */
    public CostStatisticsForm getCostStatisticsData() throws Exception{
        logger.info("[start]get CostStatisticsData by project {}",projectId);
        MvcResult result = getMockMvc().perform(get("/costStatistics?projectId="+projectId))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();

        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"),"成本统计数据获取成功");
        CostStatisticsForm costStatisticsForm = JSON.parseObject(jsonObject.getString("data"), CostStatisticsForm.class);
        List<StageForm> stageForms = costStatisticsForm.getStageForms();
        Assert.assertTrue(stageForms.size() == 1);
        Assert.assertTrue("WorkTimeForm".equals(stageForms.get(0).getWorkTimeForms().get(0).getName()));
        logger.info("[end]get CostStatisticsData success {}",costStatisticsForm);
        stageId = stageForms.get(0).getWorkTimeForms().get(0).getId();
        tripId = costStatisticsForm.getBusinessTripForms().get(0).getId();
        equimentId= costStatisticsForm.getEquipmentForms().get(0).getId();
        return costStatisticsForm;
    }

    /**
     * 根据项目id新增问题风险
     */
    public RiskProblemListForm createRisk() throws Exception {
        logger.info("[start] createRisk");
        RiskProblemListForm riskProblemListForm = new RiskProblemListForm();
        List<RiskProblemForm> riskProblemForms = new ArrayList<>();
        RiskProblemForm riskProblemForm = new RiskProblemForm();
        riskProblemForm.setLevel("level");
        riskProblemForm.setMeasure("measure");
        riskProblemForm.setOccurDate("2018-01-01");
        riskProblemForm.setPersonLiable("责任人");
        riskProblemForm.setPlanedSolveDate("2018-02-01");
        riskProblemForm.setProjectId(projectId);
        riskProblemForm.setRemark("remark");
        riskProblemForm.setRisk("risk");
        riskProblemForm.setStatus(1);
        riskProblemForms.add(riskProblemForm);
        riskProblemListForm.setRiskProblemForms(riskProblemForms);
        String json = JSON.toJSONString(riskProblemListForm);
        MvcResult result = getMockMvc().perform(post("/riskProblem")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"),"提交风险与问题数据成功");
        logger.info("[end] createRisk success {}",riskProblemListForm);
        RiskProblemListForm riskProblem= JSON.parseObject(jsonObject.getString("data"), RiskProblemListForm.class);
        return riskProblem;
    }

    /**
     * 获取问题风险
     * @throws Exception
     */
    public void getRisk() throws Exception {
        logger.info("[start] getWareList ");
        MvcResult result = getMockMvc().perform(get("/riskProblem?projectId="+projectId))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"),"风险问题数据获取成功");
        List<RiskProblemForm> riskProblemForms = JSON.parseArray(jsonObject.getString("data"),RiskProblemForm.class);
        Assert.assertTrue(riskProblemForms.size() >= 1);
        List<RiskProblemForm> apps = JSON.parseArray(jsonObject.get("data").toString(), RiskProblemForm.class);
        riskId = apps.get(0).getId();
        logger.info("[end] getWareList success {}",apps);
    }

    /**
     * 更新问题风险
     */
    public void updateRisk(RiskProblemListForm riskProblemListForm) throws Exception {
        logger.info("[start] updateRisk   {}",riskProblemListForm);
        List<RiskProblemEntity> list = riskProblemService.queryByProject(projectId);
        List<RiskProblemForm> riskProblemForms = riskProblemListForm.getRiskProblemForms();
        for (RiskProblemForm riskProblemEntity: riskProblemForms) {
            riskProblemEntity.setMeasure("update setMeasure");
            riskProblemEntity.setPersonLiable("update setPersonLiable");
        }
        String json = JSON.toJSONString(riskProblemListForm);
        MvcResult result = getMockMvc().perform(post("/riskProblem")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"), "提交风险与问题数据成功");
        logger.info("[end]after updateRisk list {}",list);
    }

    /**
     * 删除问题风险
     */
    public void deleteRisk() throws Exception {
        logger.info("[start]deleteRisk by id {}",riskId);
        MvcResult result = getMockMvc().perform(delete("/riskProblem/"+riskId))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"), "删除风险与问题数据成功");
        logger.info("[end]deleteRisk by id {}",riskId);
    }

    /**
     * 新增干系人信息
     * @throws Exception
     */
    public void createProjectRelatedPerson() throws Exception {
        logger.info("[start] createProjectRelatedPerson");
        ProjectRelatedPerson person1 = new ProjectRelatedPerson();
        person1.setProjectId(projectId);
        person1.setCompanyName("create 阿里");
        person1.setName("里斯");
        person1.setRemark1("remark1");
        person1.setRemark2("remark2");
        person1.setRole("乙方");
        person1.setType(ProjectArgs.STAGE_PROJECT_INTERFACE_UNIT_TYPE);

        ProjectRelatedPerson person = new ProjectRelatedPerson();
        person.setProjectId(projectId);
        person.setCompanyName("create sensetime");
        person.setName("zhangsan");
        person.setRemark1("remark1");
        person.setRemark2("remark2");
        person.setRole("first party");
        person.setType(ProjectArgs.STAGE_PROJECT_MEMBER_TYPE);
        ArrayList<ProjectRelatedPerson> list = new ArrayList<>();
        ProjectRelatedPersonForm projectRelatedPersonForm = new ProjectRelatedPersonForm();
        list.add(person);
        list.add(person1);
        projectRelatedPersonForm.setPersonLists(list);
        String json = JSON.toJSONString(projectRelatedPersonForm);
        MvcResult result = getMockMvc().perform(post("/projectRelatedPerson")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"), "新增或更新项目干系人成功");
        logger.info("[end] createProjectRelatedPerson {}",jsonObject.get("data").toString());
    }
    /**
     * 查询项目
     */
    public List<ProjectRelatedPerson> getProjectRelatedPerson() throws Exception {
        logger.info("[start] getProjectRelatedPerson by projectId {}",projectId);
        MvcResult result = getMockMvc().perform(get("/projectRelatedPerson/"+projectId))
                .andExpect(status().isOk())// 模拟发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"), "获取项目干系人列表");
        String data = jsonObject.get("data").toString();
        List<ProjectRelatedPerson> persons = JSON.parseArray(data, ProjectRelatedPerson.class);
        logger.info("[end] getProjectRelatedPerson success {} ",persons);
        relatedPersonID=persons.get(0).getId();
        return persons;
    }

    /**
     * 新增干系人信息
     * @throws Exception
     */
    public void updateProjectRelatedPerson(List<ProjectRelatedPerson> ProjectRelatedPerson) throws Exception {
        logger.info("[start] updateProjectRelatedPerson");
        ArrayList<com.sensetime.iva.ipet.entity.ProjectRelatedPerson> list = new ArrayList<>();
        ProjectRelatedPersonForm projectRelatedPersonForm = new ProjectRelatedPersonForm();
        for (com.sensetime.iva.ipet.entity.ProjectRelatedPerson person:ProjectRelatedPerson) {
            person.setRemark3("update"+person.getId());
            person.setRemark4("update"+person.getProjectId());
            list.add(person);
        }
        projectRelatedPersonForm.setPersonLists(list);
        String json = JSON.toJSONString(projectRelatedPersonForm);
        MvcResult result = getMockMvc().perform(post("/projectRelatedPerson")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"), "新增或更新项目干系人成功");
        logger.info("[end] updateProjectRelatedPerson {}",jsonObject.get("data").toString());
    }

    /**
     * 删除项目干系人信息
     */
    public  void deleteRelatedPerson(Integer relatedPersonID) throws Exception{
        logger.info("[start] 删除项目干系人信息");
        MvcResult result = getMockMvc().perform(delete("/projectRelatedPerson/"+relatedPersonID))
                .andExpect(status().isOk())// 模拟发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"), "删除项目干系人成功");
        logger.info("删除项目干系人信息 success relatedPersonID ={}",relatedPersonID);
    }


    /**
     * 根据项目id新增实施清单
     */
    public void createApplyList() throws Exception {
        logger.info("[start] createApplyList");
        ArrayList<ApplyList> list = new ArrayList<>();
        ApplyList applyList = new ApplyList();
        applyList.setDetailJob("test applyList");
        applyList.setProjectId(projectId);
        applyList.setWorkload(55.5f);
        applyList.setStage(1);
        applyList.setProblemRemark("setProblemRemark");
        applyList.setExecuteMan("setExecuteMan");
        applyList.setTaskList(2);
        list.add(applyList);
        ApplyListForm applyListForm = new ApplyListForm();
        applyListForm.setApplyLists(list);
        String json = JSON.toJSONString(applyListForm);
        MvcResult result = getMockMvc().perform(post("/applyList")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"), ReturnMsg.CREATE_APPLY_LIST);
        logger.info("[end] createApplyList success {}",applyListForm);
    }

    /**
     * 获取实施清单
     * @throws Exception
     */
    public void getApplyList() throws Exception {
        logger.info("[start] getApplyList ");
        MvcResult result = getMockMvc().perform(get("/applyList/"+projectId))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        String data = jsonObject.get("data").toString();
        ApplyListForm apps = JSON.parseObject(data, ApplyListForm.class);
         appId = apps.getApplyLists().get(0).getId();
        Assert.assertEquals(jsonObject.get("desc"), ReturnMsg.APPLY_LIST);
        logger.info("[end] getApplyList success {}",apps);
    }

    /**
     * 更新实施清单
     */
    public void updateApplyList() throws Exception {
        logger.info("[start] updateApplyList by projectId {}",projectId);
        List<ApplyList> list = applyListService.selectByProjectId(projectId);
        logger.info("before updateApplyList list {}",list);
        list.get(0).setDetailJob(" setDetailJob update");
        list.get(0).setExecuteMan("update setExecuteMan");
        ApplyListForm applyListForm = new ApplyListForm();
        applyListForm.setApplyLists(list);
        String json = JSON.toJSONString(applyListForm);
        MvcResult result = getMockMvc().perform(post("/applyList")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"), ReturnMsg.CREATE_APPLY_LIST);
        logger.info("[end]after updateApplyList list {}",list);

    }

    /**
     * 更新实施清单
     */
    public void deleteApplyList() throws Exception {
        logger.info("[start]deleteApplyList by id {}",appId);
        MvcResult result = getMockMvc().perform(delete("/applyList/"+appId))
                .andExpect(status().isOk())// 模拟发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"), ReturnMsg.DELETE_APPLY_LIST);
        logger.info("[end]deleteApplyList by id {}",appId);
    }

    /**
     * 根据项目id新增工勘清单
     */
    public void createSurveyList() throws Exception {
        logger.info("[start] createSurveyList");
            ArrayList<SurveyList> list = new ArrayList<>();
            SurveyList surveyList = new SurveyList();
            surveyList.setProjectId(projectId);
            surveyList.setAntiCollision(false);
        surveyList.setAmount(2);
        surveyList.setDirectionAngle(54.9f);
        surveyList.setEyeDistance(2);
        surveyList.setFocalGraph("setFocalGraph");
        surveyList.setGlare("setGlare");
        surveyList.setFocalLength(154f);
        surveyList.setReexamination("setReexamination");
        surveyList.setScreen(true);
        surveyList.setPassingRate("setPassingRate");
        surveyList.setWidthHeight(65f);
        surveyList.setDc("50V");
        surveyList.setWeaklight("setWeaklight");
            list.add(surveyList);
            SurveyListForm surveyListForm = new SurveyListForm();
            surveyListForm.setSurveyLists(list);
            String json = JSON.toJSONString(surveyListForm);
            MvcResult result = getMockMvc().perform(post("/surveyList")
                    .contentType(MediaType.APPLICATION_JSON).content(json))
                    .andExpect(status().isOk())// 模拟向testRest发送get请求
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                    .andReturn();
            JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
            Assert.assertEquals(jsonObject.get("desc"), ReturnMsg.ADD_OR_UPDATE_SURVEY_LIST);
        logger.info("[end] createSurveyList success {}",surveyListForm);
    }

    /**
     * 获取工勘清单
     * @throws Exception
     */
    public void getSurveyList() throws Exception {
        logger.info("[start] getSurveyList ");
        MvcResult result = getMockMvc().perform(get("/surveyList/"+projectId))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        String data = jsonObject.get("data").toString();
        SurveyListForm apps = JSON.parseObject(data, SurveyListForm.class);
        surId = apps.getSurveyLists().get(0).getId();
        Assert.assertEquals(jsonObject.get("desc"), ReturnMsg.SUCCESS);
        logger.info("[end] getSurveyList success {}",apps);
    }

    /**
     * 更新工勘清单
     */
    public void updateSurveyList() throws Exception {
        logger.info("[start] updateSurveyList by projectId {}",projectId);
        List<SurveyList> list = surveyListService.selectByProjectId(projectId);
        SurveyList surveyList = list.get(0);
        surveyList.setGlare("update setGlare");
        surveyList.setFocalLength(154f);
        surveyList.setReexamination("update setReexamination");
        surveyList.setScreen(true);
        surveyList.setPassingRate("update setPassingRate");
        surveyList.setWidthHeight(65f);
        surveyList.setDc("50V");
        surveyList.setWeaklight("update setWeaklight");
        SurveyListForm surveyListForm = new SurveyListForm();
        surveyListForm.setSurveyLists(list);
        String json = JSON.toJSONString(surveyListForm);
        MvcResult result = getMockMvc().perform(post("/surveyList")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"), ReturnMsg.ADD_OR_UPDATE_SURVEY_LIST);
        logger.info("[end]after updateApplyList list {}",list);

    }

    /**
     * 删除工勘清单
     */
    public void deleteSurveyList() throws Exception {
        logger.info("[start]deleteSurveyList by id {}",surId);
        MvcResult result = getMockMvc().perform(delete("/surveyList/"+surId))
                .andExpect(status().isOk())// 模拟发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"), "删除工勘清单信息成功");
        logger.info("[end]deleteSurveyList by id {}",surId);
    }

    /**
     * 根据项目id新增交付物清单
     */
    public void createDeliverList() throws Exception {
        logger.info("[start] createDeliverList");
        ArrayList<DeliverList> list = new ArrayList<>();
        DeliverList deliverList = new DeliverList();
        deliverList.setTarget("test DeliverList");
        deliverList.setName("name");
        deliverList.setRemark("remark");
        deliverList.setType(1);
        deliverList.setProjectId(projectId);
        list.add(deliverList);
        DeliverListForm deliverListForm = new DeliverListForm();
        deliverListForm.setDeliverLists(list);
        String json = JSON.toJSONString(deliverListForm);
        MvcResult result = getMockMvc().perform(post("/deliverList")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"), ReturnMsg.ADD_OR_UPDATE_DELIVER_LIST);
        logger.info("[end] createDeliverList success {}",deliverListForm);
    }

    /**
     * 获取交付物清单
     * @throws Exception
     */
    public void getDeliverList() throws Exception {
        logger.info("[start] getDeliverList ");
        MvcResult result = getMockMvc().perform(get("/deliverList/"+projectId))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"), ReturnMsg.SUCCESS);
        DeliverListForm apps = JSON.parseObject(jsonObject.get("data").toString(), DeliverListForm.class);
        deleverId = apps.getDeliverLists().get(0).getId();
        logger.info("[end] getDeliverList success {}",apps);
    }

    /**
     * 更新交付物清单
     */
    public void updateDeliverList() throws Exception {
        logger.info("[start] updateSurveyList by projectId {}",projectId);
        List<DeliverList> list = deliverListService.selectByProjectId(projectId);
        DeliverList deliverList = list.get(0);
        deliverList .setTarget("test deliverList update");
        deliverList.setName("update name");
        deliverList.setPath("update path");
        DeliverListForm deliverListForm = new DeliverListForm();
        deliverListForm.setDeliverLists(list);
        String json = JSON.toJSONString(deliverListForm);
        MvcResult result = getMockMvc().perform(post("/deliverList")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"), ReturnMsg.ADD_OR_UPDATE_DELIVER_LIST);
        logger.info("[end]after updateApplyList list {}",list);

    }

    /**
     * 删除交付物信息
     */
    public void deleteDeliverList() throws Exception {
        logger.info("[start]deleteDeliverList by id {}",deleverId);
        MvcResult result = getMockMvc().perform(delete("/deliverList/"+deleverId))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"), ReturnMsg.DELETE_LIST_EMPTY);
        logger.info("[end]deleteDeliverList by id {}",deleverId);
    }

    /**
     * 根据项目id新增软硬件清单
     */
    public void createWareList() throws Exception {
        logger.info("[start] createWareList");
        ArrayList<WareList> list = new ArrayList<>();
        WareList wareList = new WareList();
        wareList.setConfigCode("test wareList");
        wareList.setNodeName("node name");
        wareList.setPoliceIp("localhost");
        wareList.setAccountPassword("pwd");
        wareList.setConfigCode("code");
        wareList.setLicenseExpiration("setLicenseExpiration");
        wareList.setSoftwareModule("setSoftwareModule");
        wareList.setRevisedRecord("setRevisedRecord");
        wareList.setVideoPrivateIp("setVideoPrivateIp");
        wareList.setPort("4567");
        wareList.setProjectId(projectId);
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
        logger.info("[end] createWareList success {}",wareListForm);
    }

    /**
     * 获取交付物清单
     * @throws Exception
     */
    public void getWareList() throws Exception {
        logger.info("[start] getWareList ");
        MvcResult result = getMockMvc().perform(get("/wareList/"+projectId))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"), "获取软硬件列表");
        WareListForm apps = JSON.parseObject(jsonObject.get("data").toString(), WareListForm.class);
        wareId = apps.getWareLists().get(0).getId();
        logger.info("[end] getWareList success {}",apps);
    }

    /**
     * 更新交付物清单
     */
    public void updateWareList() throws Exception {
        logger.info("[start] updateWareList by projectId {}",projectId);
        List<WareList> list = wareListService.selectByProjectId(projectId);
        WareList wareList = list.get(0);
        wareList.setConfigCode(" wareList update");
        wareList.setHardwareConfig("update setHardwareConfig");
        wareList.setRemark("update remark");
        wareList.setAccountPassword("update setAccountPassword");
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
        logger.info("[end]after updateWareList list {}",list);

    }

    /**
     * 删除交付物信息
     */
    public void deleteWareList() throws Exception {
        logger.info("[start]deleteWareList by id {}",wareId);
        MvcResult result = getMockMvc().perform(delete("/wareList/"+wareId))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"), "删除软硬件信息成功");
        logger.info("[end]deleteWareList by id {}",wareId);
    }

    /**
     * 创建项目结项信息
     */
    public void createProjectConclusion() throws Exception{
        logger.info("[start] createProjectConclusion with projectId {}",projectId);
        Project project = projectService.selectByPrimaryKey(projectId);

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
        MvcResult   result = getMockMvc().perform(post("/projectConclusion")
                    .contentType(MediaType.APPLICATION_JSON).content(json))
                    .andExpect(status().isOk())// 模拟向testRest发送get请求
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                    .andReturn();
            JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
            Assert.assertEquals(jsonObject.get("desc"),"提交项目结项数据成功");
        logger.info("[end]createProjectConclusion success {}",projectConclusionDataForm);
    }

    /**
     * 更新项目结项信息
     * @throws Exception
     */
    public void updateProjectConclusion()throws Exception{
        logger.info("[start] updateProjectConclusion by projectId {}",projectId);
        ProjectConclusionDataForm projectConclusionDataForm = new ProjectConclusionDataForm();
        ProjectConclusionEntity projectConclusionEntity = projectConclusionService.queryByProject(projectId);
        BeanUtils.copyProperties(projectConclusionEntity,projectConclusionDataForm);
        projectConclusionDataForm.setRemark("updateRemark");
        projectConclusionDataForm.setAfterSaleDescribe("update setAfterSaleDescribe");
        String json = JSON.toJSONString(projectConclusionDataForm);
        MvcResult result = getMockMvc().perform(post("/projectConclusion")
                    .contentType(MediaType.APPLICATION_JSON).content(json))
                    .andExpect(status().isOk())// 模拟向testRest发送get请求
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                    .andReturn();
            JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
            Assert.assertEquals(jsonObject.get("desc"),"提交项目结项数据成功");
            ProjectConclusionEntity updateResult = projectConclusionService.queryByProject(projectId);
            Assert.assertEquals(updateResult.getRemark(),"updateRemark");
          logger.info("[end] updateProjectConclusion success {}",projectConclusionDataForm);
    }

    /**
     * 获取项目结项信息
     * @throws Exception
     */
    public void getProjectConclusion() throws Exception{
        logger.info("[start] getProjectConclusion by projectId {}",projectId);
        MvcResult result = getMockMvc().perform(get("/projectConclusion?projectId="+projectId))
                    .andExpect(status().isOk())// 模拟向testRest发送get请求
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                    .andReturn();
            JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
            Assert.assertEquals(jsonObject.get("desc"),"项目结项数据获取成功");
            ProjectConclusionDataForm projectConclusionDataForm = JSON.parseObject(jsonObject.getString("data"),ProjectConclusionDataForm.class);
            Assert.assertEquals(projectConclusionDataForm.getProjectManager(),"超级管理员");
         conclusionId= projectConclusionDataForm.getId();
         logger.info("[end] getProjectConclusion success {}",projectConclusionDataForm);
    }

    /**
     * 发起结项
     * @throws Exception
     */
    public void launchConclusion(Integer conclusionId) throws Exception{
        logger.info("[start]LaunchConclusion by conclusionId{}",conclusionId);
        MvcResult result = getMockMvc().perform(get("/launchConclusion/"+conclusionId))
                    .andExpect(status().isOk())// 模拟向testRest发送get请求
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                    .andReturn();

            JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
            Assert.assertEquals(jsonObject.get("desc"),"发起结项成功");
        logger.info("[end]LaunchConclusion by conclusionId {} success" ,conclusionId);

        List<AccountEntity> accountEntities = accountService.queryAccountByResourceCode(IConstant.PROJECT_CONCLUSION_APPROVAL_RESOURCE_CODE);
            //删除结项申请的消息
            for (AccountEntity accountEntity : accountEntities ) {
                List<Message> message = messageService.getUnReadByRecId(accountEntity.getId());
                Assert.assertTrue(message.size() >=1 );
            }
    }

    /**
     * 结项请求打回
     * @throws Exception
     */
    public void approvalConclusion(Integer conclusionId,int status) throws Exception{
        logger.info("[start]approvalConclusion by conclusionId {} success" ,conclusionId);
        ProjectConclusionApprovalForm projectConclusionApprovalForm = new ProjectConclusionApprovalForm();
        projectConclusionApprovalForm.setId(conclusionId);
        projectConclusionApprovalForm.setStatus(status);
        projectConclusionApprovalForm.setReason("打回原因");

        String json = JSON.toJSONString(projectConclusionApprovalForm);
        MvcResult result = getMockMvc().perform(post("/approvalConclusion")
                    .contentType(MediaType.APPLICATION_JSON).content(json))
                    .andExpect(status().isOk())// 模拟向testRest发送get请求
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                    .andReturn();

            JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
            Assert.assertEquals(jsonObject.get("desc"),"审批结项成功");
        logger.info("[end]approvalConclusion by conclusionId {} success" ,conclusionId);
    }

    /**
     * 激活项目失败
     */
    public void activeFailProject() throws  Exception{
        logger.info("[start]activeProject project by projectId {}",projectId);
        MvcResult result = getMockMvc().perform(get("/project/active/"+projectId))
                .andExpect(status().isOk())// 模拟发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"), "项目不是结项状态,无法激活");
        logger.info("[end]activeProject project by projectId {} fail",jsonObject.get("msg").toString());

    }
    /**
     * 激活项目失败
     */
    public void activeSuccessProject() throws  Exception{
        AccountEntity accountEntity = accountService.loadAccountByUsername("admin");
        logger.info("[start]activeProject project by projectId {}",projectId);
        MvcResult result = getMockMvc().perform(get("/project/active/"+projectId))
                .andExpect(status().isOk())// 模拟发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        Assert.assertEquals(jsonObject.get("desc"), ReturnMsg.ACTIVE_PROJECT_SUCCESS);
        List<Message> message = messageService.getUnReadByRecId(accountEntity.getId());
        Assert.assertTrue(message.size() >=1 );
        logger.info("[end]activeProject project by projectId {} success",projectId);
    }

    @Transactional
    public void getUnHandleMessages() throws Exception{
        accountService.loadAccountByUsername("admin");
        initSecurityContextDate();
            MvcResult result = getMockMvc().perform(get("/message"))
                    .andExpect(status().isOk())// 模拟向testRest发送get请求
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                    .andReturn();
            JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
            List<Message> messages = JSON.parseArray(jsonObject.get("data").toString(), Message.class);
            Assert.assertEquals(jsonObject.get("desc"),"获取未处理消息成功");
            Assert.assertTrue(messages.size() >= 1 );
            messageId=messages.get(0).getId();
    }

    @Transactional
    public void setMessageHandled() throws Exception{
        accountService.loadAccountByUsername("admin");
            MvcResult result = getMockMvc().perform(get("/message/"+messageId))
                    .andExpect(status().isOk())// 模拟向testRest发送get请求
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                    .andReturn();
            JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
            Assert.assertEquals(jsonObject.get("desc"),"处理消息成功");
            Assert.assertTrue(messageService.queryById(messageId).isHandle() );
    }

    public void getPmIndex() throws Exception{
        accountService.loadAccountByUsername("admin");
        initSecurityContextDate();
        MvcResult result = getMockMvc().perform(post("/report/index?isPm=true"))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
    String data = jsonObject.get("data").toString();
    logger.info("get ReportIndexForm data success {}",data);
        Assert.assertEquals(jsonObject.get("desc"),ReturnMsg.SUCCESS);
    }


    public void getAdminIndex() throws Exception{
        accountService.loadAccountByUsername("admin");
        initSecurityContextDate();
        MvcResult result = getMockMvc().perform(post("/report/index?isPm=false"))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        String data = jsonObject.get("data").toString();
        logger.info("get getAdminIndex data success {}",data);
        Assert.assertEquals(jsonObject.get("desc"),ReturnMsg.SUCCESS);
    }


    public void getPmBoard() throws Exception{
        accountService.loadAccountByUsername("admin");
        initSecurityContextDate();
        MvcResult result = getMockMvc().perform(get("/report/pmBoard"))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        String data = jsonObject.get("data").toString();
        logger.info("get getPmBoard data success {}",data);
        Assert.assertEquals(jsonObject.get("desc"),ReturnMsg.SUCCESS);
    }


    public void getAdminBoard() throws Exception{
        accountService.loadAccountByUsername("admin");
        initSecurityContextDate();
        MvcResult result = getMockMvc().perform(get("/report/adminBoard"))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        String data = jsonObject.get("data").toString();
        logger.info("get getAdminBoard data success {}",data);
        Assert.assertEquals(jsonObject.get("desc"),ReturnMsg.SUCCESS);
    }

    public void getAdminActive() throws Exception{
        accountService.loadAccountByUsername("admin");
        initSecurityContextDate();
        MvcResult result = getMockMvc().perform(post("/report/risk?isPm=false"))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        String data = jsonObject.get("data").toString();
        logger.info("get getAdminActive data success {}",data);
        Assert.assertEquals(jsonObject.get("desc"),ReturnMsg.SUCCESS);
    }

    public void getPmActive() throws Exception{
        accountService.loadAccountByUsername("admin");
        initSecurityContextDate();
        MvcResult result = getMockMvc().perform(post("/report/risk?isPm=true"))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        String data = jsonObject.get("data").toString();
        logger.info("get getPmActive data success {}",data);
        Assert.assertEquals(jsonObject.get("desc"),ReturnMsg.SUCCESS);
    }

    public void getAdminJunctions() throws Exception{
        accountService.loadAccountByUsername("admin");
        initSecurityContextDate();
        MvcResult result = getMockMvc().perform(post("/report/junctions?isPm=false"))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        String data = jsonObject.get("data").toString();
        logger.info("get getAdminJunctions data success {}",data);
        Assert.assertEquals(jsonObject.get("desc"),ReturnMsg.SUCCESS);
    }

    public void getPmJunctions() throws Exception{
        accountService.loadAccountByUsername("admin");
        initSecurityContextDate();
        MvcResult result = getMockMvc().perform(post("/report/junctions?isPm=true"))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        String data = jsonObject.get("data").toString();
        logger.info("get getAdminJunctions data success {}",data);
        Assert.assertEquals(jsonObject.get("desc"),ReturnMsg.SUCCESS);
    }
}
