package com.sensetime.iva.ipet.web.controller;

import com.sensetime.iva.ipet.common.*;
import com.sensetime.iva.ipet.entity.Project;
import com.sensetime.iva.ipet.entity.SurveyList;
import com.sensetime.iva.ipet.entity.enumeration.RequestMethod;
import com.sensetime.iva.ipet.entity.enumeration.ResourceType;
import com.sensetime.iva.ipet.service.ProjectService;
import com.sensetime.iva.ipet.service.SurveyListService;
import com.sensetime.iva.ipet.util.ReturnUtil;
import com.sensetime.iva.ipet.vo.form.SurveyListForm;
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
 * @author  gongchao
 */
@Api(description  = "工勘清单（增、删、查）")
@RestController
public class SurveyListController {
    private static final Logger logger = LoggerFactory.getLogger(SurveyListController.class);
    @Autowired
    SurveyListService surveyListService;
    @Autowired
    ProjectService projectService;

    @MyResources(name ="获取工勘清单列表",code="get_survey_list",type=ResourceType.URL,path="/surveyList/{projectId}",parent = PredefineResource.PROJECT_ACCEPTANCE_NODE_CODE,seq = 6,method = RequestMethod.GET,role = IConstant.ROLE_PM+","+IConstant.ROLE_MANAGER)
    @GetMapping(value = { "/surveyList/{projectId}" },produces="application/json;charset=UTF-8")
    @ApiOperation(value="获取工勘清单列表", notes="获取工勘清单列表")
    public ResponseBody<SurveyListForm> getSurveyList(@ApiParam(name="projectId",value="项目id",required=true) @PathVariable Integer projectId){
        logger.info("[start]getSurveyList by  projectId {}",projectId);
        List<SurveyList> surveyLists = surveyListService.selectByProjectId(projectId);
        SurveyListForm surveyListForm = new SurveyListForm();
        surveyListForm.setSurveyLists(surveyLists);
        return ReturnUtil.success(ReturnMsg.SUCCESS,surveyListForm);
    }

    @MyResources(name ="新增工勘清单",code="add_survey_list",type=ResourceType.URL,path="/surveyList",parent = PredefineResource.PROJECT_ACCEPTANCE_NODE_CODE,seq = 7,method = RequestMethod.POST,role = IConstant.ROLE_PM)
    @PostMapping(value = { "/surveyList" },produces="application/json;charset=UTF-8")
    @ApiOperation(value="新增或更新工勘清单", notes="新增或更新工勘清单")
    @ApiImplicitParam(name = "surveyListForm", value = "工勘清单信息", required = true, dataType = "SurveyListForm")
    public ResponseBody addSurveyList(@RequestBody SurveyListForm surveyListForm){
        List<SurveyList> surveyList = surveyListForm.getSurveyLists();
        logger.info("[start]addSurveyList surveyList  {}",surveyList);
            if(surveyList!=null&&surveyList.size()>0) {
                for (SurveyList su : surveyList) {
                    if(su.getProjectId()==null){
                        logger.error("[end] 所在项目id不能为空");
                        return ReturnUtil.error(Code.PARAM_ERROR,ExceptionMsg.EMPTY_PROJECT);
                    }
                    Project project = projectService.selectByPrimaryKey(su.getProjectId());
                    if(project == null){
                        logger.error("[end] 所在项目不能为空");
                        return ReturnUtil.error(Code.ERROR, ExceptionMsg.EMPTY_PROJECT);
                    }
                    if(su.getId()==null){
                        surveyListService.insert(su);
                        logger.info("addSurveyList success SurveyList {}",su);
                    }else{
                        SurveyList su1 = surveyListService.selectByPrimaryKey(su.getId());
                        if(su1==null){
                            logger.error("[end]addSurveyList error {}",ReturnMsg.SURVEY_LIST_EMPTY);
                            return ReturnUtil.error(Code.ERROR,ReturnMsg.SURVEY_LIST_EMPTY);
                        }else{
                            surveyListService.updateByPrimaryKey(su);
                            logger.info("updateSurveyList success SurveyList {}",su);
                        }
                    }
                 }
                 return ReturnUtil.success(ReturnMsg.ADD_OR_UPDATE_SURVEY_LIST);
            }else{
              logger.error("[end] addDeliverList error {}",ReturnMsg.SURVEY_LIST_EMPTY);
               return ReturnUtil.error(Code.ERROR,ReturnMsg.ADD_OR_UPDATE_SURVEY_LIST_FAIL,ReturnMsg.SURVEY_LIST_EMPTY);
            }
    }

    @MyResources(name ="删除工勘清单信息",code="delete_survey_list",type=ResourceType.URL,path="/surveyList/{id}",parent = PredefineResource.PROJECT_ACCEPTANCE_NODE_CODE,seq = 8,method = RequestMethod.DELETE,role = IConstant.ROLE_PM)
    @DeleteMapping(value = { "/surveyList/{id}" },produces="application/json;charset=UTF-8")
    @ApiOperation(value="删除工勘清单信息", notes="删除工勘清单信息")
    public ResponseBody deleteSurveyList(@ApiParam(name="id",value="工勘清单id",required=true) @PathVariable("id") Integer id){
        logger.info("[start]deleteSurveyList id {} ", id);
        surveyListService.deleteByPrimaryKey(id);
        return ReturnUtil.success(ReturnMsg.DELETE_SURVEY_LIST);
    }
}
