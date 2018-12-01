package com.sensetime.iva.ipet.mapper;

import com.sensetime.iva.ipet.entity.Project;
import com.sensetime.iva.ipet.vo.form.ProjectFilterForm;
import com.sensetime.iva.ipet.vo.form.ProjectForm;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author  gongchao
 */
@Repository
public interface ProjectMapper {

    /**
     * 删除项目
     * @param id 项目id
     * @return 删除成功数量
     */
    int deleteByPrimaryKey(Integer id);
    /**
     * 保存项目
     * @param project 项目
     * @return 插入成功数量
     */
    int insert(Project project);
    /**
     * 根据id查项目
     * @param id 项目id
     * @return 单一项目
     */
    Project selectByPrimaryKey(Integer id);
    /**
     * 查询所有的项目
     *@param projectFilterForm 项目过滤条件
     * @return 所有项目集合
     */
    List<ProjectForm> selectAll(@Param(value = "projectFilterForm") ProjectFilterForm projectFilterForm);
    /**
     * 跟新项目
     * @param project 项目
     * @return 更新成功条数
     */
    int updateByPrimaryKey(Project project);

    /**
     * 根查询不在当前项目状态的项目
     * @param status 项目状态
     * @return 项目
     */
    @Select("select * from project where status <> #{status}")
    List<Project> selectNotInStatus(Integer status);

    /**
     * 更改项目状态
     * @param id 项目id
     * @param status 项目状态
     */
    void changeProjectStatus(@Param("id") int id, @Param("status") int status);

    /**
     * 查询不同的销售经理
     * @param createUserId PM
     * @return
     */
    List<String> getDistinctSaleManager(@Param("createUserId") Integer createUserId);

    /**
     * 查询不同的解决方案经理
     * @param createUserId PM
     * @return
     */
    List<String> getDistinctSolutionManager(@Param("createUserId") Integer createUserId);
}
