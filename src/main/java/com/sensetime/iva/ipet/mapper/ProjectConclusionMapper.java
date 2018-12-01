package com.sensetime.iva.ipet.mapper;

import com.sensetime.iva.ipet.entity.ProjectConclusionEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;


/**
 * @author : ChaiLongLong
 * @date : 2018/8/13 13:24
 */
@Repository
public interface ProjectConclusionMapper {

    /**
     * 新增项目结项
     * @param projectConclusionEntity 项目结项
     */
    void addProjectConclusion(ProjectConclusionEntity projectConclusionEntity);

    /**
     * 更新项目结项
     * @param projectConclusionEntity 项目结项
     */
    void updateProjectConclusion(ProjectConclusionEntity projectConclusionEntity);

    /**
     * 根据项目id查询项目结项信息
     * @param id 项目id
     * @return 项目结项信息
     */
    ProjectConclusionEntity queryByProject(int id);

    /**
     * 根据id删除项目结项
     * @param id 项目结项信息id
     */
    void deleteById(int id);

    /**
     * 根据id查询项目结项信息
     * @param id 项目结项id
     * @return 项目结项信息
     */
    ProjectConclusionEntity queryById(int id);

    /**
     * 更新项目结项状态
     * @param id 结项id
     * @param status 结项状态
     * @param updateTime 更新时间
     */
    void updateStatus(@Param("id") int id,@Param("status") int status, @Param("updateTime") Timestamp updateTime);

    /**
     *查询全部项目结项
     * @return  全部项目结项
     */
    @Select("select * from project_conclusion")
    List<ProjectConclusionEntity> selectAll();

}
