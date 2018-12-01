package com.sensetime.iva.ipet.web.controller;

import com.sensetime.iva.ipet.common.Code;
import com.sensetime.iva.ipet.common.ExceptionMsg;
import com.sensetime.iva.ipet.common.IConstant;
import com.sensetime.iva.ipet.common.PredefineResource;
import com.sensetime.iva.ipet.entity.Project;
import com.sensetime.iva.ipet.entity.WareList;
import com.sensetime.iva.ipet.entity.enumeration.RequestMethod;
import com.sensetime.iva.ipet.entity.enumeration.ResourceType;
import com.sensetime.iva.ipet.service.ProjectService;
import com.sensetime.iva.ipet.service.WareListService;
import com.sensetime.iva.ipet.util.ReturnUtil;
import com.sensetime.iva.ipet.vo.form.WareListForm;
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
@Api(description  = "软硬件（增、删、改、查）")
@RestController
public class WareListController {
    private static final Logger logger = LoggerFactory.getLogger(WareListController.class);
    @Autowired
    WareListService wareListService;
    @Autowired
    ProjectService projectService;

    @MyResources(name ="获取软硬件列表",code="get_ware_list",type=ResourceType.URL,path="/wareList/{projectId}",parent = PredefineResource.PROJECT_ACCEPTANCE_NODE_CODE,seq = 9,method = RequestMethod.GET,role = IConstant.ROLE_PM+","+IConstant.ROLE_MANAGER)
    @GetMapping(value = { "/wareList/{projectId}" },produces="application/json;charset=UTF-8")
    @ApiOperation(value="获取软硬件列表", notes="获取软硬件列表")
    public ResponseBody<WareListForm> getWareList(@ApiParam(name="projectId",value="项目id",required=true) @PathVariable Integer projectId){
        logger.info("getWareList param  projectId="+projectId);
        List<WareList> wareLists = wareListService.selectByProjectId(projectId);
        WareListForm wareListForm = new WareListForm();
        wareListForm.setWareLists(wareLists);
        return ReturnUtil.success("获取软硬件列表",wareListForm);
    }

    @MyResources(name ="新增软硬件",code="add_ware_list",type=ResourceType.URL,path="/wareList",parent = PredefineResource.PROJECT_ACCEPTANCE_NODE_CODE,seq = 10,method = RequestMethod.POST,role = IConstant.ROLE_PM)
    @PostMapping(value = { "/wareList" },produces="application/json;charset=UTF-8")
    @ApiOperation(value="新增或更新软硬件", notes="新增或更新软硬件")
    @ApiImplicitParam(name = "wareListForm", value = "软硬件信息", required = true, dataType = "WareListForm")
    public ResponseBody addWareList(@RequestBody WareListForm wareListForm){
        List<WareList> wareList = wareListForm.getWareLists();
        logger.info("[start]addWareList {}  ", wareList);
        if(wareList!=null&&wareList.size()>0) {
            for (WareList wa : wareList) {
                if (wa.getProjectId() == null) {
                    logger.error("[end]所在项目id不能为空");
                    return ReturnUtil.error(Code.PARAM_ERROR, "所在项目id不能为空");
                }
                Project project = projectService.selectByPrimaryKey(wa.getProjectId());
                if (project == null) {
                    logger.error("[end]所在项目不能为空");
                    return ReturnUtil.error(Code.ERROR, ExceptionMsg.EMPTY_PROJECT);
                }
                if(wa.getId()==null){
                    logger.info("新增软硬件成功 {}",wa);
                    wareListService.insert(wa);
                }else{
                    WareList wareList1 = wareListService.selectByPrimaryKey(wa.getId());
                    if(wareList1==null){
                        logger.error("[end]所在项目不能为空");
                        return ReturnUtil.error(Code.ERROR, ExceptionMsg.EMPTY_PROJECT);
                    }
                    logger.info("更新软硬件成功 {}",wa);
                    wareListService.updateByPrimaryKey(wa);
                }
            }
            return ReturnUtil.success("新增或更新软硬件信息成功");
        }else{
            logger.error("[end] addDeliverList error","新增或更新软硬件为空");
            return ReturnUtil.error(Code.ERROR,"新增或更新软硬件信息失败");
        }
    }

    @MyResources(name ="删除软硬件信息",code="delete_ware_list",type=ResourceType.URL,path="/wareList/{id}",parent = PredefineResource.PROJECT_ACCEPTANCE_NODE_CODE,seq = 11,method = RequestMethod.DELETE,role = IConstant.ROLE_PM)
    @DeleteMapping(value = { "/wareList/{id}" },produces="application/json;charset=UTF-8")
    @ApiOperation(value="删除软硬件信息", notes="删除软硬件信息")
    public ResponseBody deleteWareList(@ApiParam(name="id",value="软硬件id",required=true)@PathVariable("id") Integer id){
        logger.info("deleteWareList param  "+ id);
        wareListService.deleteByPrimaryKey(id);
        return ReturnUtil.success("删除软硬件信息成功");
    }
}
