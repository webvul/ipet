package com.sensetime.iva.ipet.service.impl;

import com.sensetime.iva.ipet.common.CellNum;
import com.sensetime.iva.ipet.common.ExcelTitleToClass;
import com.sensetime.iva.ipet.common.IConstant;
import com.sensetime.iva.ipet.common.ProjectArgs;
import com.sensetime.iva.ipet.entity.*;
import com.sensetime.iva.ipet.mapper.ProjectConclusionMapper;
import com.sensetime.iva.ipet.service.*;
import com.sensetime.iva.ipet.util.ChooseUtil;
import com.sensetime.iva.ipet.util.ExcelImportUtil;
import com.sensetime.iva.ipet.vo.form.ProjectConclusionApprovalForm;
import com.sensetime.iva.ipet.vo.form.ProjectConclusionDataForm;
import com.sensetime.iva.ipet.web.exception.BusinessException;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @author : ChaiLongLong
 * @date : 2018/8/13 14:14
 */
@Component
public class ProjectConclusionServiceImpl implements ProjectConclusionService {

    private static final Logger logger = LoggerFactory.getLogger(ProjectConclusionServiceImpl.class);

    @Autowired
    ProjectConclusionMapper projectConclusionMapper;
    @Autowired
    ProjectService projectService;
    @Autowired
    AccountService accountService;
    @Autowired
    StageService stageService;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    SimpMessagingTemplate messagingTemplate;
    @Autowired
    MessageService messageService;

    @Override
    public void addProjectConclusion(ProjectConclusionEntity projectConclusionEntity) {
        logger.info("addProjectConclusion param projectConclusionEntity "+projectConclusionEntity.toString());
        projectConclusionMapper.addProjectConclusion(projectConclusionEntity);
    }

    @Override
    public void updateProjectConclusion(ProjectConclusionEntity projectConclusionEntity) {
        logger.info("updateProjectConclusion param projectConclusionEntity "+projectConclusionEntity.toString());
        projectConclusionMapper.updateProjectConclusion(projectConclusionEntity);
    }

    @Override
    public ProjectConclusionEntity queryByProject(int id) {
        logger.info("queryByProject id "+id);
        return projectConclusionMapper.queryByProject(id);
    }

    @Override
    public void deleteById(int id) {
        logger.info("deleteById id " + id);
        projectConclusionMapper.deleteById(id);
    }

    @Override
    public ProjectConclusionEntity queryById(int id) {
        logger.info("queryById id "+id);
        return projectConclusionMapper.queryById(id);
    }

