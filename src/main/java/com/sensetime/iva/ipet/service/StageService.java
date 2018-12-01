package com.sensetime.iva.ipet.service;

import com.sensetime.iva.ipet.entity.StageEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: ChaiLongLong
 * @date : 2018/8/8 10:22
 */
@Service
public interface StageService {

    /**
     * 新增阶段
     * @param stageEntity 阶段
     */
    void addStage(StageEntity stageEntity);

    /**
     * 更新阶段
     * @param stageEntity 阶段
     */
    void updateStage(StageEntity stageEntity);

    /**
     * 根据项目计划id和阶段type查询阶段以及阶段对应的数据
     * @param id  项目计划id
     * @param type   1：每周看板    2：工时
     * @return
     */
    List<StageEntity> queryStagesByProjectIdAndType(int id, int type);

    /**
     * 根据项目计划id和阶段type查询  项目是否有阶段
     * @param id 项目id
     * @param type 类型
     * @return stage集合
     */
    List<StageEntity> queryProjectStageByProIdAndType(int id, int type);


    /**
     * 根据id删除阶段
     * @param id 阶段id
     */
    void deleteById(int id);

    /**
     * 根据stage id查询阶段
     * @param id stage id
     * @return 阶段
     */
    StageEntity queryById(int id);

    /**
     * 根据项目计划id和阶段type只查询阶段数据
     * @param id stageId
     * @param type 类型
     * @return 阶段数据
     */
    List<StageEntity> selectByIdAndType(int id, int type);

    /**
     * 查询所有项目阶段
     * @return 所有项目阶段
     */
    List<StageEntity> selectAll();
}
