package com.sensetime.iva.ipet.service.impl;

import com.sensetime.iva.ipet.common.CellNum;
import com.sensetime.iva.ipet.common.ExcelTitleToClass;
import com.sensetime.iva.ipet.config.dozer.EjbGenerator;
import com.sensetime.iva.ipet.entity.RiskProblemEntity;
import com.sensetime.iva.ipet.mapper.RiskProblemMapper;
import com.sensetime.iva.ipet.service.RiskProblemService;
import com.sensetime.iva.ipet.util.ExcelImportUtil;
import com.sensetime.iva.ipet.vo.form.RiskProblemForm;
import com.sensetime.iva.ipet.vo.form.RiskProblemListForm;
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
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ChaiLongLong
 * @date : 2018/8/9 9:15
 */
@Component
public class RiskProblemServiceImpl implements RiskProblemService {

    private static final Logger logger = LoggerFactory.getLogger(RiskProblemServiceImpl.class);

    @Autowired
    RiskProblemMapper riskProblemMapper;
    @Autowired
    protected EjbGenerator ejbGenerator = new EjbGenerator();

    @Override
    public void addRiskProblem(RiskProblemEntity riskProblemEntity) {
        logger.info("addRiskProblem param riskProblemEntity "+riskProblemEntity.toString());
        riskProblemMapper.addRiskProblem(riskProblemEntity);
    }

    @Override
    public void updateRiskProblem(RiskProblemEntity riskProblemEntity) {
        logger.info("updateRiskProblem param riskProblemEntity "+riskProblemEntity.toString());
        riskProblemMapper.updateRiskProblem(riskProblemEntity);
    }

    @Override
    public List<RiskProblemEntity> queryByProject(int id) {
        logger.info("queryByProject param id "+id);
        return riskProblemMapper.queryByProject(id);
    }

    @Override
    public void deleteById(int id) {
        logger.info("deleteById param id "+id);
        riskProblemMapper.deleteById(id);
    }

    @Override
    public RiskProblemEntity queryById(int id) {
        logger.info("queryById param id "+id);
        return riskProblemMapper.queryById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<RiskProblemForm> initRiskProblemData(Integer projectId) {
        List<RiskProblemForm> riskProblemForms = new ArrayList<>();
        List<RiskProblemEntity> riskProblemEntities = riskProblemMapper.queryByProject(projectId);
        if(riskProblemEntities != null && riskProblemEntities.size() > 0 ){
            riskProblemForms = ejbGenerator.convert(riskProblemEntities,RiskProblemForm.class);
        }
        return riskProblemForms;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void submitRiskProblemData(RiskProblemListForm riskProblemListForm) throws Exception {
        logger.info("submitRiskProblemData param riskProblemForms "+riskProblemListForm.toString());
        for (RiskProblemForm riskProblemForm : riskProblemListForm.getRiskProblemForms()) {
            if(riskProblemForm.getProjectId() == null || riskProblemForm.getProjectId() == 0){
                throw new BusinessException("RiskProblemForm中projectId异常");
            }

            //根据有无id判断出差数据是否为新增
            if(null == riskProblemForm.getId() || riskProblemForm.getId() == 0){
                RiskProblemEntity riskProblemEntity = new RiskProblemEntity();
                BeanUtils.copyProperties(riskProblemForm,riskProblemEntity);
                riskProblemEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
                riskProblemMapper.addRiskProblem(riskProblemEntity);
            }else{
                //比对数据查看提交的出差数据与数据库中的是否相同，不相同则更新操作
                RiskProblemEntity riskProblemEntity = riskProblemMapper.queryById(riskProblemForm.getId());
                RiskProblemForm queryRiskProblemForm = ejbGenerator.convert(riskProblemEntity,RiskProblemForm.class);
                if(!queryRiskProblemForm.equals(riskProblemForm)){
                    BeanUtils.copyProperties(riskProblemForm,riskProblemEntity);
                    riskProblemEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                    riskProblemMapper.updateRiskProblem(riskProblemEntity);
                }
            }
        }
    }

    @Override
    public void exportRiskProblem(HSSFWorkbook wb, Integer projectId) {
        logger.info("exportRiskProblem projectId "+projectId);
        List<RiskProblemEntity> riskProblemEntities = riskProblemMapper.queryByProject(projectId);
        logger.info("riskProblemEntities [{}]",riskProblemEntities);
        //设置sheet的名字
        logger.info("创建风险问题sheet");
        HSSFSheet sheet = wb.createSheet(ExcelTitleToClass.EXPORT_PROJECT_PLAN_RISK_PROBLEM_SHEET);

        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);

        //标题样式
        HSSFCellStyle titleStyle = ExcelImportUtil.createCellStyle(wb, (short) 12, true, true);
        //合并首行单元格
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,8));

