package com.sensetime.iva.ipet.mapper;

import com.sensetime.iva.ipet.entity.TopologicalGraph;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author  gongchao
 */
@Repository
public interface TopologicalGraphMapper {
    /**
     *删除
     * @param id id
     * @return  成功数量
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *新增拓扑图
     * @param topologicalGraph
     * @return 拓扑图
     */
    int insert(TopologicalGraph topologicalGraph);

    /**
     *id查询拓扑图
     * @param id id
     * @return 拓扑图
     */
    TopologicalGraph selectByPrimaryKey(Integer id);

    /**
     *查询全部拓扑图
     * @return 拓扑图
     */
    List<TopologicalGraph> selectAll();

    /**
     *更新拓扑图
     * @param topologicalGraph 拓扑图
     * @return 成功数量
     */
    int updateByPrimaryKey(TopologicalGraph topologicalGraph);

    /**
     * 获取项目计划上传文件的数量
     * @param projectId 项目id
     * @return 数量
     */
    @Select("select count(id) from topological_graph where project_id = #{projectId}")
    int selectCountByProjectId(Integer projectId);

    /**
     * 根据项目id查询拓扑图
     * @param projectId 项目id
     * @return 拓扑图
     */
    @Select("select * from topological_graph where project_id = #{projectId}")
    List<TopologicalGraph> selectByProjectId(Integer projectId);
}