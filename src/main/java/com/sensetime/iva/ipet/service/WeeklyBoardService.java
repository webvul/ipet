package com.sensetime.iva.ipet.service;

import com.sensetime.iva.ipet.entity.WeeklyBoard;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author  gongchao
 */
@Service
public interface WeeklyBoardService {

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
     * 根据项目计划id查询所有看板
     * @param projectId 周报id
     * @return 看板数据
     */
   List<WeeklyBoard>  selectByProjectId(Integer projectId);
}
