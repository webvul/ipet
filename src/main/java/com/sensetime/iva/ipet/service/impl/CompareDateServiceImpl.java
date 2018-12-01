package com.sensetime.iva.ipet.service.impl;

import com.sensetime.iva.ipet.entity.CompareDate;
import com.sensetime.iva.ipet.mapper.CompareDateMapper;
import com.sensetime.iva.ipet.service.CompareDateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author gongchao
 * @date 14:34 2018/9/18
 */
@Service
public class CompareDateServiceImpl implements CompareDateService {
    private static final Logger logger = LoggerFactory.getLogger(CompareDateServiceImpl.class);
    @Autowired
    CompareDateMapper compareDateMapper;
    @Override
    public int deleteByPrimaryKey(Integer id) {
        logger.info("deleteByPrimaryKey param id "+id);
        return compareDateMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(CompareDate compareDate) {
        logger.info("insert param compareDate "+compareDate);
        return compareDateMapper.insert(compareDate);
    }

    @Override
    public CompareDate selectByPrimaryKey(Integer id) {
        logger.info("selectByPrimaryKey param id "+id);
        return compareDateMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<CompareDate> selectAll() {
        logger.info("selectAll param CompareDate");
        return compareDateMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(CompareDate compareDate) {
        logger.info("updateByPrimaryKey param compareDate "+compareDate);
        return compareDateMapper.updateByPrimaryKey(compareDate);
    }
}
