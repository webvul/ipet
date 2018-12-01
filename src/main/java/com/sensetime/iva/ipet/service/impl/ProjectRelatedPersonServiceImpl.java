package com.sensetime.iva.ipet.service.impl;

import com.sensetime.iva.ipet.common.CellNum;
import com.sensetime.iva.ipet.common.ExcelTitleToClass;
import com.sensetime.iva.ipet.common.ProjectArgs;
import com.sensetime.iva.ipet.entity.ProjectRelatedPerson;
import com.sensetime.iva.ipet.mapper.ProjectRelatedPersonMapper;
import com.sensetime.iva.ipet.service.ProjectRelatedPersonService;
import com.sensetime.iva.ipet.util.ExcelImportUtil;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author  gongchao
 */
@Component
public class ProjectRelatedPersonServiceImpl implements ProjectRelatedPersonService {
    private static final Logger logger = LoggerFactory.getLogger(ProjectRelatedPersonServiceImpl.class);

    @Autowired
    ProjectRelatedPersonMapper projectRelatedPersonMapper;
    @Override
    public int deleteByPrimaryKey(Integer id) {
        logger.info("deleteByPrimaryKey id="+ id);
        return projectRelatedPersonMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(ProjectRelatedPerson projectRelatedPerson) {
        logger.info("insert ProjectRelatedPerson "+ projectRelatedPerson);
        return projectRelatedPersonMapper.insert(projectRelatedPerson);
    }

    @Override
    public ProjectRelatedPerson selectByPrimaryKey(Integer id) {
        logger.info("selectByPrimaryKey id "+ id);
        return projectRelatedPersonMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<ProjectRelatedPerson> selectAll() {
        logger.info("selectAll ProjectRelatedPerson");
        return projectRelatedPersonMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(ProjectRelatedPerson projectRelatedPerson) {
        logger.info("updateByPrimaryKey ProjectRelatedPerson="+projectRelatedPerson);
        return projectRelatedPersonMapper.updateByPrimaryKey(projectRelatedPerson);
    }

    @Override
    public List<ProjectRelatedPerson> selectByProjectId(Integer projectId) {
        logger.info("selectByProjectId projectId="+projectId);
        return projectRelatedPersonMapper.selectByProjectId(projectId);
    }

    @Override
    public void exportProjectRelatedPerson(HSSFWorkbook wb, Integer projectId) throws Exception {
        logger.debug("获取项目干系人信息");
        List<ProjectRelatedPerson> personLists = this.selectByProjectId(projectId);
        logger.info("get ProjectRelatedPerson [{}]",personLists);
        //设置sheet的名字
        logger.debug("创建项目干系人sheet");
        HSSFSheet sheet = wb.createSheet(ExcelTitleToClass.EXPORT_PROJECT_RELATED_PERSON);
        //标题样式
        HSSFCellStyle titleStyle = ExcelImportUtil.createCellStyle(wb, (short) 12, true, true);
        logger.debug("创建项目干系人的列标题名称");
        if(personLists!=null&&personLists.size()>0){
            logger.debug("给软硬件清单的excel设置值"+personLists.toString());
            Map<String, List<ProjectRelatedPerson>> personByType = this.groupPersonByType(personLists);
            logger.info("group by member type result [{}]",personByType);
            //起始excel行
            int startRow=0;
            for (List<ProjectRelatedPerson> pList :personByType.values()) {
                Integer type = pList.get(0).getType();
                //项目组成员
                if(ProjectArgs.STAGE_PROJECT_MEMBER_TYPE==type){
                    startRow= this.getWbTitle(sheet,titleStyle,startRow,"项目组成员",pList);
                }else{
                    //接口单位成员
                    startRow=this.getWbTitle(sheet,titleStyle,startRow,pList.get(0).getCompanyName(),pList);
                }
            }
        }else{
           int startRow=this.getWbTitle(sheet,titleStyle,0,"项目组成员",null);
            this.getWbTitle(sheet,titleStyle,startRow,"接口单位成员",null);
            logger.warn("没有查到项目干系人的相关数据");
        }
    }

    @Override
    public int getWbTitle(HSSFSheet sheet, HSSFCellStyle titleStyle,int startRow,String companyName,List<ProjectRelatedPerson> pList) throws Exception {
        sheet.setColumnWidth(CellNum.ZERO_COLUMN,20 * 256);
        sheet.setColumnWidth(CellNum.FIRST_COLUMN,20 * 256);
        sheet.setColumnWidth(CellNum.SECOUND_COLUMN,20 * 256);
        sheet.setColumnWidth(CellNum.THIRD_COLUMN,20 * 256);
        sheet.setColumnWidth(CellNum.FOURTH_COLUMN,20 * 256);
        sheet.setColumnWidth(CellNum.FIFTH_COLUMN,20 * 256);
        //第一次进入设置标题
        if(startRow==0){
            Row row = sheet.createRow(CellNum.ZERO_COLUMN);
            Cell cell0 = row.createCell(CellNum.ZERO_COLUMN);
            cell0.setCellStyle(titleStyle);
            cell0.setCellValue("项目干系人");
            sheet.addMergedRegion(new CellRangeAddress(0,0,0,5));
            startRow++;
        }
        //设置项目组成员或者接口单位名称
        Row row2 = sheet.createRow(startRow);
        Cell cell1 = row2.createCell(CellNum.ZERO_COLUMN);
        cell1.setCellValue(companyName);
        sheet.addMergedRegion(new CellRangeAddress(startRow,startRow,0,5));
        startRow++;
        //设置列的标题
        Row row = sheet.createRow(startRow);
        Cell cell2 = row.createCell(CellNum.ZERO_COLUMN);
        cell2.setCellStyle(titleStyle);
        cell2.setCellValue("角色");
        Cell cell3 = row.createCell(CellNum.FIRST_COLUMN);
        cell3.setCellStyle(titleStyle);
        cell3.setCellValue("姓名");
        Cell cell4 = row.createCell(CellNum.SECOUND_COLUMN);
        cell4.setCellStyle(titleStyle);
        cell4.setCellValue("备注1");
        Cell cell5 = row.createCell(CellNum.THIRD_COLUMN);
        cell5.setCellStyle(titleStyle);
        cell5.setCellValue("备注2");
        Cell cell6 = row.createCell(CellNum.FOURTH_COLUMN);
        cell6.setCellStyle(titleStyle);
        cell6.setCellValue("备注3");
        Cell cell7 = row.createCell(CellNum.FIFTH_COLUMN);
        cell7.setCellStyle(titleStyle);
        cell7.setCellValue("备注4");
        //下一行
        startRow++;
        //设置值
        if(pList!=null&&pList.size()>0){
            for (ProjectRelatedPerson p: pList ) {
                Row dataRow = sheet.createRow(startRow);
                this.setValue(dataRow,p);
                //每添加一行数据向下延伸一行
                startRow++;
            }
        }
        return startRow;
    }

    @Override
    public Map<String,List<ProjectRelatedPerson> > groupPersonByType(List<ProjectRelatedPerson> personLists){
        HashMap<String, List<ProjectRelatedPerson>> personMap = new HashMap<>(16);
        for (ProjectRelatedPerson p: personLists) {
            //项目成员类型
            if(ProjectArgs.STAGE_PROJECT_MEMBER_TYPE==p.getType()){
                if(personMap.containsKey(p.getType().toString())){
                    personMap.get(p.getType().toString()).add(p);
                }else{
                    List<ProjectRelatedPerson> personList = new ArrayList<>();
                    personList.add(p);
                    personMap.put(p.getType().toString(),personList);
                }
            }else{
                //接口单位
                if(personMap.containsKey(p.getCompanyName())){
                    personMap.get(p.getCompanyName()).add(p);
                }else{
                    List<ProjectRelatedPerson> personList = new ArrayList<>();
                    personList.add(p);
                    personMap.put(p.getCompanyName(),personList);
                }
            }
        }
        return personMap;
    }

    @Override
    public void setValue(Row row, ProjectRelatedPerson person) throws Exception{
        row.createCell(CellNum.ZERO_COLUMN).setCellValue(person.getRole());
        row.createCell(CellNum.FIRST_COLUMN).setCellValue(person.getName());
        row.createCell(CellNum.SECOUND_COLUMN).setCellValue(person.getRemark1());
        row.createCell(CellNum.THIRD_COLUMN).setCellValue(person.getRemark2());
        row.createCell(CellNum.FOURTH_COLUMN).setCellValue(person.getRemark3());
        row.createCell(CellNum.FIFTH_COLUMN).setCellValue(person.getRemark4());
    }
}
