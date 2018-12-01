package com.sensetime.iva.ipet.service.impl;

import com.sensetime.iva.ipet.common.CellNum;
import com.sensetime.iva.ipet.common.ExcelTitleToClass;
import com.sensetime.iva.ipet.entity.SurveyList;
import com.sensetime.iva.ipet.mapper.SurveyListMapper;
import com.sensetime.iva.ipet.service.SurveyListService;
import com.sensetime.iva.ipet.util.ExcelImportUtil;
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
public class SurveyListServiceImpl implements SurveyListService {
    private static final Logger logger = LoggerFactory.getLogger(SurveyListServiceImpl.class);
    @Autowired
    SurveyListMapper surveyListMapper;
    @Override
    public int deleteByPrimaryKey(Integer id) {
        logger.info("deleteByPrimaryKey id="+id);
        return surveyListMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(SurveyList survey) {
        logger.info("insert survey="+survey);
        return surveyListMapper.insert(survey);
    }

    @Override
    public SurveyList selectByPrimaryKey(Integer id) {
        logger.info("selectByPrimaryKey id="+id);
        return surveyListMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SurveyList> selectAll() {
        logger.info("selectAll SurveyList");
        return surveyListMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(SurveyList survey) {
        logger.info("updateByPrimaryKey survey="+survey);
        return surveyListMapper.updateByPrimaryKey(survey);
    }

    @Override
    public List<SurveyList> selectByProjectId(Integer projectId) {
        logger.info("selectByProjectId projectId="+projectId);
        return surveyListMapper.selectByProjectId(projectId);
    }

    @Override
    public void exportSurvey(HSSFWorkbook wb, Integer projectId) throws Exception {
        logger.info("获取工勘清单信息");
        List<SurveyList> deliverLists = this.selectByProjectId(projectId);
        logger.info("get deliverLists by projectId [{}] result [{}]",projectId,deliverLists);
        //设置sheet的名字
        logger.info("创建工勘清单sheet");
        HSSFSheet sheet = wb.createSheet(ExcelTitleToClass.EXPORT_PROJECT_CHECK_SURVEY);
        //标题样式
        HSSFCellStyle titleStyle = ExcelImportUtil.createCellStyle(wb, (short) 12, true, true);
        logger.info("创建工勘清单的列标题名称");
        this.getWbTitle(sheet,titleStyle);
        if(deliverLists!=null&&deliverLists.size()>0){
            logger.info("给工勘清单的excel设置值:"+deliverLists.toString());
            this.setValues(sheet,deliverLists);
        }else{
            logger.warn("没有查到工勘清单的相关数据");
        }
    }

    @Override
    public void getWbTitle(HSSFSheet sheet, HSSFCellStyle titleStyle) throws Exception {
        logger.info("设置列工勘清单的宽度");
        sheet.setColumnWidth(CellNum.THIRTEENTH,20 * 256);
        sheet.setColumnWidth(CellNum.FOURTEENTH,20 * 256);
        sheet.setColumnWidth(CellNum.FIFTEENTH,20 * 256);
        sheet.setColumnWidth(CellNum.SIXTEENTH,20 * 256);
        sheet.setColumnWidth(CellNum.SEVENTEENTH,20 * 256);
        sheet.setColumnWidth(CellNum.EIGHTEENTH,20 * 256);
        sheet.setColumnWidth(CellNum.NINTEENTH,20 * 256);
        sheet.setColumnWidth(CellNum.TWENTIETH,20 * 256);
        sheet.setColumnWidth(CellNum.TWENTIETH1,20 * 256);
        sheet.setColumnWidth(CellNum.TWENTIETH2,20 * 256);

        logger.info("设置列工勘清单的标题");
        //第一行的标题
        Row row = sheet.createRow(CellNum.ZERO_ROW);
        Cell cell0 = row.createCell(CellNum.ZERO_COLUMN);
        cell0.setCellStyle(titleStyle);
        cell0.setCellValue("编号");
        Cell cell1 = row.createCell(CellNum.FIRST_COLUMN);
        cell1.setCellStyle(titleStyle);
        cell1.setCellValue("警区");
        Cell cell2 = row.createCell(CellNum.SECOUND_COLUMN);
        cell2.setCellStyle(titleStyle);
        cell2.setCellValue("地点");
        Cell cell3 = row.createCell(CellNum.THIRD_COLUMN);
        cell3.setCellStyle(titleStyle);
        cell3.setCellValue("数量");
        Cell cell4 = row.createCell(CellNum.FOURTH_COLUMN);
        cell4.setCellStyle(titleStyle);
        cell4.setCellValue("点位");
        Cell cell5 = row.createCell(CellNum.FOURTEENTH_ROW);
        cell5.setCellStyle(titleStyle);
        cell5.setCellValue("架设");
        Cell cell21 = row.createCell(CellNum.TWENTIETH1);
        cell21.setCellStyle(titleStyle);
        cell21.setCellValue("初验");
        Cell cell22 = row.createCell(CellNum.TWENTIETH2);
        cell22.setCellStyle(titleStyle);
        cell22.setCellValue("调试");
        Cell cell24 = row.createCell(CellNum.TWENTIETH4);
        cell24.setCellStyle(titleStyle);
        cell24.setCellValue("终验");
        //第二行标题
        Row row1 = sheet.createRow(CellNum.FIRST_ROW);
        Cell c4 = row1.createCell(CellNum.FOURTH_COLUMN);
        c4.setCellStyle(titleStyle);
        c4.setCellValue("照度");
        Cell c7 = row1.createCell(CellNum.SEVENTH_COLUMN);
        c7.setCellStyle(titleStyle);
        c7.setCellValue("人流量");
        Cell c9 = row1.createCell(CellNum.NINTH_COLUMN);
        c9.setCellStyle(titleStyle);
        c9.setCellValue("供电");
        Cell c11 = row1.createCell(CellNum.ELEVENTH);
        c11.setCellStyle(titleStyle);
        c11.setCellValue("GIS信息");
        Cell c14 = row1.createCell(CellNum.FOURTEENTH);
        c14.setCellStyle(titleStyle);
        c14.setCellValue("镜头");
        Cell c17 = row1.createCell(CellNum.SEVENTEENTH);
        c17.setCellStyle(titleStyle);
        c17.setCellValue("臂装/吊装/立杆");
        Cell c21 = row1.createCell(CellNum.TWENTIETH1);
        c21.setCellStyle(titleStyle);
        c21.setCellValue("初验");
        Cell c22 = row1.createCell(CellNum.TWENTIETH2);
        c22.setCellStyle(titleStyle);
        c22.setCellValue("效果验收");
        Cell c24 = row1.createCell(CellNum.TWENTIETH4);
        c24.setCellStyle(titleStyle);
        c24.setCellValue("终验");
        //第三行标题
        Row row2 = sheet.createRow(CellNum.SECOUND_ROW);
        Cell ce4 = row2.createCell(CellNum.FOURTH_COLUMN);
        ce4.setCellStyle(titleStyle);
        ce4.setCellValue("强光");Cell ce5 = row2.createCell(CellNum.FIFTH_COLUMN);
        ce5.setCellStyle(titleStyle);
        ce5.setCellValue("背光");
        Cell ce6 = row2.createCell(CellNum.SIXTH_COLUMN);
        ce6.setCellStyle(titleStyle);
        ce6.setCellValue("弱光");
        Cell ce7 = row2.createCell(CellNum.SEVENTH_COLUMN);
        ce7.setCellStyle(titleStyle);
        ce7.setCellValue("过人率");
        Cell ce8 = row2.createCell(CellNum.EIGHTH_COLUMN);
        ce8.setCellStyle(titleStyle);
        ce8.setCellValue("正脸率");
        Cell ce9 = row2.createCell(CellNum.NINTH_COLUMN);
        ce9.setCellStyle(titleStyle);
        ce9.setCellValue("POE");
        Cell ce10 = row2.createCell(CellNum.TENTH_COLUMN);
        ce10.setCellStyle(titleStyle);
        ce10.setCellValue("直流电源");
        Cell ce11 = row2.createCell(CellNum.ELEVENTH);
        ce11.setCellStyle(titleStyle);
        ce11.setCellValue("经度");
        Cell ce12 = row2.createCell(CellNum.TWELFTH);
        ce12.setCellStyle(titleStyle);
        ce12.setCellValue("纬度");
        Cell ce13 = row2.createCell(CellNum.THIRTEENTH);
        ce13.setCellStyle(titleStyle);
        ce13.setCellValue("方向角度（度）");
        Cell ce14 = row2.createCell(CellNum.FOURTEENTH);
        ce14.setCellStyle(titleStyle);
        ce14.setCellValue("镜头焦距（毫米）");
        Cell ce15 = row2.createCell(CellNum.FIFTEENTH);
        ce15.setCellStyle(titleStyle);
        ce15.setCellValue("取景距离（米）");
        Cell ce16 = row2.createCell(CellNum.SIXTEENTH);
        ce16.setCellStyle(titleStyle);
        ce16.setCellValue("取景宽度（米）");
        Cell ce17 = row2.createCell(CellNum.SEVENTEENTH);
        ce17.setCellStyle(titleStyle);
        ce17.setCellValue("高度（米）");
        Cell ce18 = row2.createCell(CellNum.EIGHTEENTH);
        ce18.setCellStyle(titleStyle);
        ce18.setCellValue("横向长度（米）");
        Cell ce19 = row2.createCell(CellNum.NINTEENTH);
        ce19.setCellStyle(titleStyle);
        ce19.setCellValue("遮挡物（有/无）");
        Cell ce20 = row2.createCell(CellNum.TWENTIETH);
        ce20.setCellStyle(titleStyle);
        ce20.setCellValue("防碰撞（有/无）");
        Cell ce21 = row2.createCell(CellNum.TWENTIETH1);
        ce21.setCellStyle(titleStyle);
        ce21.setCellValue("点位验收");
        Cell ce22 = row2.createCell(CellNum.TWENTIETH2);
        ce22.setCellStyle(titleStyle);
        ce22.setCellValue("眼间距（像素）");
        Cell ce23 = row2.createCell(CellNum.TWENTIETH3);
        ce23.setCellStyle(titleStyle);
        ce23.setCellValue("对焦图");
        Cell ce24 = row2.createCell(CellNum.TWENTIETH4);
        ce24.setCellStyle(titleStyle);
        ce24.setCellValue("复验");
        this.addMergedRegion(sheet);
    }

    @Override
    public void setValues(HSSFSheet sheet, List<SurveyList> surveyLists) throws Exception {
        int startRow=CellNum.THIRD_ROW;
        for (int i=0;i<surveyLists.size();i++){
            SurveyList su = surveyLists.get(i);
            HSSFRow row = sheet.createRow(startRow);
            //编号
            row.createCell(CellNum.ZERO_COLUMN).setCellValue(su.getNo());
            //警区
            row.createCell(CellNum.FIRST_COLUMN).setCellValue(su.getConstablewick());
            //地点
            row.createCell(CellNum.SECOUND_COLUMN).setCellValue(su.getLocation());
            //数量
            row.createCell(CellNum.THIRD_COLUMN).setCellValue(su.getAmount());
            //强光
            row.createCell(CellNum.FOURTH_COLUMN).setCellValue(su.getGlare());
            //背光
            row.createCell(CellNum.FIFTH_COLUMN).setCellValue(su.getBacklight());
            //弱光
            row.createCell(CellNum.SIXTH_COLUMN).setCellValue(su.getWeaklight());
            //过人率
            row.createCell(CellNum.SEVENTH_COLUMN).setCellValue(su.getPassingRate());
            //过脸率
            row.createCell(CellNum.EIGHTH_COLUMN).setCellValue(su.getPositiveRate());
            //POE
            row.createCell(CellNum.NINTH_COLUMN).setCellValue(su.getPoe());
            //直流电源
            row.createCell(CellNum.TENTH_COLUMN).setCellValue(su.getDc());
            //经度
            row.createCell(CellNum.ELEVENTH).setCellValue(su.getLongitude());
            //纬度
            row.createCell(CellNum.TWELFTH).setCellValue(su.getLatitude());
            //方向角度
            row.createCell(CellNum.THIRTEENTH).setCellValue(su.getDirectionAngle());
            //镜头焦距
            row.createCell(CellNum.FOURTEENTH).setCellValue(su.getFocalLength());
            //取景距离
            row.createCell(CellNum.FIFTEENTH).setCellValue(su.getViewDistance());
            //取景宽度
            row.createCell(CellNum.SIXTEENTH).setCellValue(su.getViewWidth());
            //高度
            row.createCell(CellNum.SEVENTEENTH).setCellValue(su.getHeight());
            //横向长度
            row.createCell(CellNum.EIGHTEENTH).setCellValue(su.getWidthHeight());
            //有无遮挡物
            row.createCell(CellNum.NINTEENTH).setCellValue(su.getScreen());
            //师范防碰撞
            row.createCell(CellNum.TWENTIETH).setCellValue(su.getAntiCollision());
            //点位验收
            row.createCell(CellNum.TWENTIETH1).setCellValue(su.getPointCheck());
            //眼间距
            row.createCell(CellNum.TWENTIETH2).setCellValue(su.getEyeDistance());
            //对焦图
            row.createCell(CellNum.TWENTIETH3).setCellValue(su.getFocalGraph());
            //复验
            row.createCell(CellNum.TWENTIETH4).setCellValue(su.getReexamination());
            startRow++;
        }
    }

    @Override
    public void addMergedRegion(HSSFSheet sheet) {
        logger.info("合并单元格");
        sheet.addMergedRegion(new CellRangeAddress(0,2,0,0));
        sheet.addMergedRegion(new CellRangeAddress(0,2,1,1));
        sheet.addMergedRegion(new CellRangeAddress(0,2,2,2));
        sheet.addMergedRegion(new CellRangeAddress(0,2,3,3));
        sheet.addMergedRegion(new CellRangeAddress(0,0,4,13));
        sheet.addMergedRegion(new CellRangeAddress(0,0,14,20));
        sheet.addMergedRegion(new CellRangeAddress(0,0,22,23));
        sheet.addMergedRegion(new CellRangeAddress(1,1,4,6));
        sheet.addMergedRegion(new CellRangeAddress(1,1,7,8));
        sheet.addMergedRegion(new CellRangeAddress(1,1,9,10));
        sheet.addMergedRegion(new CellRangeAddress(1,1,11,13));
        sheet.addMergedRegion(new CellRangeAddress(1,1,14,16));
        sheet.addMergedRegion(new CellRangeAddress(1,1,17,20));
        sheet.addMergedRegion(new CellRangeAddress(1,1,22,23));
    }
}
