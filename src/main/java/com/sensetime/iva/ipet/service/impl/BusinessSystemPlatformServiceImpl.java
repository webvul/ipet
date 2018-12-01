package com.sensetime.iva.ipet.service.impl;

import com.sensetime.iva.ipet.entity.BusinessSystemPlatform;
import com.sensetime.iva.ipet.mapper.BusinessSystemPlatformMapper;
import com.sensetime.iva.ipet.service.BusinessSystemPlatformService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author  gongchao
 */
@Component
public class BusinessSystemPlatformServiceImpl implements BusinessSystemPlatformService {
    private static final Logger logger = LoggerFactory.getLogger(BusinessSystemPlatformServiceImpl.class);

    @Autowired
    BusinessSystemPlatformMapper businessSystemPlatformMapper;
    @Override
    public List<BusinessSystemPlatform> selectAll() {
        logger.info("selectAll BusinessSystemPlatform");
        return businessSystemPlatformMapper.selectAll();
    }

    @Override
    public BusinessSystemPlatform selectByPrimaryKey(Integer id) {
        logger.info("selectByPrimaryKey id="+id);
        return businessSystemPlatformMapper.selectByPrimaryKey(id);
    }

    @Override
    public BusinessSystemPlatform selectByNameAndSystemId(String name, Integer  businessSystemId) {
        logger.info("selectByNameAndSystemId name="+name+"businessSystemId="+businessSystemId);
        return businessSystemPlatformMapper.selectByNameAndSystemId(name,businessSystemId);
    }

    @Override
    public int updateByPrimaryKey(BusinessSystemPlatform businessSystemPlatform) {
        logger.info("updateByPrimaryKey businessSystemPlatform="+businessSystemPlatform);
        return businessSystemPlatformMapper.updateByPrimaryKey(businessSystemPlatform);
    }

    @Override
    public int insert(BusinessSystemPlatform businessSystemPlatform) {
        logger.info("insert businessSystemPlatform="+businessSystemPlatform);
        return businessSystemPlatformMapper.insert(businessSystemPlatform);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        logger.info("deleteByPrimaryKey id="+id);
        return businessSystemPlatformMapper.deleteByPrimaryKey(id);
    }
}
