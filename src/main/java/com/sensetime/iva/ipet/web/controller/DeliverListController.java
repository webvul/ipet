package com.sensetime.iva.ipet.web.controller;

import com.sensetime.iva.ipet.common.*;
import com.sensetime.iva.ipet.entity.DeliverList;
import com.sensetime.iva.ipet.entity.Project;
import com.sensetime.iva.ipet.entity.enumeration.RequestMethod;
import com.sensetime.iva.ipet.entity.enumeration.ResourceType;
import com.sensetime.iva.ipet.service.DeliverListService;
import com.sensetime.iva.ipet.service.ProjectService;
import com.sensetime.iva.ipet.util.ReturnUtil;
import com.sensetime.iva.ipet.vo.form.DeliverListForm;
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
@Api(description  = "交付物（增、删、查）")
public class DeliverListController {
    private static final Logger logger = LoggerFactory.getLogger(DeliverListController.class);
    @Autowired
    DeliverListService deliverListService;
    @Autowired
    ProjectService projectService;
    @MyResources(name ="获取交付物列表",code="get_deliver_list",type=ResourceType.URL,path="/deliverList/{projectId}",parent = PredefineResource.PROJECT_ACCEPTANCE_NODE_CODE,seq = 17,method = RequestMethod.GET,role = IConstant.ROLE_PM+","+IConstant.ROLE_MANAGER)
    @GetMapping(value = { "/deliverList/{projectId}" },produces="application/json;charset=UTF-8")
    @ApiOperation(value="获取交付物列表", notes="获取交付物列表")
    public ResponseBody<DeliverListForm> getDeliverList(@ApiParam(name="projectId",value="项目id",required=true) @PathVariable Integer projectId){
        logger.info("[start ]getDeliverList by  projectId="+projectId);
        List<DeliverList> deliverLists = deliverListService.selectByProjectId(projectId);
        DeliverListForm deliverListForm = new DeliverListForm();
        deliverListForm.setDeliverLists(deliverLists);
        return ReturnUtil.success(ReturnMsg.SUCCESS,deliverListForm);
    }

    @MyResources(name ="新增交付物",code="add_deliver_list",type=ResourceType.URL,path="/deliverList",parent = PredefineResource.PROJECT_ACCEPTANCE_NODE_CODE,seq = 18,method = RequestMethod.POST,role = IConstant.ROLE_PM)
    @PostMapping(value = { "/deliverList" },produces="application/json;charset=UTF-8")
    @ApiOperation(value="新增交付物", notes="新增交付物")
    @ApiImplicitParam(name = "deliverListForm", value = "交付物信息", required = true, dataType = "DeliverListForm")
    public ResponseBody addDeliverList(@RequestBody DeliverListForm deliverListForm){
         List<DeliverList> deliverList = deliverListForm.getDeliverLists();
        logger.info("[start]addDeliverList deliverList {}  ", deliverList);
        if(deliverList!=null&&deliverList.size()>0) {
            for (DeliverList de : deliverList) {
                if (de.getProjectId() == null) {
                    logger.error("[end]所在项目id不能为空");
                    return ReturnUtil.error(Code.PARAM_ERROR, ExceptionMsg.EMPTY_PROJECT);
                }
                Project project = projectService.selectByPrimaryKey(de.getProjectId());
                if (project == null) {
                    logger.error("[end]所在项目不能为空");
                    return ReturnUtil.error(Code.ERROR, ExceptionMsg.EMPTY_PROJECT);
                }
                if(de.getId()==null){
                    logger.info("新增交付物成功 {}",de);
                    deliverListService.insert(de);
                }else{
                    DeliverList deliverList1 = deliverListService.selectByPrimaryKey(de.getId());
                     if(deliverList1==null){
                         logger.error("[end]新增或更新交付物为空");
                         return ReturnUtil.error(Code.ERROR, ReturnMsg.DELIVER_LIST_EMPTY);
                     }
                    logger.info("更新交付物成功 {}",de);
                    deliverListService.updateByPrimaryKey(de);
                }
            }
            return ReturnUtil.success(ReturnMsg.ADD_OR_UPDATE_DELIVER_LIST);
        }else{
            logger.error("[end]addDeliverList error","新增或更新交付物为空");
            return ReturnUtil.error(Code.ERROR,ReturnMsg.DELIVER_LIST_EMPTY);
        }
    }
    @MyResources(name ="删除交付物信息",code="delete_deliver_list",type=ResourceType.URL,path="/deliverList/{id}",parent = PredefineResource.PROJECT_ACCEPTANCE_NODE_CODE,seq = 19,method = RequestMethod.DELETE,role = IConstant.ROLE_PM)
    @DeleteMapping(value = { "/deliverList/{id}" },produces="application/json;charset=UTF-8")
    @ApiOperation(value="删除交付物信息", notes="删除交付物信息")
    public ResponseBody deleteDeliverList(@ApiParam(name="id",value="交付物id",required=true)@PathVariable("id") Integer id){
        logger.info("[start]deleteDeliverList by id  {}", id);
        deliverListService.deleteByPrimaryKey(id);
        return ReturnUtil.success(ReturnMsg.DELETE_LIST_EMPTY);
    }
}
