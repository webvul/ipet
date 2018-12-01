package com.sensetime.iva.ipet.service;

import com.sensetime.iva.ipet.entity.AccountEntity;
import com.sensetime.iva.ipet.entity.RoleEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: ChaiLongLong
 */
@Service
public interface AccountService {

    /**
     * 根据用户名称查询用户
     * @param name 用户名
     * @return 账号信息
     */
    AccountEntity loadAccountByUsername(String name);

    /**
     * 根据账号id获取账号信息
     * @param id 账号id
     * @return 账号信息
     */
    AccountEntity queryAccountById(int id);

    /**
     * 根据账号id查询账号角色
     * @param id 账号id
     * @return 账号的角色
     */
    List<RoleEntity> getRolesByAccountId(int id);

    /**
     * 获取所有账号信息
     * @param username 模糊查询
     * @return 所有账号信息
     */
    List<AccountEntity> getAll(String username);

    /**
     * 添加账号信息
     * @param accountEntity 要添加的账号信息
     */
    void addAccount(AccountEntity accountEntity);

    /**
     * 更新账号信息
     * @param accountEntity 账号信息
     */
    void updateAccount(AccountEntity accountEntity);

    /**
     * 根据账号id删除账号信息
     * @param id 账号id
     */
    void deleteAccountById(int id);

    /**
     * 根据role_id查询分配了该角色的账号
     * @param id role_id
     * @return 分配了该角色的账号
     */
    List<AccountEntity> queryAccountsByRoleId(int id);

    /**
     * 根据资源code查询有该资源权限的用户
     * @param code 资源code
     * @return 用户
     */
    List<AccountEntity> queryAccountByResourceCode(String code);

    /**
     * 获取项目中的所有创建用户
     * @return 用户
     */
    List<AccountEntity> getAllAccountFromProject();

    /**
     * 获取所有PM账号信息
     * @param username 模糊查询
     * @return 所有账号信息
     */
    List<AccountEntity> getAllPM(String username);

    /**
     *获取同一区域的项目经理
     * @param areaId 区域id
     * @return
     */
    List<AccountEntity> getSameUserByAreaId(Integer areaId);
}
