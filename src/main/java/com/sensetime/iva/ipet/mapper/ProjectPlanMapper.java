package com.sensetime.iva.ipet.mapper;

import com.sensetime.iva.ipet.entity.ProjectPlan;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author  gongchao
 */
@Repository
public interface ProjectPlanMapper {
    /**
     * 根据id查周报
     * @param id 项目名称
     * @return 周报
     */
    int deleteByPrimaryKey(Integer id);
    /**
     * insert周报
     * @param projectPlan 周报
     * @return 成功数量
     */
    int insert(ProjectPlan projectPlan);
    /**
     * 根据id查询周报
     * @param id 周报id
     * @return 周报
     */
    ProjectPlan selectByPrimaryKey(Integer id);
    /**
     * 根据id查询周报
     * @return 周报
     */
    List<ProjectPlan> selectAll();

    /**
     * 更新周报
     * @param projectPlan 周报
     * @return 成功数量
     */
    int updateByPrimaryKey(ProjectPlan projectPlan);

    /**
     * 根据项目id和周时间戳进行查询
     * @param projectId 项目id
     * @param startDate 开始结束时间
     * @return 周报
     */
    @Select({"select * from project_plan where project_id = #{projectId} and start_date = #{startDate}"})
    ProjectPlan selectByProjectIdAndStartDate(Integer projectId, String startDate);

    /**
     * 根据项目id进行查询
     * @param projectId 项目id
     * @return 降序周报
     */
    @Select({"select * from project_plan where project_id = #{projectId} order by id desc"})
     List<ProjectPlan> selectByProjectId(Integer projectId);

}