package com.sensetime.iva.ipet.web.controller;

import com.sensetime.iva.ipet.common.*;
import com.sensetime.iva.ipet.config.LoadFileConfig;
import com.sensetime.iva.ipet.entity.File;
import com.sensetime.iva.ipet.entity.Project;
import com.sensetime.iva.ipet.entity.ProjectPlan;
import com.sensetime.iva.ipet.entity.enumeration.RequestMethod;
import com.sensetime.iva.ipet.entity.enumeration.ResourceType;
import com.sensetime.iva.ipet.service.*;
import com.sensetime.iva.ipet.util.ExcelImportUtil;
import com.sensetime.iva.ipet.util.ReturnUtil;
import com.sensetime.iva.ipet.vo.form.ProjectPlanForm;
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
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;

/**
 * @author  gongchao
 */
@RestController
@Api(description  = "项目计划(周报)（增、删、改、查、上传文件）")
public class ProjectPlanController {
    private static final Logger logger = LoggerFactory.getLogger(ProjectPlanController.class);
    @Autowired
    ProjectPlanService projectPlanService;
    @Autowired
    ProjectService projectService;
    @Autowired
    LoadFileConfig loadFileConfig;
    @Autowired
    StageService stageService;
    @Autowired
    FileService fileService;
    @Autowired
    ProjectPlanFormService projectPlanFormService;
    @Autowired
    ServerProperties serverProperties;
    @Autowired
    WeeklyBoardService weeklyBoardService;

    @MyResources(name ="获取项目计划(周报)列表",code="get_project_plan",type=ResourceType.URL,path="/projectPlan/{projectId}",parent = PredefineResource.PROJECT_PLAN_NODE_CODE,seq = 1,method = RequestMethod.GET,role = IConstant.ROLE_PM+","+IConstant.ROLE_MANAGER)
    @GetMapping(value = "/projectPlan/{projectId}",produces="application/json;charset=UTF-8")
    @ApiOperation(value="获取项目计划(周报)列表", notes="获取项目计划(周报)列表")
    public ResponseBody<ProjectPlanForm> getProjectPlan(@ApiParam(name="projectId",value="项目id",required=true) @PathVariable  Integer projectId) throws Exception{
        logger.info("[start]getProjectPlan accept projectId {}",projectId);
        ProjectPlanForm projectPlan = projectPlanFormService.getByProjectId(projectId);
        return ReturnUtil.success(ReturnMsg.PROJECT_PLAN_LIST,projectPlan);
    }

    @MyResources(name ="新增或更新项目周报",code="add_project_plan",type=ResourceType.URL,path="/projectPlan",parent = PredefineResource.PROJECT_PLAN_NODE_CODE,seq = 2,method = RequestMethod.POST,role = IConstant.ROLE_PM)
    @PostMapping(value="/projectPlan",produces="application/json;charset=UTF-8")
    @ApiOperation(value="新增或更新项目周报", notes="新增或更新项目周报")
    @ApiImplicitParam(name = "projectPlanForm", value = "项目周报projectPlanVo", required = true, dataType = "ProjectPlanForm")
        public ResponseBody<ProjectPlanForm> addProjectPlan(@RequestBody ProjectPlanForm projectPlanForm){
        logger.info("[start]addProjectPlan accept projectPlanForm {}",projectPlanForm);
        Project project = projectPlanForm.getProject();
        if(project==null||project.getId()==null){
            logger.error("[end]project not exist  error: {}",ExceptionMsg.EMPTY_PROJECT);
            return ReturnUtil.error(Code.ERROR,ReturnMsg.CREATE_PROJECT_PLAN_FAIL);
        }
        Project exist = projectService.selectByPrimaryKey(project.getId());
        if(exist==null){
            logger.error("[end]projectId not exist  error: {}",ExceptionMsg.EMPTY_PROJECT);
            return ReturnUtil.error(Code.ERROR,ReturnMsg.CREATE_PROJECT_PLAN_FAIL);
        }
        projectPlanFormService.createOrUpdate(projectPlanForm);
        return ReturnUtil.success(ReturnMsg.CREATE_PROJECT_PLAN_SUCCESS,projectPlanForm);
    }

    @DeleteMapping(value = { "/projectPlan/{id}" },produces="application/json;charset=UTF-8")
    @ApiOperation(value="删除项目周报", notes="删除项目周报")
    @ApiParam(name="id",value="项目周报id",required = true)
    public ResponseBody deleteProjectPlan(@PathVariable("id") Integer id){
        logger.info("[start]deleteProjectPlan accept id {}",id);
        ProjectPlan projectPlan = projectPlanService.selectByPrimaryKey(id);
        if(projectPlan == null){
            logger.error("[end]projectPlan not exist  error: {}",ReturnMsg.PROJECT_PLAN_NOT_EXIST);
            return ReturnUtil.error(Code.PARAM_ERROR,ReturnMsg.PROJECT_PLAN_NOT_EXIST);
        }
        projectPlanService.deleteByPrimaryKey(id);
        return ReturnUtil.success(ReturnMsg.DELETE_PROJECT_PLAN_SUCCESS);
    }

