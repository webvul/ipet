package com.sensetime.iva.ipet.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.StringUtil;
import com.sensetime.iva.ipet.common.Code;
import com.sensetime.iva.ipet.common.IConstant;
import com.sensetime.iva.ipet.common.PredefineResource;
import com.sensetime.iva.ipet.entity.AreaEntity;
import com.sensetime.iva.ipet.entity.enumeration.RequestMethod;
import com.sensetime.iva.ipet.entity.enumeration.ResourceType;
import com.sensetime.iva.ipet.service.AreaService;
import com.sensetime.iva.ipet.util.ReturnUtil;
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
 * @Author: ChaiLongLong
 * @date : 2018/8/1 16:08
 */
@Api(description  = "区域（增、删、改、获取区域列表）")
@RestController
public class AreaController {

    private static final Logger logger = LoggerFactory.getLogger(AreaController.class);

    @Autowired
    AreaService areaService;

    @MyResources(name ="获取区域列表",code="get_areas",type=ResourceType.URL,path="/area",parent = PredefineResource.ACCOUNT_MANAGEMENT_NODE_CODE,seq = 6,method = RequestMethod.GET,role = IConstant.ROLE_PM+","+IConstant.ROLE_MANAGER)
    @GetMapping(value = { "/area" },produces="application/json;charset=UTF-8")
    @ApiOperation(value="获取区域列表", notes="获取区域列表")
    public ResponseBody<PageInfo<AreaEntity>> getAreas(@RequestParam(defaultValue = "1") @ApiParam(name="page",value="页数",required=true) Integer page, @RequestParam(defaultValue = "20") @ApiParam(name="size",value="每页条数",required=true) Integer size){
        logger.info("getAreas param  page: "+ page +" size: "+size);
        PageHelper.startPage(page,size);
        List<AreaEntity> areaEntityList=areaService.getAll();
        PageInfo pageInfo = new PageInfo(areaEntityList);
        return ReturnUtil.success("获取区域列表",pageInfo);
    }

    @MyResources(name ="新增区域",code="add_area",type=ResourceType.URL,path="/area",parent = PredefineResource.ACCOUNT_MANAGEMENT_NODE_CODE,seq = 7,method = RequestMethod.POST)
    @PostMapping(value = { "/area" },produces="application/json;charset=UTF-8")
    @ApiOperation(value="新增区域", notes="新增区域")
    @ApiImplicitParam(name = "area", value = "区域area", required = true, dataType = "AreaEntity")
    public ResponseBody addArea(@RequestBody AreaEntity area){
        logger.info("addArea param  "+ area.toString());
        if(StringUtil.isEmpty(area.getName())){
            return ReturnUtil.error(Code.PARAM_ERROR,"区域名称不能为空");
        }
        AreaEntity areaEntity = areaService.queryByName(area.getName());
        if(areaEntity != null){
            return ReturnUtil.error(Code.PARAM_ERROR,"区域已存在");
        }
        areaService.addArea(area);
        return ReturnUtil.success("新增区域成功");
    }

    @MyResources(name ="删除区域",code="delete_area",type=ResourceType.URL,path="/area/{id}",parent = PredefineResource.ACCOUNT_MANAGEMENT_NODE_CODE,seq = 8,method = RequestMethod.DELETE)
    @DeleteMapping(value = { "/area/{id}" },produces="application/json;charset=UTF-8")
    @ApiOperation(value="删除区域", notes="删除区域")
    @ApiImplicitParam(name="id",value="区域id",dataType="Integer", paramType = "delete")
    public ResponseBody deleteArea(@PathVariable("id") Integer id){
        logger.info("deleteArea param  "+ id);
        AreaEntity areaEntity = areaService.queryById(id);
        if(areaEntity == null){
            return ReturnUtil.error(Code.PARAM_ERROR,"区域不存在");
        }
        areaService.deleteArea(id);
        return ReturnUtil.success("删除区域成功");
    }

    @MyResources(name ="更新区域",code="update_area",type=ResourceType.URL,path="/area",parent = PredefineResource.ACCOUNT_MANAGEMENT_NODE_CODE,seq = 9,method = RequestMethod.PUT)
    @PutMapping(value = { "/area" },produces="application/json;charset=UTF-8")
    @ApiOperation(value="更新区域", notes="更新区域")
    @ApiImplicitParam(name = "area", value = "区域area", required = true, dataType = "AreaEntity")
    public ResponseBody putArea(@RequestBody AreaEntity area){
        logger.info("putArea param  "+ area.toString());
        if(StringUtil.isEmpty(area.getName())){
            return ReturnUtil.error(Code.PARAM_ERROR,"区域名称不能为空");
        }
        AreaEntity areaEntity = areaService.queryById(area.getId());
        if(areaEntity == null){
            return ReturnUtil.error(Code.PARAM_ERROR,"区域不存在");
        }
        AreaEntity queryByNameResult = areaService.queryByName(area.getName());
        if(queryByNameResult != null && queryByNameResult.getId() != area.getId()){
            return ReturnUtil.error(Code.PARAM_ERROR,"区域已存在");
        }
        areaService.updateArea(area);
        return ReturnUtil.success("更新区域成功");
    }

}
