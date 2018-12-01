package com.sensetime.iva.ipet.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sensetime.iva.ipet.common.*;
import com.sensetime.iva.ipet.entity.Project;
import com.sensetime.iva.ipet.entity.ProjectStage;
import com.sensetime.iva.ipet.entity.enumeration.RequestMethod;
import com.sensetime.iva.ipet.entity.enumeration.ResourceType;
import com.sensetime.iva.ipet.service.AreaService;
import com.sensetime.iva.ipet.service.ProjectService;
import com.sensetime.iva.ipet.service.ProjectStageService;
import com.sensetime.iva.ipet.util.ExcelImportUtil;
import com.sensetime.iva.ipet.util.ReturnUtil;
import com.sensetime.iva.ipet.vo.form.ProjectFilterForm;
import com.sensetime.iva.ipet.vo.form.ProjectForm;
import com.sensetime.iva.ipet.vo.form.ProjectInitFilterForm;
import com.sensetime.iva.ipet.vo.form.ProjectListForm;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;

/**
 * @author  gongchao
 */
@RestController
@Api(description  = "立项（增、删、改、导出、查、项目激活、获取上次导入或未创建的项目信息）")
public class ProjectController {
    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);
    @Autowired
    ProjectService projectService;
    @Autowired
    ProjectStageService projectStageService;
    @Autowired
    AreaService areaService;

    @MyResources(name ="获取项目列表",code="get_projects",type=ResourceType.URL,path="/project/list",parent = PredefineResource.PROJECT_LIST_NODE_CODE,seq = 3,method = RequestMethod.POST,role = IConstant.ROLE_PM+","+IConstant.ROLE_MANAGER)
    @PostMapping(value = "/project/list" ,produces="application/json;charset=UTF-8")
    @ApiOperation(value="获取项目列表", notes="throw new BusinessException")
    @ApiImplicitParam(name = "projectFilterForm", value = "项目过滤条件", dataType = "ProjectFilterForm")
    public ResponseBody<ProjectListForm> getProjects(@RequestBody ProjectFilterForm projectFilterForm){
        logger.info("[start]getProjects accept projectFilterForm {}",projectFilterForm);
        List<ProjectForm> projects=projectService.selectAll(projectFilterForm);
        ProjectListForm listForm = new ProjectListForm();
        if(projects!=null&&projects.size()>0){
            listForm.setProjectFormList(projects);
        }
        return ReturnUtil.success(ReturnMsg.PROJECT_LIST,listForm);
    }

    @MyResources(name ="获取上次导入的项目信息",code="get_init_project",type=ResourceType.URL,path="/project/init",parent = PredefineResource.PROJECT_ESTABLISHMENT_NODE_CODE,seq = 1,method = RequestMethod.GET,role = IConstant.ROLE_PM)
    @GetMapping("/project/init")
    @ApiOperation(value="获取上次导入的项目信息", notes="获取上次导入的项目信息")
    public ResponseBody<Project> getInitProject(){
        logger.info("[start]getInitProject");
        Project projects=projectService.init();
        return ReturnUtil.success(ReturnMsg.PROJECT_INIT,projects);
    }

    @MyResources(name ="上传立项文件",code="import_project",type=ResourceType.URL,path="/project/doc",parent = PredefineResource.PROJECT_ESTABLISHMENT_NODE_CODE,seq = 2,method = RequestMethod.POST,role = IConstant.ROLE_PM)
    @PostMapping(value = "/project/doc",produces="application/json;charset=UTF-8")
    @ApiOperation(value="上传立项文件",notes="小于10M")
    public ResponseBody<Project> importProject(@ApiParam(name="file",value="立项文件",required=true) @RequestParam("file") MultipartFile file) throws Exception{
        logger.info("[start]importProject accept fileName {}",file.getOriginalFilename());
        Project project;
        try {
            project = projectService.importProject(file);
        }catch (IndexOutOfBoundsException e) {
            logger.error("[end]analysis excel error {}### detail error {} ",ExceptionMsg.EXCEL_TYPE_ERROR,e.getMessage());
            return ReturnUtil.error(Code.ERROR, ExceptionMsg.EXCEL_TYPE_ERROR);
        }

        return ReturnUtil.success(ReturnMsg.SUCCESS,project);
    }

    @MyResources(name ="创建立项",code="create_project",type=ResourceType.URL,path="/project",parent = PredefineResource.PROJECT_ESTABLISHMENT_NODE_CODE,seq = 3,method = RequestMethod.POST,role = IConstant.ROLE_PM)
    @PostMapping(value = "/project",produces="application/json;charset=UTF-8")
    @ApiOperation(value="创建立项",notes="创建立项")
    @ApiImplicitParam(name = "project", value = "项目信息", required = true, dataType = "Project")
    public ResponseBody<Project> createProject(@RequestBody Project project) throws Exception {
        logger.info("[start]createProject accept project {}",project);
        if(project==null){
            logger.error("[end]project is empty error {}",ExceptionMsg.EMPTY_PROJECT);
            return ReturnUtil.error(Code.ERROR,ReturnMsg.CREATE_PROJECT_FAIL);
        }
        List<ProjectStage> projectStages = project.getProjectStages();
        if(projectStages!=null&&projectStages.size()>0){
            projectService.createProject(project,projectStages);
        }else{
            projectService.createProject(project,null);
        }
        logger.debug("[end]createProject success project {}",project);
        return ReturnUtil.success(ReturnMsg.CREATE_PROJECT_SUCCESS,project);
    }


    @MyResources(name ="更新已创建的项目信息",code="update_project_level",type=ResourceType.URL,path="/project/level",parent = PredefineResource.PROJECT_ESTABLISHMENT_NODE_CODE,seq = 4,method = RequestMethod.POST,role = IConstant.ROLE_PM)
    @PostMapping(value = "/project/level",produces="application/json;charset=UTF-8")
    @ApiOperation(value="更新已创建的项目信息",notes="更新已创建的项目信息")
    public ResponseBody<Project> updateProject(@ApiParam(name="id",value="项目id")@RequestParam("id") Integer id, @ApiParam(name="level",value="修改后的项目等级")@RequestParam("level") Integer level)  {
        logger.info("[start]updateProject accept id {}",id);
        Project project = projectService.selectByPrimaryKey(id);
        project.setLevel(level);
        projectService.updateByPrimaryKey(project);
        return ReturnUtil.success(ReturnMsg.UPDATE_PROJECT_SUCCESS,project);
    }

    @MyResources(name ="立项导出",code="export_project_establishment",type=ResourceType.URL,path="/project/export/{id}",parent = PredefineResource.PROJECT_ESTABLISHMENT_NODE_CODE,seq = 5,method = RequestMethod.GET,role = IConstant.ROLE_PM+","+IConstant.ROLE_MANAGER)
    @GetMapping(value = "/project/export/{id}",produces="application/json;charset=UTF-8")
    @ApiOperation(value="立项导出",notes="立项导出")
    public void export(HttpServletResponse response, HttpServletRequest request,@ApiParam(name="id",value="立项id") @PathVariable Integer id) throws  Exception{
        logger.info("[start]exportProject ");
        OutputStream os =null;
        try{
            // 取得输出流
            os= response.getOutputStream();
            //初始化excel
            HSSFWorkbook wb = ExcelImportUtil.createExportWorkbook(ExcelTitleToClass.EXPORT_PROJECT, response,request);
            Project project = projectService.selectByPrimaryKey(id);
            projectService.export(wb,project);
            wb.write(os);
        }catch (Exception e ){
            logger.error("[end] project export error {}",e.getMessage());
            throw new BusinessException("立项导出出错");
        }finally {
            if(os!=null){
                os.flush();
                os.close();
            }
        }
    }

    @DeleteMapping(value = "/project/{id}",produces="application/json;charset=UTF-8")
    @ApiOperation(value="立项删除",notes="立项导除")
    public ResponseBody delete(@ApiParam(name="id",value="立项id") @PathVariable Integer id) {
        logger.info("[start]deleteProject accept id {}",id);
        Project project = projectService.selectByPrimaryKey(id);
        projectService.delete(project);
        return ReturnUtil.success(ReturnMsg.SUCCESS);
    }

    @MyResources(name ="结项项目激活",code="active_project",type=ResourceType.URL,path="/project/active/{id}",parent = PredefineResource.PROJECT_LIST_NODE_CODE,seq = 1,method = RequestMethod.GET,role = IConstant.ROLE_MANAGER)
    @GetMapping(value = "/project/active/{id}",produces="application/json;charset=UTF-8")
    @ApiOperation(value="结项项目激活",notes="结项项目激活")
    public ResponseBody activeProject(@ApiParam(name="id",value="需要激活项目id",required = true) @PathVariable Integer id) throws Exception{
        logger.info("[start]activeProject accept id {}",id);
        projectService.activeProject(id);
        logger.debug("[end]activeProject success id {}",id);
        return ReturnUtil.success(ReturnMsg.ACTIVE_PROJECT_SUCCESS);
    }

    @MyResources(name ="获取所有过滤条件",code="get_filter",type=ResourceType.URL,path="/project/filter",parent = PredefineResource.PROJECT_LIST_NODE_CODE,seq = 2,method = RequestMethod.GET,role = IConstant.ROLE_PM+","+IConstant.ROLE_MANAGER)
    @GetMapping(value = "/project/filter",produces="application/json;charset=UTF-8")
    @ApiOperation(value="获取所有过滤条件",notes="获取所有过滤条件")
    public ResponseBody<ProjectInitFilterForm> getFilter(@ApiParam(name="isActive",value="是否是活跃项目的菜单",required = true) @RequestParam Boolean isActive,
                                  @ApiParam(name="isPm",value="是否是PM",required = true) @RequestParam Boolean isPm, @ApiParam(name="isArea",value="是否区域菜单",required = true) @RequestParam Boolean isArea) {
        logger.info("[start] project getFilter accept isActive [{}] ，isPmMyMenu[{}]",isActive,isPm);
        ProjectInitFilterForm initFilterForm;
        try{
             initFilterForm = projectService.getFilter(isActive,isPm,isArea);
        }catch (Exception e){
            logger.error("get initFilterForm error {}",e.getMessage());
            return ReturnUtil.error(Code.ERROR,e.getMessage());
        }
        return ReturnUtil.success(ReturnMsg.PROJECT_FILTER_SUCCESS,initFilterForm);
    }

    @MyResources(name ="保存项目信息",code="project_cache",type=ResourceType.URL,path="/project/cache",parent = PredefineResource.PROJECT_ESTABLISHMENT_NODE_CODE,seq = 6,method = RequestMethod.POST,role = IConstant.ROLE_PM)
    @PostMapping(value = "/project/cache",produces="application/json;charset=UTF-8")
    @ApiOperation(value="保存项目信息",notes="保存项目信息")
    @ApiImplicitParam(name = "project", value = "项目信息", required = true, dataType = "Project")
    public ResponseBody cacheProject(@RequestBody Project project)  {
        logger.info("[start]cacheProject accept project {}",project);
        projectService.cacheProject(project);
        logger.debug("cacheProject  success ");
        return ReturnUtil.success(ReturnMsg.CACHE_PROJECT_SUCCESS);
    }
    @MyResources(name ="清除项目缓存信息",code="project_cleanCache",type=ResourceType.URL,path="/project/cleanCache",parent = PredefineResource.PROJECT_ESTABLISHMENT_NODE_CODE,seq = 10,method = RequestMethod.DELETE,role = IConstant.ROLE_PM)
    @DeleteMapping(value = "/project/cleanCache",produces="application/json;charset=UTF-8")
    @ApiOperation(value="清除项目缓存信息",notes="清除项目缓存信息")
    public ResponseBody cleanCacheProject()  {
        logger.info("[start]cleanCacheProject");
        projectService.cleanCacheProject();
        logger.debug("cleanCacheProject  success ");
        return ReturnUtil.success(ReturnMsg.CLEAN_CACHE_PROJECT_SUCCESS);
    }

    @MyResources(name ="获取当前id项目明细信息",code="get_project",type=ResourceType.URL,path="/project/{id}",parent = PredefineResource.PROJECT_ESTABLISHMENT_NODE_CODE,seq = 7,method = RequestMethod.GET,role = IConstant.ROLE_PM+","+IConstant.ROLE_MANAGER)
    @GetMapping("/project/{id}")
    @ApiOperation(value="获取当前id项目明细信息", notes="获取当前id项目明细信息")
    public ResponseBody<Project> getProject(@ApiParam(name="id",value="当前id项目",required = true) @PathVariable Integer id){
        logger.info("[start]getProject accept id {}",id);
        Project project=projectService.selectByPrimaryKey(id);
        return ReturnUtil.success(ReturnMsg.SUCCESS,project);
    }

    @MyResources(name ="更新已创建的项目信息",code="update_project",type=ResourceType.URL,path="/project",parent = PredefineResource.PROJECT_ESTABLISHMENT_NODE_CODE,seq = 8,method = RequestMethod.PUT,role = IConstant.ROLE_PM)
    @PutMapping(value = ("/project"),produces="application/json;charset=UTF-8")
    @ApiOperation(value="更新已创建的项目信息", notes="更新已创建的项目信息")
    @ApiImplicitParam(name = "project", value = "项目立项project", required = true, dataType = "Project")
    public ResponseBody putProject(@RequestBody Project project){
        logger.info("[start]putProject accept project {}",project);
        Project pro = projectService.selectByPrimaryKey(project.getId());
        if(pro == null){
            logger.error("[end] project not exist error {}",ExceptionMsg.EMPTY_PROJECT);
            return ReturnUtil.error(Code.PARAM_ERROR,ExceptionMsg.EMPTY_PROJECT);
        }
        if(ProjectArgs.PROJECT_STATUS_JUNCTIONS_NUM==pro.getStatus()){
            logger.error("[end] project has JUNCTIONS error {}",ReturnMsg.PROJECT_STATUS_JUNCTIONS);
            return ReturnUtil.error(Code.PARAM_ERROR,ReturnMsg.PROJECT_STATUS_JUNCTIONS);
        }
        List<ProjectStage> stages = project.getProjectStages();
        for (ProjectStage stage:stages) {
            if(stage.getId()==null){
                stage.setProjectId(pro.getId());
                logger.info("createProjectStage accept stage {}",stage);
                projectStageService.insert(stage);
            }else{
                ProjectStage s = projectStageService.selectByPrimaryKey(stage.getId());
                if(s == null){
                    logger.error("[end] projectStage id not exist error {}",ReturnMsg.PROJECT_STAGE_NOT_EXIST);
                    return ReturnUtil.error(Code.PARAM_ERROR,ReturnMsg.PROJECT_STAGE_NOT_EXIST);
                }
                logger.info("updateProjectStage accept stage {}",stage);
                projectStageService.updateByPrimaryKey(stage);
            }
        }
        logger.info("updateProject accept project {}",project);
        projectService.updateByPrimaryKey(project);
        logger.debug("updateProject success ");
        return ReturnUtil.success(ReturnMsg.UPDATE_PROJECT_SUCCESS,project);
    }

    @MyResources(name ="删除项目阶段信息",code="delete_project_stage",type=ResourceType.URL,path="/projectStage/{id}",parent = PredefineResource.PROJECT_ESTABLISHMENT_NODE_CODE,seq = 9,method = RequestMethod.DELETE,role = IConstant.ROLE_PM)
    @DeleteMapping(value = { "/projectStage/{id}" },produces="application/json;charset=UTF-8")
    @ApiOperation(value="删除项目阶段信息", notes="删除项目阶段信息")
    public ResponseBody deleteProjectStage(@ApiParam(name="id",value="项目阶段id",required=true) @PathVariable("id") Integer id){
        logger.info("[start] deleteProjectStage id {} ", id);
        projectStageService.deleteByPrimaryKey(id);
        return ReturnUtil.success(ReturnMsg.DELETE_PROJECT_STAGE);
    }
}
