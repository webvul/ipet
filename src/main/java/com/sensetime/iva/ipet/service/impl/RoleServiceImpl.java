package com.sensetime.iva.ipet.service.impl;

import com.sensetime.iva.ipet.entity.RoleEntity;
import com.sensetime.iva.ipet.mapper.RoleMapper;
import com.sensetime.iva.ipet.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author : ChaiLongLong
 * @date :  2018/8/3 13:46
 */
@Component
public class RoleServiceImpl implements RoleService {

    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    RoleMapper roleMapper;

    @Override
    public List<RoleEntity> getAll() {
        return roleMapper.getAll();
    }

    @Override
    public List<RoleEntity> queryRoleByAccountId(int accountId) {
        logger.info("queryRoleByAccountId param accountId "+accountId);
        return roleMapper.queryRoleByAccountId(accountId);
    }

    @Override
    public RoleEntity queryById(int id) {
        logger.info("queryById param id "+id);
        return roleMapper.queryById(id);
    }

    @Override
    public void addRole(RoleEntity roleEntity) {
        logger.info("addRole param roleEntity "+roleEntity.toString());
        roleEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        roleMapper.addRole(roleEntity);
    }

    @Override
    public void deleteRole(int id) {
        logger.info("deleteRole param id "+id);
        /**
         * TODO 此处要注意该角色与account和resource是否有对应关系，否则不准删除,处理权限时完善
         */
        roleMapper.deleteRole(id);
    }

    @Override
    public void deleteAccountRoleByAccId(int id) {
        logger.info("deleteAccountRoleByAccId param id "+id);
        roleMapper.deleteAccountRoleByAccId(id);
    }

    @Override
    public void updateRole(RoleEntity roleEntity) {
        logger.info("updateRole param roleEntity "+roleEntity.toString());
        roleEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        roleMapper.updateRole(roleEntity);
    }

    @Override
    public void addRolesForAccount(int accountId, List<RoleEntity> roles) {
        logger.info("addRolesForAccount param accountId "+accountId +" roles "+roles.toString());
        roleMapper.addRolesForAccount(accountId, roles);
    }

    @Override
    public List<RoleEntity> queryRoleByNameOrRoleName(String name, String roleName) {
        logger.info("queryRoleByNameOrRoleName param name "+name +" roleName "+roleName);
        return roleMapper.queryRoleByNameOrRoleName(name,roleName);
    }

    @Override
    public RoleEntity queryRoleByRoleName(String roleName) {
        return roleMapper.queryRoleByRoleName(roleName);
    }
}
