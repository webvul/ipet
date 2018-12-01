package com.sensetime.iva.ipet.service.impl;

import com.sensetime.iva.ipet.common.CellNum;
import com.sensetime.iva.ipet.common.ExcelTitleToClass;
import com.sensetime.iva.ipet.entity.WareList;
import com.sensetime.iva.ipet.mapper.WareListMapper;
import com.sensetime.iva.ipet.service.WareListService;
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
 * @author  gongchao
 */
@Component
public class WareListServiceImpl implements WareListService {
    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    WareListMapper wareListMapper;
    @Override
    public int deleteByPrimaryKey(Integer id) {
        logger.info("deleteByPrimaryKey id="+id);
        return wareListMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(WareList ware) {
        logger.info("insert ware="+ware);
        return wareListMapper.insert(ware);
    }

    @Override
    public WareList selectByPrimaryKey(Integer id) {
        logger.info("selectByPrimaryKey id="+id);
        return wareListMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<WareList> selectAll() {
        logger.info("selectAll WareList");
        return wareListMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(WareList ware) {
        logger.info("updateByPrimaryKey ware="+ware);
        return wareListMapper.updateByPrimaryKey(ware);
    }

    @Override
    public List<WareList> selectByProjectId(Integer projectId) {
        logger.info("selectByProjectId projectId="+projectId);
        return wareListMapper.selectByProjectId(projectId);
    }

    @Override
    public void exportWare(HSSFWorkbook wb, Integer projectId) throws Exception {
        logger.info("获取软硬件清单信息");
        List<WareList> wareLists = this.selectByProjectId(projectId);
        logger.info("get wareLists by projectId [{}] result [{}]",projectId,wareLists);
        //设置sheet的名字
        logger.info("创建软硬件清单sheet");
        HSSFSheet sheet = wb.createSheet(ExcelTitleToClass.EXPORT_PROJECT_CHECK_WARE);
        //标题样式
        HSSFCellStyle titleStyle = ExcelImportUtil.createCellStyle(wb, (short) 12, true, true);
        logger.info("创建软硬件清单的列标题名称");
        this.getWbTitle(sheet,titleStyle);
        if(wareLists!=null&&wareLists.size()>0){
            logger.info("给软硬件清单的excel设置值"+wareLists.toString());
            this.setValues(sheet,wareLists);
        }else{
            logger.warn("没有查到软硬件清单的相关数据");
        }
    }

    @Override
    public void getWbTitle(HSSFSheet sheet, HSSFCellStyle titleStyle) throws Exception {
        logger.info("设置列软硬件清单的宽度");
        sheet.setColumnWidth(CellNum.ZERO_COLUMN,20 * 256);
        sheet.setColumnWidth(CellNum.FIRST_COLUMN,40 * 256);
        sheet.setColumnWidth(CellNum.SECOUND_COLUMN,40 * 256);
        sheet.setColumnWidth(CellNum.THIRD_COLUMN,20 * 256);
        sheet.setColumnWidth(CellNum.FOURTH_COLUMN,20 * 256);
        sheet.setColumnWidth(CellNum.FIFTH_COLUMN,20 * 256);
        sheet.setColumnWidth(CellNum.EIGHTH_COLUMN,20 * 256);
        sheet.setColumnWidth(CellNum.NINTH_COLUMN,20 * 256);
        sheet.setColumnWidth(CellNum.TENTH_COLUMN,20 * 256);
        sheet.setColumnWidth(CellNum.ELEVENTH,20 * 256);
        sheet.setColumnWidth(CellNum.TWELFTH,20 * 256);
        sheet.setColumnWidth(CellNum.FOURTEENTH,20 * 256);
        logger.info("设置列软硬件清单的标题");
        //第一行的标题
        Row row = sheet.createRow(CellNum.ZERO_ROW);
        Cell cell0 = row.createCell(CellNum.ZERO_COLUMN);
        cell0.setCellStyle(titleStyle);
        cell0.setCellValue("SN编号");
        Cell cell1 = row.createCell(CellNum.FIRST_COLUMN);
        cell1.setCellStyle(titleStyle);
        cell1.setCellValue("服务节点名称");
        Cell cell2 = row.createCell(CellNum.SECOUND_COLUMN);
        cell2.setCellStyle(titleStyle);
        cell2.setCellValue("服务器配置标准代号");
        Cell cell3 = row.createCell(CellNum.THIRD_COLUMN);
        cell3.setCellStyle(titleStyle);
        cell3.setCellValue("服务器尺寸");
        Cell cell4 = row.createCell(CellNum.FOURTH_COLUMN);
        cell4.setCellStyle(titleStyle);
        cell4.setCellValue("服务器硬件配置");
        Cell cell5 = row.createCell(CellNum.FIFTH_COLUMN);
        cell5.setCellStyle(titleStyle);
        cell5.setCellValue("软件/模块");
        Cell cell6 = row.createCell(CellNum.SIXTH_COLUMN);
        cell6.setCellStyle(titleStyle);
        cell6.setCellValue("版本");
        Cell cell7 = row.createCell(CellNum.SEVENTH_COLUMN);
        cell7.setCellStyle(titleStyle);
        cell7.setCellValue("端口");
        Cell cell8 = row.createCell(CellNum.EIGHTH_COLUMN);
        cell8.setCellStyle(titleStyle);
        cell8.setCellValue("视频专网IP");
        Cell cell9 = row.createCell(CellNum.NINTH_COLUMN);
        cell9.setCellStyle(titleStyle);
        cell9.setCellValue("公安网IP");
        Cell cell10 = row.createCell(CellNum.TENTH_COLUMN);
        cell10.setCellStyle(titleStyle);
        cell10.setCellValue("服务器账户/密码");
        Cell cell11 = row.createCell(CellNum.ELEVENTH);
        cell11.setCellStyle(titleStyle);
        cell11.setCellValue("更新日期");
        Cell cell12 = row.createCell(CellNum.TWELFTH);
        cell12.setCellStyle(titleStyle);
        cell12.setCellValue("加密狗到期时间");
        Cell cell13 = row.createCell(CellNum.THIRTEENTH);
        cell13.setCellStyle(titleStyle);
        cell13.setCellValue("修订记录");
        Cell cell14 = row.createCell(CellNum.FOURTEENTH);
        cell14.setCellStyle(titleStyle);
        cell14.setCellValue("备注");
    }

    @Override
    public void setValues(HSSFSheet sheet, List<WareList> wareLists) throws Exception {
        int startRow=CellNum.FIRST_ROW;
        for (int i=0;i<wareLists.size();i++){
            WareList wa = wareLists.get(i);
            HSSFRow row = sheet.createRow(startRow);
            //SN编号
            row.createCell(CellNum.ZERO_COLUMN).setCellValue(wa.getSnNo());
            //服务节点名称
            row.createCell(CellNum.FIRST_COLUMN).setCellValue(wa.getNodeName());
            //服务器配置标准代号
            row.createCell(CellNum.SECOUND_COLUMN).setCellValue(wa.getConfigCode());
            //服务器尺寸
            row.createCell(CellNum.THIRD_COLUMN).setCellValue(wa.getSize());
            //服务器硬件配置
            row.createCell(CellNum.FOURTH_COLUMN).setCellValue(wa.getHardwareConfig());
            //软件/模块
            row.createCell(CellNum.FIFTH_COLUMN).setCellValue(wa.getSoftwareModule());
            //版本
            row.createCell(CellNum.SIXTH_COLUMN).setCellValue(wa.getVersion());
            //端口
            row.createCell(CellNum.SEVENTH_COLUMN).setCellValue(wa.getPort());
            //视频专网IP
            row.createCell(CellNum.EIGHTH_COLUMN).setCellValue(wa.getVideoPrivateIp());
            //公安网IP
            row.createCell(CellNum.NINTH_COLUMN).setCellValue(wa.getPoliceIp());
            //服务器账户/密码
            row.createCell(CellNum.TENTH_COLUMN).setCellValue(wa.getAccountPassword());
            //更新日期
            row.createCell(CellNum.ELEVENTH).setCellValue(wa.getUpdateDate());
            //加密狗到期时间
            row.createCell(CellNum.TWELFTH).setCellValue(wa.getLicenseExpiration());
            //修订记录
            row.createCell(CellNum.THIRTEENTH).setCellValue(wa.getRevisedRecord());
            //备注
            row.createCell(CellNum.FOURTEENTH).setCellValue(wa.getRemark());
            startRow++;
        }
    }
}
