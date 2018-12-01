package com.sensetime.iva.ipet.web.controller;

import com.sensetime.iva.ipet.common.*;
import com.sensetime.iva.ipet.entity.Project;
import com.sensetime.iva.ipet.entity.ProjectConclusionEntity;
import com.sensetime.iva.ipet.entity.enumeration.RequestMethod;
import com.sensetime.iva.ipet.entity.enumeration.ResourceType;
import com.sensetime.iva.ipet.service.ProjectConclusionService;
import com.sensetime.iva.ipet.service.ProjectService;
import com.sensetime.iva.ipet.util.ExcelImportUtil;
import com.sensetime.iva.ipet.util.ReturnUtil;
import com.sensetime.iva.ipet.vo.form.ProjectConclusionApprovalForm;
import com.sensetime.iva.ipet.vo.form.ProjectConclusionDataForm;
import com.sensetime.iva.ipet.vo.response.ResponseBody;
import com.sensetime.iva.ipet.web.annotation.MyResources;
import com.sensetime.iva.ipet.web.exception.BusinessException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;


/**
 * @author : ChaiLongLong
 * @date : 2018/8/13 15:09
 */
@Api(description  = "项目结项（初始化、提交、发起结项、审批结项、导出）")
@RestController
public class ProjectConclusionController {

    private static final Logger logger = LoggerFactory.getLogger(ProjectConclusionController.class);

    @Autowired
    ProjectConclusionService projectConclusionService;
    @Autowired
    ProjectService projectService;

    @MyResources(name ="项目结项数据初始化",code="init_project_conclusion_data",type=ResourceType.URL,path="/projectConclusion",parent = PredefineResource.PROJECT_CONCLUSION_NODE_CODE,seq = 1,method = RequestMethod.GET,role = IConstant.ROLE_PM+","+IConstant.ROLE_MANAGER)
    @GetMapping(value = { "/projectConclusion" },produces="application/json;charset=UTF-8")
    @ApiOperation(value="项目结项数据初始化", notes="根据项目id获取项目结项数据")
    public ResponseBody<ProjectConclusionDataForm> initProjectConclusionData(@RequestParam @ApiParam(name="projectId",value="项目id",required=true) Integer projectId){
        logger.info("initProjectConclusionData param  projectId "+ projectId);
        Project project = projectService.selectByPrimaryKey(projectId);
        if(project == null){
            logger.error("initProjectConclusionData  项目不存在 ");
            return ReturnUtil.error(Code.PARAM_ERROR,"项目不存在");
        }
        ProjectConclusionDataForm projectConclusionDataForm = projectConclusionService.initProjectConclusionData(projectId);
        return ReturnUtil.success("项目结项数据获取成功",projectConclusionDataForm);
    }

    @MyResources(name ="提交项目结项数据",code="submit_project_conclusion_data",type=ResourceType.URL,path="/projectConclusion",
            parent = PredefineResource.PROJECT_CONCLUSION_NODE_CODE,seq = 2,method = RequestMethod.POST,role = IConstant.ROLE_PM)
    @PostMapping(value = { "/projectConclusion" },produces="application/json;charset=UTF-8")
    @ApiOperation(value="提交项目结项数据", notes="提交项目结项数据")
    @ApiImplicitParam(name = "projectConclusionDataForm", value = "项目结项表单projectConclusionDataForm", required = true, dataType = "ProjectConclusionDataForm")
    public ResponseBody submitProjectConclusionData(@RequestBody ProjectConclusionDataForm projectConclusionDataForm) throws Exception{
        logger.info("submitProjectConclusionData param  projectConclusionDataForm "+ projectConclusionDataForm.toString());
        if(projectConclusionDataForm == null){
            return ReturnUtil.error(Code.ERROR,"对象不能为空");
        }
        projectConclusionService.submitProjectConclusionData(projectConclusionDataForm);
        return ReturnUtil.success("提交项目结项数据成功",null);
    }

