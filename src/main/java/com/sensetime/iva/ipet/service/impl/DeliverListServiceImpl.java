package com.sensetime.iva.ipet.service.impl;

import com.sensetime.iva.ipet.common.CellNum;
import com.sensetime.iva.ipet.common.ExcelTitleToClass;
import com.sensetime.iva.ipet.entity.DeliverList;
import com.sensetime.iva.ipet.mapper.DeliverListMapper;
import com.sensetime.iva.ipet.service.DeliverListService;
import com.sensetime.iva.ipet.util.ChooseUtil;
import com.sensetime.iva.ipet.util.ExcelImportUtil;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author gongchao
 */
@Component
public class DeliverListServiceImpl implements DeliverListService {
    private static final Logger logger = LoggerFactory.getLogger(DeliverListServiceImpl.class);

    @Autowired
    DeliverListMapper deliverListMapper;
    @Override
    public int deleteByPrimaryKey(Integer id) {
        logger.info("deleteByPrimaryKey id="+id);
        return deliverListMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(DeliverList deliver) {
        logger.info("insert deliver="+deliver);
        return deliverListMapper.insert(deliver);
    }

    @Override
    public DeliverList selectByPrimaryKey(Integer id) {
        logger.info("selectByPrimaryKey id="+id);
        return deliverListMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<DeliverList> selectAll() {
        logger.info("selectAll DeliverList");
        return deliverListMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(DeliverList deliver) {
        logger.info("updateByPrimaryKey deliver="+deliver);
        return deliverListMapper.updateByPrimaryKey(deliver);
    }

    @Override
    public List<DeliverList> selectByProjectId(Integer projectId) {
        logger.info("selectByProjectId projectId="+projectId);
        return deliverListMapper.selectByProjectId(projectId);
    }

    @Override
    public void exportDeliver(HSSFWorkbook wb, Integer projectId) throws Exception {
            logger.info("获取交付物清单信息");
            List<DeliverList> deliverLists = this.selectByProjectId(projectId);
            logger.info("get all deliverLists [{}]",deliverLists);
            //设置sheet的名字
            logger.info("创建交付物清单sheet");
            HSSFSheet sheet = wb.createSheet(ExcelTitleToClass.EXPORT_PROJECT_CHECK_DELIVER);
            //标题样式
            HSSFCellStyle titleStyle = ExcelImportUtil.createCellStyle(wb, (short) 12, true, true);
            logger.info("创建软硬件清单的列标题名称");
            this.getWbTitle(sheet,titleStyle);
            if(deliverLists!=null&&deliverLists.size()>0){
                logger.info("给交付物清单的excel设置值"+deliverLists.toString());
                this.setValues(sheet,deliverLists);
            }else{
                logger.warn("没有查到交付物清单的相关数据");
            }
    }

    @Override
    public void getWbTitle(HSSFSheet sheet, HSSFCellStyle titleStyle) throws Exception {
            logger.info("设置列交付物清单的宽度");
            sheet.setColumnWidth(CellNum.ZERO_COLUMN,20 * 256);
            sheet.setColumnWidth(CellNum.FIRST_COLUMN,20 * 256);
            sheet.setColumnWidth(CellNum.SECOUND_COLUMN,20 * 256);
            sheet.setColumnWidth(CellNum.THIRD_COLUMN,50 * 256);
            sheet.setColumnWidth(CellNum.FOURTH_COLUMN,20 * 256);
            logger.info("设置列交付物清单的标题");
            //第一行的标题
            Row row = sheet.createRow(CellNum.ZERO_ROW);
            Cell cell0 = row.createCell(CellNum.ZERO_COLUMN);
            cell0.setCellStyle(titleStyle);
            cell0.setCellValue("交付物");
            Cell cell1 = row.createCell(CellNum.FIRST_COLUMN);
            cell1.setCellStyle(titleStyle);
            cell1.setCellValue("交付对象");
            Cell cell2 = row.createCell(CellNum.SECOUND_COLUMN);
            cell2.setCellStyle(titleStyle);
            cell2.setCellValue("交付物类型");
            Cell cell3 = row.createCell(CellNum.THIRD_COLUMN);
            cell3.setCellStyle(titleStyle);
            cell3.setCellValue("交付物路径");
            Cell cell4 = row.createCell(CellNum.FOURTH_COLUMN);
            cell4.setCellStyle(titleStyle);
            cell4.setCellValue("是否交接完成");
            Cell cell5 = row.createCell(CellNum.FIFTH_COLUMN);
            cell5.setCellStyle(titleStyle);
    }

    @Override
    public void setValues(HSSFSheet sheet, List<DeliverList> deliverLists) throws Exception {
            int startRow=CellNum.FIRST_ROW;
            for (int i=0;i<deliverLists.size();i++){
                DeliverList wa = deliverLists.get(i);
                HSSFRow row = sheet.createRow(startRow);
                //交付物
                row.createCell(CellNum.ZERO_COLUMN).setCellValue(wa.getName());
                //交付对象
                row.createCell(CellNum.FIRST_COLUMN).setCellValue(wa.getTarget());
                //交付物类型
                row.createCell(CellNum.SECOUND_COLUMN).setCellValue(ChooseUtil.getDeliverType(wa.getType()));
                //交付物路径
                row.createCell(CellNum.THIRD_COLUMN).setCellValue(wa.getPath());
                //是否交接完成
                row.createCell(CellNum.FOURTH_COLUMN).setCellValue(wa.getRemark());
                startRow++;
            }

    }

}
