package com.sensetime.iva.ipet.mapper;

import com.sensetime.iva.ipet.entity.AccountEntity;
import com.sensetime.iva.ipet.entity.RoleEntity;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: ChaiLongLong
 */

import com.sensetime.iva.ipet.entity.AccountEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountMapper {

	/**
	 * 获取所有账号信息
	 * @param username 模糊查询
	 * @return 所有账号信息
	 */
	List<AccountEntity> getAll(String username);

	/**
	 * 根据用户名称查询用户
	 * @param username 用户名
	 * @return 账号信息
	 */
	AccountEntity loadUserByUsername(String username);

	/**
	 * 根据账号id获取账号信息
	 * @param id 账号id
	 * @return 账号信息
	 */
	AccountEntity queryById(int id);

	/**
	 * 根据账号id查询账号角色
	 * @param id 账号id
	 * @return 账号的角色
	 */
	List<RoleEntity> getRolesByAccountId(int id);

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
	void deleteAccount(int id);


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
	List<AccountEntity> queryAccountByResourceCode(@Param("code") String code);


	/**
	 * 获取项目中的所有创建用户
	 * @return 用户
	 */
	@Select("select * from account where id in (select distinct create_user_id from project )")
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
	@Select("select * from account where area_id =#{areaId}")
	List<AccountEntity> getSameUserByAreaId(Integer areaId);
}