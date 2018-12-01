package com.sensetime.iva.ipet.service.impl;

import com.sensetime.iva.ipet.common.CellNum;
import com.sensetime.iva.ipet.common.ExcelTitleToClass;
import com.sensetime.iva.ipet.common.ProjectArgs;
import com.sensetime.iva.ipet.entity.ApplyList;
import com.sensetime.iva.ipet.mapper.ApplyListMapper;
import com.sensetime.iva.ipet.service.*;
import com.sensetime.iva.ipet.util.ExcelImportUtil;
import com.sensetime.iva.ipet.web.exception.BusinessException;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
public class ApplyListServiceImpl implements ApplyListService {
    private static final Logger logger = LoggerFactory.getLogger(ApplyListServiceImpl.class);

    @Autowired
    ApplyListMapper applyListMapper;
    @Autowired
    DeliverListService deliverListService;
    @Autowired
    PhysicalMapService physicalMapService;
    @Autowired
    SurveyListService surveyListService;
    @Autowired
    TopologicalGraphService topologicalGraphService;
    @Autowired
    WareListService wareListService;
    @Override
    public int deleteByPrimaryKey(Integer id) {
        logger.info("deleteByPrimaryKey id="+id);
        return applyListMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(ApplyList apply) {
        logger.info("insert apply="+apply);
        return applyListMapper.insert(apply);
    }

    @Override
    public ApplyList selectByPrimaryKey(Integer id) {
        logger.info("selectByPrimaryKey id="+id);
        return applyListMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<ApplyList> selectAll() {
        logger.info("selectAll ApplyList");
        return applyListMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(ApplyList apply) {
        logger.info("updateByPrimaryKey apply="+apply);
        return applyListMapper.updateByPrimaryKey(apply);
    }

    @Override
    public List<ApplyList> selectByProjectId(Integer projectId) {
        logger.info("selectByProjectId projectId="+projectId);
        return applyListMapper.selectByProjectId(projectId);
    }

    @Override
    public void export(HSSFWorkbook wb, Integer projectId) throws Exception{
        logger.debug("exportApply start by projectId [{}]",projectId);
        this.exportApply(wb,projectId);
        logger.debug("exportSurvey start by projectId [{}]",projectId);
        surveyListService.exportSurvey(wb,projectId);
        logger.debug("exportWare start by projectId [{}]",projectId);
        wareListService.exportWare(wb,projectId);
        logger.debug("exportDeliver start by projectId [{}]",projectId);
        deliverListService.exportDeliver(wb,projectId);
        logger.debug("exportTopological start by projectId [{}]",projectId);
        topologicalGraphService.exportTopological(wb,projectId);
        logger.debug("exportPhysical start by projectId [{}]",projectId);
        physicalMapService.exportPhysical(wb,projectId);
    }

    @Override
    public void exportApply(HSSFWorkbook wb, Integer projectId) throws Exception{
        logger.info("获取实施清单信息");
        List<ApplyList> applyLists = this.selectByProjectId(projectId);
        logger.info("get applyLists by projectId [{}] result applyLists is",projectId,applyLists);
        //设置sheet的名字
        logger.info("创建实施清单sheet");
        HSSFSheet sheet = wb.createSheet(ExcelTitleToClass.EXPORT_PROJECT_CHECK_APPLY);
        logger.info("create sheet name is [{}]",ExcelTitleToClass.EXPORT_PROJECT_CHECK_APPLY);
        //标题样式
        HSSFCellStyle titleStyle = ExcelImportUtil.createCellStyle(wb, (short) 12, true, true);
        logger.info("创建实施清单的列标题名称");
        this.getWbTitle(sheet,titleStyle);
        getWeekBoardTitle(sheet);
        if(applyLists!=null&&applyLists.size()>0){
            logger.info("给实施清单的excel设置值"+applyLists.toString());
            for (ApplyList app: applyLists) {
                this.setValues(sheet,app);
            }
        }else{
            logger.warn("没有查到实施清单清单的相关数据");
        }
    }

    @Override
    public void getWbTitle(HSSFSheet sheet, HSSFCellStyle titleStyle) throws Exception{
       logger.info("设置列实施清单的宽度");
        sheet.setColumnWidth(CellNum.ZERO_COLUMN,20 * 256);
        sheet.setColumnWidth(CellNum.FIRST_COLUMN,20 * 256);
        sheet.setColumnWidth(CellNum.SECOUND_COLUMN,40 * 256);
        sheet.setColumnWidth(CellNum.THIRD_COLUMN,20 * 256);
        sheet.setColumnWidth(CellNum.FOURTH_COLUMN,20 * 256);
        sheet.setColumnWidth(CellNum.FIFTH_COLUMN,50 * 256);
        logger.info("设置列实施清单的标题");
        Row row = sheet.createRow(CellNum.ZERO_ROW);
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
        cell3.setCellValue("工作量");
        Cell cell4 = row.createCell(CellNum.FOURTH_COLUMN);
        cell4.setCellStyle(titleStyle);
        cell4.setCellValue("执行人");
        Cell cell5 = row.createCell(CellNum.FIFTH_COLUMN);
        cell5.setCellStyle(titleStyle);
        cell5.setCellValue("风险问题及备注");
        logger.info("合并单元格");
        sheet.addMergedRegion(new CellRangeAddress(3,4,0,0));
        sheet.addMergedRegion(new CellRangeAddress(5,12,0,0));
        sheet.addMergedRegion(new CellRangeAddress(13,15,0,0));
        sheet.addMergedRegion(new CellRangeAddress(17,18,0,0));
    }

    @Override
    public void setValues(HSSFSheet sheet, ApplyList app) throws Exception{
        //获取阶段和任务项判断将数据放在哪一行
        String line = app.getStage().toString().concat("-").concat(app.getTaskList().toString());
        switch (line){
            case ProjectArgs.DESIGN_DELOPY:
                HSSFRow row1 = sheet.getRow(CellNum.FIRST_ROW);
                this.setValue(row1,app);
                break;
            case  ProjectArgs.TEST_PRODUCT_TEST:
                HSSFRow row2 = sheet.getRow(CellNum.SECOUND_ROW);
                this.setValue(row2,app);
                break;
            case  ProjectArgs.PRE_DELIVER_EXPLORATION:
                HSSFRow row3 = sheet.getRow(CellNum.THIRD_ROW);
                this.setValue(row3,app);
                break;
            case  ProjectArgs.PRE_DELIVER_CHECK:
                HSSFRow row4 = sheet.getRow(CellNum.FOURTH_ROW);
                this.setValue(row4,app);
                break;
            case  ProjectArgs.DELIVER_LINE:
                HSSFRow row5 = sheet.getRow(CellNum.CELL_FIFTH_ROW);
                this.setValue(row5,app);
                break;
            case  ProjectArgs.DELIVER_DELOPY:
                HSSFRow row6 = sheet.getRow(CellNum.SIXTH_ROW);
                this.setValue(row6,app);
                break;
            case  ProjectArgs.DELIVER_DEBUGER:
                HSSFRow row7 = sheet.getRow(CellNum.SEVENTH_ROW);
                this.setValue(row7,app);
                break;
            case  ProjectArgs.DELIVER_TRANS:
                HSSFRow row8 = sheet.getRow(CellNum.EIGHTH_ROW);
                this.setValue(row8,app);
                break;
            case  ProjectArgs.DELIVER_PROMOTE:
                HSSFRow row9 = sheet.getRow(CellNum.NINTH_ROW);
                this.setValue(row9,app);
                break;
            case  ProjectArgs.DELIVER_ROLLBACK:
                HSSFRow row10 = sheet.getRow(CellNum.TENTH_ROW);
               this.setValue(row10,app);
                break;
            case  ProjectArgs.DELIVER_DILATATION:
                HSSFRow row11 = sheet.getRow(CellNum.ELEVENTH_ROW);
                this.setValue(row11,app);
                break;
            case  ProjectArgs.DELIVER_ABOLISH:
                HSSFRow row12 = sheet.getRow(CellNum.TWELFTH_ROW);
                this.setValue(row12,app);
                break;
            case  ProjectArgs.MAINTAIN_TRAIN:
                HSSFRow row13 = sheet.getRow(CellNum.THIRTEENTH_ROW);
                this.setValue(row13,app);
                break;
            case  ProjectArgs.MAINTAIN_STATION:
                HSSFRow row14 = sheet.getRow(CellNum.FOURTEENTH_ROW);
                this.setValue(row14,app);
                break;
            case  ProjectArgs.MAINTAIN_REPAIRE:
                HSSFRow row15 = sheet.getRow(CellNum.FIFTEENTH_ROW);
                 this.setValue(row15,app);
                break;
            case  ProjectArgs.ACCEPT_ACCEPT:
                HSSFRow row16 = sheet.getRow(CellNum.SIXTEENTH_ROW);
                this.setValue(row16,app);
                break;
            case  ProjectArgs.AFTER_SALE_INSPECTION:
                HSSFRow row17 = sheet.getRow(CellNum.SEVENTEENTH_ROW);
                this.setValue(row17,app);
                break;
            case  ProjectArgs.AFTER_SALE_GUARANTEE:
                HSSFRow row18 = sheet.getRow(CellNum.EIGHTEENTH_ROW);
               this.setValue(row18,app);
                break;
              default:
                break;
        }
    }

    @Override
    public void setValue(Row row, ApplyList app) throws Exception{
        row.createCell(CellNum.SECOUND_COLUMN).setCellValue(app.getDetailJob());
        row.createCell(CellNum.THIRD_COLUMN).setCellValue(app.getWorkload());
        row.createCell(CellNum.FOURTH_COLUMN).setCellValue(app.getExecuteMan());
        row.createCell(CellNum.FIFTH_COLUMN).setCellValue(app.getProblemRemark());
    }

    @Override
    public void getWeekBoardTitle(HSSFSheet sheet) throws Exception {
        try{
            HSSFRow row1 = sheet.createRow(CellNum.FIRST_ROW);
            row1.createCell(CellNum.ZERO_COLUMN).setCellValue(ProjectArgs.DESIGN);
            row1.createCell(CellNum.FIRST_COLUMN).setCellValue(ProjectArgs.DELOPY);

            HSSFRow row2 = sheet.createRow(CellNum.SECOUND_ROW);
            row2.createCell(CellNum.ZERO_COLUMN).setCellValue(ProjectArgs.TEST);
            row2.createCell(CellNum.FIRST_COLUMN).setCellValue(ProjectArgs.PRODUCT_TEST);

            HSSFRow row3 = sheet.createRow(CellNum.THIRD_ROW);
            row3.createCell(CellNum.ZERO_COLUMN).setCellValue(ProjectArgs.PRE_DELIVER);
            row3.createCell(CellNum.FIRST_COLUMN).setCellValue(ProjectArgs.EXPLORATION);
            HSSFRow row4 = sheet.createRow(CellNum.FOURTH_ROW);
            row4.createCell(CellNum.ZERO_COLUMN).setCellValue(ProjectArgs.PRE_DELIVER);
            row4.createCell(CellNum.FIRST_COLUMN).setCellValue(ProjectArgs.CHECK);
            HSSFRow row5 = sheet.createRow(CellNum.CELL_FIFTH_ROW);
            row5.createCell(CellNum.ZERO_COLUMN).setCellValue(ProjectArgs.DELIVER);
            row5.createCell(CellNum.FIRST_COLUMN).setCellValue(ProjectArgs.LINE);
            HSSFRow row6 = sheet.createRow(CellNum.SIXTH_ROW);
            row6.createCell(CellNum.ZERO_COLUMN).setCellValue(ProjectArgs.DELIVER);
            row6.createCell(CellNum.FIRST_COLUMN).setCellValue(ProjectArgs.DELI_DELOPY);
            HSSFRow row7 = sheet.createRow(CellNum.SEVENTH_ROW);
            row7.createCell(CellNum.ZERO_COLUMN).setCellValue(ProjectArgs.DELIVER);
            row7.createCell(CellNum.FIRST_COLUMN).setCellValue(ProjectArgs.DEBUGER);
            HSSFRow row8 = sheet.createRow(CellNum.EIGHTH_ROW);
            row8.createCell(CellNum.ZERO_COLUMN).setCellValue(ProjectArgs.DELIVER);
            row8.createCell(CellNum.FIRST_COLUMN).setCellValue(ProjectArgs.TRANS);
            HSSFRow row9 = sheet.createRow(CellNum.NINTH_ROW);
            row9.createCell(CellNum.ZERO_COLUMN).setCellValue(ProjectArgs.DELIVER);
            row9.createCell(CellNum.FIRST_COLUMN).setCellValue(ProjectArgs.PROMOTE);
            HSSFRow row10 = sheet.createRow(CellNum.TENTH_ROW);
            row10.createCell(CellNum.ZERO_COLUMN).setCellValue(ProjectArgs.DELIVER);
            row10.createCell(CellNum.FIRST_COLUMN).setCellValue(ProjectArgs.ROLLBACK);
            HSSFRow row11 = sheet.createRow(CellNum.ELEVENTH_ROW);
            row11.createCell(CellNum.ZERO_COLUMN).setCellValue(ProjectArgs.DELIVER);
            row11.createCell(CellNum.FIRST_COLUMN).setCellValue(ProjectArgs.DILATATION);
            HSSFRow row12 = sheet.createRow(CellNum.TWELFTH_ROW);
            row12.createCell(CellNum.ZERO_COLUMN).setCellValue(ProjectArgs.DELIVER);
            row12.createCell(CellNum.FIRST_COLUMN).setCellValue(ProjectArgs.ABOLISH);
            HSSFRow row13 = sheet.createRow(CellNum.THIRTEENTH_ROW);
            row13.createCell(CellNum.ZERO_COLUMN).setCellValue(ProjectArgs.MAINTAIN);
            row13.createCell(CellNum.FIRST_COLUMN).setCellValue(ProjectArgs.TRAIN);
            HSSFRow row14 = sheet.createRow(CellNum.FOURTEENTH_ROW);
            row14.createCell(CellNum.ZERO_COLUMN).setCellValue(ProjectArgs.MAINTAIN);
            row14.createCell(CellNum.FIRST_COLUMN).setCellValue(ProjectArgs.STATION);
            HSSFRow row15 = sheet.createRow(CellNum.FIFTEENTH_ROW);
            row15.createCell(CellNum.ZERO_COLUMN).setCellValue(ProjectArgs.MAINTAIN);
            row15.createCell(CellNum.FIRST_COLUMN).setCellValue(ProjectArgs.REPAIR);
            HSSFRow row16 = sheet.createRow(CellNum.SIXTEENTH_ROW);
            row16.createCell(CellNum.ZERO_COLUMN).setCellValue(ProjectArgs.PREFIX_ACCEPT);
            row16.createCell(CellNum.FIRST_COLUMN).setCellValue(ProjectArgs.SUFIX_ACCEPT);
            HSSFRow row17 = sheet.createRow(CellNum.SEVENTEENTH_ROW);
            row17.createCell(CellNum.ZERO_COLUMN).setCellValue(ProjectArgs.AFTER_SALE);
            row17.createCell(CellNum.FIRST_COLUMN).setCellValue(ProjectArgs.INSPECTION);
            HSSFRow row18 = sheet.createRow(CellNum.EIGHTEENTH_ROW);
            row18.createCell(CellNum.ZERO_COLUMN).setCellValue(ProjectArgs.AFTER_SALE);
            row18.createCell(CellNum.FIRST_COLUMN).setCellValue(ProjectArgs.GUARANTEE);

            sheet.addMergedRegion(new CellRangeAddress(CellNum.THIRD_ROW,CellNum.FOURTH_ROW,0,0));
            sheet.addMergedRegion(new CellRangeAddress(CellNum.CELL_FIFTH_ROW,CellNum.TWELFTH_ROW,0,0));
            sheet.addMergedRegion(new CellRangeAddress(CellNum.THIRTEENTH_ROW,CellNum.FIFTEENTH_ROW,0,0));
            sheet.addMergedRegion(new CellRangeAddress(CellNum.SEVENTEENTH_ROW,CellNum.EIGHTEENTH_ROW,0,0));


            logger.debug("设置明细信息标题");
        }catch (Exception e){
            logger.error("set weekly board error [{}]",e.getMessage());
            throw new BusinessException("设置项目计划明细信息标题时错误");
        }
    }
}
