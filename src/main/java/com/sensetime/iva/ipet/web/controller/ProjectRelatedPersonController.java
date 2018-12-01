package com.sensetime.iva.ipet.web.controller;

import com.sensetime.iva.ipet.common.Code;
import com.sensetime.iva.ipet.common.IConstant;
import com.sensetime.iva.ipet.common.PredefineResource;
import com.sensetime.iva.ipet.entity.Project;
import com.sensetime.iva.ipet.entity.ProjectRelatedPerson;
import com.sensetime.iva.ipet.entity.enumeration.RequestMethod;
import com.sensetime.iva.ipet.entity.enumeration.ResourceType;
import com.sensetime.iva.ipet.service.ProjectRelatedPersonService;
import com.sensetime.iva.ipet.service.ProjectService;
import com.sensetime.iva.ipet.util.ReturnUtil;
import com.sensetime.iva.ipet.vo.form.ProjectRelatedPersonForm;
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
@RestController
@Api(description  = "项目干系人（增、删、改、查）")
public class ProjectRelatedPersonController {
    private static final Logger logger = LoggerFactory.getLogger(ProjectRelatedPersonController.class);

    @Autowired
    ProjectRelatedPersonService projectRelatedPersonService;
    @Autowired
    ProjectService projectService;

    @MyResources(name ="获取项目干系人列表",code="get_project_related_person",type=ResourceType.URL,path="/projectRelatedPerson/{projectId}",parent = PredefineResource.PROJECT_PLAN_NODE_CODE,seq = 13,method = RequestMethod.GET,role = IConstant.ROLE_PM+","+IConstant.ROLE_MANAGER)
    @GetMapping(value = "/projectRelatedPerson/{projectId}",produces="application/json;charset=UTF-8")
    @ApiOperation(value="获取项目干系人列表", notes="获取项目干系人列表")
    public ResponseBody<List<ProjectRelatedPerson>> getProjectRelatedPerson(@ApiParam(name="projectId",value="项目id",required=true) @PathVariable Integer projectId){
        logger.info("[start]getProjectRelatedPerson ");
        List<ProjectRelatedPerson> relatedPeople = projectRelatedPersonService.selectByProjectId(projectId);
        logger.info("[end]getProjectRelatedPerson success relatedPeople {}",relatedPeople);
        return ReturnUtil.success("获取项目干系人列表",relatedPeople);
    }

    @MyResources(name ="新增项目干系人",code="add_project_related_person",type=ResourceType.URL,path="/projectRelatedPerson",parent = PredefineResource.PROJECT_PLAN_NODE_CODE,seq = 14,method = RequestMethod.POST,role = IConstant.ROLE_PM)
    @PostMapping(value = "/projectRelatedPerson",produces="application/json;charset=UTF-8")
    @ApiOperation(value="新增或更新项目干系人", notes="新增或更新项目干系人")
    @ApiImplicitParam(name = "projectRelatedPersonForm", value = "项目干系人projectRelatedPersonForm", required = true, dataType = "ProjectRelatedPersonForm")
    public ResponseBody<List<ProjectRelatedPerson>> addProjectRelatedPerson(@RequestBody ProjectRelatedPersonForm projectRelatedPersonForm){
        List<ProjectRelatedPerson> personLists = projectRelatedPersonForm.getPersonLists();
        logger.info("[start]addProjectRelatedPerson  projectRelatedPersonForm {}",projectRelatedPersonForm);
            if(personLists!=null&&personLists.size()>0){
                for (ProjectRelatedPerson projectRelatedPerson: personLists) {
                    if(projectRelatedPerson.getProjectId()==null){
                        logger.error("[end] getProjectRelatedPerson error ","所在项目不存在,不能添加干系人信息");
                        return ReturnUtil.error(Code.ERROR,"所在项目不存在,不能添加干系人信息");
                    }
                    Project project = projectService.selectByPrimaryKey(projectRelatedPerson.getProjectId());
                    if(project==null){
                       logger.error("[end] getProjectRelatedPerson error ","所在项目不存在,不能添加干系人信息");
                       return ReturnUtil.error(Code.ERROR,"所在项目不存在,不能添加干系人信息");
                    }
                    if(projectRelatedPerson.getId()==null){
                       projectRelatedPersonService.insert(projectRelatedPerson);
                        logger.info("addProjectRelatedPerson success projectRelatedPerson {}",projectRelatedPerson);
                    }else{
                        ProjectRelatedPerson person = projectRelatedPersonService.selectByPrimaryKey(projectRelatedPerson.getId());
                        if(person==null){
                            logger.error("[end]getProjectRelatedPerson error ","更新干系人信息不存在,不能更新");
                            return ReturnUtil.error(Code.ERROR,"更新干系人信息不存在,不能更新");
                        }
                        projectRelatedPersonService.updateByPrimaryKey(projectRelatedPerson);
                    }
                 }
                logger.info("[end]addProjectRelatedPerson success");
                return ReturnUtil.success("新增或更新项目干系人成功",personLists);
            }else{
                 logger.error("addProjectRelatedPerson error","新增数据为空");
                 return ReturnUtil.error(Code.ERROR,"新增项目干系人失败");
            }
    }

    @MyResources(name ="删除项目干系人",code="delete_project_related_person",type=ResourceType.URL,path="/projectRelatedPerson/{id}",parent = PredefineResource.PROJECT_PLAN_NODE_CODE,seq = 15,method = RequestMethod.DELETE,role = IConstant.ROLE_PM)
    @DeleteMapping(value = { "/projectRelatedPerson/{id}" },produces="application/json;charset=UTF-8")
    @ApiOperation(value="删除项目干系人",notes="删除项目干系人")
    public ResponseBody deleteProjectRelatedPerson(@ApiParam(name="id",value="项目干系人id") @PathVariable Integer id) {
        logger.info(" deleteProjectRelatedPerson id=" +id);
        projectRelatedPersonService.deleteByPrimaryKey(id);
        return ReturnUtil.success("删除项目干系人成功",id);
    }
}
