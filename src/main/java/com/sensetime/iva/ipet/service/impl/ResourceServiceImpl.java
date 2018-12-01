package com.sensetime.iva.ipet.service.impl;

import com.sensetime.iva.ipet.entity.Resource;
import com.sensetime.iva.ipet.mapper.ResourceMapper;
import com.sensetime.iva.ipet.service.ResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: ChaiLongLong
 * @date : 2018/8/3 14:10
 */
@Component
public class ResourceServiceImpl implements ResourceService {
    private static final Logger logger = LoggerFactory.getLogger(ResourceServiceImpl.class);

    @Autowired
    ResourceMapper resourceMapper;

    @Override
    public List<Resource> getAll() {
        logger.info("getAll Resource");
        return resourceMapper.getAll();
    }

    @Override
    public Resource queryByCode(String code) {
        logger.info("get Resource by code [{}]",code);
        return resourceMapper.queryByCode(code);
    }

    @Override
    public void addResource(Resource resource) {
        logger.info("addResource [{}]",resource);
        resourceMapper.addResource(resource);
    }

    @Override
    public void updateResource(Resource resource) {
        logger.info("updateResource [{}]",resource);
        resourceMapper.updateResource(resource);
    }

    @Override
    public void setResourceForRole(int roleId, List<Resource> resources) {
        logger.info("setResourceForRole  roleId [{}] resources [{}]",roleId,resources);
        resourceMapper.setResourceForRole(roleId, resources);
    }

    @Override
    public void addResourceForRole(int roleId, int resourceId) {
        logger.info("addResourceForRole  roleId [{}] resourceId [{}]",roleId,resourceId);
        resourceMapper.addResourceForRole(roleId, resourceId);
    }

    @Override
    public List<Resource> getResourcesByRoleId(int roleId) {
        logger.info("getResourcesByRoleId [{}]",roleId);
        return resourceMapper.getResourcesByRoleId(roleId);
    }

    @Override
    public List<Resource> getAllAndRoles() {
        logger.info("getAllAndRoles");
        return resourceMapper.getAllAndRoles();
    }


    @Override
    public List<Resource> queryResourceAndRolesByPathAndMethod(String path, String method) {
        logger.info("queryResourceAndRolesByPathAndMethod path [{}] method [{}]",path,method);
        return resourceMapper.queryResourceAndRolesByPathAndMethod(path, method);
    }

    @Override
    public List<Resource> getAllUrlResource() {
        logger.info("getAllUrlResource");
        return resourceMapper.getAllUrlResource();
    }

    @Override
    public Resource queryById(int id) {
        logger.info("Resource queryById [{}]",id);
        return resourceMapper.queryById(id);
    }

    @Override
    public void deleteResourceForRole(int id) {
        logger.info("deleteResourceForRole by id [{}]",id);
        resourceMapper.deleteResourceForRole(id);
    }

    @Override
    public void deleteResourceById(int id) {
        logger.info("deleteResourceById by id [{}]",id);
        resourceMapper.deleteResourceById(id);
    }

//    @Override
//    public List<Resource> getMenusByAccountId(int id) {
//        return resourceMapper.getMenusByAccountId(id);
//    }

    @Override
    public List<Resource> getAccountResourceByAccId(int id) {
        logger.info("getAccountResourceByAccId by id [{}]",id);
        return resourceMapper.getAccountResourceByAccId(id);
    }

    @Override
    public List<Integer> getRoleResourceIdListByRoleId(int roleId) {
        logger.info("getRoleResourceIdListByRoleId by roleId [{}]",roleId);
        return resourceMapper.getRoleResourceIdListByRoleId(roleId);
    }

    @Override
    public void cleanRoleResources(int roleId) {
        logger.info("cleanRoleResources by roleId [{}]",roleId);
        resourceMapper.cleanRoleResources(roleId);
    }

    @Override
    public void deleteOneResourceRole(int roleId, int resourceId) {
        logger.info("deleteOneResourceRole by roleId [{}] resourceId [{}]",roleId,resourceId);
        resourceMapper.deleteOneResourceRole(roleId, resourceId);
    }
}
