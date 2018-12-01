package com.sensetime.iva.ipet.service.impl;

import com.sensetime.iva.ipet.entity.WorkTimeEntity;
import com.sensetime.iva.ipet.mapper.WorkTimeMapper;
import com.sensetime.iva.ipet.service.WorkTimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: ChaiLongLong
 * @date : 2018/8/7 15:09
 */
@Component
public class WorkTimeServiceImpl implements WorkTimeService {

    private static final Logger logger = LoggerFactory.getLogger(WorkTimeServiceImpl.class);

    @Autowired
    WorkTimeMapper workTimeMapper;

    @Override
    public void addWorkTime(WorkTimeEntity workTimeEntity) {
        logger.info("addWorkTime param workTimeEntity "+workTimeEntity.toString());
        workTimeMapper.addWorkTime(workTimeEntity);
    }

    @Override
    public void updateWorkTime(WorkTimeEntity workTimeEntity) {
        logger.info("updateWorkTime param workTimeEntity "+workTimeEntity.toString());
        workTimeMapper.updateWorkTime(workTimeEntity);
    }

    @Override
    public WorkTimeEntity queryById(int id) {
        logger.info("queryById param id "+id);
        return workTimeMapper.queryById(id);
    }

    @Override
    public void deleteById(int id) {
        logger.info("deleteById param id "+id);
        workTimeMapper.deleteById(id);
    }

    @Override
    public List<WorkTimeEntity> selectAll() {
        logger.info("selectAll WorkTimeEntity");
        return workTimeMapper.selectAll();
    }
}
