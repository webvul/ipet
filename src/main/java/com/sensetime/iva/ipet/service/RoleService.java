package com.sensetime.iva.ipet.service;

import com.sensetime.iva.ipet.entity.RoleEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: ChaiLongLong
 * @date : 2018/8/2 16:56
 */
@Service
public interface RoleService {

    /**
     * 查询所有角色
     * @return 角色集合
     */
    List<RoleEntity> getAll();

    /**
     * 根据账号id查询账号所属角色
     * @param accountId 账号id
     * @return 账号所有角色集合
     */
    List<RoleEntity> queryRoleByAccountId(int accountId);

    /**
     * 根据角色id查询角色
     * @param id 角色id
     * @return 角色
     */
    RoleEntity queryById(int id);

    /**
     * 添加角色
     * @param roleEntity 角色
     */
    void addRole(RoleEntity roleEntity);

    /**
     * 根据角色id删除角色
     * @param id 角色id
     */
    void deleteRole(int id);

    /**
     * 删除account_role中账号角色对应关系
     * @param id 账号id
     */
    void deleteAccountRoleByAccId(int id);

    /**
     * 更新角色
     * @param roleEntity 角色
     */
    void updateRole(RoleEntity roleEntity);

    /**
     * 添加account_role 账号角色对应关系
     * @param accountId 账号id
     * @param roles 角色集合
     */
    void addRolesForAccount(int accountId,List<RoleEntity> roles);

    /**
     * 根据name和roleName查询角色
     * @param name 角色名称
     * @param roleName 角色代号
     * @return 结果
     */
    List<RoleEntity> queryRoleByNameOrRoleName(String name,String roleName);

    /**
     * 根据role代码查询角色
     * @param roleName role代码
     * @return 角色
     */
    RoleEntity queryRoleByRoleName(String roleName);

}
