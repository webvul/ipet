package com.sensetime.iva.ipet.web.controller;

import com.sensetime.iva.ipet.common.Code;
import com.sensetime.iva.ipet.common.IConstant;
import com.sensetime.iva.ipet.common.PredefineResource;
import com.sensetime.iva.ipet.entity.BusinessTripEntity;
import com.sensetime.iva.ipet.entity.EquipmentEntity;
import com.sensetime.iva.ipet.entity.Project;
import com.sensetime.iva.ipet.entity.WorkTimeEntity;
import com.sensetime.iva.ipet.entity.enumeration.RequestMethod;
import com.sensetime.iva.ipet.entity.enumeration.ResourceType;
import com.sensetime.iva.ipet.service.*;
import com.sensetime.iva.ipet.util.ReturnUtil;
import com.sensetime.iva.ipet.vo.form.CostStatisticsForm;
import com.sensetime.iva.ipet.vo.response.ResponseBody;
import com.sensetime.iva.ipet.web.annotation.MyResources;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javassist.tools.rmi.ObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author : ChaiLongLong
 * @date : 2018/8/7 15:18
 */
@Api(description  = "成本统计（初始化、提交）")
@RestController
public class CostStatisticsController {

    private static final Logger logger = LoggerFactory.getLogger(CostStatisticsController.class);

    @Autowired
    CostStatisticsService costStatisticsService;
    @Autowired
    ProjectService projectService;
    @Autowired
    WorkTimeService workTimeService;
    @Autowired
    BusinessTripService businessTripService;
    @Autowired
    EquipmentService equipmentService;

    @MyResources(name ="成本统计数据初始化",code="init_cost_statistics_data",type=ResourceType.URL,path="/costStatistics",parent = PredefineResource.PROJECT_PLAN_NODE_CODE,seq = 5,method = RequestMethod.GET,role = IConstant.ROLE_PM+","+IConstant.ROLE_MANAGER)
    @GetMapping(value = { "/costStatistics" },produces="application/json;charset=UTF-8")
    @ApiOperation(value="成本统计数据初始化", notes="根据项目id获取工时、出差、设备数据")
    public ResponseBody<CostStatisticsForm> initCostStatisticsData(@RequestParam @ApiParam(name="projectId",value="项目id",required=true) Integer projectId) throws Exception{
        logger.info("initCostStatisticsData param  projectId "+ projectId);
        Project project = projectService.selectByPrimaryKey(projectId);
        if(project == null ){
            logger.error("项目不存在");
            return ReturnUtil.error(Code.PARAM_ERROR,"项目不存在");
        }
        CostStatisticsForm costStatisticsForm = costStatisticsService.initCostStatisticsData(projectId);
        return ReturnUtil.success("成本统计数据获取成功",costStatisticsForm);
    }

    @MyResources(name ="提交成本统计数据",code="submit_cost_statistics_data",type=ResourceType.URL,path="/costStatistics",parent = PredefineResource.PROJECT_PLAN_NODE_CODE,seq = 6,method = RequestMethod.POST,role = IConstant.ROLE_PM)
    @PostMapping(value = { "/costStatistics" },produces="application/json;charset=UTF-8")
    @ApiOperation(value="提交成本统计数据", notes="提交项目所属工时、出差、设备数据")
    @ApiImplicitParam(name = "costStatisticsForm", value = "项目所属工时、出差、设备数据costStatisticsForm", required = true, dataType = "CostStatisticsForm")
    public ResponseBody submitCostStatisticsData(@RequestBody CostStatisticsForm costStatisticsForm) throws Exception{
        logger.info("submitCostStatisticsData param  costStatisticsForm "+ costStatisticsForm.toString());
        costStatisticsService.submitCostStatisticsData(costStatisticsForm);
        return ReturnUtil.success("提交成本统计数据成功",null);
    }

    @MyResources(name ="删除工时",code="delete_work_time",type=ResourceType.URL,path="/costStatistics/workTime/{id}",parent = PredefineResource.PROJECT_PLAN_NODE_CODE,seq = 7,method = RequestMethod.DELETE,role = IConstant.ROLE_PM)
    @DeleteMapping(value = { "/costStatistics/workTime/{id}" },produces="application/json;charset=UTF-8")
    @ApiOperation(value="删除工时", notes="删除工时")
    public ResponseBody deleteWorkTime(@ApiParam(name="id",value="工时id",required=true) @PathVariable("id") Integer id){
        logger.info("deleteWorkTime param  id "+ id);
        WorkTimeEntity workTimeEntity = workTimeService.queryById(id);
        if ( workTimeEntity == null){
            logger.error("deleteWorkTime id "+id+" 数据不存在");
            return ReturnUtil.error(Code.ERROR,"数据不存在");
        }
        workTimeService.deleteById(id);
        return ReturnUtil.success("删除工时成功",null);
    }

    @MyResources(name ="删除出差",code="delete_business_trip",type=ResourceType.URL,path="/costStatistics/trip/{id}",parent = PredefineResource.PROJECT_PLAN_NODE_CODE,seq = 8,method = RequestMethod.DELETE,role = IConstant.ROLE_PM)
    @DeleteMapping(value = { "/costStatistics/trip/{id}" },produces="application/json;charset=UTF-8")
    @ApiOperation(value="删除出差", notes="删除出差")
    public ResponseBody deleteBusinessTrip(@ApiParam(name="id",value="出差id",required=true) @PathVariable("id") Integer id){
        logger.info("deleteBusinessTrip param  id "+ id);
        BusinessTripEntity businessTripEntity = businessTripService.queryById(id);
        if ( businessTripEntity == null){
            logger.error("deleteBusinessTrip id "+id+" 数据不存在");
            return ReturnUtil.error(Code.ERROR,"数据不存在");
        }
        businessTripService.deleteById(id);
        return ReturnUtil.success("删除出差成功",null);
    }

    @MyResources(name ="删除设备",code="delete_equipment",type=ResourceType.URL,path="/costStatistics/equipment/{id}",parent = PredefineResource.PROJECT_PLAN_NODE_CODE,seq = 9,method = RequestMethod.DELETE,role = IConstant.ROLE_PM)
    @DeleteMapping(value = { "/costStatistics/equipment/{id}" },produces="application/json;charset=UTF-8")
    @ApiOperation(value="删除设备", notes="删除设备")
    public ResponseBody deleteEquipment(@ApiParam(name="id",value="设备id",required=true) @PathVariable("id") Integer id){
        logger.info("deleteEquipment param  id "+ id);
        EquipmentEntity equipmentEntity = equipmentService.queryById(id);
        if ( equipmentEntity == null){
            logger.error("deleteEquipment id "+id+" 数据不存在");
            return ReturnUtil.error(Code.ERROR,"数据不存在");
        }
        equipmentService.deleteById(id);
        return ReturnUtil.success("删除设备成功",null);
    }

}
