package com.sensetime.iva.ipet.service.impl;

import com.sensetime.iva.ipet.entity.File;
import com.sensetime.iva.ipet.mapper.FileMapper;
import com.sensetime.iva.ipet.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author  gongchao
 */
@Component
public class FileServiceImpl implements FileService {
    private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Autowired
    FileMapper fileMapper;
    @Override
    public int deleteByPrimaryKey(Integer id) {
        return fileMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(File file) {
        logger.info("insert file "+file);
        return fileMapper.insert(file);
    }

    @Override
    public File selectByPrimaryKey(Integer id) {
        logger.info("selectByPrimaryKey id "+id);
        return fileMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<File> selectAll() {
        logger.info("selectAll file");
        return fileMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(File file) {
        logger.info("updateByPrimaryKey file"+file);
        return fileMapper.updateByPrimaryKey(file);
    }

    @Override
    public int selectCountByProjectId(Integer projectId) {
        logger.info("select  project file  count: projectId="+projectId);
        return fileMapper.selectCountByProjectId(projectId);
    }

    @Override
    public List<File> selectByProjectId(Integer projectId) {
        logger.info("selectByProjectId: projectId="+projectId);
        return fileMapper.selectByProjectId(projectId);
    }
}
