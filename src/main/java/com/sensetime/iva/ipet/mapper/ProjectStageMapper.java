package com.sensetime.iva.ipet.mapper;


import com.sensetime.iva.ipet.entity.ProjectStage;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author gongchao
 */
@Repository
public interface ProjectStageMapper {
    /**
     * 删除项目阶段
     * @param id 项目阶段id
     * @return 删除成功数量
     */
    int deleteByPrimaryKey(Integer id);
    /**
     * 保存项目阶段
     * @param projectStage 项目阶段
     * @return 插入成功数量
     */
    int insert(ProjectStage projectStage);
    /**
     * 根据id查项目阶段
     * @param id 项目阶段id
     * @return 单一项目阶段
     */
    ProjectStage selectByPrimaryKey(Integer id);
    /**
     * 查询所有的项目阶段
     * @return 所有项目阶段集合
     */
    List<ProjectStage> selectAll();
    /**
     * 跟新项目阶段
     * @param projectStage 项目阶段
     * @return 更新成功条数
     */
    int updateByPrimaryKey(ProjectStage projectStage);

    /**
     * 根据项目id查出所有阶段
     * @param projectId
     * @return 当前项目所有阶段
     */
    @Select("select * from project_stage where project_id = #{projectId}")
    List<ProjectStage> getByProjectId(Integer projectId);

}
