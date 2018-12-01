package com.sensetime.iva.ipet.service.impl;

import com.sensetime.iva.ipet.entity.LoginInfoEntity;
import com.sensetime.iva.ipet.mapper.LoginInfoMapper;
import com.sensetime.iva.ipet.service.LoginInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author : ChaiLongLong
 * @date : 2018/9/12 17:18
 */
@Component
public class LoginInfoServiceImpl implements LoginInfoService {
    private static final Logger logger = LoggerFactory.getLogger(LoginInfoServiceImpl.class);
    @Autowired
    LoginInfoMapper loginInfoMapper;

    @Override
    public List<LoginInfoEntity> getAll() {
        logger.info("getAll LoginInfoEntity");
        return loginInfoMapper.getAll();
    }

    @Override
    public void addLoginInfo(LoginInfoEntity loginInfoEntity) {
        logger.info("addLoginInfo loginInfoEntity [{}]",loginInfoEntity);
        loginInfoMapper.addLoginInfo(loginInfoEntity);
    }

    @Override
    public void deleteLoginInfoById(int id) {
        logger.info("deleteLoginInfoById id [{}]",id);
        loginInfoMapper.deleteLoginInfoById(id);
    }

    @Override
    public LoginInfoEntity queryById(int id) {
        logger.info("LoginInfoEntity id [{}]",id);
        return loginInfoMapper.queryById(id);
    }

    @Override
    public List<LoginInfoEntity> queryByName(String name) {
        logger.info("query LoginInfoEntity by name [{}]",name);
        return loginInfoMapper.queryByName(name);
    }
}
