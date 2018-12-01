package com.sensetime.iva.ipet.mapper;

import com.sensetime.iva.ipet.entity.WeeklyBoard;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author  gongchao
 */
@Repository
public interface WeeklyBoardMapper {
    /**
     *根据主键删除
     * @param id 看板id
     * @return 成功数量
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 保存看板数据
     * @param weeklyBoard  看板数据
     * @return 成功数据
     */
    int insert(WeeklyBoard weeklyBoard);

    /**
     * 主键查询
     * @param id 看板id
     * @return 看板
     */
    WeeklyBoard selectByPrimaryKey(Integer id);

    /**
     * 查询所有看板
     * @return 看板数据
     */
    List<WeeklyBoard> selectAll();

    /**
     * 根据主键更新
     * @param weeklyBoard 看板任务
     * @return 成功数量
     */
    int updateByPrimaryKey(WeeklyBoard weeklyBoard);

    /**
     * 根据项目id查询所有看板
     * @param projectId 项目id
     * @return 看板数据
     */
    @Select("select * from weekly_board where project_id = #{projectId} order by stage, task")
    List<WeeklyBoard> selectByProjectId(Integer projectId);
}