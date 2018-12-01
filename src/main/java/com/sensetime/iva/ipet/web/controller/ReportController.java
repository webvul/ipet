package com.sensetime.iva.ipet.web.controller;

import com.sensetime.iva.ipet.common.*;
import com.sensetime.iva.ipet.entity.enumeration.RequestMethod;
import com.sensetime.iva.ipet.entity.enumeration.ResourceType;
import com.sensetime.iva.ipet.service.ReportService;
import com.sensetime.iva.ipet.util.ReturnUtil;
import com.sensetime.iva.ipet.vo.form.*;
import com.sensetime.iva.ipet.vo.response.ResponseBody;
import com.sensetime.iva.ipet.web.annotation.MyResources;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author gongchao
 * @date 9:28 2018/9/17
 */
@RestController
@Api(description  = "获取相关报表信息")
public class ReportController {
    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

    @Autowired
    private ReportService reportService;

    @MyResources(name ="获取PM或者管理员首页信息",code="get_index_report",type=ResourceType.URL,path="/report/index",parent = PredefineResource.REPORT_INDEX_NODE_CODE,seq = 1,method = RequestMethod.POST,role = IConstant.ROLE_PM+","+IConstant.ROLE_MANAGER)
    @PostMapping(value = "/report/index",produces="application/json;charset=UTF-8")
    @ApiOperation(value="获取PM或者管理员首页信息",notes="获取PM或者管理员首页信息")
    public ResponseBody<ReportIndexForm> getIndexReport(@ApiParam(name="isPm",value="是否是PM")@RequestParam("isPm") Boolean isPm)  {
        logger.info("get getIndexReport isPM [{}]",isPm);
        ReportIndexForm reportIndexForm;
        try {
            reportIndexForm = reportService.getReportIndexForm(isPm);
        }catch (Exception e){
            logger.error("getIndexReport error {}",e.getMessage());
            return ReturnUtil.error(Code.ERROR, e.getMessage());
        }
        return ReturnUtil.success(ReturnMsg.SUCCESS,reportIndexForm);
    }

    @MyResources(name ="获取PM看板信息",code="get_pm_board_report",type=ResourceType.URL,path="/report/pmBoard",parent = PredefineResource.REPORT_PM_BOARD_CODE,seq = 2,method = RequestMethod.GET,role = IConstant.ROLE_PM)
    @GetMapping(value = "/report/pmBoard",produces="application/json;charset=UTF-8")
    @ApiOperation(value="获取PM看板信息",notes="获取PM看板信息")
    public ResponseBody<ReportPmBoardForm> getPmBoardReport()  {
        logger.info("get getPmBoardReport");
        ReportPmBoardForm reportPmBoardForm;
        try {
             reportPmBoardForm = reportService.getReportPmBoardForm();
        }catch (Exception e){
            logger.error("getPmBoardReport error {}",e.getMessage());
            return ReturnUtil.error(Code.ERROR, e.getMessage());
        }
        return ReturnUtil.success(ReturnMsg.SUCCESS,reportPmBoardForm);
    }

    @MyResources(name ="获取管理员看板信息",code="get_admin_board_report",type=ResourceType.URL,path="/report/adminBoard",parent = PredefineResource.REPORT_ADMIN_BOARD_CODE,seq = 3,method = RequestMethod.GET,role = IConstant.ROLE_MANAGER)
    @GetMapping(value = "/report/adminBoard",produces="application/json;charset=UTF-8")
    @ApiOperation(value="获取管理员看板信息",notes="获取管理员看板信息")
    public ResponseBody<ReportAdminBoardForm> getAdminBoard()  {
        logger.info("get getReportAdminBoardForm");
        ReportAdminBoardForm reportAdminBoardForm;
        try {
             reportAdminBoardForm = reportService.getReportAdminBoardForm();
        }catch (Exception e){
            logger.error("getReportAdminBoardForm error {}",e.getMessage());
            return ReturnUtil.error(Code.ERROR, e.getMessage());
        }
        return ReturnUtil.success(ReturnMsg.SUCCESS,reportAdminBoardForm);
    }

    @MyResources(name ="获取延期项目风险统计数据",code="get_risk_report",type=ResourceType.URL,path="/report/risk",parent = PredefineResource.REPORT_RISK_CODE,seq = 4,method = RequestMethod.POST,role = IConstant.ROLE_PM+","+IConstant.ROLE_MANAGER)
    @PostMapping(value = "/report/risk",produces="application/json;charset=UTF-8")
    @ApiOperation(value="获取延期项目风险统计数据",notes="获取延期项目风险统计数据")
    public ResponseBody<List<ReportRiskAndHelpForm>> getRiskAndHelp(@ApiParam(name="isPm",value="是否是PM")@RequestParam("isPm") Boolean isPm)  {
        logger.info("get ReportRiskAndHelpForm by isPm [{}]",isPm);
        List<ReportRiskAndHelpForm>  reportRiskAndHelpForm;
        try {
             reportRiskAndHelpForm = reportService.getReportRiskAndHelpForm(ProjectArgs.PROJECT_STATUS_DELAY_NUM,isPm);
       logger.info("getRiskAndHelp success [{}]",reportRiskAndHelpForm);
        }catch (Exception e){
            logger.error("ReportRiskAndHelpForm error {}",e.getMessage());
            return ReturnUtil.error(Code.ERROR, e.getMessage());
        }
        return ReturnUtil.success(ReturnMsg.SUCCESS,reportRiskAndHelpForm);
    }

    @MyResources(name ="获取结项看板信息",code="get_junctions_report",type=ResourceType.URL,path="/report/junctions",parent = PredefineResource.REPORT_JUNCTIONS_CODE,seq = 5,method = RequestMethod.POST,role = IConstant.ROLE_PM+","+IConstant.ROLE_MANAGER)
    @PostMapping(value = "/report/junctions",produces="application/json;charset=UTF-8")
    @ApiOperation(value="获取结项看板信息",notes="获取结项看板信息")
    public ResponseBody<ReportJunctionsProjectForm> getJunctionsProjectForm(@ApiParam(name="isPm",value="是否是PM")@RequestParam("isPm") Boolean isPm)  {
        logger.info("get ReportJunctionsProjectForm");
        ReportJunctionsProjectForm  reportJunctionsProjectForm;
        try {
             reportJunctionsProjectForm = reportService.getReportJunctionsProjectForm(isPm);
        }catch (Exception e){
            logger.error("ReportJunctionsProjectForm error {}",e.getMessage());
            return ReturnUtil.error(Code.ERROR, e.getMessage());
        }
        return ReturnUtil.success(ReturnMsg.SUCCESS,reportJunctionsProjectForm);
    }
}
