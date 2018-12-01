package com.sensetime.iva.ipet.web.controller;

import com.sensetime.iva.ipet.common.*;
import com.sensetime.iva.ipet.entity.ApplyList;
import com.sensetime.iva.ipet.entity.Project;
import com.sensetime.iva.ipet.entity.enumeration.RequestMethod;
import com.sensetime.iva.ipet.entity.enumeration.ResourceType;
import com.sensetime.iva.ipet.service.ApplyListService;
import com.sensetime.iva.ipet.service.ProjectService;
import com.sensetime.iva.ipet.util.ExcelImportUtil;
import com.sensetime.iva.ipet.util.ReturnUtil;
import com.sensetime.iva.ipet.vo.form.ApplyListForm;
import com.sensetime.iva.ipet.vo.response.ResponseBody;
import com.sensetime.iva.ipet.web.annotation.MyResources;
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
import java.util.List;

/**
 * @author  gongchao
 */
@RestController
@Api(description  = "实施清单（增、删、改、查、导出项目验收相关信息）")
public class ApplyListController {
    private static final Logger logger = LoggerFactory.getLogger(ApplyListController.class);
    @Autowired
    ApplyListService applyListService;
    @Autowired
    ProjectService projectService;

    @MyResources(name ="获取实施清单列表",code="get_apply_list",type=ResourceType.URL,path="/applyList/{projectId}",parent = PredefineResource.PROJECT_ACCEPTANCE_NODE_CODE,seq = 1,method = RequestMethod.GET,role = IConstant.ROLE_PM+","+IConstant.ROLE_MANAGER)
    @GetMapping(value = { "/applyList/{projectId}" },produces="application/json;charset=UTF-8")
    @ApiOperation(value="获取实施清单列表", notes="获取实施清单列表")
    public ResponseBody<ApplyListForm> getApplyList(@ApiParam(name="projectId",value="项目id",required=true) @PathVariable Integer projectId){
        logger.info("[start]getApplyList by  projectId {}",projectId);
        try {
            List<ApplyList> wareLists = applyListService.selectByProjectId(projectId);
            ApplyListForm applyListForm = new ApplyListForm();
            applyListForm.setApplyLists(wareLists);
            return ReturnUtil.success(ReturnMsg.APPLY_LIST,applyListForm);
        }catch (Exception e){
            logger.error("[end]getApplyList error [{}]",e);
            return ReturnUtil.error(Code.ERROR,ReturnMsg.APPLY_LIST_FAIL);
        }
    }

    @MyResources(name ="导出项目验收清单列表",code="export_apply_list",type= ResourceType.URL,path="/applyList/export/{projectId}",parent = PredefineResource.PROJECT_ACCEPTANCE_NODE_CODE,seq = 2,method =RequestMethod.GET,role = IConstant.ROLE_PM+","+IConstant.ROLE_MANAGER)
    @GetMapping(value = { "/applyList/export/{projectId}" },produces="application/json;charset=UTF-8")
    @ApiOperation(value="导出项目验收清单列表", notes="导出项目验收清单列表")
    public void export(HttpServletResponse response, HttpServletRequest request, @ApiParam(name="projectId",value="项目id",required=true) @PathVariable Integer projectId)throws  Exception{
        logger.info("[start] export all project  about accept messages by projectId [{}]",projectId);
        OutputStream os =null;
        try{
            // 取得输出流
            os= response.getOutputStream();
            //初始化excel
            HSSFWorkbook wb = ExcelImportUtil.createExportWorkbook(ExcelTitleToClass.EXPORT_PROJECT_CHECK, response,request);
            applyListService.export(wb,projectId);
            wb.write(os);
        }catch (Exception e){
            logger.error("[end] export all project  about accept error messages ：[{}]",e.getMessage());
        }finally {
            if (os != null) {
                os.flush();
                os.close();
            }
        }
    }

    @MyResources(name ="新增实施清单",code="add_apply_list",type=ResourceType.URL,path="/applyList",parent = PredefineResource.PROJECT_ACCEPTANCE_NODE_CODE, seq = 3,method =RequestMethod.POST,role=IConstant.ROLE_PM)
    @PostMapping(value = { "/applyList" },produces="application/json;charset=UTF-8")
    @ApiOperation(value="新增或更新实施清单", notes="新增或更新实施清单")
    @ApiImplicitParam(name = "applyListForm", value = "实施清单信息", required = true, dataType = "ApplyListForm")
    public ResponseBody addApplyList(@RequestBody ApplyListForm applyListForm){
        List<ApplyList> applyLists = applyListForm.getApplyLists();
        logger.info("[start]addApplyList applyLists {} ", applyLists);
        if(applyLists!=null&& applyLists.size()>0){
            for (ApplyList applyList: applyLists ) {
                if(applyList.getProjectId()==null){
                    logger.error("[end]"+ExceptionMsg.EMPTY_PROJECT);
                    return ReturnUtil.error(Code.PARAM_ERROR,ExceptionMsg.EMPTY_PROJECT);
                }
                Project project = projectService.selectByPrimaryKey(applyList.getProjectId());
                if(project == null){
                    logger.error("[end]"+ExceptionMsg.EMPTY_PROJECT);
                    return ReturnUtil.error(Code.ERROR,ExceptionMsg.EMPTY_PROJECT);
                }
                if(applyList.getId()==null){
                    applyListService.insert(applyList);
                }else{
                    ApplyList applyList1 = applyListService.selectByPrimaryKey(applyList.getId());
                   if(applyList1==null){
                       logger.error("[end]{}",ReturnMsg.APPLY_LIST_EMPTY);
                       return ReturnUtil.error(Code.ERROR,ReturnMsg.APPLY_LIST_EMPTY);
                   }
                    applyListService.updateByPrimaryKey(applyList);
                }
            }
            logger.info("[end]addOrUpdateApplyList success");
            return ReturnUtil.success(ReturnMsg.CREATE_APPLY_LIST);
        }else{
            logger.error("[end]addApplyList error","新增数据为空");
            return ReturnUtil.error(Code.ERROR,ReturnMsg.CREATE_APPLY_LIST_FAIL);
        }
    }

    @MyResources(name ="删除实施清单信息",code="delete_apply_list",type=ResourceType.URL,path="/applyList/{id}",parent = PredefineResource.PROJECT_ACCEPTANCE_NODE_CODE,seq = 4,method = RequestMethod.DELETE,role = IConstant.ROLE_PM)
    @DeleteMapping(value = { "/applyList/{id}" },produces="application/json;charset=UTF-8")
    @ApiOperation(value="删除实施清单信息", notes="删除实施清单信息")
    public ResponseBody deleteApplyList(@ApiParam(name="id",value="实施清单id",required=true) @PathVariable("id") Integer id){
        logger.info("deleteApplyList param  "+ id);
        applyListService.deleteByPrimaryKey(id);
        return ReturnUtil.success(ReturnMsg.DELETE_APPLY_LIST);
    }
}
