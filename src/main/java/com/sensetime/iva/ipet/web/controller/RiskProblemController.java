package com.sensetime.iva.ipet.web.controller;

import com.sensetime.iva.ipet.common.Code;
import com.sensetime.iva.ipet.common.IConstant;
import com.sensetime.iva.ipet.common.PredefineResource;
import com.sensetime.iva.ipet.entity.Project;
import com.sensetime.iva.ipet.entity.RiskProblemEntity;
import com.sensetime.iva.ipet.entity.enumeration.RequestMethod;
import com.sensetime.iva.ipet.entity.enumeration.ResourceType;
import com.sensetime.iva.ipet.service.ProjectService;
import com.sensetime.iva.ipet.service.RiskProblemService;
import com.sensetime.iva.ipet.util.ReturnUtil;
import com.sensetime.iva.ipet.vo.form.RiskProblemForm;
import com.sensetime.iva.ipet.vo.form.RiskProblemListForm;
import com.sensetime.iva.ipet.vo.response.ResponseBody;
import com.sensetime.iva.ipet.web.annotation.MyResources;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : ChaiLongLong
 * @date : 2018/8/9 9:40
 */
@Api(description  = "项目计划--风险与问题（初始化、提交）")
@RestController
public class RiskProblemController {

    private static final Logger logger = LoggerFactory.getLogger(RiskProblemController.class);

    @Autowired
    RiskProblemService riskProblemService;
    @Autowired
    ProjectService projectService;

    @MyResources(name ="风险问题数据初始化",code="init_risk_problem_data",type=ResourceType.URL,path="/riskProblem",parent = PredefineResource.PROJECT_PLAN_NODE_CODE,seq = 10,method = RequestMethod.GET,role = IConstant.ROLE_PM+","+IConstant.ROLE_MANAGER)
    @GetMapping(value = { "/riskProblem" },produces="application/json;charset=UTF-8")
    @ApiOperation(value="风险问题数据初始化", notes="根据项目id获取风险问题数据")
    public ResponseBody<List<RiskProblemForm>> initRiskProblemData(@RequestParam @ApiParam(name="projectId",value="项目id",required=true) Integer projectId){
        logger.info("initRiskProblemData param  projectId "+ projectId);
        Project project = projectService.selectByPrimaryKey(projectId);
        if(project == null){
            logger.error("initRiskProblemData error 项目不存在");
            return ReturnUtil.error(Code.PARAM_ERROR,"项目不存在");
        }
        List<RiskProblemForm> riskProblemForms = riskProblemService.initRiskProblemData(projectId);
        return ReturnUtil.success("风险问题数据获取成功",riskProblemForms);
    }

    @MyResources(name ="提交风险与问题数据",code="submit_risk_problem_data",type=ResourceType.URL,path="/riskProblem",parent = PredefineResource.PROJECT_PLAN_NODE_CODE,seq = 11,method = RequestMethod.POST,role = IConstant.ROLE_PM)
    @PostMapping(value = { "/riskProblem" },produces="application/json;charset=UTF-8")
    @ApiOperation(value="提交风险与问题数据", notes="提交项目所属风险与问题数据")
    @ApiImplicitParam(name = "riskProblemListForm", value = "风险问题数据riskProblemForms", required = true, dataType = "RiskProblemListForm")
    public ResponseBody<RiskProblemListForm> submitRiskProblemData(@RequestBody RiskProblemListForm riskProblemListForm) throws Exception{
        logger.info("submitRiskProblemData param  riskProblemForms "+ riskProblemListForm.toString());
        if(riskProblemListForm != null && riskProblemListForm.getRiskProblemForms() != null  && riskProblemListForm.getRiskProblemForms().size() >0){
            riskProblemService.submitRiskProblemData(riskProblemListForm);
        }
        return ReturnUtil.success("提交风险与问题数据成功",riskProblemListForm);
    }

    @MyResources(name ="删除风险与问题",code="delete_risk_problem_data",type=ResourceType.URL,path="/riskProblem/{id}",parent = PredefineResource.PROJECT_PLAN_NODE_CODE,seq = 12,method = RequestMethod.DELETE,role = IConstant.ROLE_PM)
    @DeleteMapping(value = { "/riskProblem/{id}" },produces="application/json;charset=UTF-8")
    @ApiOperation(value="删除风险与问题", notes="删除风险与问题")
    public ResponseBody deleteRiskProblemData(@ApiParam(name="id",value="风险与问题id",required=true) @PathVariable("id") Integer id){
        logger.info("deleteRiskProblemData param  id "+ id);
        RiskProblemEntity riskProblemEntity = riskProblemService.queryById(id);
        if(riskProblemEntity == null){
            logger.error("riskProblemEntity id "+id +" 不存在");
            return ReturnUtil.error(Code.PARAM_ERROR,"数据不存在");
        }
        riskProblemService.deleteById(id);
        return ReturnUtil.success("删除风险与问题数据成功",null);
    }
}
