package com.sensetime.iva.ipet.mapper;

import com.sensetime.iva.ipet.entity.ProjectRelatedPerson;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author  gongchao
 */
@Repository
public interface ProjectRelatedPersonMapper {
    /**
     * 根据id删除
     * @param id  PK
     * @return 成功数量
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 新增
     * @param projectRelatedPerson  干系人
     * @return 成功数量
     */
    int insert(ProjectRelatedPerson projectRelatedPerson);

    /**
     * PK查询
     * @param id PK
     * @return 干系人
     */
    ProjectRelatedPerson selectByPrimaryKey(Integer id);

    /**
     * 查询全部
     * @return 全部干系人
     */
    List<ProjectRelatedPerson> selectAll();

    /**
     * 更新
     * @param projectRelatedPerson 干系人
     * @return 成功数量
     */
    int updateByPrimaryKey(ProjectRelatedPerson projectRelatedPerson);

    /**
     * 根据项目id查询
     * @param projectId  项目id
     * @return 干系人
     */
    @Select("select * from project_related_person where project_id = #{projectId}")
    List<ProjectRelatedPerson> selectByProjectId(Integer projectId);

}