        //标题
        Row row0=sheet.createRow(CellNum.ZERO_ROW);
        Cell cell0 = row0.createCell(CellNum.ZERO_COLUMN);
        cell0.setCellStyle(titleStyle);
        cell0.setCellValue(ExcelTitleToClass.EXPORT_PROJECT_PLAN_RISK_PROBLEM_TITLE);

        Row row1=sheet.createRow(CellNum.FIRST_ROW);
        row1.createCell(CellNum.ZERO_COLUMN).setCellValue("编号");
        row1.createCell(CellNum.FIRST_COLUMN).setCellValue("风险/问题");
        row1.createCell(CellNum.SECOUND_COLUMN).setCellValue("级别");
        row1.createCell(CellNum.THIRD_COLUMN).setCellValue("具体措施");
        row1.createCell(CellNum.FOURTH_COLUMN).setCellValue("发生日期");
        row1.createCell(CellNum.FIFTH_COLUMN).setCellValue("计划解决日期");
        row1.createCell(CellNum.SIXTH_COLUMN).setCellValue("状态");
        row1.createCell(CellNum.SEVENTH_COLUMN).setCellValue("责任人");
        row1.createCell(CellNum.EIGHTH_COLUMN).setCellValue("备注");
        setCellBorder(row1,cellStyle);

        if(riskProblemEntities.size() > 0 ){
            for (int i=0; i< riskProblemEntities.size(); i++){
                RiskProblemEntity riskProblemEntity = riskProblemEntities.get(i);
                Row riskProblemRow=sheet.createRow(CellNum.SECOUND_ROW +i);
                riskProblemRow.createCell(CellNum.ZERO_COLUMN).setCellValue(i+1);
                riskProblemRow.createCell(CellNum.FIRST_COLUMN).setCellValue(riskProblemEntity.getRisk());
                riskProblemRow.createCell(CellNum.SECOUND_COLUMN).setCellValue(riskProblemEntity.getLevel());
                riskProblemRow.createCell(CellNum.THIRD_COLUMN).setCellValue(riskProblemEntity.getMeasure());
                riskProblemRow.createCell(CellNum.FOURTH_COLUMN).setCellValue(riskProblemEntity.getOccurDate());
                riskProblemRow.createCell(CellNum.FIFTH_COLUMN).setCellValue(riskProblemEntity.getPlanedSolveDate());
                riskProblemRow.createCell(CellNum.SIXTH_COLUMN).setCellValue(convertStatus(riskProblemEntity.getStatus()!=null?riskProblemEntity.getStatus():-1));
                riskProblemRow.createCell(CellNum.SEVENTH_COLUMN).setCellValue(riskProblemEntity.getPersonLiable());
                riskProblemRow.createCell(CellNum.EIGHTH_COLUMN).setCellValue(riskProblemEntity.getRemark());
                setCellBorder(riskProblemRow,cellStyle);
            }
        }


    }

    @Override
    public List<RiskProblemEntity> selectAll() {
        logger.info("selectAll RiskProblemEntity");
        return riskProblemMapper.selectAll();
    }

    /**
     * 设置无合并单元格的单元格边框
     */
    private void setCellBorder(Row row, HSSFCellStyle cellStyle){
        for (Cell cell : row) {
            cell.setCellStyle(cellStyle);
        }
    }

    /**
     * 将int status 转为对应文字状态
     * @param status 1:待解决 2:处理中 3:已解决 4:可接受
     * @return 对应文字状态
     */
    private String convertStatus(int status){
        switch (status){
            case 1:
                return "待解决";
            case 2:
                return "处理中";
            case 3:
                return "已解决";
            case 4:
                return "可接受";
            default:
                return "";
        }

    }
}
