package com.sensetime.iva.ipet.service.impl;

import com.sensetime.iva.ipet.common.DatePattern;
import com.sensetime.iva.ipet.common.ProjectArgs;
import com.sensetime.iva.ipet.entity.AccountEntity;
import com.sensetime.iva.ipet.mapper.ReportMapper;
import com.sensetime.iva.ipet.service.ReportService;
import com.sensetime.iva.ipet.util.AccountUtils;
import com.sensetime.iva.ipet.util.ChooseUtil;
import com.sensetime.iva.ipet.util.DateUtil;
import com.sensetime.iva.ipet.vo.form.*;
import com.sensetime.iva.ipet.web.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author gongchao
 * @date 9:52 2018/9/13
 */
@Component
public class ReportServiceImpl  implements ReportService {
    private static final Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);

    @Autowired
    ReportMapper reportMapper;


    @Override
    public List<Integer> getSameUserByAreaId(Integer areaId) {
        logger.info("getSameUserByAreaId by areaId [{}]",areaId);
        return reportMapper.getSameUserByAreaId(areaId);
    }

    @Override
    public List<ReportBoardFrom> getReportBoardCount(List<Integer> userIds) {
        logger.info("getReportBoardCount userIds [{}]",userIds);
        List<ReportBoardFrom> reportBoardCount;
        if(userIds==null||userIds.size()<1){
             reportBoardCount = reportMapper.getReportBoardCount(null);
        }else{
            String s = ChooseUtil.listToString(new ArrayList<>(userIds));
            reportBoardCount= reportMapper.getReportBoardCount(s);
        }
        if(reportBoardCount==null||reportBoardCount.size()<1){
            return new ArrayList<>();
        }
        logger.info("getReportBoardCount result ReportBoardFrom [{}]",reportBoardCount);
        return reportBoardCount;
    }

    @Override
    public List<ReportBoardFrom> getReportBoard(List<ReportBoardFrom> reportBoardFromsList) {
        //每周集中在周五显示
        int week=4;
        List<ReportBoardFrom> froms = new ArrayList<>();
        if(reportBoardFromsList !=null&& reportBoardFromsList.size()>0){
            if(reportBoardFromsList.size()==1){
                ReportBoardFrom reportBoardFrom = reportBoardFromsList.get(0);
                //"2018-35"
                String[] dateWeek = reportBoardFrom.getDate().split("-");
                //获取周五的日期
                String date = DateUtil.getWeekDayOfWeekNo(dateWeek[0], dateWeek[1], week);
                //保存看板数据
                ReportBoardFrom tempForm = new ReportBoardFrom();
                tempForm.setCount(reportBoardFrom.getCount());
                tempForm.setDate(date);
                froms.add(tempForm);
            }else{
                //保存每周都有立项数据的看板数据
                Map<String, ReportBoardFrom> tempFormMap = new HashMap<>(16);
                //将所有存在的立项数据保存到Map中
                for (ReportBoardFrom from: reportBoardFromsList) {
                    String[] dateWeek = from.getDate().split("-");
                    //获取周五的日期
                    String date = DateUtil.getWeekDayOfWeekNo(dateWeek[0], dateWeek[1], week);
                    ReportBoardFrom tempForm = new ReportBoardFrom();
                    tempForm.setCount(from.getCount());
                    tempForm.setDate(date);
                    tempFormMap.put(date,tempForm);
                }
                //  设置存在周没有立项的数据
                //获取开始日期
                String[] startWeek = reportBoardFromsList.get(0).getDate().split("-");
                //获取开始周五的日期
                String startDate = DateUtil.getWeekDayOfWeekNo(startWeek[0], startWeek[1], week);
                String currentDate = DateUtil.dateToString(new Date(), DatePattern.DEFAULT_DATE);

                //获取一共有多少周
                int weekCount = DateUtil.getWeekCount(startDate, currentDate);
                //从开始周判断，有数据就获取，没有就说明没有立项数据，数量就为0
                for (int i=0;i<=weekCount;i++){
                    //从开始日期每次累加一周
                    int  addWeek= Integer.parseInt(startWeek[1])+i;
                    //获取每周五的日期，这个日期也是tempFormMap的key
                    String tempStartDateKey = DateUtil.getWeekDayOfWeekNo(startWeek[0], addWeek+"", week);
                    //每周有立项的数据
                    if(tempFormMap.containsKey(tempStartDateKey)){
                        froms.add(tempFormMap.get(tempStartDateKey));
                    }else{
                        //本周不存在立项
                        ReportBoardFrom tempForm = new ReportBoardFrom();
                        tempForm.setCount(0);
                        tempForm.setDate(tempStartDateKey);
                        froms.add(tempForm);
                    }
                }
            }
        }
        logger.info("get index report datas [{}]",froms);
        return froms;
    }

    @Override
    public List<Integer> getIsContractProjectType(Boolean isContract) {
        List<Integer> types = new ArrayList<>();
        //只查询合同类型
        if(isContract){
            types.add(ProjectArgs.PROJECT_TYPE_NUM_HT);
        }else{
            types.add(ProjectArgs.PROJECT_TYPE_NUM_PK);
            types.add(ProjectArgs.PROJECT_TYPE_NUM_SD);
            types.add(ProjectArgs.PROJECT_TYPE_NUM_HT_TO_SD);
        }
        return types;
    }

    @Override
    public List<ReportProjectAreaForm> getReportProjectArea(List<Integer> types) {
        List<ReportProjectAreaForm> reportProjectArea;
        if(types==null||types.size()<1){
            reportProjectArea  = reportMapper.getReportProjectArea(null);
        }else{
            reportProjectArea = reportMapper.getReportProjectArea(ChooseUtil.listToString(new ArrayList<>(types)));
        }
        if(reportProjectArea==null||reportProjectArea.size()<1){
            return new ArrayList<>();
        }
        logger.info("getReportProjectArea by types [{}] result [{}]",types,reportProjectArea);
        return reportProjectArea;
    }

    @Override
    public List<ReportProjectTypeForm> getReportProjectTypeForm(List<Integer> userIds) {
        List<ReportProjectTypeForm> reportProjectTypeForm;
        if(userIds==null||userIds.size()<1){
            reportProjectTypeForm  = reportMapper.getReportProjectTypeForm(null);
        }else {
            reportProjectTypeForm = reportMapper.getReportProjectTypeForm(ChooseUtil.listToString(new ArrayList<>(userIds)));
        }
        if(reportProjectTypeForm==null||reportProjectTypeForm.size()<1){
            return new ArrayList<>();
        }
        logger.info("getReportProjectTypeForm data [{}] by userIds [{}]",reportProjectTypeForm,userIds);
        return reportProjectTypeForm;
    }

    @Override
    public ReportIndexForm getReportIndexForm(Boolean isPM) throws  Exception{
        logger.info("getReportIndexForm by isPm [{}]",isPM);
        //首页数据
        ReportIndexForm indexForm = new ReportIndexForm();
        //PM
        if(isPM){
            AccountEntity accountEntity;
            try {
                accountEntity = AccountUtils.getCurrentHr();
            }catch (Exception e){
                logger.error("NOT LOGIN");
                throw  new BusinessException("未登陆");
            }
            if(accountEntity.getAreaId()==null){
                logger.error("PM {} NOT EXIST area_id ",accountEntity);
                throw  new BusinessException("当前用户没有所在区域");
            }
            List<Integer> sameAreaByUserId = getSameUserByAreaId(accountEntity.getAreaId());
            //获取每周立项数据
            List<ReportBoardFrom> reportBoardCount = getReportBoardCount(sameAreaByUserId);
            List<ReportBoardFrom> reportBoard = getReportBoard(reportBoardCount);
            logger.debug("reportBoard data [{}]by userIds [{}]",reportBoard,sameAreaByUserId);
            indexForm.setBoardList(reportBoard);
            //项目类型
            List<ReportProjectTypeForm> reportProjectTypeForm = getReportProjectTypeForm(sameAreaByUserId);
            logger.debug("ReportProjectTypeForm data [{}]by userIds [{}]",reportProjectTypeForm,sameAreaByUserId);
            indexForm.setProjectTypeList(reportProjectTypeForm);
            //项目类型数量总计
            indexForm.setProjectAmount(getProjectAmount(reportProjectTypeForm));
            //延期项目
            List<Integer> statusList = new ArrayList<>();
            statusList.add(ProjectArgs.PROJECT_STATUS_DELAY_NUM);
            List<ReportDelayProjectForm> reportDelayProjectForm = getReportDelayProjectForm(sameAreaByUserId, statusList);
            logger.debug("ReportDelayProjectForm data [{}]by userIds [{}]",reportDelayProjectForm,sameAreaByUserId);
            indexForm.setDelayProjectList(reportDelayProjectForm);
            //系统数量
            List<ReportSystemFrom> reportSystemFrom = getReportSystemFrom(sameAreaByUserId);
            logger.debug("ReportSystemFrom data [{}]by userIds [{}]",reportSystemFrom,sameAreaByUserId);
            indexForm.setSystemList(reportSystemFrom);
            //平台数量
            List<ReportPlatformFrom> reportPlatformFrom = getReportPlatformFrom(sameAreaByUserId);
            logger.debug("ReportPlatformFrom data [{}]by userIds [{}]",reportPlatformFrom,sameAreaByUserId);
            indexForm.setPlatformList(reportPlatformFrom);
        }else{
            //管理员 全部查询不做限制 获取每周立项数据
            List<ReportBoardFrom> reportBoardCount = getReportBoardCount(null);
            List<ReportBoardFrom> reportBoard = getReportBoard(reportBoardCount);
            logger.debug("admin ReportBoardFrom data [{}]",reportBoard);
            indexForm.setBoardList(reportBoard);
           //项目类型
            List<ReportProjectTypeForm> reportProjectTypeForm = getReportProjectTypeForm(null);
            logger.debug("admin ReportProjectTypeForm data [{}]",reportProjectTypeForm);
            indexForm.setProjectTypeList(reportProjectTypeForm);
            //项目类型数量总计
            indexForm.setProjectAmount(getProjectAmount(reportProjectTypeForm));
            //合同类型
            List<Integer> onlyContractType = getIsContractProjectType(Boolean.TRUE);
            List<ReportProjectAreaForm> reportProjectAreaOnlyContract = getReportProjectArea(onlyContractType);
            logger.debug("admin ReportProjectAreaForm data [{}]",reportProjectAreaOnlyContract);
            indexForm.setOnlyContractAreaList(reportProjectAreaOnlyContract);
            //排除合同类型
            List<Integer> exceptContractType = getIsContractProjectType(Boolean.FALSE);
            List<ReportProjectAreaForm> reportProjectAreaExceptContract = getReportProjectArea(exceptContractType);
            logger.debug("admin ReportProjectAreaForm data [{}]",reportProjectAreaExceptContract);
            indexForm.setExceptContractAreaList(reportProjectAreaExceptContract);
           //延期项目
            List<Integer> statusList = new ArrayList<>();
            statusList.add(ProjectArgs.PROJECT_STATUS_DELAY_NUM);
            List<ReportDelayProjectForm> reportDelayProjectForm = getReportDelayProjectForm(null, statusList);
            logger.debug("admin ReportDelayProjectForm data [{}]",reportDelayProjectForm);
            indexForm.setDelayProjectList(reportDelayProjectForm);

        }
        logger.info("admin ReportIndexForm data [{}]",indexForm);
        return indexForm;
    }

    @Override
    public List<ReportDelayProjectForm> getReportDelayProjectForm(List<Integer> userIds, List<Integer> statusList) {
        List<ReportDelayProjectForm> reportDelayProjectForm;
        Boolean isUserEmpty=Boolean.TRUE;
        Boolean isStatusEmpty=Boolean.TRUE;
        if(userIds==null||userIds.size()<1){
            isUserEmpty=Boolean.FALSE;
        }
        if(statusList==null||statusList.size()<1){
            isStatusEmpty=Boolean.FALSE;
        }
        //仅仅用户为空
        if(!isUserEmpty&&isStatusEmpty){
            reportDelayProjectForm= reportMapper.getReportDelayProjectForm(null,ChooseUtil.listToString(new ArrayList<>(statusList)));
        }
        //仅仅状态为空
        else if(isUserEmpty&&!isStatusEmpty){
            reportDelayProjectForm=  reportMapper.getReportDelayProjectForm(ChooseUtil.listToString(new ArrayList<>(userIds)),null);
        }
        //全都为空
       else if(!isUserEmpty&&!isStatusEmpty){
            reportDelayProjectForm= reportMapper.getReportDelayProjectForm(null,null);
        }else {
            //全都不为空
            reportDelayProjectForm = reportMapper.getReportDelayProjectForm(ChooseUtil.listToString(new ArrayList<>(userIds)), ChooseUtil.listToString(new ArrayList<>(statusList)));
        }
        //前端空结构
        if(reportDelayProjectForm==null||reportDelayProjectForm.size()<1){
            return new ArrayList<>();
        }
        logger.info("ReportDelayProjectForm data [{}]by userIds [{}]and statusLists [{}]",reportDelayProjectForm,userIds,statusList);
        return  reportDelayProjectForm;
    }

    @Override
    public List<ReportSystemFrom> getReportSystemFrom(List<Integer> userIds) {
        List<ReportSystemFrom> reportSystemFrom;
        if(userIds==null||userIds.size()<1){
            reportSystemFrom= reportMapper.getReportSystemFrom(null);
        }else {
            reportSystemFrom = reportMapper.getReportSystemFrom(ChooseUtil.listToString(new ArrayList<>(userIds)));
        }if(reportSystemFrom==null||reportSystemFrom.size()<1){
            return new ArrayList<>();
        }
        logger.info("ReportSystemFrom data [{}]by userIds [{}]",reportSystemFrom,userIds);
        return reportSystemFrom;
    }

    @Override
    public List<ReportPlatformFrom> getReportPlatformFrom(List<Integer> userIds) {
        List<ReportPlatformFrom> reportPlatformFrom;
        if(userIds==null||userIds.size()<1){
            reportPlatformFrom= reportMapper.getReportPlatformFrom(null);
        }else{
            reportPlatformFrom = reportMapper.getReportPlatformFrom(ChooseUtil.listToString(new ArrayList<>(userIds)));
        }if(reportPlatformFrom==null||reportPlatformFrom.size()<1){
            return new ArrayList<>();
        }
        logger.info("ReportPlatformFrom data [{}]by userIds [{}]",reportPlatformFrom,userIds);
        return reportPlatformFrom;
    }

    @Override
    public Integer getProjectAmount(List<ReportProjectTypeForm> reportProjectTypeForm) {
        if(reportProjectTypeForm==null||reportProjectTypeForm.size()<1){
            return 0;
        }else{
            Integer count=0;
            for (ReportProjectTypeForm form: reportProjectTypeForm) {
                count+=form.getCountAmount();
            }
            return count;
        }
    }

    @Override
    public List<ReportProjectStatisticForm> getReportProjectByCreateTimeAndStatus(List<Integer> statusList) {
        List<ReportProjectStatisticForm> byCreateTimeAndStatus;
        if(statusList==null||statusList.size()<1){
             byCreateTimeAndStatus = reportMapper.getReportProjectByCreateTimeAndStatus(null);
        }else{
            byCreateTimeAndStatus = reportMapper.getReportProjectByCreateTimeAndStatus(ChooseUtil.listToString(new ArrayList<>(statusList)));
        }if(byCreateTimeAndStatus==null||byCreateTimeAndStatus.size()<1){
            return new ArrayList<>();
        }
        logger.info("ReportProjectStatisticForm data [{}]by statusList [{}]",byCreateTimeAndStatus,statusList);
        return byCreateTimeAndStatus;
    }

    @Override
    public List<ReportProjectStatisticForm> getReportProjectByUpdateTimeAndStatus(List<Integer> statusList) {
        List<ReportProjectStatisticForm> updateTimeAndStatus;
        if(statusList==null||statusList.size()<1){
            updateTimeAndStatus = reportMapper.getReportProjectByUpdateTimeAndStatus(null);
        }else{
            updateTimeAndStatus = reportMapper.getReportProjectByUpdateTimeAndStatus(ChooseUtil.listToString(new ArrayList<>(statusList)));
        }if(updateTimeAndStatus==null||updateTimeAndStatus.size()<1){
            return new ArrayList<>();
        }
        logger.info("ReportProjectStatisticForm data [{}]by statusList [{}]",updateTimeAndStatus,statusList);
        return updateTimeAndStatus;
    }

    @Override
    public ReportAdminBoardForm getReportAdminBoardForm() throws Exception {
        ReportAdminBoardForm reportAdminBoardForm = new ReportAdminBoardForm();
        try{
            //项目类型
            List<ReportProjectTypeForm> reportProjectTypeForm = getReportProjectTypeForm(null);
            logger.debug("admin ReportProjectTypeForm data [{}]",reportProjectTypeForm);
            reportAdminBoardForm.setProjectTypeList(reportProjectTypeForm);
            //项目类型数量总计
            reportAdminBoardForm.setProjectAmount(getProjectAmount(reportProjectTypeForm));
            //合同类型
            List<Integer> onlyContractType = getIsContractProjectType(Boolean.TRUE);
            List<ReportProjectAreaForm> reportProjectAreaOnlyContract = getReportProjectArea(onlyContractType);
            logger.debug("admin ReportProjectAreaForm data [{}]",reportProjectAreaOnlyContract);
            reportAdminBoardForm.setOnlyContractAreaList(reportProjectAreaOnlyContract);
            //排除合同类型
            List<Integer> exceptContractType = getIsContractProjectType(Boolean.FALSE);
            List<ReportProjectAreaForm> reportProjectAreaExceptContract = getReportProjectArea(exceptContractType);
            logger.debug("admin ReportProjectAreaForm data [{}]",reportProjectAreaExceptContract);
            reportAdminBoardForm.setExceptContractAreaList(reportProjectAreaExceptContract);
            //系统数量
            List<ReportSystemFrom> reportSystemFrom = getReportSystemFrom(null);
            logger.debug("admin ReportSystemFrom data [{}]",reportSystemFrom);

            reportAdminBoardForm.setSystemList(reportSystemFrom);
            //平台数量
            List<ReportPlatformFrom> reportPlatformFrom = getReportPlatformFrom(null);
            logger.debug("admin ReportPlatformFrom data [{}]",reportPlatformFrom);
            reportAdminBoardForm.setPlatformList(reportPlatformFrom);
            //项目统计
            //获取结项状态
            List<Integer> junctionsStatus = getIsJunctionsProjectStatus(Boolean.TRUE);
            //获取历史结项数据
            List<ReportProjectStatisticForm> junctionList = getReportProjectByUpdateTimeAndStatus(junctionsStatus);
            //获取历史立项数据
            List<ReportProjectStatisticForm> createList = getReportProjectByCreateTimeAndStatus(null);
            //获取项目统计的数据i
            List<ReportCreateJunctionsForm> statisticForm = getReportProjectStatisticForm(junctionList, createList);
            logger.debug("admin ReportCreateJunctionsForm data [{}]",statisticForm);
            reportAdminBoardForm.setCreateOrJunctionsList(statisticForm);
            //各项目经理项目总数和结项的统计
            List<ReportJunctionsProjectAmountForm> junctionsProjectAmountForms = getReportJunctionsProjectAmountForm(null, ProjectArgs.PROJECT_STATUS_JUNCTIONS_NUM + "");
            reportAdminBoardForm.setProjectAmountList(junctionsProjectAmountForms);
            logger.debug("admin ReportJunctionsProjectAmountForm data [{}]",junctionsProjectAmountForms);
            logger.debug("get reportAdminBoardForm success data{}",reportAdminBoardForm);
        }catch (Exception e){
            logger.error("get ReportAdminBoardForm error {}",e.getMessage());
            throw new BusinessException("获取看版信息失败");
        }
        logger.info("get reportAdminBoardForm success data{}",reportAdminBoardForm);
        return reportAdminBoardForm;
    }

    @Override
    public ReportPmBoardForm getReportPmBoardForm() throws Exception {
        ReportPmBoardForm reportPmBoardForm = new ReportPmBoardForm();
            AccountEntity accountEntity;
            try {
                accountEntity = AccountUtils.getCurrentHr();
            }catch (Exception e){
                logger.error("NOT LOGIN");
                throw  new BusinessException("未登陆");
            }
            if(accountEntity.getAreaId()==null){
                logger.error("PM {} NOT EXIST area_id ",accountEntity);
                throw  new BusinessException("当前用户没有所在区域");
            }
            try{
                //我的数据
                List<Integer> pmId = new ArrayList<>();
                pmId.add(accountEntity.getId());
                //项目类型
            List<ReportProjectTypeForm> reportProjectTypeForm = getReportProjectTypeForm(pmId);
            logger.debug("pm ReportProjectTypeForm data [{}]",reportProjectTypeForm);
            reportPmBoardForm.setProjectTypeList(reportProjectTypeForm);
            //项目类型数量总计
            reportPmBoardForm.setProjectAmount(getProjectAmount(reportProjectTypeForm));
            //系统数量
            List<ReportSystemFrom> reportSystemFrom = getReportSystemFrom(pmId);
            logger.debug("pm ReportSystemFrom data [{}]",reportSystemFrom);
            reportPmBoardForm.setSystemList(reportSystemFrom);
            //平台数量
            List<ReportPlatformFrom> reportPlatformFrom = getReportPlatformFrom(pmId);
            logger.debug("pm ReportPlatformFrom data [{}]",reportPlatformFrom);
            reportPmBoardForm.setPlatformList(reportPlatformFrom);

            //区域的数据
            List<Integer> sameAreaByUserId = getSameUserByAreaId(accountEntity.getAreaId());
                //项目类型
                List<ReportProjectTypeForm> reportAreaProjectTypeForm = getReportProjectTypeForm(sameAreaByUserId);
                logger.debug("pm ReportProjectTypeForm data [{}]",reportAreaProjectTypeForm);
                reportPmBoardForm.setAreaProjectTypeList(reportAreaProjectTypeForm);
                //项目类型数量总计
                reportPmBoardForm.setAreaProjectAmount(getProjectAmount(reportAreaProjectTypeForm));
                //系统数量
                List<ReportSystemFrom> reportAreaSystemFrom = getReportSystemFrom(sameAreaByUserId);
                logger.debug("pm ReportSystemFrom data [{}]",reportAreaSystemFrom);
                reportPmBoardForm.setAreaSystemList(reportAreaSystemFrom);
                //平台数量
                List<ReportPlatformFrom> reportAreaPlatformFrom = getReportPlatformFrom(sameAreaByUserId);
                logger.debug("pm ReportPlatformFrom data [{}]",reportAreaPlatformFrom);
                reportPmBoardForm.setAreaPlatformList(reportAreaPlatformFrom);
                logger.debug("pm reportPmBoardForm success data",reportPmBoardForm);
            }catch (Exception e){
            logger.error("get ReportPmBoardForm error {}",e.getMessage());
            throw new BusinessException("获取看版信息失败");
        }
        logger.info("pm ReportPmBoardForm data success[{}]",reportPmBoardForm);
        return reportPmBoardForm;
    }

    @Override
    public List<Integer> getIsJunctionsProjectStatus(Boolean isJunctions) {
        List<Integer> types = new ArrayList<>();
        //只查询结项类型
        if(isJunctions){
            types.add(ProjectArgs.PROJECT_STATUS_JUNCTIONS_NUM);
        }else{
            types.add(ProjectArgs.PROJECT_STATUS_READY_NUM);
            types.add(ProjectArgs.PROJECT_STATUS_INPROGRESS_NUM);
            types.add(ProjectArgs.PROJECT_STATUS_DELAY_NUM);
            types.add(ProjectArgs.PROJECT_STATUS_ONLINE_NUM);
            types.add(ProjectArgs.PROJECT_STATUS_ACCEPTED_NUM);
            types.add(ProjectArgs.PROJECT_STATUS_CANCEL_NUM);
        }
        return types;
    }

    @Override
    public List<ReportCreateJunctionsForm> getReportProjectStatisticForm(List<ReportProjectStatisticForm> junctionList, List<ReportProjectStatisticForm> createList) throws Exception {
        List<ReportCreateJunctionsForm> adminForm=new ArrayList<>();
        Boolean isJunctions=Boolean.TRUE;
        Boolean isCreate=Boolean.TRUE;
       if(junctionList==null||junctionList.size()<1){
           isJunctions=Boolean.FALSE;
           logger.debug("isJunctions empty Boolean.FALSE");
       }
        if(createList==null||createList.size()<1){
            isCreate=Boolean.FALSE;
            logger.debug("isCreate empty Boolean.FALSE");
        }
       try {
            //全空
            if(!isJunctions&&!isCreate){
                return adminForm;
            }else if(isCreate&&!isJunctions){
            //立项有，结项没有
                adminForm = onlyCreateList(createList);
                logger.debug("get projectStatisticForm {}",adminForm);
            }else if (isCreate&&isJunctions){
            //立项有，结项有
                adminForm=bothCreateAndJunctionList(junctionList, createList);
              logger.debug("get projectStatisticForm {}",adminForm);
            }else{
            //异常情况
                logger.error("没有立项数据但是存在结项数据 junctionList [{}]||||createList [{}] ",junctionList,createList);
                throw new BusinessException("没有立项数据但是存在结项数据");
            }
       }catch (Exception e){
           logger.error("获取项目立项、结项等数据的报表异常 {}",e.getMessage());
           throw new BusinessException(e.getMessage());
       }
        logger.info("admin ReportCreateJunctionsForm data success[{}]",adminForm);
        return adminForm;
    }

    @Override
    public List<ReportCreateJunctionsForm> onlyCreateList(List<ReportProjectStatisticForm> createList) throws Exception {
        List<ReportCreateJunctionsForm> adminForm=new ArrayList<>();
        ReportProjectStatisticForm startForm = createList.get(0);
        try{
            //有且仅有一个月的数据
            if(createList.size()==1){
                ReportCreateJunctionsForm form = new ReportCreateJunctionsForm();
                form.setMonthlyCreateAmount(startForm.getCount());
                form.setMonthlyDate(startForm.getDate());
                form.setMonthlyJunctionsAmount(0);
                form.setSumActiveAmount(startForm.getCount());
                form.setSumJunctionsAmount(0);
                form.setSumCreateAmount(startForm.getCount());
                adminForm.add(form);
            }else{
                //将数据放入Map用于后面做查询是否存在
                HashMap<String,ReportProjectStatisticForm> createMap = new HashMap<>(16);
                for (ReportProjectStatisticForm form: createList ) {
                    createMap.put(form.getDate(),form);
                }
                String currentDate = DateUtil.dateToString(new Date(), DatePattern.yyyy_MM);
                //开始到结束时间的月份数量
                int monthCount = DateUtil.getMonthCount(startForm.getDate(), currentDate);
                //从开始周判断，有数据就获取，没有就说明没有立项数据，数量就为0
                for (int i=0;i<=monthCount;i++){
                    //第一个月
                    if(i==0){
                        ReportCreateJunctionsForm form = new ReportCreateJunctionsForm();
                        form.setMonthlyCreateAmount(startForm.getCount());
                        form.setMonthlyDate(startForm.getDate());
                        form.setMonthlyJunctionsAmount(0);
                        form.setSumActiveAmount(startForm.getCount());
                        form.setSumJunctionsAmount(0);
                        form.setSumCreateAmount(startForm.getCount());
                        logger.debug(" init ReportCreateJunctionsForm {}",form);
                        adminForm.add(form);
                        i++;
                    }
                    //次月
                    //获取每月日期，这个日期也是tempFormMap的key
                    String tempStartDateKey = DateUtil.getNextFewMonth(startForm.getDate(),i);
                    logger.debug("onlyCreateList tempStartDateKey data {}",tempStartDateKey);
                    if(createMap.containsKey(tempStartDateKey)){
                        //本月有立项的数据
                        ReportProjectStatisticForm thisMonth = createMap.get(tempStartDateKey);
                        //获取上月的数据
                        ReportCreateJunctionsForm lastMonth = adminForm.get(i-1);
                        sumCreateForm(adminForm,thisMonth,lastMonth,tempStartDateKey);
                    }else{
                        //本月不存在立项
                        ReportProjectStatisticForm thisMonth=new ReportProjectStatisticForm();
                        thisMonth.setCount(0);
                        thisMonth.setDate(tempStartDateKey);
                        ReportCreateJunctionsForm lastMonth = adminForm.get(i-1);
                        sumCreateForm(adminForm,thisMonth,lastMonth,tempStartDateKey);
                    }
                }
           }
        }catch (Exception e){
            logger.error("onlyCreateList get  ReportProjectStatisticForm error {}",e.getMessage());
            throw e;
        }
        logger.info("get adminBoardForm success {} ",adminForm);
        return adminForm;
    }

    @Override
    public List<ReportCreateJunctionsForm> bothCreateAndJunctionList(List<ReportProjectStatisticForm> junctionList, List<ReportProjectStatisticForm> createList) throws Exception {
        List<ReportCreateJunctionsForm> pmForm=new ArrayList<>();
        ReportProjectStatisticForm startCreForm = createList.get(0);
        ReportProjectStatisticForm startJunForm = junctionList.get(0);
        try{
            String currentDate =DateUtil.dateToString(new Date(),DatePattern.yyyy_MM);
            //有且仅有一个月的数据并且是同一月
            if(createList.size()==1&&junctionList.size()==1&&startCreForm.getDate().equals(startJunForm.getDate().equals(currentDate))){
                logger.debug("有且仅有一个月的数据并且是同一月");
                ReportCreateJunctionsForm form = new ReportCreateJunctionsForm();
                form.setMonthlyCreateAmount(startCreForm.getCount());
                form.setMonthlyDate(startCreForm.getDate());
                form.setMonthlyJunctionsAmount(startJunForm.getCount());
                form.setSumActiveAmount(startCreForm.getCount()-startJunForm.getCount());
                form.setSumJunctionsAmount(startJunForm.getCount());
                form.setSumCreateAmount(startCreForm.getCount());
                pmForm.add(form);
            }else{
                //将数据放入Map用于后面做查询是否存在
                //立项数据
                HashMap<String,ReportProjectStatisticForm> createMap = new HashMap<>(16);
                for (ReportProjectStatisticForm form: createList ) {
                    createMap.put(form.getDate(),form);
                }
                //结项数据
                HashMap<String,ReportProjectStatisticForm> junMap = new HashMap<>(16);
                for (ReportProjectStatisticForm form: junctionList ) {
                    junMap.put(form.getDate(),form);
                }
                //获取立项和结项中最小和最大日期(当前日期)
                String minDate = DateUtil.getMinOrMax(startCreForm.getDate(), startJunForm.getDate(), Boolean.TRUE);
                logger.debug("get create project min date {}",minDate);
                //开始到结束时间的月份数量
                int monthCount = DateUtil.getMonthCount(minDate,currentDate);
                //从最小月判断，有数据就获取，没有就说明没有立项或结项数据，数量就为0
                for (int i=0;i<=monthCount;i++){
                    //第一个月
                    if(i==0){
                        ReportCreateJunctionsForm form = new ReportCreateJunctionsForm();
                        form.setMonthlyCreateAmount(startCreForm.getCount());
                        form.setMonthlyDate(startCreForm.getDate());
                        form.setSumCreateAmount(startCreForm.getCount());
                        //立项和结项
                        if(junMap.containsKey(minDate)){
                            form.setSumActiveAmount(startCreForm.getCount()-startJunForm.getCount());
                            form.setMonthlyJunctionsAmount(startJunForm.getCount());
                            form.setSumJunctionsAmount(startJunForm.getCount());
                        }else{
                            //有且仅有立项
                            form.setSumActiveAmount(startCreForm.getCount());
                            form.setMonthlyJunctionsAmount(0);
                            form.setSumJunctionsAmount(0);
                        }
                        logger.debug(" init ReportCreateJunctionsForm {}",form);
                        pmForm.add(form);
                        //次月
                        i++;
                    }
                    //获取每月日期，这个日期也是tempFormMap的key
                    String tempStartDateKey = DateUtil.getNextFewMonth(minDate,i);
                    logger.debug("bothCreateAndJunctionList tempStartDateKey data {}",tempStartDateKey);
                    //获取上月累计数量
                    ReportCreateJunctionsForm lastMonth = pmForm.get(i - 1);
                    //每月立项和结项统计
                    sumCreateOrJunctionsForm(pmForm,lastMonth,tempStartDateKey,createMap,junMap);
                }
            }
        }catch (Exception e){
            logger.error("bothCreateAndJunctionList get  ReportProjectStatisticForm error {}",e.getMessage());
            throw e;
        }
        logger.info("pm ReportCreateJunctionsForm data success[{}]",pmForm);
        return pmForm;
    }

    @Override
    public void sumCreateForm(List<ReportCreateJunctionsForm> forms, ReportProjectStatisticForm thisMonth, ReportCreateJunctionsForm lastMonth,String thisDate){
        ReportCreateJunctionsForm form = new ReportCreateJunctionsForm();
        form.setMonthlyDate(thisDate);
        form.setMonthlyJunctionsAmount(0);
        form.setMonthlyCreateAmount(thisMonth.getCount());
        form.setSumActiveAmount(lastMonth.getSumActiveAmount()+thisMonth.getCount());
        form.setSumCreateAmount(lastMonth.getSumCreateAmount()+thisMonth.getCount());
        form.setSumJunctionsAmount(0);
        forms.add(form);
    }

    @Override
    public void sumCreateOrJunctionsForm(List<ReportCreateJunctionsForm> forms, ReportCreateJunctionsForm lastMonth, String tempStartDateKey,HashMap<String,ReportProjectStatisticForm> createMap, HashMap<String,ReportProjectStatisticForm> junMap) {
        ReportCreateJunctionsForm form = new ReportCreateJunctionsForm();
        form.setMonthlyDate(tempStartDateKey);
        //本月数据
        ReportProjectStatisticForm create = createMap.get(tempStartDateKey);
        ReportProjectStatisticForm junctions = junMap.get(tempStartDateKey);
        //都有
        if(createMap.containsKey(tempStartDateKey)&&junMap.containsKey(tempStartDateKey)){
            logger.debug("create and junctions both exist");
            form.setMonthlyCreateAmount(create.getCount());
            form.setSumJunctionsAmount(lastMonth.getSumJunctionsAmount()-junctions.getCount());
            form.setMonthlyJunctionsAmount(junctions.getCount());
            form.setSumCreateAmount(lastMonth.getSumCreateAmount()+create.getCount());
            form.setSumActiveAmount(lastMonth.getSumActiveAmount()+create.getCount()-junctions.getCount());
        }//有且仅有结项
        else if(!createMap.containsKey(tempStartDateKey)&&junMap.containsKey(tempStartDateKey)){
            logger.debug("only junctions  exist");
            form.setMonthlyJunctionsAmount(+junctions.getCount());
            form.setSumJunctionsAmount(lastMonth.getSumJunctionsAmount()+junctions.getCount());
            form.setMonthlyCreateAmount(0);
            form.setSumCreateAmount(lastMonth.getSumCreateAmount());
            form.setSumActiveAmount(lastMonth.getSumActiveAmount()-junctions.getCount());
        } //有且仅有立项
        else if(createMap.containsKey(tempStartDateKey)&&!junMap.containsKey(tempStartDateKey)){
            logger.debug("only create  exist");
            form.setMonthlyCreateAmount(create.getCount());
            form.setSumJunctionsAmount(lastMonth.getSumJunctionsAmount());
            form.setMonthlyJunctionsAmount(0);
            form.setSumCreateAmount(lastMonth.getSumCreateAmount()+create.getCount());
            form.setSumActiveAmount(lastMonth.getSumActiveAmount()+create.getCount());
        }//都没有
        else{
            logger.debug("none of both");
            form.setMonthlyCreateAmount(0);
            form.setSumJunctionsAmount(lastMonth.getSumJunctionsAmount());
            form.setMonthlyJunctionsAmount(0);
            form.setSumCreateAmount(lastMonth.getSumCreateAmount());
            form.setSumActiveAmount(lastMonth.getSumActiveAmount());
        }
        forms.add(form);
    }

    @Override
    public List<ReportRiskAndHelpForm> getReportRiskAndHelpForm(Integer status,Boolean isPm) throws Exception {
        if (isPm) {
            AccountEntity accountEntity;
            try {
                accountEntity = AccountUtils.getCurrentHr();
            } catch (Exception e) {
                logger.error("NOT LOGIN");
                throw new BusinessException("未登陆");
            }
            if (accountEntity.getAreaId() == null) {
                logger.error("PM {} NOT EXIST area_id ", accountEntity);
                throw new BusinessException("当前用户没有所在区域");
            }
            List<Integer> sameAreaByUserId = getSameUserByAreaId(accountEntity.getAreaId());
            if(sameAreaByUserId==null||sameAreaByUserId.size()<1){
                return reportMapper.getReportRiskAndHelpForm(status.toString(),accountEntity.getId().toString());
            }else{
                String userIds = ChooseUtil.listToString(new ArrayList<>(sameAreaByUserId));
                return reportMapper.getReportRiskAndHelpForm(status.toString(),userIds);
            }
        }else{
            return reportMapper.getReportRiskAndHelpForm(status.toString(),null);
        }
    }

    @Override
    public List<ReportJunctionsBaseForm> getReportJunctionsBaseForm(String userIds, String status) {
        logger.debug("getReportJunctionsBaseForm by ids [{}]and status [{}] ",userIds,status);
        return reportMapper.getReportJunctionsBaseForm(userIds,status);
    }

    @Override
    public List<ReportJunctionsBaseForm> getReportTypeBaseJunctionsForm(String userIds, String type) {
        logger.debug("getReportTypeBaseJunctionsForm by ids [{}]and type [{}] ",userIds,type);
        return reportMapper.getReportTypeBaseJunctionsForm(userIds,type);
    }

    @Override
    public List<ReportJunctionsBaseForm> getReportLevelBaseJunctionsForm(String userIds, String level) {
        logger.debug("getReportLevelBaseJunctionsForm by ids [{}]and level [{}] ",userIds,level);
        return reportMapper.getReportLevelBaseJunctionsForm(userIds, level);
    }


    @Override
    public List<ReportJunctionsTypeForm> getReportTypeJunctionsForm(List<Integer> userIdList) {
        String userIds=null;
        if(userIdList!=null&&userIdList.size()>0){
            userIds = ChooseUtil.listToString(new ArrayList<>(userIdList));
        }
        logger.debug("get getReportTypeJunctionsForm by userIds [{}]",userIds);
        List<ReportJunctionsTypeForm> list=new ArrayList<>();
        //合同
        List<ReportJunctionsBaseForm> ht = getReportTypeBaseJunctionsForm(userIds, ProjectArgs.PROJECT_TYPE_NUM_HT + "");
        logger.debug("get ReportJunctionsTypeForm [ht] data success[{}]",ht);
        //PK
        List<ReportJunctionsBaseForm> pk = getReportTypeBaseJunctionsForm(userIds, ProjectArgs.PROJECT_TYPE_NUM_PK + "");
        logger.debug("get ReportJunctionsTypeForm [pk] data success[{}]",pk);
        //试点
        List<ReportJunctionsBaseForm> sd = getReportTypeBaseJunctionsForm(userIds, ProjectArgs.PROJECT_TYPE_NUM_SD + "");
        logger.debug("get ReportJunctionsLevelForm [sd] data success[{}]",sd);
        //试点到合同
        List<ReportJunctionsBaseForm> sdToHt = getReportTypeBaseJunctionsForm(userIds, ProjectArgs.PROJECT_TYPE_NUM_HT_TO_SD + "");
        logger.debug("get ReportJunctionsTypeForm [sdToHt] data success[{}]",sdToHt);
        if(ht!=null&&ht.size()>0){
            for (int i=0;i<ht.size();i++){
                ReportJunctionsTypeForm form = new ReportJunctionsTypeForm();
                ReportJunctionsBaseForm tempHt = ht.get(i);
                ReportJunctionsBaseForm tempPk = pk.get(i);
                ReportJunctionsBaseForm tempSd = sd.get(i);
                ReportJunctionsBaseForm tempSdToHt = sdToHt.get(i);
                form.setDate(tempHt.getDate());
                form.setContractAmount(tempHt.getAmount());
                form.setPkAmount(tempPk.getAmount());
                form.setExperimentAmount(tempSd.getAmount());
                form.setExperimentToContractAmount(tempSdToHt.getAmount());
                list.add(form);
            }
        }
        logger.info("get ReportJunctionsTypeForm data success[{}]",list);
        return list;
    }

    @Override
    public List<ReportJunctionsLevelForm> getReportLevelJunctionsForm(List<Integer> userIdList) {
        String userIds=null;
        if(userIdList!=null&&userIdList.size()>0){
             userIds = ChooseUtil.listToString(new ArrayList<>(userIdList));
        }
        List<ReportJunctionsLevelForm> list=new ArrayList<>();
        logger.debug("get ReportJunctionsLevelForm by userIds [{}]",userIdList);
        //普通
        List<ReportJunctionsBaseForm> p0 = getReportLevelBaseJunctionsForm(userIds, ProjectArgs.PROJECT_LEVEL_NUM_P0 + "");
        logger.debug("get ReportJunctionsLevelForm [p0] data success[{}]",p0);
        //VIP
        List<ReportJunctionsBaseForm> p1 = getReportLevelBaseJunctionsForm(userIds, ProjectArgs.PROJECT_LEVEL_NUM_P1 + "");
        logger.debug("get ReportJunctionsLevelForm [p1] data success[{}]",p1);
        if(p0!=null&&p0.size()>0){
            for (int i=0;i<p0.size();i++){
                ReportJunctionsLevelForm form = new ReportJunctionsLevelForm();
                ReportJunctionsBaseForm temp0 = p0.get(i);
                ReportJunctionsBaseForm temp1 = p1.get(i);
                form.setDate(temp0.getDate());
                form.setP0Amount(temp0.getAmount());
                form.setP1Amount(temp1.getAmount());
                list.add(form);
            }
        }
        logger.info("get ReportJunctionsLevelForm data success[{}]by  userIds [{}]",list,userIds);
        return list;
    }


    @Override
    public ReportJunctionsProjectForm getReportJunctionsProjectForm(Boolean isPm) throws Exception {
        ReportJunctionsProjectForm reportJunctionsProjectForm = new ReportJunctionsProjectForm();
        logger.info("getReportJunctionsProjectForm by isPm [{}]",isPm);
        try{
            //pm
            if (isPm) {
                AccountEntity accountEntity;
                try {
                    accountEntity = AccountUtils.getCurrentHr();
                } catch (Exception e) {
                    logger.error("NOT LOGIN");
                    throw new BusinessException("未登陆");
                }
                if (accountEntity.getAreaId() == null) {
                    logger.error("PM {} NOT EXIST area_id ", accountEntity);
                    throw new BusinessException("当前用户没有所在区域");
                }
                List<Integer> sameAreaByUserId = getSameUserByAreaId(accountEntity.getAreaId());
                if(sameAreaByUserId==null||sameAreaByUserId.size()<1){
                    List<Integer> userId = new ArrayList<>();
                    userId.add(accountEntity.getId());
                    //项目经理和项目数量
                    List<ReportJunctionsBaseForm> reportJunctionsBaseForm = getReportJunctionsBaseForm(accountEntity.getId().toString(), ProjectArgs.PROJECT_STATUS_JUNCTIONS_NUM + "");
                    //类型
                    List<ReportJunctionsTypeForm> reportTypeJunctionsForm = getReportTypeJunctionsForm(userId);
                   //级别
                    List<ReportJunctionsLevelForm> reportLevelJunctionsForm = getReportLevelJunctionsForm(userId);
                    //各项目经理项目总数和结项的统计
                    List<ReportJunctionsProjectAmountForm> junctionsProjectAmountForms = getReportJunctionsProjectAmountForm(userId, ProjectArgs.PROJECT_STATUS_JUNCTIONS_NUM + "");

                    reportJunctionsProjectForm.setJunctionsList(reportJunctionsBaseForm);
                    reportJunctionsProjectForm.setTypeLIst(reportTypeJunctionsForm);
                    reportJunctionsProjectForm.setLevelLIst(reportLevelJunctionsForm);
                    reportJunctionsProjectForm.setJunctionsAmountList(junctionsProjectAmountForms);
                    logger.debug("only pm one person get reportJunctionsProjectForm  data success[{}]",reportJunctionsProjectForm);
                }else{
                    String s = ChooseUtil.listToString(new ArrayList<>(sameAreaByUserId));
                    //项目经理和项目数量
                    List<ReportJunctionsBaseForm> reportJunctionsBaseForm = getReportJunctionsBaseForm(s, ProjectArgs.PROJECT_STATUS_JUNCTIONS_NUM + "");
                    //类型
                    List<ReportJunctionsTypeForm> reportTypeJunctionsForm = getReportTypeJunctionsForm(sameAreaByUserId);
                    //级别
                    List<ReportJunctionsLevelForm> reportLevelJunctionsForm = getReportLevelJunctionsForm(sameAreaByUserId);
                    //各项目经理项目总数和结项的统计
                    List<ReportJunctionsProjectAmountForm> junctionsProjectAmountForms = getReportJunctionsProjectAmountForm(sameAreaByUserId, ProjectArgs.PROJECT_STATUS_JUNCTIONS_NUM + "");

                    reportJunctionsProjectForm.setJunctionsList(reportJunctionsBaseForm);
                    reportJunctionsProjectForm.setTypeLIst(reportTypeJunctionsForm);
                    reportJunctionsProjectForm.setLevelLIst(reportLevelJunctionsForm);
                    reportJunctionsProjectForm.setJunctionsAmountList(junctionsProjectAmountForms);
                    logger.debug("only same area pm get reportJunctionsProjectForm  data success[{}]",reportJunctionsProjectForm);
                }
            }else{
                //管理员数据
                List<ReportJunctionsBaseForm> reportJunctionsBaseForm = getReportJunctionsBaseForm(null, ProjectArgs.PROJECT_STATUS_JUNCTIONS_NUM + "");
                //类型
                List<ReportJunctionsTypeForm> reportTypeJunctionsForm = getReportTypeJunctionsForm(null);
                //级别
                List<ReportJunctionsLevelForm> reportLevelJunctionsForm = getReportLevelJunctionsForm(null);
                //各项目经理项目总数和结项的统计
                List<ReportJunctionsProjectAmountForm> junctionsProjectAmountForms = getReportJunctionsProjectAmountForm(null,ProjectArgs.PROJECT_STATUS_JUNCTIONS_NUM + "");

                reportJunctionsProjectForm.setJunctionsList(reportJunctionsBaseForm);
                reportJunctionsProjectForm.setTypeLIst(reportTypeJunctionsForm);
                reportJunctionsProjectForm.setLevelLIst(reportLevelJunctionsForm);
                reportJunctionsProjectForm.setJunctionsAmountList(junctionsProjectAmountForms);
                logger.debug("admin get all reportJunctionsProjectForm  data success[{}]",reportJunctionsProjectForm);
            }
           }catch( Exception e){
                logger.error("getReportJunctionsProjectForm error [{}]",e.getMessage());
               throw e;
           }
        logger.info("get ReportJunctionsProjectForm data success[{}]",reportJunctionsProjectForm);
        return reportJunctionsProjectForm;
    }

    @Override
    public List<ReportJunctionsProjectAmountForm> getReportJunctionsProjectAmountForm(List<Integer> userIdList, String status) {
        String userIds=null;
        if(userIdList!=null&&userIdList.size()>0){
            userIds = ChooseUtil.listToString(new ArrayList<>(userIdList));
        }
        List<ReportJunctionsProjectAmountForm> junctionsProjectAmountForms = reportMapper.getReportJunctionsProjectAmountForm(userIds, status);
       if(junctionsProjectAmountForms==null||junctionsProjectAmountForms.size()<1){
           logger.debug("get getReportJunctionsProjectAmountForm data success[{empty}]");
           return new ArrayList<>();
       }
        logger.debug("get getReportJunctionsProjectAmountForm data success[{}]by userId [{}]and status [{}]",junctionsProjectAmountForms,userIds,status);
        return junctionsProjectAmountForms;
    }
}
