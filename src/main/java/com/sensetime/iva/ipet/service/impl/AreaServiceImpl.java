package com.sensetime.iva.ipet.service.impl;

import com.sensetime.iva.ipet.entity.AreaEntity;
import com.sensetime.iva.ipet.mapper.AreaMapper;
import com.sensetime.iva.ipet.service.AreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author : ChaiLongLong
 * @date : 2018/8/3 13:57
 */
@Component
public class AreaServiceImpl implements AreaService {

    private static final Logger logger = LoggerFactory.getLogger(AreaServiceImpl.class);

    @Autowired
    AreaMapper areaMapper;

    @Override
    public List<AreaEntity> getAll() {
        return areaMapper.getAll();
    }

    @Override
    public void addArea(AreaEntity areaEntity) {
        logger.info("addArea param areaEntity "+areaEntity.toString());
        areaEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        areaMapper.addArea(areaEntity);
    }

    @Override
    public void updateArea(AreaEntity areaEntity) {
        logger.info("updateArea param areaEntity "+areaEntity.toString());
        areaEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        areaMapper.updateArea(areaEntity);
    }

    @Override
    public void deleteArea(int id) {
        logger.info("deleteArea param id "+id);
        areaMapper.deleteArea(id);
    }

    @Override
    public AreaEntity queryById(int id) {
        logger.info("queryById param id "+id);
        return areaMapper.queryById(id);
    }

    @Override
    public AreaEntity queryByName(String name) {
        logger.info("queryByName param name "+name);
        return areaMapper.queryByName(name);
    }
}
