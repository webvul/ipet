package com.sensetime.iva.ipet.web.controller;

import com.sensetime.iva.ipet.common.Code;
import com.sensetime.iva.ipet.common.IConstant;
import com.sensetime.iva.ipet.common.PredefineResource;
import com.sensetime.iva.ipet.entity.ConvertTree;
import com.sensetime.iva.ipet.entity.Resource;
import com.sensetime.iva.ipet.entity.RoleEntity;
import com.sensetime.iva.ipet.entity.TreeNode;
import com.sensetime.iva.ipet.entity.enumeration.RequestMethod;
import com.sensetime.iva.ipet.entity.enumeration.ResourceType;
import com.sensetime.iva.ipet.service.ResourceService;
import com.sensetime.iva.ipet.service.RoleService;
import com.sensetime.iva.ipet.util.ReturnUtil;
import com.sensetime.iva.ipet.vo.form.ResourceForm;
import com.sensetime.iva.ipet.vo.response.ResponseBody;
import com.sensetime.iva.ipet.web.annotation.MyResources;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : ChaiLongLong
 * @date : 2018/8/31 14:17
 */
@Api(description  = "资源权限（查询，修改）")
@RestController
public class ResourceController {

    private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);

    @Autowired
    ResourceService resourceService;
    @Autowired
    RoleService roleService;
    @Autowired
    RedisTemplate redisTemplate;

    @MyResources(name ="获取用户资源权限",code="get_account_resource",type=ResourceType.URL,path="/resource/{accountId}",parent = PredefineResource.RESOURCE_NODE_CODE,seq = 1,method = RequestMethod.GET,role = IConstant.ROLE_PM+","+IConstant.ROLE_MANAGER)
    @GetMapping(value = { "/resource/{accountId}" },produces="application/json;charset=UTF-8")
    @ApiOperation(value="获取用户资源权限", notes="根据账户id获取用户资源权限")
    @ApiImplicitParam(name="accountId",value="账号accountId",dataType="Integer", paramType = "path")
    public ResponseBody<List<TreeNode<Resource>>> getAccountResource(@PathVariable("accountId") Integer accountId){
        logger.info("getAccountResource param  accountId "+ accountId);
        List<Resource> resourceList = resourceService.getAccountResourceByAccId(accountId);
        ConvertTree convertTree = new ConvertTree<>();
        List<TreeNode> result= convertTree.getForest(resourceList,"code", "parent");
        return ReturnUtil.success("获取用户资源权限成功",result);
    }

    @MyResources(name ="获取所有资源权限",code="get_all_resource",type=ResourceType.URL,path="/resource",parent = PredefineResource.RESOURCE_NODE_CODE,seq = 2,method = RequestMethod.GET)
    @GetMapping(value = { "/resource" },produces="application/json;charset=UTF-8")
    @ApiOperation(value="获取所有资源权限", notes="获取所有资源权限")
    public ResponseBody<List<TreeNode<Resource>>> getAllResource(){
        logger.info("getAllResource ");
        List<Resource> resourceList = resourceService.getAll();
        ConvertTree convertTree = new ConvertTree<>();
        List<TreeNode> result= convertTree.getForest(resourceList,"code", "parent");
        return ReturnUtil.success("获取所有资源权限成功",result);
    }

    @MyResources(name ="修改角色资源权限",code="update_role_resource",type=ResourceType.URL,path="/resource",parent = PredefineResource.RESOURCE_NODE_CODE,seq = 3,method = RequestMethod.PUT)
    @PutMapping(value = { "/resource" },produces="application/json;charset=UTF-8")
    @ApiOperation(value="修改角色资源权限", notes="该接口为角色权限批量修改接口，非单一修改接口")
    @ApiImplicitParam(name = "resourceForm", value = "资源resourceForm", required = true, dataType = "ResourceForm")
    public ResponseBody updateRoleResource(@RequestBody ResourceForm resourceForm){
        logger.info("updateRoleResource param "+resourceForm.toString());

        if(resourceForm.getRoleId() != null && resourceForm.getRoleId() != 0 ){
            RoleEntity adminRole = roleService.queryRoleByRoleName("ROLE_admin");
            if(resourceForm.getRoleId() == adminRole.getId()){
                return ReturnUtil.error(Code.PARAM_ERROR,"超级管理员权限不可修改");
            }
            //当resource集合为空或者size=0时，默认为清空角色资源
            if(resourceForm.getResources() != null && resourceForm.getResources().size() > 0){

                List<Integer> roleResources = resourceService.getRoleResourceIdListByRoleId(resourceForm.getRoleId());
                List<Integer> resourceFormResourcesTemp = new ArrayList<>();
                resourceFormResourcesTemp.addAll(resourceForm.getResources());
                //求差集获取新增权限
                resourceFormResourcesTemp.removeAll(roleResources);
                //求差集获取删除权限
                roleResources.removeAll(resourceForm.getResources());

                if (resourceFormResourcesTemp != null && resourceFormResourcesTemp.size() > 0){
                    for (Integer addResourceId: resourceFormResourcesTemp) {
                        Resource resource = resourceService.queryById(addResourceId);
                        if(resource != null && !resource.getType().equals(ResourceType.NODE)){
                            resourceService.addResourceForRole(resourceForm.getRoleId(), addResourceId);
                        }else {
                            return ReturnUtil.error(Code.PARAM_ERROR,"资源 "+addResourceId+" 不存在或类型有误");
                        }
                    }
                }

                if(roleResources != null  && roleResources.size() > 0){
                    for (Integer cleanResourceId: roleResources) {
                        Resource resource = resourceService.queryById(cleanResourceId);
                        if(resource != null && !resource.getType().equals(ResourceType.NODE)){
                            resourceService.deleteOneResourceRole(resourceForm.getRoleId(), cleanResourceId);
                        }else {
                            return ReturnUtil.error(Code.PARAM_ERROR,"资源 "+cleanResourceId+" 不存在或类型有误");
                        }
                    }
                }
            }else {
                resourceService.cleanRoleResources(resourceForm.getRoleId());
            }
        }else {
            return ReturnUtil.error(Code.PARAM_ERROR,"角色id不能为空");
        }

        //修改角色资源后，更新redis中资源角色对应关系
        List<Resource> resourceList = resourceService.getAllAndRoles();
        Map<String,Resource> resourceMap = new HashMap<>(resourceList.size());
        for (Resource resource : resourceList ) {
            resourceMap.put(resource.getPath()+"-"+resource.getMethod(),resource);
        }
        redisTemplate.opsForValue().set(IConstant.ALL_RESOURCES_AND_ROLES,resourceMap);
        return ReturnUtil.success("修改角色资源权限成功");
    }

}
