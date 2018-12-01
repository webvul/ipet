package com.sensetime.iva.ipet.service.impl;

import com.sensetime.iva.ipet.common.CellNum;
import com.sensetime.iva.ipet.common.ExcelTitleToClass;
import com.sensetime.iva.ipet.common.ProjectArgs;
import com.sensetime.iva.ipet.config.dozer.EjbGenerator;
import com.sensetime.iva.ipet.entity.*;
import com.sensetime.iva.ipet.service.*;
import com.sensetime.iva.ipet.util.DateUtil;
import com.sensetime.iva.ipet.vo.form.*;
import com.sensetime.iva.ipet.web.exception.BusinessException;
import javassist.tools.rmi.ObjectNotFoundException;
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
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author : ChaiLongLong
 * @date : 2018/8/10 17:06
 */
@Component
public class CostStatisticsServiceImpl implements CostStatisticsService {

    private static final Logger logger = LoggerFactory.getLogger(CostStatisticsServiceImpl.class);

    @Autowired
    WorkTimeService workTimeService;
    @Autowired
    BusinessTripService businessTripService;
    @Autowired
    EquipmentService equipmentService;
    @Autowired
    StageService stageService;
    @Autowired
    ProjectService projectService;
    @Autowired
    protected EjbGenerator ejbGenerator = new EjbGenerator();