    @MyResources(name ="发起结项",code="launch_conclusion",type=ResourceType.URL,path="/launchConclusion/{id}",parent = PredefineResource.PROJECT_CONCLUSION_NODE_CODE,seq = 3,method = RequestMethod.GET,role = IConstant.ROLE_PM)
    @GetMapping(value = { "/launchConclusion/{id}" },produces="application/json;charset=UTF-8")
    @ApiOperation(value="发起结项", notes="发起结项")
    @ApiImplicitParam(name = "id", value = "项目结项id", dataType = "Integer", paramType = "path")
    public ResponseBody<ProjectConclusionEntity> launchConclusion(@PathVariable("id") Integer id) throws Exception{
        logger.info("launchConclusion param  id "+ id);
        ProjectConclusionEntity projectConclusionEntity = projectConclusionService.queryById(id);
        if(projectConclusionEntity == null){
            logger.error("launchConclusion error 结项数据不存在");
            return ReturnUtil.error(Code.ERROR,"发起结项失败");
        }
        projectConclusionService.launchConclusion(id);
        return ReturnUtil.success("发起结项成功",projectConclusionEntity);
    }

    @MyResources(name ="审批结项",code=IConstant.PROJECT_CONCLUSION_APPROVAL_RESOURCE_CODE,type=ResourceType.URL,path="/approvalConclusion",parent = PredefineResource.PROJECT_CONCLUSION_NODE_CODE,seq = 4,method = RequestMethod.POST,role = IConstant.ROLE_MANAGER)
    @PostMapping(value = { "/approvalConclusion" },produces="application/json;charset=UTF-8")
    @ApiOperation(value="审批结项", notes="审批结项")
    @ApiImplicitParam(name = "projectConclusionApprovalForm", value = "审批结项projectConclusionApprovalForm", required = true, dataType = "ProjectConclusionApprovalForm")
    public ResponseBody approvalConclusion(@RequestBody ProjectConclusionApprovalForm projectConclusionApprovalForm) throws Exception{
        logger.info("approvalConclusion param  projectConclusionApprovalForm "+ projectConclusionApprovalForm.toString());
        if(projectConclusionApprovalForm == null){
            logger.error("对象不能为空");
            return ReturnUtil.error(Code.ERROR,"对象不能为空");
        }
        if (projectConclusionApprovalForm.getId() == 0 || projectConclusionService.queryById(projectConclusionApprovalForm.getId()) == null) {
            logger.error("项目结项不存在");
            return ReturnUtil.error(Code.ERROR,"项目结项不存在");
        }
        if(projectConclusionApprovalForm.getStatus() != ProjectArgs.ADOPT && projectConclusionApprovalForm.getStatus() != ProjectArgs.REPULSE ){
            logger.error("审批数据有误");
            return ReturnUtil.error(Code.ERROR,"审批数据有误");
        }
        projectConclusionService.approvalConclusion(projectConclusionApprovalForm);
        return ReturnUtil.success("审批结项成功",null);
    }

    @MyResources(name ="结项导出",code="export_project_conclusion",type=ResourceType.URL,path="/projectConclusion/{id}",parent = PredefineResource.PROJECT_CONCLUSION_NODE_CODE,seq = 5,method = RequestMethod.GET,role = IConstant.ROLE_PM+","+IConstant.ROLE_MANAGER)
    @GetMapping("/projectConclusion/{id}")
    @ApiOperation(value="结项导出",notes="结项导出")
    public void exportProjectConclusion(HttpServletResponse response, HttpServletRequest request, @ApiParam(name="id",value="结项id") @PathVariable Integer id) throws  Exception{
        logger.info(" exportProjectConclusion param "+id);
        OutputStream os =null;
        try{
            // 取得输出流
            os= response.getOutputStream();
            //初始化excel
            HSSFWorkbook wb = ExcelImportUtil.createExportWorkbook(ExcelTitleToClass.EXPORT_PROJECT_CONCLUSION, response,request);
            ProjectConclusionEntity projectConclusionEntity = projectConclusionService.queryById(id);
            projectConclusionService.export(wb,projectConclusionEntity);
            wb.write(os);
        }catch (Exception e ){
            logger.error(" exportProjectConclusion error："+e.getMessage());
            throw new BusinessException("结项导出出错");
        }finally {
            if(os!=null){
                os.flush();
                os.close();
            }
        }
    }


}
