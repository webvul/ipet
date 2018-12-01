package com.sensetime.iva.ipet.service.impl;

import com.sensetime.iva.ipet.entity.BusinessTripEntity;
import com.sensetime.iva.ipet.entity.WorkTimeEntity;
import com.sensetime.iva.ipet.mapper.BusinessTripMapper;
import com.sensetime.iva.ipet.service.BusinessTripService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: ChaiLongLong
 * @date : 2018/8/8 14:53
 */
@Component
public class BusinessTripServiceImpl implements BusinessTripService {

    private static final Logger logger = LoggerFactory.getLogger(BusinessTripServiceImpl.class);

    @Autowired
    BusinessTripMapper businessTripMapper;

    @Override
    public void addBusinessTrip(BusinessTripEntity businessTripEntity) {
        logger.info("addBusinessTrip param businessTripEntity "+businessTripEntity.toString());
        businessTripMapper.addBusinessTrip(businessTripEntity);
    }

    @Override
    public void updateBusinessTrip(BusinessTripEntity businessTripEntity) {
        logger.info("updateBusinessTrip param businessTripEntity "+businessTripEntity.toString());
        businessTripMapper.updateBusinessTrip(businessTripEntity);
    }

    @Override
    public List<BusinessTripEntity> queryByProject(int id) {
        logger.info("queryByProject param id "+id);
        return businessTripMapper.queryByProject(id);
    }

    @Override
    public void deleteById(int id) {
        logger.info("deleteById param id "+id);
        businessTripMapper.deleteById(id);
    }

    @Override
    public BusinessTripEntity queryById(int id) {
        logger.info("queryById param id "+id);
        return businessTripMapper.queryById(id);
    }

    @Override
    public List<BusinessTripEntity> selectAll() {
        logger.info("selectAll BusinessTripEntity");
        return businessTripMapper.selectAll();
    }
}
