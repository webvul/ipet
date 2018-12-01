package com.sensetime.iva.ipet.service.impl;

import com.sensetime.iva.ipet.common.*;
import com.sensetime.iva.ipet.config.LoadFileConfig;
import com.sensetime.iva.ipet.entity.*;
import com.sensetime.iva.ipet.mapper.ProjectMapper;
import com.sensetime.iva.ipet.service.*;
import com.sensetime.iva.ipet.util.AccountUtils;
import com.sensetime.iva.ipet.util.ChooseUtil;
import com.sensetime.iva.ipet.util.ExcelImportUtil;
import com.sensetime.iva.ipet.vo.form.ProjectFilterForm;
import com.sensetime.iva.ipet.vo.form.ProjectForm;
import com.sensetime.iva.ipet.vo.form.ProjectInitFilterForm;
import com.sensetime.iva.ipet.web.exception.BusinessException;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author  gongchao
 */
@Component
public class ProjectServiceImpl implements ProjectService {
    private static final Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);

    @Autowired
    ProjectMapper projectMapper;
    @Autowired
    ProjectStageService projectStageService;
    @Autowired
    LoadFileConfig loadFileConfig;
    @Autowired
    FileService fileService;
    @Autowired
    BusinessSystemService businessSystemService;
    @Autowired
    BusinessSystemPlatformService businessSystemPlatformService;
    @Autowired
    ProjectConclusionService projectConclusionService;
    @Autowired
    AccountService accountService;
    @Autowired
    MessageService messageService;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    SimpMessagingTemplate messagingTemplate;
    @Autowired
    AreaService areaService;

    @Override
    public List<ProjectForm> selectAll(ProjectFilterForm projectFilterForm){
        logger.info("selectAll project projectFilterForm  "+projectFilterForm);
        return projectMapper.selectAll(projectFilterForm);
    }

    @Override
    public int  insert(Project project){
        logger.info("insert project="+project);
        return projectMapper.insert(project);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        logger.info("delete project id ="+id);
        return projectMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Project selectByPrimaryKey(Integer id) {
        logger.info("select project id ="+id);
        return projectMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(Project project) {
        logger.info("update project ="+project);
        return projectMapper.updateByPrimaryKey(project);
    }

    @CachePut(cacheNames = "Projects",keyGenerator = "projectKeyGenerator")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Project importProject(MultipartFile file) throws  Exception{
        long size = file.getSize();
        //验证文件大小
        logger.debug("验证文件大小");
        ExcelImportUtil.volidateFileSize(size, CellNum.MAX_LOAD);
        logger.debug("验证文件大小通过");
        logger.debug("开始验证上传文件内容及类型");
        Workbook workbook = ExcelImportUtil.getWorkbook(file);
        if(workbook==null){
            // 返回空 上传文件为空或不是xls或xlsx格式
            logger.error("file not xlx or xlsx  error {}",ExceptionMsg.EXCEL_EMPTY_OR_TYPE_ERROR);
            throw  new Exception(ExceptionMsg.EXCEL_EMPTY_OR_TYPE_ERROR);
        }
        logger.debug("得到创建的Workbook");
        //根据sheet名称获取表格数据
        Sheet sheet  =workbook.getSheet(ExcelTitleToClass.IMPORT_PROJECT);
        if(sheet==null){
            logger.error("EXCEL ANALYSIS SHEET ERROR {}",ExceptionMsg.EXCEL_GET_SHEET_ERROR);
            //获取每个Sheet表
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                logger.error("{当前上传所有excel的sheet名称为 [{}]}",workbook.getSheetAt(i).getSheetName());
            }
            throw  new Exception(ExceptionMsg.EXCEL_GET_SHEET_ERROR);
        }
        List<String> keyWords = new ArrayList<>();
        //根据sheet中的标题去分组 文字要严格匹配
        keyWords.add(ExcelTitleToClass.PROJECT);
        keyWords.add(ExcelTitleToClass.PROJECT_STAGE);
        keyWords.add(ExcelTitleToClass.PROJECT_REVIEW_RESULTS);
        //根据关键字去解析分组得到Map
        logger.info("根据"+keyWords+"关键字去解析分组得到Map");
        Map<String, List<String>> stringListMap = ExcelImportUtil.readExcelManyTypeSheet(sheet,keyWords,ExcelTitleToClass.PROJECT_MAX_COLUMN);
        logger.debug("上传文件解析数据的结果 [{}]",stringListMap);
        if(stringListMap==null){
            logger.error("EXCEL ANALYSIS ERROR {}",ExceptionMsg.EXCEL_ANALYSIS_ERROR);
            throw  new Exception(ExceptionMsg.EXCEL_ANALYSIS_ERROR);
        }
        //该组信息不用，故而移除
        stringListMap.remove(ExcelTitleToClass.PROJECT_REVIEW_RESULTS);
        logger.debug("移除[{}]的数据的结果 [{}]",ExcelTitleToClass.PROJECT_REVIEW_RESULTS,stringListMap);
        Project project=new Project();
        logger.debug("获取登陆用户");
        //项目经理
        try{ AccountEntity currentHr = AccountUtils.getCurrentHr();
            project.setCreateUserId(currentHr.getId());
        }catch (Exception e){
            logger.error("account is not login error [{}] detail error[{}]","用户未登录",e.getMessage());
            logger.error("用户未登录"+e.getMessage());
            throw  new Exception("请先登陆");
        }
        logger.debug("解析数据");
        for(Map.Entry<String, List<String>> entry: stringListMap.entrySet())
        {
            //项目立项数据处理
            if(ExcelTitleToClass.PROJECT.equals(entry.getKey())){
                logger.info("项目立项数据处理");
                project = this.setProject(entry.getValue(),project);
                logger.debug("after ANALYSIS project [{}]",project);
            }
            //项目阶段数据处理
            else if(ExcelTitleToClass.PROJECT_STAGE.equals(entry.getKey())){
                logger.info("项目阶段数据处理");
                List<ProjectStage>   stageList= projectStageService.setProjectStage(entry.getValue());
                logger.debug("after ANALYSIS ProjectStage [{}]",stageList);
                if(stageList!=null&&stageList.size()>0){
                    project.setProjectStages(stageList);
                }
            }else{
                //模板变化时不允许导入防止脏数据
                logger.error("excel type error {}",ExceptionMsg.EXCEL_TYPE_ERROR);
                throw  new Exception(ExceptionMsg.EXCEL_TYPE_ERROR);
            }
        }
        //保存文件到服务器
        logger.info("保存文件到服务器");
        File file1 = this.upLoad(file);
        fileService.insert(file1);
        project.setDocFileId(file1.getId());
        logger.debug("解析成功,返回项目立项信息");
        return project;
    }

    @Override
    public Project setProject(List<String> valuesList, Project project) throws Exception{
        logger.debug("解析的项目信息值为:"+valuesList.toString());
        //项目名称
        String name = valuesList.get(CellNum.SECOUND);
        //是否旧项目新需求
        String fromOldProject = valuesList.get(CellNum.FIOURTH);
        //项目编号
        String serial = valuesList.get(CellNum.SIXTH);
        //CRM编号
        String crmNo = valuesList.get(CellNum.EIGHTH);
        //项目类型(合同/pk试点/试点转合同)
        String type= valuesList.get(CellNum.TENTH);
        //成单金额
        String amount= valuesList.get(CellNum.TWELFTH);
        //销售经理
        String saleManager= valuesList.get(CellNum.FOURTEENTH);
        //解决方案经理
        String solutionManager= valuesList.get(CellNum.SIXTEENTH);
        //售前工程师
        String preSale= valuesList.get(CellNum.EIGHTEENTH);
        //甲方
        String firstParty= valuesList.get(CellNum.TWENTIETH);
        //合作伙伴/集成商名称
        String partners= valuesList.get(CellNum.TWENTIETH2);
        //友商
        String friends= valuesList.get(CellNum.TWENTIETH4);
        //项目背景
        String background= valuesList.get(CellNum.TWENTIETH6);
        //逻辑判断是否旧项目新需求
        if(ExcelTitleToClass.EN_YES.equals(fromOldProject)|| ExcelTitleToClass.CH_YES.equals(fromOldProject)|| ExcelTitleToClass.NUM_YES.equals(fromOldProject)){
            project.setFromOldProject(ProjectArgs.PROJECT_IS_FROM_OLD);
        }else{
            //新项目
            project.setFromOldProject(ProjectArgs.PROJECT_NOT_FROM_OLD);
        }
        project.setName(name);
        project.setSerial(serial);
        project.setCrmNo(crmNo);

        if(!StringUtils.isEmpty(type)){
            int type1 = ChooseUtil.getProjectType(type);
            if(type1==-1){
                logger.error(ExceptionMsg.TYPE_NOT_FOUND);
                throw  new Exception(ExceptionMsg.TYPE_NOT_FOUND);
            }
            project.setType(type1);
        }
        project.setAmount(amount);
        project.setSaleManager(saleManager);
        project.setSolutionManager(solutionManager);
        project.setPreSale(preSale);
        project.setFirstParty(firstParty);
        project.setPartners(partners);
        project.setFriends(friends);
        project.setStatus(ProjectArgs.PROJECT_STATUS_READY_NUM);
        project.setBackground(background);
        logger.debug("设置项目立项的值结束");
        return project;
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    @CacheEvict(cacheNames = "Projects",keyGenerator = "projectKeyGenerator")
    public Project createProject(Project project, List<ProjectStage> projectStages) throws  Exception{
        logger.debug("创建项目立项的信息");
        logger.info("createProject base Project [{}] and List<ProjectStage>",project,projectStages);
        //获取项目经理
        try{
            project.setCreateUserId(AccountUtils.getCurrentHr().getId());
        }catch (Exception e){
            logger.error("account not login [{}]","当前用户没有登陆无法获取用户信息");
            throw new BusinessException("当前用户没有登陆无法获取用户信息");
        }
        project.setStatus(ProjectArgs.PROJECT_STATUS_READY_NUM);
        projectMapper.insert(project);
        if(projectStages!=null&&projectStages.size()>0){
            for(ProjectStage stage:projectStages){
                stage.setProjectId(project.getId());
                projectStageService.insert(stage);
                logger.debug("创建项目阶段的的信息");
            }
        }
        return project;
    }

    @Override
    public File upLoad(MultipartFile file) throws Exception{
        logger.debug("开始上传文件");
        return  ExcelImportUtil.uploadFile(file, loadFileConfig.getProjectLocation());
    }

    @Override
    public HSSFWorkbook export(HSSFWorkbook wb, Project project) {
        //设置sheet的名字
        HSSFSheet sheet = wb.createSheet(ExcelTitleToClass.EXPORT_PROJECT);
        //设置第一列的宽度
        sheet.setColumnWidth(CellNum.ZERO_COLUMN,20 * 256);
        //标题样式
        HSSFCellStyle titleStyle = ExcelImportUtil.createCellStyle(wb, (short) 12, true, true);
        //合并单元格
        this.addMergedRegion(sheet);
        //标题
        Row row0=sheet.createRow(CellNum.ZERO_ROW);
        Cell cell0 = row0.createCell(CellNum.ZERO_COLUMN);
        cell0.setCellStyle(titleStyle);
        cell0.setCellValue(ExcelTitleToClass.PROJECT);

        Row row1=sheet.createRow(CellNum.FIRST_ROW);
        row1.createCell(CellNum.ZERO_COLUMN).setCellValue("项目名称");
        row1.createCell(CellNum.FIRST_COLUMN).setCellValue(project.getName()!=null?project.getName():"");

        Row row2=sheet.createRow(CellNum.SECOUND_ROW);
        row2.createCell(CellNum.ZERO_COLUMN).setCellValue("是否旧项目新需求");
        row2.createCell(CellNum.FIRST_COLUMN).setCellValue(ChooseUtil.getProjectRequireToCH(project.getFromOldProject()!=null?project.getFromOldProject():-1));
        row2.createCell(CellNum.FOURTH_COLUMN).setCellValue("项目编号");
        row2.createCell(CellNum.FIFTH_COLUMN).setCellValue(project.getSerial()!=null?project.getSerial():"");

        Row row3=sheet.createRow(CellNum.THIRD_ROW);
        row3.createCell(CellNum.ZERO_COLUMN).setCellValue("CRM编号");
        row3.createCell(CellNum.FIRST_COLUMN).setCellValue(project.getCrmNo()!=null?project.getCrmNo():"");

        Row row4=sheet.createRow(CellNum.FOURTH_ROW);
        row4.createCell(CellNum.ZERO_COLUMN).setCellValue("项目类型");
        row4.createCell(CellNum.FIRST_COLUMN).setCellValue(ChooseUtil.getProjectTypeToCH(project.getType()!=null?project.getType():-1));

        Row row5=sheet.createRow(CellNum.FIFTH_COLUMN);
        row5.createCell(CellNum.ZERO_COLUMN).setCellValue("项目级别");
        row5.createCell(CellNum.FIRST_COLUMN).setCellValue(ChooseUtil.getProjectLevelToCH(project.getLevel()!=null?project.getLevel():-1));

        Row row6=sheet.createRow(CellNum.SIXTH_ROW);
        row6.createCell(CellNum.ZERO_COLUMN).setCellValue("成单金额");
        row6.createCell(CellNum.FIRST_COLUMN).setCellValue(project.getAmount());

        Row row7=sheet.createRow(CellNum.SEVENTH_ROW);
        row7.createCell(CellNum.ZERO_COLUMN).setCellValue("销售经理");
        row7.createCell(CellNum.FIRST_COLUMN).setCellValue(project.getSaleManager()!=null?project.getSaleManager():"");

        Row row8=sheet.createRow(CellNum.EIGHTH_ROW);
        row8.createCell(CellNum.ZERO_COLUMN).setCellValue("解决方案经理");
        row8.createCell(CellNum.FIRST_COLUMN).setCellValue(project.getSolutionManager()!=null?project.getSolutionManager():"");

        Row row9=sheet.createRow(CellNum.NINTH_ROW);
        row9.createCell(CellNum.ZERO_COLUMN).setCellValue("售前工程师");
        row9.createCell(CellNum.FIRST_COLUMN).setCellValue(project.getPreSale()!=null?project.getPreSale():"");

        Row row10=sheet.createRow(CellNum.TENTH_ROW);
        row10.createCell(CellNum.ZERO_COLUMN).setCellValue("甲方名称");
        row10.createCell(CellNum.FIRST_COLUMN).setCellValue(project.getFirstParty()!=null?project.getFirstParty():"");

        Row row11=sheet.createRow(CellNum.ELEVENTH_ROW);
        row11.createCell(CellNum.ZERO_COLUMN).setCellValue("合作伙伴/集成商名称");
        row11.createCell(CellNum.FIRST_COLUMN).setCellValue(project.getPartners());

        Row row12=sheet.createRow(CellNum.TWELFTH_ROW);
        row12.createCell(CellNum.ZERO_COLUMN).setCellValue("友商");
        row12.createCell(CellNum.FIRST_COLUMN).setCellValue(project.getFriends()!=null?project.getFriends():"");

        Row row13=sheet.createRow(CellNum.THIRTEENTH_ROW);
        row13.createCell(CellNum.ZERO_COLUMN).setCellValue("项目背景");
        row13.createCell(CellNum.FIRST_COLUMN).setCellValue(project.getBackground()!=null?project.getBackground():"");

        Row row14=sheet.createRow(CellNum.FOURTEENTH_ROW);
        row14.createCell(CellNum.ZERO_COLUMN).setCellValue("附件备注");
        row14.createCell(CellNum.FIRST_COLUMN).setCellValue(project.getAttachment()!=null?project.getAttachment():"");

        Row row15=sheet.createRow(CellNum.FIFTEENTH_ROW);
        Cell cell15 = row15.createCell(CellNum.ZERO_COLUMN);
        cell15.setCellStyle(titleStyle);
        cell15.setCellValue("项目目标");

        Row row16=sheet.createRow(CellNum.SIXTEENTH_ROW);
        row16.createCell(CellNum.ZERO_COLUMN).setCellValue("项目阶段");
        row16.createCell(CellNum.FIRST_COLUMN).setCellValue("阶段目标");
        row16.createCell(CellNum.SECOUND_COLUMN).setCellValue("交付日期");
        row16.createCell(CellNum.THIRD_COLUMN).setCellValue("业务系统");
        row16.createCell(CellNum.FOURTH_COLUMN).setCellValue("平台");
        row16.createCell(CellNum.FIFTH_COLUMN).setCellValue("阶段规模");
        row16.createCell(CellNum.SIXTH_COLUMN).setCellValue("预期规模");
        row16.createCell(CellNum.SEVENTH_COLUMN).setCellValue("产品要求");
        row16.createCell(CellNum.EIGHTH_COLUMN).setCellValue("定制化需求");
        List<ProjectStage> stages = project.getProjectStages();

        int stage= CellNum.SEVENTEENTH_ROW;
        for (int i=0; i<stages.size();i++){
            ProjectStage projectStage = stages.get(i);
            Row temp=sheet.createRow(stage);
            temp.createCell(CellNum.ZERO_COLUMN).setCellValue(ChooseUtil.getProjectStepToCH(projectStage.getStep()!=null?projectStage.getStep():-1));
            temp.createCell(CellNum.FIRST_COLUMN).setCellValue(projectStage.getTarget()!=null?projectStage.getTarget():"");
            temp.createCell(CellNum.SECOUND_COLUMN).setCellValue(projectStage.getDeliveryDate()!=null?projectStage.getDeliveryDate():"");
            //系统名称
            BusinessSystem system = businessSystemService.selectByPrimaryKey(projectStage.getBusinessSystemId()!=null?projectStage.getBusinessSystemId():-1);
            if(system!=null){
                temp.createCell(CellNum.THIRD_COLUMN).setCellValue(system.getName());
            }else{
                temp.createCell(CellNum.THIRD_COLUMN).setCellValue("未查询到");
            }
            //平台名称
            BusinessSystemPlatform platform = businessSystemPlatformService.selectByPrimaryKey(projectStage.getPlatformId()!=null?projectStage.getPlatformId():-1);
            if(platform!=null){
                temp.createCell(CellNum.FOURTH_COLUMN).setCellValue(platform.getName());
            }else{
                temp.createCell(CellNum.FOURTH_COLUMN).setCellValue("未查询到");
            }
            temp.createCell(CellNum.FIFTH_COLUMN).setCellValue(projectStage.getStageScale()!=null?projectStage.getStageScale():"");
            temp.createCell(CellNum.SIXTH_COLUMN).setCellValue(projectStage.getExpectedScale()!=null?projectStage.getExpectedScale():"");
            temp.createCell(CellNum.SEVENTH_COLUMN).setCellValue(ChooseUtil.getProjectRequireToCH(projectStage.getProductRequire()!=null?projectStage.getProductRequire():-1));
            temp.createCell(CellNum.EIGHTH_COLUMN).setCellValue(ChooseUtil.getProjectRequireToCH(projectStage.getCustomization()!=null?projectStage.getCustomization():-1));
            stage++;
        }
        return wb;
    }

    @Override
    public void addMergedRegion(HSSFSheet sheet) {
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,9));
        sheet.addMergedRegion(new CellRangeAddress(1,1,1,9));
        sheet.addMergedRegion(new CellRangeAddress(2,2,1,3));
        sheet.addMergedRegion(new CellRangeAddress(2,2,5,9));
        sheet.addMergedRegion(new CellRangeAddress(3,3,1,9));
        sheet.addMergedRegion(new CellRangeAddress(4,4,1,9));
        sheet.addMergedRegion(new CellRangeAddress(5,5,1,9));
        sheet.addMergedRegion(new CellRangeAddress(6,6,1,9));
        sheet.addMergedRegion(new CellRangeAddress(7,7,1,9));
        sheet.addMergedRegion(new CellRangeAddress(8,8,1,9));
        sheet.addMergedRegion(new CellRangeAddress(9,9,1,9));
        sheet.addMergedRegion(new CellRangeAddress(10,10,1,9));
        sheet.addMergedRegion(new CellRangeAddress(11,11,1,9));
        sheet.addMergedRegion(new CellRangeAddress(12,12,1,9));
        sheet.addMergedRegion(new CellRangeAddress(13,13,1,9));
        sheet.addMergedRegion(new CellRangeAddress(14,14,1,9));
        sheet.addMergedRegion(new CellRangeAddress(15,15,0,9));
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int delete(Project project) {
        List<ProjectStage> projectStages = project.getProjectStages();
        for (ProjectStage stage: projectStages) {
            projectStageService.deleteByPrimaryKey(stage.getId());
        }
        int i = this.deleteByPrimaryKey(project.getId());
        return i;
    }

    @Cacheable(cacheNames = "Projects",keyGenerator = "projectKeyGenerator")
    @Override
    public Project init() {
       List<ProjectStage> stages = new ArrayList<>();
        Project project = new Project();
        project.setProjectStages(stages);
        logger.debug("init Project data [{}]",project);
        return project;
    }

    @Override
    public List<Project> selectNotInStatus(Integer status) {
        logger.info("Project selectNotInStatus [{}]",status);
        return projectMapper.selectNotInStatus(status);
    }

    @Override
    public void changeProjectStatus(int id, int status) {
        logger.info("changeProjectStatus by projectId [{}] and status [{}]",id,status);
        projectMapper.changeProjectStatus(id, status);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void activeProject(int id) throws Exception{
        Project project = this.selectByPrimaryKey(id);
        logger.info("select by projectId [{}] result Project [{}]",id,project);
        if(project==null){
            logger.error("激活项目不存在");
            throw new BusinessException("激活项目不存在");
        }
        if(ProjectArgs.PROJECT_STATUS_JUNCTIONS_NUM!=project.getStatus()){
            logger.error("项目不是结项状态 status [{}]",project.getSerial());
            throw new BusinessException("项目不是结项状态,无法激活");
        }
        project.setStatus(project.getLastStatus());
        this.updateByPrimaryKey(project);
        ProjectConclusionEntity conclusionEntity = projectConclusionService.queryByProject(id);
        logger.info("get conclusionEntity by projectId [{}] result [{}]",id,conclusionEntity);
        if(conclusionEntity!=null){
            logger.info("结项激活");
            projectConclusionService.updateStatus(conclusionEntity.getId(),ProjectArgs.UNHANDLE,new Timestamp(System.currentTimeMillis()));
            logger.info("结项激活成功");
        }else{
            logger.warn("当前项目查不到结项数据");
        }

        Message message = new Message();
        message.setReceiverId(project.getCreateUserId());
        message.setEvent(IConstant.PROJECT_CONCLUSION_ACTIVE);
        message.setTitle(IConstant.PROJECT_CONCLUSION_ACTIVE_TITLE);
        message.setContent(project.getName());
        message.setSend("系统");
        message.setParam1(project.getId());
        message.setHandle(false);
        message.setCreateTime(new Timestamp(System.currentTimeMillis()));
        logger.info("create ProjectConclusion message [{}]",message);
        messageService.addMessage(message);

        AccountEntity accountEntity = accountService.queryAccountById(project.getCreateUserId());
        /**
         * 若用户在线，则推送相关消息
         */
        AccountEntity redisAccount = (AccountEntity) redisTemplate.boundValueOps(accountEntity.getUsername()).get();
        if(redisAccount != null){
            messagingTemplate.convertAndSendToUser(redisAccount.getUsername(),"/message",message);
        }
    }

    @Override
    public ProjectInitFilterForm getFilter(Boolean isActive,Boolean isPm,Boolean isArea) throws Exception{
        logger.info("创建过滤条件 isActive [{}]  isPm [{}] isArea [{}]",isActive,isPm,isArea);
        ProjectInitFilterForm initFilterForm = new ProjectInitFilterForm();
        //判断是PM还是管理员
        if(!isPm){
            logger.info("login user is administrator");
            //管理员有项目经理的过滤条件
            List<AccountEntity> accounts = accountService.getAllAccountFromProject();
            logger.info("all accounts [{}]",accounts);
            HashMap<Integer, String> projectManageMap = new HashMap<>(16);
            //前端要空结构
            initFilterForm.setProjectManagerMap(projectManageMap);
            if(accounts!=null&&accounts.size()>0){
                for (AccountEntity account: accounts ) {
                    projectManageMap.put(account.getId(),account.getUsername());
                }
            }
            logger.info("projectManageMap [{}]",projectManageMap);
            //管理员拥有所有区域过滤条件
            List<AreaEntity> areas = areaService.getAll();
            HashMap<Integer, String> areaMap = new HashMap<>(16);
            initFilterForm.setAreaMap(areaMap);
            if(areas!=null&&areas.size()>0){
                for (AreaEntity areaEntity: areas ) {
                    areaMap.put(areaEntity.getId(),areaEntity.getName());
                }
            }
            logger.info("areaMap [{}]",areaMap);
            //销售经理
            List<String> saleManager = this.getDistinctSaleManager(null);
            if(saleManager!=null&&saleManager.size()>0){
                initFilterForm.setSaleManagers(saleManager);
            }else{
                initFilterForm.setSaleManagers(new ArrayList<>());
            }
            logger.info("saleManager id [{}]",saleManager);
            //解决方案项目经理
            List<String> solutionManager = this.getDistinctSolutionManager(null);
            if(solutionManager!=null&&solutionManager.size()>0){
                initFilterForm.setSolutionManagers(solutionManager);
            }else{
                initFilterForm.setSolutionManagers(new ArrayList<>());
            }
            logger.info("solutionManager id [{}]",solutionManager);
        }else{
            logger.info("login user is pm");
            //PM我的过滤条件
            AccountEntity currentHr = AccountUtils.getCurrentHr();
            Integer createUserId = currentHr.getId();
            //项目经理
            HashMap<Integer, String> projectManageMap = new HashMap<>(16);
            //PM所在区域的所有PM
           if(isArea){
               logger.info("login user is get 区域页面");
               if(currentHr.getAreaId()==null){
                   logger.error("当前PM没有维护所在区域");
                    throw new BusinessException("当前PM没有维护所在区域");
               }
               List<AccountEntity> accounts = accountService.getSameUserByAreaId(currentHr.getAreaId());
               if(accounts!=null&&accounts.size()>0){
                   for (AccountEntity account: accounts ) {
                       projectManageMap.put(account.getId(),account.getUsername());
                   }
               }
           }else{//仅仅自己的项目
               logger.info("login user is get 我的页面");
               projectManageMap.put(createUserId,currentHr.getUsername());
           }
            initFilterForm.setProjectManagerMap(projectManageMap);
            //区域
            AreaEntity areaEntity = areaService.queryById(currentHr.getAreaId() == null ? -1 : currentHr.getAreaId());
            HashMap<Integer, String> areaMap = new HashMap<>(16);
            initFilterForm.setAreaMap(areaMap);
            if(areaEntity!=null){
                areaMap.put(areaEntity.getId(),areaEntity.getName());
            }
            logger.info("areaMap [{}]",areaMap);
            //销售经理
            List<String> saleManager = this.getDistinctSaleManager(createUserId);
            if(saleManager!=null&&saleManager.size()>0){
                initFilterForm.setSaleManagers(saleManager);
            }else{
                initFilterForm.setSaleManagers(new ArrayList<>());
            }
            logger.info("saleManager id [{}]",saleManager);
            //解决方案项目经理
            List<String> solutionManager = this.getDistinctSolutionManager(createUserId);
            if(solutionManager!=null&&solutionManager.size()>0){
                initFilterForm.setSolutionManagers(solutionManager);
            }else{
                initFilterForm.setSolutionManagers(new ArrayList<>());
            }
            logger.info("solutionManager id [{}]",solutionManager);
        }
        if(isActive){
            //活跃项目
            initFilterForm.setStatusMap(ChooseUtil.getActiveStatusMap());
        }else{
            //结项项目
            initFilterForm.setStatusMap(ChooseUtil.getAcceptStatusMap());
        }
        //项目级别
        initFilterForm.setLevelMap(ChooseUtil.getLevelMap());
        //项目类型
        initFilterForm.setTypeMap(ChooseUtil.getTypeMap());
        //完成率
        initFilterForm.setCompletionRateMap(ChooseUtil.getCompletionRateMap());
        //创建时间
        initFilterForm.setCreateTimeMap(ChooseUtil.getCreateTimeMap());
        logger.info("return  initFilterForm [{}]",initFilterForm);
        return initFilterForm;
    }

    @Override
    public List<String> getDistinctSaleManager(Integer createUserId) {
        return projectMapper.getDistinctSaleManager(createUserId);
    }
    @Override
    public List<String> getDistinctSolutionManager(Integer createUserId) {
        return projectMapper.getDistinctSolutionManager(createUserId);
    }

    @CachePut(cacheNames = "Projects",keyGenerator = "projectKeyGenerator")
    @Override
    public Project cacheProject(Project project) {
        return project;
    }
    @CachePut(cacheNames = "Projects",keyGenerator = "projectKeyGenerator")
    @Override
    public Project cleanCacheProject() {
        return null;
    }

}
