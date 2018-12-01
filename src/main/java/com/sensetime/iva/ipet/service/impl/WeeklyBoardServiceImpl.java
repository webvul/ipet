package com.sensetime.iva.ipet.service.impl;

import com.sensetime.iva.ipet.entity.WeeklyBoard;
import com.sensetime.iva.ipet.mapper.WeeklyBoardMapper;
import com.sensetime.iva.ipet.service.WeeklyBoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author  gongchao
 */
@Component
public class WeeklyBoardServiceImpl implements WeeklyBoardService {
    private static final Logger logger = LoggerFactory.getLogger(WeeklyBoardServiceImpl.class);

    @Autowired
    WeeklyBoardMapper weeklyBoardMapper;
    @Override
    public int deleteByPrimaryKey(Integer id) {
        logger.info("deleteByPrimaryKey id="+id);
        return weeklyBoardMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(WeeklyBoard weeklyBoard) {
        logger.info("insert weeklyBoard="+weeklyBoard);
        return weeklyBoardMapper.insert(weeklyBoard);
    }

    @Override
    public WeeklyBoard selectByPrimaryKey(Integer id) {
        logger.info("selectByPrimaryKey WeeklyBoard id="+id);
        return weeklyBoardMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<WeeklyBoard> selectAll() {
        logger.info("selectAll WeeklyBoard");
        return weeklyBoardMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(WeeklyBoard weeklyBoard) {
        logger.info("updateByPrimaryKey WeeklyBoard="+weeklyBoard);
        return weeklyBoardMapper.updateByPrimaryKey(weeklyBoard);
    }

    @Override
    public List<WeeklyBoard> selectByProjectId(Integer projectId) {
        logger.info("selectByProjectId projectId="+projectId);
        return weeklyBoardMapper.selectByProjectId(projectId);
    }
}
