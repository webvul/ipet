package com.sensetime.iva.ipet.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.StringUtil;
import com.sensetime.iva.ipet.common.Code;
import com.sensetime.iva.ipet.common.IConstant;
import com.sensetime.iva.ipet.common.PredefineResource;
import com.sensetime.iva.ipet.entity.AreaEntity;
import com.sensetime.iva.ipet.entity.RoleEntity;
import com.sensetime.iva.ipet.entity.enumeration.RequestMethod;
import com.sensetime.iva.ipet.entity.enumeration.ResourceType;
import com.sensetime.iva.ipet.service.RoleService;
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
 * @author : ChaiLongLong
 * @date : 2018/8/3 14:45
 */
@Api(description  = "角色（增、改）")
@RestController
public class RoleController {

    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    RoleService roleService;

    @MyResources(name ="新增角色",code="add_role",type=ResourceType.URL,path="/role",parent = PredefineResource.ACCOUNT_MANAGEMENT_NODE_CODE,seq = 10,method = RequestMethod.POST)
    @PostMapping(value = { "/role" },produces="application/json;charset=UTF-8")
    @ApiOperation(value="新增角色", notes="新增角色")
    @ApiImplicitParam(name = "role", value = "角色role", required = true, dataType = "RoleEntity")
    public ResponseBody addRole(@RequestBody RoleEntity role){
        logger.info("addRole param  "+ role.toString());
        if(StringUtil.isEmpty(role.getName()) || StringUtil.isEmpty(role.getRoleName())){
            return ReturnUtil.error(Code.PARAM_ERROR,"角色名不能为空",null);
        }
        if(roleService.queryRoleByNameOrRoleName(role.getName(),role.getRoleName()).size() > 0 ){
            return ReturnUtil.error(Code.ERROR,"角色已存在",null);
        }
        roleService.addRole(role);
        return ReturnUtil.success("新增角色成功",null);
    }

    @MyResources(name ="更新角色",code="update_role",type=ResourceType.URL,path="/role",parent = PredefineResource.ACCOUNT_MANAGEMENT_NODE_CODE,seq = 5,method = RequestMethod.PUT)
    @PutMapping(value = { "/role" },produces="application/json;charset=UTF-8")
    @ApiOperation(value="更新角色", notes="更新角色")
    @ApiImplicitParam(name = "role", value = "角色role", required = true, dataType = "RoleEntity")
    public ResponseBody updateRole(@RequestBody RoleEntity role){
        logger.info("updateRole param  "+ role.toString());
        if(StringUtil.isEmpty(role.getName()) || StringUtil.isEmpty(role.getRoleName())){
            return ReturnUtil.error(Code.PARAM_ERROR,"角色名不能为空",null);
        }

        List<RoleEntity> roleEntities = roleService.queryRoleByNameOrRoleName(role.getName(),role.getRoleName());
        if(roleEntities.size() > 1){
            return ReturnUtil.error(Code.ERROR,"角色已存在",null);
        }else if (roleEntities.size() == 1 && roleEntities.get(0).getId() != role.getId()){
            return ReturnUtil.error(Code.ERROR,"角色已存在",null);
        }

        roleService.updateRole(role);
        return ReturnUtil.success("更新角色成功",null);
    }

    @MyResources(name ="获取角色列表",code="get_roles",type=ResourceType.URL,path="/role",parent = PredefineResource.ACCOUNT_MANAGEMENT_NODE_CODE,seq = 12,method = RequestMethod.GET)
    @GetMapping(value = { "/role" },produces="application/json;charset=UTF-8")
    @ApiOperation(value="获取角色列表", notes="获取角色列表")
    public ResponseBody<PageInfo<RoleEntity>> getRoles(@RequestParam(defaultValue = "1") @ApiParam(name="page",value="页数",required=true) Integer page, @RequestParam(defaultValue = "20") @ApiParam(name="size",value="每页条数",required=true) Integer size){
        logger.info("getAreas param  page: "+ page +" size: "+size);
        PageHelper.startPage(page,size);
        List<RoleEntity> roleEntities=roleService.getAll();
        PageInfo pageInfo = new PageInfo(roleEntities);
        return ReturnUtil.success("获取角色列表",pageInfo);
    }




}