    @Override
    public void updateStatus(int id, int status, Timestamp updateTime) {
        logger.info("updateStatus id "+id+" status "+status+" updateTime "+updateTime);
        projectConclusionMapper.updateStatus(id, status,updateTime);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ProjectConclusionDataForm initProjectConclusionData(int projectId) {
        logger.info("initProjectConclusionData param "+projectId);
        Project project = projectService.selectByPrimaryKey(projectId);
        ProjectConclusionEntity projectConclusionEntity = projectConclusionMapper.queryByProject(projectId);
       logger.info("get projectConclusionEntity [{}]",projectConclusionEntity);
        ProjectConclusionDataForm projectConclusionDataForm = new ProjectConclusionDataForm();

        projectConclusionDataForm.setName(project.getName());
        projectConclusionDataForm.setSerial(project.getSerial());
        projectConclusionDataForm.setProjectManager(accountService.queryAccountById(project.getCreateUserId()).getName());
        projectConclusionDataForm.setFriends(project.getFriends());
        if(projectConclusionEntity != null){
            BeanUtils.copyProperties(projectConclusionEntity, projectConclusionDataForm);
        }
        return projectConclusionDataForm;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void submitProjectConclusionData(ProjectConclusionDataForm projectConclusionDataForm) throws Exception{
        logger.info("submitProjectConclusionData " +projectConclusionDataForm.toString());
        if(projectConclusionDataForm.getId() != null && projectConclusionDataForm.getId() != 0){
            ProjectConclusionEntity projectConclusionEntity = projectConclusionMapper.queryById(projectConclusionDataForm.getId());
            logger.info("update projectConclusionEntity by id [{}] result [{}]",projectConclusionDataForm.getId(),projectConclusionEntity);
            BeanUtils.copyProperties(projectConclusionDataForm, projectConclusionEntity);
            projectConclusionMapper.updateProjectConclusion(projectConclusionEntity);
        }else {
            ProjectConclusionEntity projectConclusionEntity = new ProjectConclusionEntity();
            BeanUtils.copyProperties(projectConclusionDataForm, projectConclusionEntity);
            projectConclusionEntity.setStatus(ProjectArgs.UNHANDLE);
            logger.info("insert ProjectConclusionEntity [{}]",projectConclusionEntity);
            projectConclusionMapper.addProjectConclusion(projectConclusionEntity);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void launchConclusion(int projectConclusionId) throws Exception{
        logger.info("launchConclusion param projectConclusionId "+projectConclusionId);
        ProjectConclusionEntity projectConclusionEntity = projectConclusionMapper.queryById(projectConclusionId);
        logger.info("get projectConclusionEntity [{}]",projectConclusionEntity);
        Project project = projectService.selectByPrimaryKey(projectConclusionEntity.getProjectId());
        //当结项状态为 ProjectArgs.UNHANDLE=1 未处理    ProjectArgs.REPULSE=4 打回时，才可发起结项
        if(projectConclusionEntity.getStatus() == ProjectArgs.UNHANDLE || projectConclusionEntity.getStatus() == ProjectArgs.REPULSE){
            projectConclusionMapper.updateStatus(projectConclusionId,ProjectArgs.WAIT_FOR_APPROVAL,new Timestamp(System.currentTimeMillis()));
            sendMessageToHaveApprovalAuthorityAccount(projectConclusionEntity.getProjectId(), project.getName(), IConstant.PROJECT_CONCLUSION_REPLY_TITLE, IConstant.PROJECT_CONCLUSION_REPLY);
        }else{
            logger.error("当前状态不可结项 [{}]",projectConclusionEntity.getStatus());
            throw new BusinessException("当前状态不可结项");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void approvalConclusion(ProjectConclusionApprovalForm projectConclusionApprovalForm) throws Exception {
        logger.info("approvalConclusion param "+projectConclusionApprovalForm.toString());
        ProjectConclusionEntity projectConclusionEntity = projectConclusionMapper.queryById(projectConclusionApprovalForm.getId());

        //修改项目结项状态
        projectConclusionMapper.updateStatus(projectConclusionApprovalForm.getId(),projectConclusionApprovalForm.getStatus(),new Timestamp(System.currentTimeMillis()));

        projectConclusionMapper.updateStatus(projectConclusionApprovalForm.getId(),projectConclusionApprovalForm.getStatus(),new Timestamp(System.currentTimeMillis()));
        Project project = projectService.selectByPrimaryKey(projectConclusionEntity.getProjectId());
        if(project==null){
            logger.error("结项的项目不存在,无法结项 projectId [{}]",projectConclusionEntity.getProjectId());
            throw  new Exception("结项的项目不存在,无法结项");
        }
        //当结项状态为ProjectArgs.WAIT_FOR_APPROVAL 2 待审批时才可审批
        if(projectConclusionEntity.getStatus() == ProjectArgs.WAIT_FOR_APPROVAL){
            logger.info("结项相关操作");
            //当项目结项通过，更新项目状态，并更新项目所属看板和工时对应的最后一个阶段的结束时间
            if(projectConclusionApprovalForm.getStatus() == ProjectArgs.ADOPT){
                //修改项目状态为已结项
                projectService.changeProjectStatus(projectConclusionEntity.getProjectId(), ProjectArgs.PROJECT_STATUS_JUNCTIONS_NUM);

                List<StageEntity> boardStageEntities = stageService.queryProjectStageByProIdAndType(projectConclusionEntity.getProjectId(),ProjectArgs.STAGE_PROJECT_PLAN_TYPE);
                List<StageEntity> workTimeEntities = stageService.queryProjectStageByProIdAndType(projectConclusionEntity.getProjectId(),ProjectArgs.STAGE_PROJECT_MAN_HOUR_TYPE);
                //若申请和审批跨周的话，会产生空数据的stage及对应的周报
                if(boardStageEntities != null && boardStageEntities.size() >0 ){
                    StageEntity stageEntity = boardStageEntities.get(0);
                    stageEntity.setEndDate(new Date());
                    stageService.updateStage(stageEntity);
                }
                if(workTimeEntities != null && workTimeEntities.size() >0 ){
                    StageEntity stageEntity = workTimeEntities.get(0);
                    stageEntity.setEndDate(new Date());
                    stageService.updateStage(stageEntity);
                }
                //保存结项前的项目状态
                project.setLastStatus(project.getStatus());
                project.setStatus(ProjectArgs.PROJECT_STATUS_JUNCTIONS_NUM);
                projectService.updateByPrimaryKey(project);
            }
            /**
             * 将该结项之前的结项申请消息结束
             */
            messageService.updateUnHandleByEventAndParam1(IConstant.PROJECT_CONCLUSION_REPLY, projectConclusionEntity.getProjectId(), new Timestamp(System.currentTimeMillis()));
            /**
             * 生成相关审批结果消息，若项目经理在线则推送消息
             */
            Message message = new Message();
            message.setReceiverId(project.getCreateUserId());

            if(projectConclusionApprovalForm.getStatus() == ProjectArgs.REPULSE){
                message.setEvent(IConstant.PROJECT_CONCLUSION_RESULT_REPULSE);
                message.setTitle(IConstant.PROJECT_CONCLUSION_RESULT_REPULSE_TITLE);
                message.setParam2(projectConclusionApprovalForm.getReason());
            }else if(projectConclusionApprovalForm.getStatus() == ProjectArgs.ADOPT){
                message.setEvent(IConstant.PROJECT_CONCLUSION_RESULT_ADOPT);
                message.setTitle(IConstant.PROJECT_CONCLUSION_RESULT_ADOPT_TITLE);
            }
            message.setContent(project.getName());
            message.setSend("系统");
            message.setParam1(project.getId());
            message.setHandle(false);
            message.setCreateTime(new Timestamp(System.currentTimeMillis()));

            messageService.addMessage(message);

            AccountEntity accountEntity = accountService.queryAccountById(project.getCreateUserId());
            /**
             * 若用户在线，则推送相关消息
             */
            AccountEntity redisAccount = (AccountEntity) redisTemplate.boundValueOps(accountEntity.getUsername()).get();
            if(redisAccount != null){
                messagingTemplate.convertAndSendToUser(redisAccount.getUsername(),"/message",message);
            }
            //推送消息给拥有审批权限的用户，刷新消
            sendMessageToHaveApprovalAuthorityAccount(projectConclusionEntity.getProjectId(), project.getName(), IConstant.REFRESH_MESSAGE_TITLE, IConstant.REFRESH_MESSAGE);
        }else if(projectConclusionEntity.getStatus() == ProjectArgs.ADOPT || projectConclusionEntity.getStatus() == ProjectArgs.REPULSE){
            logger.error("该项目已审批 status [{}]",projectConclusionEntity.getStatus());
            throw new BusinessException("该项目已审批");
        }else{
            logger.error("当前状态不可审批 status [{}]",projectConclusionEntity.getStatus());
            throw new BusinessException("当前状态不可审批");
        }
    }

    @Override
    public HSSFWorkbook export(HSSFWorkbook wb, ProjectConclusionEntity projectConclusionEntity) {
        logger.info("export projectConclusionEntity [{}]",projectConclusionEntity);
        Project project = projectService.selectByPrimaryKey(projectConclusionEntity.getProjectId());

        //设置sheet的名字
        HSSFSheet sheet = wb.createSheet(ExcelTitleToClass.EXPORT_PROJECT_CONCLUSION_SHEET);
        //设置表单第一列宽度
        sheet.setColumnWidth(0,20 * 256);

        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);

        //标题样式
        HSSFCellStyle titleStyle = ExcelImportUtil.createCellStyle(wb, (short) 12, true, true);
        //合并单元格
        this.addMergedRegion(sheet);
        //标题
        Row row0=sheet.createRow(CellNum.ZERO_ROW);
        Cell cell0 = row0.createCell(CellNum.ZERO_COLUMN);
        cell0.setCellStyle(titleStyle);
        cell0.setCellValue(ExcelTitleToClass.EXPORT_PROJECT_CONCLUSION_TITLE);

        Row row1=sheet.createRow(CellNum.FIRST_ROW);
        row1.createCell(CellNum.ZERO_COLUMN).setCellValue("项目名称");
        row1.createCell(CellNum.FIRST_COLUMN).setCellValue(project.getName());
        setExcelBorder(row1,cellStyle);

        Row row2=sheet.createRow(CellNum.SECOUND_ROW);
        row2.createCell(CellNum.ZERO_COLUMN).setCellValue("项目编号");
        row2.createCell(CellNum.FIRST_COLUMN).setCellValue(project.getSerial());
        setExcelBorder(row2,cellStyle);

        Row row3=sheet.createRow(CellNum.THIRD_ROW);
        row3.createCell(CellNum.ZERO_COLUMN).setCellValue("项目经理");
        row3.createCell(CellNum.FIRST_COLUMN).setCellValue(accountService.queryAccountById(project.getCreateUserId()).getName());
        setExcelBorder(row3,cellStyle);

        Row row4=sheet.createRow(CellNum.FOURTH_ROW);
        row4.createCell(CellNum.ZERO_COLUMN).setCellValue("项目目标");
        row4.createCell(CellNum.FIRST_COLUMN).setCellValue(projectConclusionEntity.getTarget());
        setExcelBorder(row4,cellStyle);

        Row row5=sheet.createRow(CellNum.FIFTH_COLUMN);
        row5.createCell(CellNum.ZERO_COLUMN).setCellValue("项目周期");
        row5.createCell(CellNum.FIRST_COLUMN).setCellValue(projectConclusionEntity.getCycle());
        setExcelBorder(row5,cellStyle);

        Row row6=sheet.createRow(CellNum.SIXTH_ROW);
        row6.createCell(CellNum.ZERO_COLUMN).setCellValue("身份");
        row6.createCell(CellNum.FIRST_COLUMN).setCellValue(ChooseUtil.getProjectConclusionIdentityToCH(projectConclusionEntity.getIdentity()));
        setExcelBorder(row6,cellStyle);

        Row row7=sheet.createRow(CellNum.SEVENTH_ROW);
        row7.createCell(CellNum.ZERO_COLUMN).setCellValue("参与友商");
        row7.createCell(CellNum.FIRST_COLUMN).setCellValue(project.getFriends());
        setExcelBorder(row7,cellStyle);

        Row row8=sheet.createRow(CellNum.EIGHTH_ROW);
        row8.createCell(CellNum.ZERO_COLUMN).setCellValue("方案");
        row8.createCell(CellNum.FIRST_COLUMN).setCellValue(projectConclusionEntity.getPlan());
        setExcelBorder(row8,cellStyle);

        Row row9=sheet.createRow(CellNum.NINTH_ROW);
        row9.createCell(CellNum.ZERO_COLUMN).setCellValue("阶段性结论");
        row9.createCell(CellNum.FIRST_COLUMN).setCellValue(projectConclusionEntity.getPhaseConclusion());
        setExcelBorder(row9,cellStyle);

        Row row10=sheet.createRow(CellNum.TENTH_ROW);
        row10.createCell(CellNum.FIRST_COLUMN).setCellValue("方案设计");
        row10.createCell(CellNum.SECOUND_COLUMN).setCellValue("定制化开发");
        row10.createCell(CellNum.THIRD_COLUMN).setCellValue("预交付");
        row10.createCell(CellNum.FOURTH_COLUMN).setCellValue("交付");
        row10.createCell(CellNum.FIFTH_COLUMN).setCellValue("维护");
        row10.createCell(CellNum.SIXTH_COLUMN).setCellValue("验收");
        row10.createCell(CellNum.SEVENTH_COLUMN).setCellValue("售后");
        row10.createCell(CellNum.EIGHTH_COLUMN).setCellValue("故障个数");
        row10.createCell(CellNum.NINTH_COLUMN).setCellValue("实施次数");
        row10.createCell(CellNum.TENTH_COLUMN).setCellValue("总计");
        setCellBorder(row10,cellStyle);

        Row row11=sheet.createRow(CellNum.ELEVENTH_ROW);
        row11.createCell(CellNum.ZERO_COLUMN).setCellValue("周期（单位：工作日）");
        row11.createCell(CellNum.FIRST_COLUMN).setCellValue(projectConclusionEntity.getPlanDesignCycle());
        row11.createCell(CellNum.SECOUND_COLUMN).setCellValue(projectConclusionEntity.getCustomizationDevelopmentCycle());
        row11.createCell(CellNum.THIRD_COLUMN).setCellValue(projectConclusionEntity.getPreDeliverCycle());
        row11.createCell(CellNum.FOURTH_COLUMN).setCellValue(projectConclusionEntity.getDeliverCycle());
        row11.createCell(CellNum.FIFTH_COLUMN).setCellValue(projectConclusionEntity.getMaintenanceCycle());
        row11.createCell(CellNum.SIXTH_COLUMN).setCellValue(projectConclusionEntity.getAcceptanceCycle());
        row11.createCell(CellNum.SEVENTH_COLUMN).setCellValue(projectConclusionEntity.getAfterSaleCycle());
        row11.createCell(CellNum.EIGHTH_COLUMN).setCellValue(projectConclusionEntity.getFaultNum());
        row11.createCell(CellNum.NINTH_COLUMN).setCellValue(projectConclusionEntity.getImplementNum());
        row11.createCell(CellNum.TENTH_COLUMN).setCellValue(projectConclusionEntity.getTotal());
        setCellBorder(row11,cellStyle);

        Row row12=sheet.createRow(CellNum.TWELFTH_ROW);
        row12.createCell(CellNum.ZERO_COLUMN).setCellValue("说明");
        row12.createCell(CellNum.FIRST_COLUMN).setCellValue(projectConclusionEntity.getPlanDesignDescribe());
        row12.createCell(CellNum.SECOUND_COLUMN).setCellValue(projectConclusionEntity.getCustomizationDevelopmentDescribe());
        row12.createCell(CellNum.THIRD_COLUMN).setCellValue(projectConclusionEntity.getPreDeliverDescribe());
        row12.createCell(CellNum.FOURTH_COLUMN).setCellValue(projectConclusionEntity.getDeliverDescribe());
        row12.createCell(CellNum.FIFTH_COLUMN).setCellValue(projectConclusionEntity.getMaintenanceDescribe());
        row12.createCell(CellNum.SIXTH_COLUMN).setCellValue(projectConclusionEntity.getAcceptanceDescribe());
        row12.createCell(CellNum.SEVENTH_COLUMN).setCellValue(projectConclusionEntity.getAfterSaleDescribe());
        row12.createCell(CellNum.EIGHTH_COLUMN).setCellValue(projectConclusionEntity.getFaultNumDescribe());
        row12.createCell(CellNum.NINTH_COLUMN).setCellValue(projectConclusionEntity.getImplementDescribe());
        row12.createCell(CellNum.TENTH_COLUMN).setCellValue(projectConclusionEntity.getTotalDescribe());
        setCellBorder(row12,cellStyle);

        //标题
        Row row13=sheet.createRow(CellNum.THIRTEENTH_ROW);
        Cell cell13 = row13.createCell(CellNum.ZERO_COLUMN);
        cell13.setCellStyle(titleStyle);
        cell13.setCellValue(ExcelTitleToClass.EXPORT_PROJECT_CONCLUSION_TITLE_PROCESS);

        Row row14=sheet.createRow(CellNum.FOURTEENTH_ROW);
        row14.createCell(CellNum.ZERO_COLUMN).setCellValue("主要动作及事件");
        row14.createCell(CellNum.FIRST_COLUMN).setCellValue(projectConclusionEntity.getActionEvent());
        setExcelBorder(row14,cellStyle);

        //标题
        Row row15=sheet.createRow(CellNum.FIFTEENTH_ROW);
        Cell cell15 = row15.createCell(CellNum.ZERO_COLUMN);
        cell15.setCellStyle(titleStyle);
        cell15.setCellValue(ExcelTitleToClass.EXPORT_PROJECT_CONCLUSION_TITLE_ANALYSIS_SUMMARY);


        Row row16=sheet.createRow(CellNum.SIXTEENTH_ROW);
        row16.createCell(CellNum.ZERO_COLUMN).setCellValue("我司优劣势");
        row16.createCell(CellNum.FIRST_COLUMN).setCellValue(projectConclusionEntity.getOurStrengthsWeaknesses());
        setExcelBorder(row16,cellStyle);

        Row row17=sheet.createRow(CellNum.SEVENTEENTH_ROW);
        row17.createCell(CellNum.ZERO_COLUMN).setCellValue("友商优劣势");
        row17.createCell(CellNum.FIRST_COLUMN).setCellValue(projectConclusionEntity.getFriendsStrengthsWeaknesses());
        setExcelBorder(row17,cellStyle);

        Row row18=sheet.createRow(CellNum.EIGHTEENTH_ROW);
        row18.createCell(CellNum.ZERO_COLUMN).setCellValue("遗留问题");
        row18.createCell(CellNum.FIRST_COLUMN).setCellValue(projectConclusionEntity.getLegacy());
        setExcelBorder(row18,cellStyle);

        Row row19=sheet.createRow(CellNum.NINTEENTH_ROW);
        row19.createCell(CellNum.ZERO_COLUMN).setCellValue("经验总结");
        row19.createCell(CellNum.FIRST_COLUMN).setCellValue(projectConclusionEntity.getExperienceSummary());
        setExcelBorder(row19,cellStyle);

        Row row20=sheet.createRow(CellNum.TWENTIETH_ROW);
        row20.createCell(CellNum.ZERO_COLUMN).setCellValue("改进建议");
        row20.createCell(CellNum.FIRST_COLUMN).setCellValue(projectConclusionEntity.getImprovement());
        setExcelBorder(row20,cellStyle);

        return wb;
    }

    @Override
    public List<ProjectConclusionEntity> selectAll() {
        logger.info("selectAll ProjectConclusionEntity");
        return projectConclusionMapper.selectAll();
    }

    /**
     * 合并单元格
     */
    private void addMergedRegion(HSSFSheet sheet) {
        //项目情况title
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,10));
        //项目信息
        sheet.addMergedRegion(new CellRangeAddress(1,1,1,10));
        sheet.addMergedRegion(new CellRangeAddress(2,2,1,10));
        sheet.addMergedRegion(new CellRangeAddress(3,3,1,10));
        sheet.addMergedRegion(new CellRangeAddress(4,4,1,10));
        sheet.addMergedRegion(new CellRangeAddress(5,5,1,10));
        sheet.addMergedRegion(new CellRangeAddress(6,6,1,10));
        sheet.addMergedRegion(new CellRangeAddress(7,7,1,10));
        sheet.addMergedRegion(new CellRangeAddress(8,8,1,10));
        sheet.addMergedRegion(new CellRangeAddress(9,9,1,10));
        //过程title
        sheet.addMergedRegion(new CellRangeAddress(13,13,0,10));
        //过程信息
        sheet.addMergedRegion(new CellRangeAddress(14,14,1,10));
        //分析总结title
        sheet.addMergedRegion(new CellRangeAddress(15,15,0,10));
        //分析总结信息
        sheet.addMergedRegion(new CellRangeAddress(16,16,1,10));
        sheet.addMergedRegion(new CellRangeAddress(17,17,1,10));
        sheet.addMergedRegion(new CellRangeAddress(18,18,1,10));
        sheet.addMergedRegion(new CellRangeAddress(19,19,1,10));
        sheet.addMergedRegion(new CellRangeAddress(20,20,1,10));
    }

    /**
     * 设置包含合并单元的行的边框
     */
    private void setExcelBorder(Row row, HSSFCellStyle cellStyle){
        row.createCell(2);
        row.createCell(3);
        row.createCell(4);
        row.createCell(5);
        row.createCell(6);
        row.createCell(7);
        row.createCell(8);
        row.createCell(9);
        row.createCell(10);
        for (Cell cell : row) {
            cell.setCellStyle(cellStyle);
        }
    }

    /**
     * 设置无合并单元格的单元格边框
     */
    private void setCellBorder(Row row, HSSFCellStyle cellStyle){
        for (Cell cell : row) {
            cell.setCellStyle(cellStyle);
        }
    }

    private void sendMessageToHaveApprovalAuthorityAccount(int param1, String projectName, String title, int event){
        /**
         * 调用消息推送接口推送结项消息给审批人
         * 审批人为拥有 IConstant.PROJECT_CONCLUSION_APPROVAL_RESOURCE_CODE 权限的人
         */
        List<AccountEntity> accountEntities = accountService.queryAccountByResourceCode(IConstant.PROJECT_CONCLUSION_APPROVAL_RESOURCE_CODE);
        logger.info("需要推送的用户 [{}]",accountEntities);
        Message message = new Message();
        message.setHandle(false);
        message.setParam1(param1);
        message.setSend("系统");
        message.setTitle(title);
        message.setContent(projectName);
        message.setEvent(event);
        message.setCreateTime(new Timestamp(System.currentTimeMillis()));

        for (AccountEntity accountEntity : accountEntities) {
            message.setReceiverId(accountEntity.getId());
            messageService.addMessage(message);
            /**
             * 若用户在线，则推送相关消息
             */
            AccountEntity redisAccount = (AccountEntity) redisTemplate.boundValueOps(accountEntity.getUsername()).get();
            if(redisAccount != null){
                messagingTemplate.convertAndSendToUser(redisAccount.getUsername(),"/message",message);
            }
        }
    }
}
