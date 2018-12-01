package com.sensetime.iva.ipet.service.impl;

import com.sensetime.iva.ipet.entity.BusinessSystem;
import com.sensetime.iva.ipet.mapper.BusinessSystemMapper;
import com.sensetime.iva.ipet.service.BusinessSystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author  gongchao
 */
@Component
public class BusinessSystemServiceImpl implements BusinessSystemService {
    private static final Logger logger = LoggerFactory.getLogger(BusinessSystemServiceImpl.class);

    @Autowired
    BusinessSystemMapper businessSystemMapper;
    @Override
    public List<BusinessSystem> selectAll() {
        logger.info("selectAll BusinessSystem");
        return businessSystemMapper.selectAll();
    }

    @Override
    public BusinessSystem selectByPrimaryKey(Integer id) {
        logger.info("selectByPrimaryKey id="+id);
        return businessSystemMapper.selectByPrimaryKey(id);
    }

    @Override
    public BusinessSystem selectByName(String name) {
        logger.info("selectByName name="+name);
        return businessSystemMapper.selectByName(name);
    }

    @Override
    public int updateByPrimaryKey(BusinessSystem businessSystem) {
        logger.info("updateByPrimaryKey businessSystem="+businessSystem);
        return businessSystemMapper.updateByPrimaryKey(businessSystem);
    }

    @Override
    public int insert(BusinessSystem businessSystem) {
        logger.info("insert businessSystem="+businessSystem);
        return businessSystemMapper.insert(businessSystem);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        logger.info("deleteByPrimaryKey id="+id);
        return businessSystemMapper.deleteByPrimaryKey(id);
    }
}
