package com.sensetime.iva.ipet.mapper;

import com.sensetime.iva.ipet.entity.Resource;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: ChaiLongLong
 * @date : 2018/7/31 16:21
 */
@Repository
public interface ResourceMapper {

    /**
     * 查询所有资源以及资源已分配的角色
     * @return 资源集合
     */
    List<Resource> getAllAndRoles();

    /**
     * 根据path和method查询资源以及持有该资源的角色
     * @param path path参数
     * @param method method参数
     * @return 结果集
     */
    List<Resource> queryResourceAndRolesByPathAndMethod(@Param("path") String path, @Param("method") String method);

    /**
     * 查询所有资源
     * @return 资源集合
     */
    List<Resource> getAll();

    /**
     * 查询所有type=URL 的资源
     * @return 资源集合
     */
    List<Resource> getAllUrlResource();


    /**
     * 根据code查询资源
     * @param code 资源
     * @return 资源
     */
    Resource queryByCode(String code);

    /**
     * 添加资源
     * @param resource 资源
     */
    void addResource(Resource resource);

    /**
     * 更新资源
     * @param resource 资源
     */
    void updateResource(Resource resource);

    /**
     * 给角色指定资源
     * @param roleId 要分配权限的角色
     * @param resources 要分配的权限
     */
    void setResourceForRole(@Param("roleId") int roleId , @Param("resources") List<Resource> resources);/**

     * 给角色指定资源
     * @param roleId 要分配权限的角色id
     * @param resourceId 要分配的权限id
     */
    void addResourceForRole(@Param("roleId") int roleId , @Param("resourceId") int resourceId);

    /**
     * 根据角色id查询角色持有的资源
     * @param roleId 角色id
     * @return 角色持有的资源
     */
    List<Resource> getResourcesByRoleId(@Param("roleId") int roleId);

    /**
     * 根据id查询资源
     * @param id 资源id
     * @return 资源
     */
    Resource queryById(int id);

    /**
     * 根据资源id删除资源角色表中无效资源的数据
     * @param id
     */
    void deleteResourceForRole(int id);

    /**
     * 根据资源id删除资源
     * @param id 资源id
     */
    void deleteResourceById(int id);

//    /**
//     * 根据账号id 查询权限
//     * @param id 账号id
//     * @return 所属权限
//     */
//    List<Resource> getMenusByAccountId(int id);

    /**
     * 获取账号下所有资源
     * @param id AccountId
     * @return 资源
     */
    List<Resource> getAccountResourceByAccId(int id);

    /**
     * 通过角色id或者角色持有的资源的id集合
     * @param roleId 角色id
     * @return 角色持有的资源的id集合
     */
    List<Integer> getRoleResourceIdListByRoleId(int roleId);

    /**
     * 清除角色资源
     * @param roleId 角色id
     */
    void cleanRoleResources(int roleId);

    /**
     * 清除角色的一个权限
     * @param roleId 角色id
     * @param resourceId 要清除的权限id
     */
    void deleteOneResourceRole(@Param("roleId") int roleId, @Param("resourceId") int resourceId);

}
