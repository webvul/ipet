package com.sensetime.iva.ipet.service.impl;

import com.sensetime.iva.ipet.entity.StageEntity;
import com.sensetime.iva.ipet.mapper.StageMapper;
import com.sensetime.iva.ipet.service.StageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: ChaiLongLong
 * @date : 2018/8/8 10:22
 */
@Component
public class StageServiceImpl implements StageService {

    private static final Logger logger = LoggerFactory.getLogger(StageServiceImpl.class);

    @Autowired
    StageMapper stageMapper;

    @Override
    public void addStage(StageEntity stageEntity) {
        logger.info("addStage param stageEntity "+stageEntity);
        stageMapper.addStage(stageEntity);
    }

    @Override
    public void updateStage(StageEntity stageEntity) {
        logger.info("updateStage param stageEntity "+stageEntity);
        stageMapper.updateStage(stageEntity);
    }

    @Override
    public List<StageEntity> queryStagesByProjectIdAndType(int id, int type) {
        logger.info("queryStagesByProjectIdAndType param id "+id +" type "+type);
        return stageMapper.queryStagesByProjectIdAndType(id,type);
    }

    @Override
    public List<StageEntity> queryProjectStageByProIdAndType(int id, int type) {
        return stageMapper.queryProjectStageByProIdAndType(id, type);
    }

    @Override
    public void deleteById(int id) {
        logger.info("deleteById param id "+id);
        stageMapper.deleteById(id);
    }

    @Override
    public StageEntity queryById(int id) {
        logger.info("queryById param id "+id);
        return stageMapper.queryById(id);
    }

    @Override
    public List<StageEntity> selectByIdAndType(int id, int type) {
        logger.info("selectByIdAndType id="+id+" type="+type);
        return stageMapper.selectByIdAndType(id,type);
    }

    @Override
    public List<StageEntity> selectAll() {
        logger.info("selectAll StageEntity ");
        return stageMapper.selectAll();
    }
}
