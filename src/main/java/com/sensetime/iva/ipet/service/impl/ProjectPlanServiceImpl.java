package com.sensetime.iva.ipet.service.impl;

import com.sensetime.iva.ipet.common.CellNum;
import com.sensetime.iva.ipet.common.ProjectArgs;
import com.sensetime.iva.ipet.entity.Project;
import com.sensetime.iva.ipet.entity.ProjectPlan;
import com.sensetime.iva.ipet.entity.WeeklyBoard;
import com.sensetime.iva.ipet.mapper.ProjectPlanMapper;
import com.sensetime.iva.ipet.service.*;
import com.sensetime.iva.ipet.util.ChooseUtil;
import com.sensetime.iva.ipet.util.ExcelImportUtil;
import com.sensetime.iva.ipet.web.exception.BusinessException;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author  gongchao
 */
@Component
public class ProjectPlanServiceImpl  implements ProjectPlanService {
    private static final Logger logger = LoggerFactory.getLogger(ProjectPlanServiceImpl.class);

    @Autowired
    ProjectPlanMapper projectPlanMapper;
    @Autowired
    ProjectService projectService;
    @Autowired
    WeeklyBoardService weeklyBoardService;
    @Autowired
    ProjectRelatedPersonService projectRelatedPersonService;
    @Autowired
    CostStatisticsService costStatisticsService;
    @Autowired
    RiskProblemService riskProblemService;
    @Override
    public int deleteByPrimaryKey(Integer id) {
        logger.info("deleteByPrimaryKey id="+id);
        return projectPlanMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(ProjectPlan projectPlan) {
        logger.info("insert projectPlan="+projectPlan);
        return projectPlanMapper.insert(projectPlan);
    }

    @Override
    public ProjectPlan selectByPrimaryKey(Integer id) {
        logger.info("selectByPrimaryKey id="+id);
        return projectPlanMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<ProjectPlan> selectAll() {
        logger.info("selectAll ProjectPlan");
        return projectPlanMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(ProjectPlan projectPlan) {
        logger.info("updateByPrimaryKey projectPlan"+projectPlan);
        return projectPlanMapper.updateByPrimaryKey(projectPlan);
    }

    @Override
    public ProjectPlan selectByProjectIdAndStartDate(Integer projectId, String startDate) {
        logger.info("selectByProjectIdAndStartDate projectId"+projectId+"startDate"+startDate);
        return projectPlanMapper.selectByProjectIdAndStartDate(projectId,startDate);
    }

    @Override
    public List<ProjectPlan> selectByProjectId(Integer projectId) {
        logger.info("selectByProjectId projectId"+projectId);
        return projectPlanMapper.selectByProjectId(projectId);
    }
    @Override
    public void exportProjectPlan(HSSFWorkbook wb, Project project) throws Exception {
        logger.info("export projectPlan by projectId"+project.getId());
        logger.info("export project");
        projectService.export(wb, project);
        logger.info("export projectPlan");
        this.exportInfo(wb,project.getId());
        logger.info("export costStatistics");
        costStatisticsService.exportCostStatistics(wb,project.getId());
        logger.info("export riskProblem");
        riskProblemService.exportRiskProblem(wb,project.getId());
        logger.info("export projectRelatedPerson");
        projectRelatedPersonService.exportProjectRelatedPerson(wb,project.getId());
    }
    @Override
    public void exportInfo(HSSFWorkbook wb, Integer projectId) throws Exception {
        List<ProjectPlan> projectPlans = this.selectByProjectId(projectId);
        logger.info("get projectPlans [{}]",projectPlans);
        HSSFSheet sheet = wb.getSheetAt(0);
        //获取本周开始结束时间
        HSSFCellStyle titleStyle = ExcelImportUtil.createCellStyle(wb, (short) 12, true, true);
        //设置值项目计划
        if(projectPlans!=null&&projectPlans.size()>0){
            //获取本周项目计划信息
            ProjectPlan projectPlan = projectPlans.get(0);
            this.setValues(wb,titleStyle,projectPlan);
        }else{
            this.setValues(wb,titleStyle,null);
        }
        //创建项目计划项目详细信息
        this.getWbTitle(sheet,titleStyle);
        List<WeeklyBoard> weeklyBoards = weeklyBoardService.selectByProjectId(projectId);
        logger.info("get weeklyBoards [{}]",weeklyBoards);
        int lastRowNum = sheet.getLastRowNum();
        //设置项目计划详细信息的菜单
        this.getWeekBoardTitle(sheet,lastRowNum);
            if(weeklyBoards!=null&&weeklyBoards.size() >0){
                for (WeeklyBoard board: weeklyBoards) {
                     this.setValues(sheet, board, lastRowNum);
                }
            }
    }
    @Override
    public void setValues(HSSFWorkbook wb,HSSFCellStyle titleStyle,ProjectPlan projectPlan){
        //得到添加数据的最后一行
        HSSFSheet sheet = wb.getSheetAt(0);
        int rowNum = sheet.getLastRowNum()+1;
        Row row = sheet.createRow(rowNum + 1);
        Cell c0 = row.createCell(CellNum.ZERO_COLUMN);
        c0.setCellStyle(titleStyle);
        c0.setCellValue("项目每周看板");

        Row row1 = sheet.createRow(rowNum + 2);
        Cell c10 = row1.createCell(CellNum.ZERO_COLUMN);
        c10.setCellValue("整体进展");

        Row row2 = sheet.createRow(rowNum + 3);
        Cell c20 = row2.createCell(CellNum.ZERO_COLUMN);
        c20.setCellValue("风险&求助");

        Row row3 = sheet.createRow(rowNum + 4);
        Cell c30 = row3.createCell(CellNum.ZERO_COLUMN);
        c30.setCellValue("项目状态");

        Row row4 = sheet.createRow(rowNum + 5);
        Cell c40 = row4.createCell(CellNum.ZERO_COLUMN);
        c40.setCellValue("汇报周期");

        Row row5 = sheet.createRow(rowNum + 6);
        Cell c50 = row5.createCell(CellNum.ZERO_COLUMN);
        c50.setCellValue("本周进展");

        Row row6 = sheet.createRow(rowNum + 7);
        Cell c60 = row6.createCell(CellNum.ZERO_COLUMN);
        c60.setCellValue("下周计划");
       if(projectPlan!=null){
           Cell c11 = row1.createCell(CellNum.FIRST_COLUMN);
           c11.setCellValue(projectPlan.getProjectProgress()!=null?projectPlan.getProjectProgress():"");
           Cell c21 = row2.createCell(CellNum.FIRST_COLUMN);
           c21.setCellValue(projectPlan.getRiskAndHelp()!=null?projectPlan.getRiskAndHelp():"");
           Cell c31 = row3.createCell(CellNum.FIRST_COLUMN);
           c31.setCellValue(ChooseUtil.getProjectStatusToCH(projectPlan.getProjectStatus()!=null?projectPlan.getProjectStatus():-1));
           Cell c41 = row4.createCell(CellNum.FIRST_COLUMN);
           c41.setCellValue(projectPlan.getReportCycle()!=null?projectPlan.getReportCycle():"");
           Cell c51 = row5.createCell(CellNum.FIRST_COLUMN);
           c51.setCellValue(projectPlan.getWeekProgress()!=null?projectPlan.getWeekProgress():"");
           Cell c61 = row6.createCell(CellNum.FIRST_COLUMN);
           c61.setCellValue(projectPlan.getNextWeekPlan()!=null?projectPlan.getNextWeekPlan():"");
       }

    }
    @Override
    public void getWbTitle(HSSFSheet sheet, HSSFCellStyle titleStyle) throws Exception{
        int rowNum = sheet.getLastRowNum();
        sheet.setColumnWidth(CellNum.ZERO_COLUMN,20 * 256);
        sheet.setColumnWidth(CellNum.FIRST_COLUMN,20 * 256);
        sheet.setColumnWidth(CellNum.SECOUND_COLUMN,20 * 256);
        sheet.setColumnWidth(CellNum.THIRD_COLUMN,20 * 256);
        sheet.setColumnWidth(CellNum.FOURTH_COLUMN,20 * 256);
        sheet.setColumnWidth(CellNum.FIFTH_COLUMN,20 * 256);
        sheet.setColumnWidth(CellNum.SIXTH_COLUMN,20 * 256);
        sheet.setColumnWidth(CellNum.SEVENTH_COLUMN,20 * 256);
        sheet.setColumnWidth(CellNum.EIGHTH_COLUMN,20 * 256);
        sheet.setColumnWidth(CellNum.NINTH_COLUMN,20 * 256);
        sheet.setColumnWidth(CellNum.TENTH_COLUMN,20 * 256);
        sheet.setColumnWidth(CellNum.ELEVENTH,20 * 256);
        logger.debug("设置每周看板每个标题单元格宽度");
        Row row = sheet.createRow(rowNum);
        Cell cell0 = row.createCell(CellNum.ZERO_COLUMN);
        cell0.setCellStyle(titleStyle);
        cell0.setCellValue("阶段");
        Cell cell1 = row.createCell(CellNum.FIRST_COLUMN);
        cell1.setCellStyle(titleStyle);
        cell1.setCellValue("任务项");
        Cell cell2 = row.createCell(CellNum.SECOUND_COLUMN);
        cell2.setCellStyle(titleStyle);
        cell2.setCellValue("具体工作");
        Cell cell3 = row.createCell(CellNum.THIRD_COLUMN);
        cell3.setCellStyle(titleStyle);
        cell3.setCellValue("输出物");
        Cell cell4 = row.createCell(CellNum.FOURTH_COLUMN);
        cell4.setCellStyle(titleStyle);
        cell4.setCellValue("计划开始时间");
        Cell cell5 = row.createCell(CellNum.FIFTH_COLUMN);
        cell5.setCellStyle(titleStyle);
        cell5.setCellValue("实际开始时间");
        Cell cell6 = row.createCell(CellNum.SIXTH_COLUMN);
        cell6.setCellStyle(titleStyle);
        cell6.setCellValue("计划完成时间");
        Cell cell7 = row.createCell(CellNum.SEVENTH_COLUMN);
        cell7.setCellStyle(titleStyle);
        cell7.setCellValue("实际完成时间");
        Cell cell8 = row.createCell(CellNum.EIGHTH_COLUMN);
        cell8.setCellStyle(titleStyle);
        cell8.setCellValue("完成率%");
        Cell cell9 = row.createCell(CellNum.NINTH_COLUMN);
        cell9.setCellStyle(titleStyle);
        cell9.setCellValue("工作量（人名-人天）");
        Cell cel20 = row.createCell(CellNum.TENTH_COLUMN);
        cel20.setCellStyle(titleStyle);
        cel20.setCellValue("责任人");
        Cell cel21 = row.createCell(CellNum.ELEVENTH);
        cel21.setCellStyle(titleStyle);
        cel21.setCellValue("风险、问题及备注");
        logger.debug("设置每周看板每个单元格的标题");
    }
    @Override
    public void setValues(HSSFSheet sheet, WeeklyBoard board, int rowNum) throws Exception{
        String line="";
        //获取阶段和任务项判断将数据放在哪一行
        if(board.getStage()==null||board.getTask()==null){
            logger.warn("看板中项目阶段或目标任务为空 board {}",board);
        }else{
            line = board.getStage().toString().concat("-").concat(board.getTask().toString());
        }
        switch (line){
            case ProjectArgs.PROJECT_APPROVAL_APPLY:
                HSSFRow row1 = sheet.getRow(rowNum+CellNum.FIRST_COLUMN);
                this.setValue(row1,board);
                break;
            case  ProjectArgs.PROJECT_SHENPING:
                HSSFRow row2 = sheet.getRow(rowNum+CellNum.SECOUND_COLUMN);
                this.setValue(row2,board);
                break;
            case  ProjectArgs.DESIGN_SEARCH:
                HSSFRow row3 = sheet.getRow(rowNum+CellNum.THIRD_COLUMN);
                this.setValue(row3,board);
                break;
            case  ProjectArgs.DESIGN_SYSTEM:
                HSSFRow row4 = sheet.getRow(rowNum+CellNum.FOURTH_COLUMN);
                this.setValue(row4,board);
                break;
            case  ProjectArgs.DESIGN_DELOPY:
                HSSFRow row5 = sheet.getRow(rowNum+CellNum.FIFTH_COLUMN);
                this.setValue(row5,board);
                break;
            case  ProjectArgs.DIY_PRODUCT:
                HSSFRow row6 = sheet.getRow(rowNum+CellNum.SIXTH_ROW);
                this.setValue(row6,board);
                break;
            case  ProjectArgs.DIY_DELIVERY:
                HSSFRow row7 = sheet.getRow(rowNum+CellNum.SEVENTH_ROW);
                this.setValue(row7,board);
                break;
            case  ProjectArgs.TEST_PRODUCT_TEST:
                HSSFRow row8 = sheet.getRow(rowNum+CellNum.EIGHTH_ROW);
                this.setValue(row8,board);
                break;
            case  ProjectArgs.PRE_DELIVER_EXPLORATION:
                HSSFRow row9 = sheet.getRow(rowNum+CellNum.NINTH_ROW);
                this.setValue(row9,board);
                break;
            case  ProjectArgs.PRE_DELIVER_EDEVICE:
                HSSFRow row10 = sheet.getRow(rowNum+CellNum.TENTH_ROW);
                this.setValue(row10,board);
                break;
            case  ProjectArgs.PRE_DELIVER_PREPARE:
                HSSFRow row11 = sheet.getRow(rowNum+CellNum.ELEVENTH_ROW);
                this.setValue(row11,board);
                break;
            case  ProjectArgs.PRE_DELIVER_CHECK:
                HSSFRow row12 = sheet.getRow(rowNum+CellNum.TWELFTH_ROW);
                this.setValue(row12,board);
                break;
            case  ProjectArgs.PRE_DELIVER_DELIVER:
                HSSFRow row13 = sheet.getRow(rowNum+CellNum.THIRTEENTH_ROW);
                this.setValue(row13,board);
                break;
            case  ProjectArgs.DELIVER_LINE:
                HSSFRow row14 = sheet.getRow(rowNum+CellNum.FOURTEENTH_ROW);
                this.setValue(row14,board);
                break;
            case  ProjectArgs.DELIVER_DELOPY:
                HSSFRow row15 = sheet.getRow(rowNum+CellNum.FIFTEENTH_ROW);
                this.setValue(row15,board);
                break;
            case  ProjectArgs.DELIVER_DEBUGER:
                HSSFRow row16 = sheet.getRow(rowNum+CellNum.SIXTEENTH_ROW);
                this.setValue(row16,board);
                break;
            case  ProjectArgs.DELIVER_TRANS:
                HSSFRow row17 = sheet.getRow(rowNum+CellNum.SEVENTEENTH_ROW);
                this.setValue(row17,board);
                break;
            case  ProjectArgs.DELIVER_PROMOTE:
                HSSFRow row18 = sheet.getRow(rowNum+CellNum.EIGHTEENTH_ROW);
                this.setValue(row18,board);
                break;
            case  ProjectArgs.DELIVER_ROLLBACK:
                HSSFRow row19 = sheet.getRow(rowNum+CellNum.NINTEENTH);
                this.setValue(row19,board);
                break;
            case  ProjectArgs.DELIVER_DILATATION:
                HSSFRow row20 = sheet.getRow(rowNum+CellNum.TWENTIETH);
                this.setValue(row20,board);
                break;
            case  ProjectArgs.DELIVER_ABOLISH:
                HSSFRow row21 = sheet.getRow(rowNum+CellNum.TWENTIETH1);
                this.setValue(row21,board);
                break;
            case  ProjectArgs.MAINTAIN_TRAIN:
                HSSFRow row22 = sheet.getRow(rowNum+CellNum.TWENTIETH2);
                this.setValue(row22,board);
                break;
            case  ProjectArgs.MAINTAIN_STATION:
                HSSFRow row23 = sheet.getRow(rowNum+CellNum.TWENTIETH3);
                this.setValue(row23,board);
                break;
            case  ProjectArgs.MAINTAIN_SUPPORT:
                HSSFRow row24 = sheet.getRow(rowNum+CellNum.TWENTIETH4);
                this.setValue(row24,board);
                break;
            case  ProjectArgs.MAINTAIN_REPAIRE:
                HSSFRow row25 = sheet.getRow(rowNum+CellNum.TWENTIETH5);
                this.setValue(row25,board);
                break;
            case  ProjectArgs.ACCEPT_ACCEPT:
                HSSFRow row26 = sheet.getRow(rowNum+CellNum.TWENTIETH6);
                this.setValue(row26,board);
                break;
            case  ProjectArgs.AFTER_SALE_INSPECTION:
                HSSFRow row27 = sheet.getRow(rowNum+CellNum.TWENTIETH7);
                this.setValue(row27,board);
                break;
            case  ProjectArgs.AFTER_SALE_GUARANTEE:
                HSSFRow row28 = sheet.getRow(rowNum+CellNum.TWENTIETH8);
                this.setValue(row28,board);
                break;
            default:
                break;
        }
    }

    @Override
    public void getWeekBoardTitle(HSSFSheet sheet, int rowNum) throws Exception {
        try{
            HSSFRow row1 = sheet.createRow(rowNum+CellNum.FIRST_COLUMN);
            row1.createCell(CellNum.ZERO_COLUMN).setCellValue(ProjectArgs.APPROVAL);
            row1.createCell(CellNum.FIRST_COLUMN).setCellValue(ProjectArgs.APPLY);
            HSSFRow row2 = sheet.createRow(rowNum+CellNum.SECOUND_COLUMN);
            row2.createCell(CellNum.FIRST_COLUMN).setCellValue(ProjectArgs.SHENPING);
            sheet.addMergedRegion(new CellRangeAddress(rowNum+CellNum.FIRST_COLUMN,rowNum+CellNum.FIRST_COLUMN+1,0,0));

            HSSFRow row3 = sheet.createRow(rowNum+CellNum.THIRD_COLUMN);
            row3.createCell(CellNum.ZERO_COLUMN).setCellValue(ProjectArgs.DESIGN);
            row3.createCell(CellNum.FIRST_COLUMN).setCellValue(ProjectArgs.SEARCH);
            HSSFRow row4 = sheet.createRow(rowNum+CellNum.FOURTH_COLUMN);
            row4.createCell(CellNum.FIRST_COLUMN).setCellValue(ProjectArgs.SYSTEM);
            HSSFRow row5 = sheet.createRow(rowNum+CellNum.FIFTH_COLUMN);
            row5.createCell(CellNum.FIRST_COLUMN).setCellValue(ProjectArgs.DELOPY);
            sheet.addMergedRegion(new CellRangeAddress(rowNum+CellNum.THIRD_COLUMN,rowNum+CellNum.THIRD_COLUMN+2,0,0));

            HSSFRow row6 = sheet.createRow(rowNum+CellNum.SIXTH_ROW);
            row6.createCell(CellNum.ZERO_COLUMN).setCellValue(ProjectArgs.DIY);
            row6.createCell(CellNum.FIRST_COLUMN).setCellValue(ProjectArgs.DIY_DEV);
            HSSFRow row7 = sheet.createRow(rowNum+CellNum.SEVENTH_ROW);
            row7.createCell(CellNum.FIRST_COLUMN).setCellValue(ProjectArgs.PRODUCT);
            sheet.addMergedRegion(new CellRangeAddress(rowNum+CellNum.SIXTH_ROW,rowNum+CellNum.SIXTH_ROW+1,0,0));


            HSSFRow row8 = sheet.createRow(rowNum+CellNum.EIGHTH_ROW);
            row8.createCell(CellNum.ZERO_COLUMN).setCellValue(ProjectArgs.TEST);
            row8.createCell(CellNum.FIRST_COLUMN).setCellValue(ProjectArgs.PRODUCT_TEST);

            HSSFRow row9 = sheet.createRow(rowNum+CellNum.NINTH_ROW);
            row9.createCell(CellNum.ZERO_COLUMN).setCellValue(ProjectArgs.PRE_DELIVER);
            row9.createCell(CellNum.FIRST_COLUMN).setCellValue(ProjectArgs.EXPLORATION);
            HSSFRow row10 = sheet.createRow(rowNum+CellNum.TENTH_ROW);
            row10.createCell(CellNum.FIRST_COLUMN).setCellValue(ProjectArgs.DEVICE);
            HSSFRow row11 = sheet.createRow(rowNum+CellNum.ELEVENTH_ROW);
            row11.createCell(CellNum.FIRST_COLUMN).setCellValue(ProjectArgs.PREPARE);
            HSSFRow row12 = sheet.createRow(rowNum+CellNum.TWELFTH_ROW);
            row12.createCell(CellNum.FIRST_COLUMN).setCellValue(ProjectArgs.CHECK);
            HSSFRow row13 = sheet.createRow(rowNum+CellNum.THIRTEENTH_ROW);
            row13.createCell(CellNum.FIRST_COLUMN).setCellValue(ProjectArgs.SEND);
            sheet.addMergedRegion(new CellRangeAddress(rowNum+CellNum.NINTH_ROW,rowNum+CellNum.NINTH_ROW+4,0,0));

            HSSFRow row14 = sheet.createRow(rowNum+CellNum.FOURTEENTH_ROW);
            row14.createCell(CellNum.ZERO_COLUMN).setCellValue(ProjectArgs.DELIVER);
            row14.createCell(CellNum.FIRST_COLUMN).setCellValue(ProjectArgs.LINE);
            HSSFRow row15 = sheet.createRow(rowNum+CellNum.FIFTEENTH_ROW);
            row15.createCell(CellNum.FIRST_COLUMN).setCellValue(ProjectArgs.DELI_DELOPY);
            HSSFRow row16 = sheet.createRow(rowNum+CellNum.SIXTEENTH_ROW);
            row16.createCell(CellNum.FIRST_COLUMN).setCellValue(ProjectArgs.DEBUGER);
            HSSFRow row17 = sheet.createRow(rowNum+CellNum.SEVENTEENTH_ROW);
            row17.createCell(CellNum.FIRST_COLUMN).setCellValue(ProjectArgs.TRANS);
            HSSFRow row18 = sheet.createRow(rowNum+CellNum.EIGHTEENTH_ROW);
            row18.createCell(CellNum.FIRST_COLUMN).setCellValue(ProjectArgs.PROMOTE);
            HSSFRow row19 = sheet.createRow(rowNum+CellNum.NINTEENTH);
            row19.createCell(CellNum.FIRST_COLUMN).setCellValue(ProjectArgs.ROLLBACK);
            HSSFRow row20 = sheet.createRow(rowNum+CellNum.TWENTIETH);
            row20.createCell(CellNum.FIRST_COLUMN).setCellValue(ProjectArgs.DILATATION);
            HSSFRow row21 = sheet.createRow(rowNum+CellNum.TWENTIETH1);
            row21.createCell(CellNum.FIRST_COLUMN).setCellValue(ProjectArgs.ABOLISH);

            sheet.addMergedRegion(new CellRangeAddress(rowNum+CellNum.FOURTEENTH_ROW,rowNum+CellNum.FOURTEENTH_ROW+7,0,0));

            HSSFRow row22 = sheet.createRow(rowNum+CellNum.TWENTIETH2);
            row22.createCell(CellNum.ZERO_COLUMN).setCellValue(ProjectArgs.MAINTAIN);
            row22.createCell(CellNum.FIRST_COLUMN).setCellValue(ProjectArgs.TRAIN);
            HSSFRow row23 = sheet.createRow(rowNum+CellNum.TWENTIETH3);
            row23.createCell(CellNum.FIRST_COLUMN).setCellValue(ProjectArgs.STATION);
            HSSFRow row24 = sheet.createRow(rowNum+CellNum.TWENTIETH4);
            row24.createCell(CellNum.FIRST_COLUMN).setCellValue(ProjectArgs.SUPPORT);
            HSSFRow row25 = sheet.createRow(rowNum+CellNum.TWENTIETH5);
            row25.createCell(CellNum.FIRST_COLUMN).setCellValue(ProjectArgs.REPAIR);
            sheet.addMergedRegion(new CellRangeAddress(rowNum+CellNum.TWENTIETH2,rowNum+CellNum.TWENTIETH2+3,0,0));

            HSSFRow row26 = sheet.createRow(rowNum+CellNum.TWENTIETH6);
            row26.createCell(CellNum.ZERO_COLUMN).setCellValue(ProjectArgs.PREFIX_ACCEPT);
            row26.createCell(CellNum.FIRST_COLUMN).setCellValue(ProjectArgs.SUFIX_ACCEPT);

            HSSFRow row27 = sheet.createRow(rowNum+CellNum.TWENTIETH7);
            row27.createCell(CellNum.ZERO_COLUMN).setCellValue(ProjectArgs.AFTER_SALE);
            row27.createCell(CellNum.FIRST_COLUMN).setCellValue(ProjectArgs.INSPECTION);
            HSSFRow row28 = sheet.createRow(rowNum+CellNum.TWENTIETH8);
            row28.createCell(CellNum.FIRST_COLUMN).setCellValue(ProjectArgs.GUARANTEE);
            sheet.addMergedRegion(new CellRangeAddress(rowNum+CellNum.TWENTIETH7,rowNum+CellNum.TWENTIETH7+1,0,0));
            logger.debug("设置明细信息标题");
        }catch (Exception e){
            logger.error("set weekly board error {}",e.getMessage());
            throw new BusinessException("设置项目计划明细信息标题时错误");
        }

    }

    public void setValue(Row row, WeeklyBoard board) throws Exception{
        try{
            row.createCell(CellNum.SECOUND_COLUMN).setCellValue(board.getWorkDesc()!=null?board.getWorkDesc():"");
            row.createCell(CellNum.THIRD_COLUMN).setCellValue(board.getOutput()!=null?board.getOutput():"");
            row.createCell(CellNum.FOURTH_COLUMN).setCellValue(board.getPlanStartDate()!=null?board.getPlanStartDate():"");
            row.createCell(CellNum.FIFTH_COLUMN).setCellValue(board.getRealStartDate()!=null?board.getRealStartDate():"");
            row.createCell(CellNum.SIXTH_COLUMN).setCellValue(board.getPlanFinishDate()!=null?board.getPlanFinishDate():"");
            row.createCell(CellNum.SEVENTH_COLUMN).setCellValue(board.getRealFinishDate()!=null?board.getRealFinishDate():"");
            row.createCell(CellNum.EIGHTH_COLUMN).setCellValue(board.getCompletionRate()!=null?board.getCompletionRate():0);
            row.createCell(CellNum.NINTH_COLUMN).setCellValue(board.getWorkload()!=null?board.getWorkload():"");
            row.createCell(CellNum.TENTH_COLUMN).setCellValue(board.getPersonLiable()!=null?board.getPersonLiable():"");
            row.createCell(CellNum.ELEVENTH).setCellValue(board.getRemark()!=null?board.getRemark():"");
            logger.debug("设置明细信息的值 WeeklyBoard {}",board);
        }catch (Exception e){
            logger.error(" set weekly board value error {}",e.getMessage());
            throw new BusinessException("置项目计划明细信息的值失败");
        }

    }
}