    @Override
    public CostStatisticsForm initCostStatisticsData(int projectId) throws Exception {
        logger.info("initCostStatisticsData param projectId "+projectId);
        List<StageForm> stageForms = new ArrayList<>();
        List<StageEntity> stageEntities = stageService.queryStagesByProjectIdAndType(projectId,ProjectArgs.STAGE_PROJECT_MAN_HOUR_TYPE);
       logger.info("query result stageEntities",stageEntities);
        if(stageEntities != null && stageEntities.size() > 0){
            Map<Integer,List<WorkTimeEntity>> map = new HashMap<>(stageEntities.size());
            for (StageEntity s : stageEntities ) {
                map.put(s.getId(),s.getWorkTimeEntities());
            }
            stageForms = ejbGenerator.convert(stageEntities,StageForm.class);

            for (StageForm s:stageForms) {
                if(map.containsKey(s.getId())){
                    s.setWorkTimeForms(ejbGenerator.convert(map.get(s.getId()),WorkTimeForm.class));
                }
            }
        }

        List<BusinessTripForm> businessTripForms = new ArrayList<>();
        List<BusinessTripEntity> businessTripEntities = businessTripService.queryByProject(projectId);
        logger.info("query result businessTripEntities",businessTripEntities);
        if(businessTripEntities != null && businessTripEntities.size() > 0){
            businessTripForms = ejbGenerator.convert(businessTripEntities,BusinessTripForm.class);
        }

        List<EquipmentForm> equipmentForms = new ArrayList<>();
        List<EquipmentEntity> equipmentEntities = equipmentService.queryByProject(projectId);
        logger.info("query result equipmentEntities",equipmentEntities);
        if(equipmentEntities != null && equipmentEntities.size() > 0){
            equipmentForms = ejbGenerator.convert(equipmentEntities,EquipmentForm.class);
        }
        CostStatisticsForm costStatisticsForm = new CostStatisticsForm(stageForms,businessTripForms,equipmentForms);
        logger.info("init CostStatisticsForm result costStatisticsForm",costStatisticsForm);
        return costStatisticsForm;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void submitCostStatisticsData(CostStatisticsForm costStatisticsForm) throws Exception{
        logger.info("submitCostStatisticsData param costStatisticsForm "+costStatisticsForm.toString());
        int projectId = -1;
        List<StageForm> stageForms = costStatisticsForm.getStageForms();
        logger.info("from web data stageForms [{}]",stageForms);
        if(stageForms != null && stageForms.size() > 0){
            for (StageForm stageForm : stageForms) {
                //当用户第一次提交 成本统计 页时，阶段为空，此时StageForm中最少要传递projectId,然后新增阶段，该阶段开始时间为提交时间，结束时间为提交日期所在的周日
                boolean isFirst= (stageForm.getId() == null || stageForm.getId() == 0) && stageForms.size() == 1 && stageForm.getProjectId() != null;
                Project project = projectService.selectByPrimaryKey(stageForm.getProjectId());
                if(project == null){
                    logger.error("stageForm projectId [{}] 项目不存在",stageForm.getProjectId());
                    throw new BusinessException("项目不存在");
                }
                projectId = stageForm.getProjectId();
                if (isFirst){
                    StageEntity stageEntity = new StageEntity();
                    stageEntity.setProjectId(stageForm.getProjectId());
                    stageEntity.setType(ProjectArgs.STAGE_PROJECT_MAN_HOUR_TYPE);
                    stageEntity.setStartDate(new Date());
                    if(DateUtil.getWhicthDay(new Date()) == 1){
                        stageEntity.setEndDate(new Date());
                    }else{
                        stageEntity.setEndDate(DateUtil.getSundayOfThisWeek());
                    }

                    stageService.addStage(stageEntity);
                    stageForm.setId(stageEntity.getId());
                }
                if(stageForm.getWorkTimeForms() != null && stageForm.getWorkTimeForms().size() > 0 ){
                    logger.info("stageForm.getWorkTimeForms [{}]",stageForm.getWorkTimeForms());
                    for (WorkTimeForm workTimeForm: stageForm.getWorkTimeForms()) {
                        //根据有无id判断工时数据是否为新增
                        if(null == workTimeForm.getId() || workTimeForm.getId() == 0){
                            logger.info("insert workTimeForm [{}]" ,workTimeForm);
                            WorkTimeEntity workTimeEntity = new WorkTimeEntity();
                            BeanUtils.copyProperties(workTimeForm,workTimeEntity);
                            workTimeEntity.setStageId(stageForm.getId());
                            workTimeEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
                            workTimeService.addWorkTime(workTimeEntity);
                        }else{
                            //比对数据查看提交的工时数据与数据库中的是否相同，不相同则更新操作
                            logger.info("update workTimeForm [{}]" ,workTimeForm);
                            workTimeForm.setTotal(null);
                            WorkTimeEntity workTimeEntity = workTimeService.queryById(workTimeForm.getId());
                            WorkTimeForm queryWorkTimeForm = new WorkTimeForm();
                            BeanUtils.copyProperties(workTimeEntity,WorkTimeForm.class);
                            if(!queryWorkTimeForm.equals(workTimeForm)){
                                BeanUtils.copyProperties(workTimeForm,workTimeEntity);
                                workTimeEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                                workTimeService.updateWorkTime(workTimeEntity);
                            }
                        }
                    }
                }

            }
        }

        List<BusinessTripForm> businessTripForms = costStatisticsForm.getBusinessTripForms();
        logger.info("from web data businessTripForms [{}]",businessTripForms);
        if(businessTripForms != null && businessTripForms.size() > 0){
            for (BusinessTripForm businessTripForm : businessTripForms) {
                Project project = projectService.selectByPrimaryKey(businessTripForm.getProjectId());
                if(project == null){
                    logger.error("businessTripForms projectId [{}] 项目不存在",businessTripForm.getProjectId());
                    throw new ObjectNotFoundException("项目不存在");
                }
                projectId = businessTripForm.getProjectId();
                //根据有无id判断出差数据是否为新增
                if(null == businessTripForm.getId() || businessTripForm.getId() == 0){
                    logger.info("insert businessTripForm [{}]",businessTripForm);
                    BusinessTripEntity businessTripEntity = new BusinessTripEntity();
                    BeanUtils.copyProperties(businessTripForm,businessTripEntity);
                    businessTripEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
                    businessTripService.addBusinessTrip(businessTripEntity);
                }else{
                    logger.info("update businessTripForm [{}]",businessTripForm);
                    //比对数据查看提交的出差数据与数据库中的是否相同，不相同则更新操作
                    BusinessTripEntity businessTripEntity = businessTripService.queryById(businessTripForm.getId());
                    BusinessTripForm queryBusinessTripForm = new BusinessTripForm();
                    BeanUtils.copyProperties(businessTripEntity,BusinessTripForm.class);
                    if(!queryBusinessTripForm.equals(businessTripForm)){
                        BeanUtils.copyProperties(businessTripForm,businessTripEntity);
                        businessTripEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                        businessTripService.updateBusinessTrip(businessTripEntity);
                    }
                }
            }
        }

        List<EquipmentForm> equipmentForms= costStatisticsForm.getEquipmentForms();
        logger.info("from web data equipmentForms [{}]",equipmentForms);
        if(equipmentForms != null && equipmentForms.size() > 0){
            for (EquipmentForm equipmentForm : equipmentForms) {

                Project project = projectService.selectByPrimaryKey(equipmentForm.getProjectId());
                if(project == null){
                    logger.error("equipmentForms projectId [{}] 项目不存在",equipmentForm.getProjectId());
                    throw new ObjectNotFoundException("项目不存在");
                }
                projectId = equipmentForm.getProjectId();

                //根据有无id判断设备数据是否为新增
                if(null == equipmentForm.getId() || equipmentForm.getId() == 0){
                    logger.info("insert equipmentForm [{}]",equipmentForm);
                    EquipmentEntity equipmentEntity = new EquipmentEntity();
                    BeanUtils.copyProperties(equipmentForm,equipmentEntity);
                    equipmentEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
                    equipmentService.addEquipment(equipmentEntity);
                }else{
                    logger.info("update equipmentForm [{}]",equipmentForm);
                    //比对数据查看提交的设备数据与数据库中的是否相同，不相同则更新操作
                    EquipmentEntity equipmentEntity = equipmentService.queryById(equipmentForm.getId());
                    EquipmentForm queryEquipmentForm = new EquipmentForm();
                    BeanUtils.copyProperties(equipmentEntity,EquipmentForm.class);
                    if(!queryEquipmentForm.equals(equipmentForm)){
                        BeanUtils.copyProperties(equipmentForm,equipmentEntity);
                        equipmentEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                        equipmentService.updateEquipment(equipmentEntity);
                    }
                }
            }
        }
        //当用户提交成本统计数据中无阶段数据且其他数据包含projectId，则自动生成第一个阶段
        boolean isFirstSubmitAndNoStage = (stageForms == null || stageForms.size() == 0) && projectId != -1;
        if(isFirstSubmitAndNoStage){
            logger.info("init StageEntity");
            StageEntity stageEntity = new StageEntity();
            stageEntity.setProjectId(projectId);
            stageEntity.setType(ProjectArgs.STAGE_PROJECT_MAN_HOUR_TYPE);
            stageEntity.setStartDate(new Date());
            if(DateUtil.getWhicthDay(new Date()) == 1){
                stageEntity.setEndDate(new Date());
            }else{
                stageEntity.setEndDate(DateUtil.getSundayOfThisWeek());
            }
            stageService.addStage(stageEntity);
        }
    }

    @Override
    public void exportCostStatistics(HSSFWorkbook wb, Integer projectId) {

        logger.info("exportCostStatistics projectId "+projectId);
        //  日期降序
        List<StageEntity> stageEntities = stageService.queryStagesByProjectIdAndType(projectId,ProjectArgs.STAGE_PROJECT_MAN_HOUR_TYPE);
        logger.info("stageEntities [{}]"+stageEntities);
        List<BusinessTripEntity> businessTripEntities = businessTripService.queryByProject(projectId);
        logger.info("businessTripEntities [{}]"+businessTripEntities);
        List<EquipmentEntity> equipmentEntities = equipmentService.queryByProject(projectId);
        logger.info("equipmentEntities [{}]"+equipmentEntities);
        int workTimeLineNum = 0;
        int businessTripLineNum = 0;

        //设置sheet的名字
        logger.info("创建成本统计sheet");
        HSSFSheet sheet = wb.createSheet(ExcelTitleToClass.EXPORT_PROJECT_PLAN_COST_STATISTICS_SHEET);
        //设置表单第一列宽度
        sheet.setColumnWidth(0,20 * 256);

        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);

        Row row0=sheet.createRow(CellNum.ZERO_ROW);
        Cell cell0 = row0.createCell(CellNum.ZERO_COLUMN);
        cell0.setCellValue(ExcelTitleToClass.EXPORT_PROJECT_PLAN_WORK_TIME_TITLE);

        Row row1=sheet.createRow(CellNum.FIRST_ROW);
        row1.createCell(CellNum.ZERO_COLUMN).setCellValue("姓名");
        row1.createCell(CellNum.FIRST_COLUMN).setCellValue("职能");
        row1.createCell(CellNum.SECOUND_COLUMN).setCellValue("总计");
        row1.createCell(CellNum.THIRD_COLUMN).setCellValue("本周");

        float allTotal = 0;
        float weekTotal = 0;
        float workHourTotal1 = 0;
        float workHourTotal2 = 0;
        float workHourTotal3 = 0;
        float workHourTotal4 = 0;
        float workHourTotal5 = 0;
        float workHourTotal6 = 0;
        float workHourTotal7 = 0;

        if (stageEntities.size() > 0){
            //最近日期
            StageEntity lastStage = stageEntities.get(0);
            if (stageEntities.size() == 1 && DateUtil.getDateIsWhichDayForWeek(lastStage.getStartDate()) != 1){
                int whichDay = DateUtil.getDateIsWhichDayForWeek(lastStage.getStartDate());
                for (int i=whichDay;i>0;i--){
                    row1.createCell(3+i).setCellValue(DateUtil.getAppointDay(lastStage.getStartDate(), i-whichDay));
                }
                for(int i=(whichDay+1); i < 8; i++){
                    row1.createCell(3+i).setCellValue(DateUtil.getAppointDay(lastStage.getStartDate(), i-whichDay));
                }
            }else{
                row1.createCell(CellNum.FOURTH_COLUMN).setCellValue(DateUtil.getAppointDay(lastStage.getStartDate(), 0));
                row1.createCell(CellNum.FIFTH_COLUMN).setCellValue(DateUtil.getAppointDay(lastStage.getStartDate(), 1));
                row1.createCell(CellNum.SIXTH_COLUMN).setCellValue(DateUtil.getAppointDay(lastStage.getStartDate(), 2));
                row1.createCell(CellNum.SEVENTH_COLUMN).setCellValue(DateUtil.getAppointDay(lastStage.getStartDate(), 3));
                row1.createCell(CellNum.EIGHTH_COLUMN).setCellValue(DateUtil.getAppointDay(lastStage.getStartDate(), 4));
                row1.createCell(CellNum.NINTH_COLUMN).setCellValue(DateUtil.getAppointDay(lastStage.getStartDate(), 5));
                row1.createCell(CellNum.TENTH_COLUMN).setCellValue(DateUtil.getAppointDay(lastStage.getStartDate(), 6));
            }

            if (lastStage.getWorkTimeEntities().size() > 0 ){

                for (int i=0; i<lastStage.getWorkTimeEntities().size(); i++){
                    WorkTimeEntity workTime = lastStage.getWorkTimeEntities().get(i);
                    Row workTimeRow=sheet.createRow(CellNum.SECOUND_ROW + i);
                    workTimeRow.createCell(CellNum.ZERO_COLUMN).setCellValue(workTime.getName());
                    workTimeRow.createCell(CellNum.FIRST_COLUMN).setCellValue(workTime.getWorkDesc());
                    workTimeRow.createCell(CellNum.SECOUND_COLUMN).setCellValue(workTime.getTotal());
                    workTimeRow.createCell(CellNum.THIRD_COLUMN).setCellValue(workTime.getWeekTotalHour());
                    workTimeRow.createCell(CellNum.FOURTH_COLUMN).setCellValue(workTime.getWorkHour1());
                    workTimeRow.createCell(CellNum.FIFTH_COLUMN).setCellValue(workTime.getWorkHour2());
                    workTimeRow.createCell(CellNum.SIXTH_COLUMN).setCellValue(workTime.getWorkHour3());
                    workTimeRow.createCell(CellNum.SEVENTH_COLUMN).setCellValue(workTime.getWorkHour4());
                    workTimeRow.createCell(CellNum.EIGHTH_COLUMN).setCellValue(workTime.getWorkHour5());
                    workTimeRow.createCell(CellNum.NINTH_COLUMN).setCellValue(workTime.getWorkHour6());
                    workTimeRow.createCell(CellNum.TENTH_COLUMN).setCellValue(workTime.getWorkHour7());
                    setCellBorder(workTimeRow,cellStyle);
                    allTotal += workTime.getTotal();
                    weekTotal += workTime.getWeekTotalHour();
                    workHourTotal1 += workTime.getWorkHour1();
                    workHourTotal2 += workTime.getWorkHour2();
                    workHourTotal3 += workTime.getWorkHour3();
                    workHourTotal4 += workTime.getWorkHour4();
                    workHourTotal5 += workTime.getWorkHour5();
                    workHourTotal6 += workTime.getWorkHour6();
                    workHourTotal7 += workTime.getWorkHour7();
                }
                workTimeLineNum = lastStage.getWorkTimeEntities().size();
            }

        }else {
            row1.createCell(CellNum.FOURTH_COLUMN).setCellValue("N/N");
            row1.createCell(CellNum.FIFTH_COLUMN).setCellValue("N/N");
            row1.createCell(CellNum.SIXTH_COLUMN).setCellValue("N/N");
            row1.createCell(CellNum.SEVENTH_COLUMN).setCellValue("N/N");
            row1.createCell(CellNum.EIGHTH_COLUMN).setCellValue("N/N");
            row1.createCell(CellNum.NINTH_COLUMN).setCellValue("N/N");
            row1.createCell(CellNum.TENTH_COLUMN).setCellValue("N/N");

        }
        setCellBorder(row1,cellStyle);

        sheet.addMergedRegion(new CellRangeAddress(CellNum.SECOUND_ROW + workTimeLineNum,CellNum.SECOUND_ROW + workTimeLineNum,0,1));


        Row workTimeTotalRow=sheet.createRow(CellNum.SECOUND_ROW + workTimeLineNum);
        workTimeTotalRow.createCell(CellNum.ZERO_COLUMN);
        workTimeTotalRow.createCell(CellNum.FIRST_COLUMN);
        workTimeTotalRow.createCell(CellNum.SECOUND_COLUMN).setCellValue(allTotal);
        workTimeTotalRow.createCell(CellNum.THIRD_COLUMN).setCellValue(weekTotal);
        workTimeTotalRow.createCell(CellNum.FOURTH_COLUMN).setCellValue(workHourTotal1);
        workTimeTotalRow.createCell(CellNum.FIFTH_COLUMN).setCellValue(workHourTotal2);
        workTimeTotalRow.createCell(CellNum.SIXTH_COLUMN).setCellValue(workHourTotal3);
        workTimeTotalRow.createCell(CellNum.SEVENTH_COLUMN).setCellValue(workHourTotal4);
        workTimeTotalRow.createCell(CellNum.EIGHTH_COLUMN).setCellValue(workHourTotal5);
        workTimeTotalRow.createCell(CellNum.NINTH_COLUMN).setCellValue(workHourTotal6);
        workTimeTotalRow.createCell(CellNum.TENTH_COLUMN).setCellValue(workHourTotal7);
        setCellBorder(workTimeTotalRow,cellStyle);

        Row tripTitleRow=sheet.createRow(CellNum.SECOUND_ROW + workTimeLineNum +2);
        Cell cellTripTitle = tripTitleRow.createCell(CellNum.ZERO_COLUMN);
        cellTripTitle.setCellValue(ExcelTitleToClass.EXPORT_PROJECT_PLAN_BUSINESS_TRIP_TITLE);

        Row tripRowTitle=sheet.createRow(CellNum.SECOUND_ROW + workTimeLineNum +3);
        tripRowTitle.createCell(CellNum.ZERO_COLUMN).setCellValue("出差人员");
        tripRowTitle.createCell(CellNum.FIRST_COLUMN).setCellValue("出差类型");
        tripRowTitle.createCell(CellNum.SECOUND_COLUMN).setCellValue("出差地");
        tripRowTitle.createCell(CellNum.THIRD_COLUMN).setCellValue("出发日期");
        tripRowTitle.createCell(CellNum.FOURTH_COLUMN).setCellValue("结束日期");
        tripRowTitle.createCell(CellNum.FIFTH_COLUMN).setCellValue("住宿费RMB");
        tripRowTitle.createCell(CellNum.SIXTH_COLUMN).setCellValue("交通费RMB");
        tripRowTitle.createCell(CellNum.SEVENTH_COLUMN).setCellValue("其他RMB");
        tripRowTitle.createCell(CellNum.EIGHTH_COLUMN).setCellValue("差旅总计RMB");
        setCellBorder(tripRowTitle,cellStyle);

        if( businessTripEntities.size() > 0){
            for (int i=0; i<businessTripEntities.size(); i++){
                BusinessTripEntity businessTripEntity = businessTripEntities.get(i);
                Row tripRow=sheet.createRow(CellNum.SECOUND_ROW + workTimeLineNum +4+i);
                tripRow.createCell(CellNum.ZERO_COLUMN).setCellValue(businessTripEntity.getName());
                tripRow.createCell(CellNum.FIRST_COLUMN).setCellValue(convertTripType(businessTripEntity.getType()));
                tripRow.createCell(CellNum.SECOUND_COLUMN).setCellValue(businessTripEntity.getDestination());
                tripRow.createCell(CellNum.THIRD_COLUMN).setCellValue(businessTripEntity.getStartDate());
                tripRow.createCell(CellNum.FOURTH_COLUMN).setCellValue(businessTripEntity.getEndDate());
                tripRow.createCell(CellNum.FIFTH_COLUMN).setCellValue(businessTripEntity.getAccommodation());
                tripRow.createCell(CellNum.SIXTH_COLUMN).setCellValue(businessTripEntity.getTraffic());
                tripRow.createCell(CellNum.SEVENTH_COLUMN).setCellValue(businessTripEntity.getOther());
                tripRow.createCell(CellNum.EIGHTH_COLUMN).setCellValue(businessTripEntity.getTotal());
                setCellBorder(tripRow,cellStyle);
            }
        }

        businessTripLineNum = businessTripEntities.size();

        Row equipTitleRow=sheet.createRow(CellNum.SECOUND_ROW + workTimeLineNum + businessTripLineNum +5);
        Cell equipTripTitle = equipTitleRow.createCell(CellNum.ZERO_COLUMN);
        equipTripTitle.setCellValue(ExcelTitleToClass.EXPORT_PROJECT_PLAN_EQUIPMENT_TITLE);

        Row equipRowTitle=sheet.createRow(CellNum.SECOUND_ROW + workTimeLineNum+ businessTripLineNum +6);
        equipRowTitle.createCell(CellNum.ZERO_COLUMN).setCellValue("产品类型");
        equipRowTitle.createCell(CellNum.FIRST_COLUMN).setCellValue("软件版本");
        equipRowTitle.createCell(CellNum.SECOUND_COLUMN).setCellValue("设备类型");
        equipRowTitle.createCell(CellNum.THIRD_COLUMN).setCellValue("设备数量");
        equipRowTitle.createCell(CellNum.FOURTH_COLUMN).setCellValue("显卡编号");
        equipRowTitle.createCell(CellNum.FIFTH_COLUMN).setCellValue("显卡数量");
        setCellBorder(equipRowTitle,cellStyle);

        if(equipmentEntities.size() > 0 ){
            for (int i = 0; i < equipmentEntities.size(); i++){
                EquipmentEntity equipmentEntity = equipmentEntities.get(i);
                Row equipRow=sheet.createRow(CellNum.SECOUND_ROW + workTimeLineNum + businessTripLineNum+7+i);
                equipRow.createCell(CellNum.ZERO_COLUMN).setCellValue(equipmentEntity.getProType());
                equipRow.createCell(CellNum.FIRST_COLUMN).setCellValue(equipmentEntity.getSoftwareVersion());
                equipRow.createCell(CellNum.SECOUND_COLUMN).setCellValue(equipmentEntity.getDeviceType());
                equipRow.createCell(CellNum.THIRD_COLUMN).setCellValue(equipmentEntity.getDeviceNum());
                equipRow.createCell(CellNum.FOURTH_COLUMN).setCellValue(equipmentEntity.getGraphicsCardSerial());
                equipRow.createCell(CellNum.FIFTH_COLUMN).setCellValue(equipmentEntity.getGraphicsCardNum());
                setCellBorder(equipRow,cellStyle);
            }
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

    /**
     * 将int 出差类型 转为对应文字状态
     * @param status 1:长途  2:短途
     * @return 对应文字状态
     */
    private String convertTripType(int status){
        switch (status){
            case 1:
                return "长途";
            case 2:
                return "短途";
            default:
                return "";
        }
    }

}
