package com.sensetime.iva.ipet.service.impl;

import com.sensetime.iva.ipet.entity.EquipmentEntity;
import com.sensetime.iva.ipet.mapper.EquipmentMapper;
import com.sensetime.iva.ipet.service.EquipmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: ChaiLongLong
 * @date : 2018/8/8 15:35
 */
@Component
public class EquipmentServiceImpl implements EquipmentService {

    private static final Logger logger = LoggerFactory.getLogger(EquipmentServiceImpl.class);

    @Autowired
    EquipmentMapper equipmentMapper;

    @Override
    public void addEquipment(EquipmentEntity equipmentEntity) {
        logger.info("addEquipment param equipmentEntity "+equipmentEntity.toString());
        equipmentMapper.addEquipment(equipmentEntity);
    }

    @Override
    public void updateEquipment(EquipmentEntity equipmentEntity) {
        logger.info("updateEquipment param equipmentEntity "+equipmentEntity.toString());
        equipmentMapper.updateEquipment(equipmentEntity);
    }

    @Override
    public List<EquipmentEntity> queryByProject(int id) {
        logger.info("queryByProject param id "+id);
        return equipmentMapper.queryByProject(id);
    }

    @Override
    public void deleteById(int id) {
        logger.info("deleteById param id "+id);
        equipmentMapper.deleteById(id);
    }

    @Override
    public EquipmentEntity queryById(int id) {
        logger.info("queryById param id "+id);
        return equipmentMapper.queryById(id);
    }

    @Override
    public List<EquipmentEntity> selectAll() {
        logger.info("selectAll EquipmentEntity");
        return equipmentMapper.selectAll();
    }
}
