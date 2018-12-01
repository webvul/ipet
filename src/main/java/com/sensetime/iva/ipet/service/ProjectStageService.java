package com.sensetime.iva.ipet.service;

import com.sensetime.iva.ipet.entity.ProjectStage;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author  gongchao
 */
@Service
public interface ProjectStageService {
    /**
     * 查询所有的项目阶段
     * @return 所有项目阶段集合
     */
    List<ProjectStage> selectAll();
    /**
     * 根据id查项目阶段
     * @param id 项目阶段id
     * @return 单一项目阶段
     */
    ProjectStage selectByPrimaryKey(Integer id);
    /**
     * 跟新项目阶段
     * @param projectStage 项目阶段
     * @return 更新成功条数
     */
    int updateByPrimaryKey(ProjectStage projectStage);
    /**
     * 保存项目阶段
     * @param projectStage 项目阶段
     * @return 插入成功数量
     */
    int  insert(ProjectStage projectStage);
    /**
     * 删除项目阶段
     * @param id 项目阶段id
     * @return 删除成功数量
     */
    int deleteByPrimaryKey(Integer id);
    /**
     * 根据项目id查项目阶段集合
     * @param projectId 项目id
     * @return 某个项目的所有阶段
     */
    List<ProjectStage> getByProjectId(Integer projectId);
    /**
     *将解析的excel数据设置到创建的List<ProjectStage>中
     * @param valuesList  excel解析出的数据
     * @return 解析后的项目阶段集合
     * @throws Exception 异常
     */
    List<ProjectStage> setProjectStage(List<String> valuesList) throws Exception;
}