    @MyResources(name ="删除项目计划详细信息",code="delete_weeklyBoard_list",type=ResourceType.URL,path="/weeklyBoard/{id}",parent = PredefineResource.PROJECT_PLAN_NODE_CODE,seq = 16,method = RequestMethod.DELETE,role = IConstant.ROLE_PM)
    @DeleteMapping(value = { "/weeklyBoard/{id}" },produces="application/json;charset=UTF-8")
    @ApiOperation(value="删除项目计划详细信息", notes="删除项目计划详细信息")
    public ResponseBody deleteWeeklyBoardList(@ApiParam(name="id",value="详细信息id",required=true) @PathVariable("id") Integer id){
        logger.info("[start] deleteWeeklyBoardList id {} ", id);
        weeklyBoardService.deleteByPrimaryKey(id);
        return ReturnUtil.success(ReturnMsg.DELETE_WEEKLY_BOARD);
    }
    @MyResources(name ="导入项目计划文件",code="import_project_plan",type=ResourceType.URL,path="/projectPlan/doc",parent = PredefineResource.PROJECT_PLAN_NODE_CODE,seq = 3,method = RequestMethod.POST,role = IConstant.ROLE_PM)
    @PostMapping("/projectPlan/doc")
    @ApiOperation(value="获取项目计划上传的文件")
    public ResponseBody<List<File>> importProjectPlan(@ApiParam(name="file",value="项目计划文件",required=true) @RequestParam("file") MultipartFile file,@ApiParam(name="projectId",value="项目id",required=true) @RequestParam Integer projectId) throws Exception{
        logger.info("[start]importProjectPlan accept filename {}",file.getOriginalFilename());
        File afterFile;
        List<File> list;
        //验证文件大小
        long size = file.getSize();
        ExcelImportUtil.volidateFileSize(size,CellNum.MAX_LOAD);
        //判断累计上传文件数量
        int counts = fileService.selectCountByProjectId(projectId);
        if(counts>=ProjectArgs.PROJECT_PLAN_MAX_COUNT){
            logger.error("[end]over load amount  error: {}","累计上传数量已经最大,数量为:"+counts);
            return ReturnUtil.error(Code.ERROR, ExceptionMsg.LOAD_OVER_MAX);

        }
        //验证项目是否存在
        Project project = projectService.selectByPrimaryKey(projectId);
        if(project == null){
            logger.error("[end]project not exist  error: {}",ReturnMsg.DELETE_PROJECT_PLAN_SUCCESS);
            return ReturnUtil.error(Code.ERROR,ReturnMsg.DELETE_PROJECT_PLAN_SUCCESS);
        }
        //上传文件
        afterFile = ExcelImportUtil.uploadFile(file, loadFileConfig.getProjectPlanLocation());
        afterFile.setProjectId(project.getId());
        fileService.insert(afterFile);
        list = fileService.selectByProjectId(project.getId());
        return ReturnUtil.success(ReturnMsg.SUCCESS,list);
    }

    @GetMapping(value = { "/projectPlan/export/{projectId}" },produces="application/json;charset=UTF-8")
    @MyResources(name ="下载项目计划文件",code="export_project_plan",type=ResourceType.URL,path="/projectPlan/export/{projectId}",parent = PredefineResource.PROJECT_PLAN_NODE_CODE,seq = 4,method = RequestMethod.GET,role = IConstant.ROLE_PM+","+IConstant.ROLE_MANAGER)
    @ApiOperation(value="下载项目计划文件", notes="下载项目计划文件")
    @ApiParam(name="projectId",value="项目id",required=true)
    public ResponseBody exportProjectPlan(HttpServletResponse response, HttpServletRequest request, @PathVariable("projectId") Integer projectId)throws  Exception{
        OutputStream os =null;
        try{
            Project project = projectService.selectByPrimaryKey(projectId);
            if(project==null){
                logger.error("[end]file not exist error {}",ExceptionMsg.EMPTY_PROJECT);
                return ReturnUtil.error(Code.ERROR,ExceptionMsg.EMPTY_PROJECT);
            }
            // 取得输出流
            os= response.getOutputStream();
            //初始化excel
            HSSFWorkbook wb = ExcelImportUtil.createExportWorkbook(ExcelTitleToClass.EXPORT_PROJECT_PLAN, response,request);
            projectPlanService.exportProjectPlan(wb,project);
            wb.write(os);
        }catch (Exception e ){
            logger.error("[end] project export error {}",e.getMessage());
            throw new BusinessException("下载项目计划文件失败");
        }finally {
            if(os!=null){
                os.flush();
                os.close();
            }
        }
        return ReturnUtil.error(Code.SUCCESS,"导出成功");
    }
}
