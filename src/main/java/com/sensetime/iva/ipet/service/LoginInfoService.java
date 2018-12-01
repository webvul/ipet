package com.sensetime.iva.ipet.service;

import com.sensetime.iva.ipet.entity.LoginInfoEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : ChaiLongLong
 * @date : 2018/9/12 17:17
 */
@Service
public interface LoginInfoService {

    /**
     * 获取所有登陆信息，并按日期倒叙
     * @return 所有登陆信息
     */
    List<LoginInfoEntity> getAll();

    /**
     * 新增登陆记录
     * @param loginInfoEntity 登陆记录
     */
    void addLoginInfo(LoginInfoEntity loginInfoEntity);

    /**
     * 根据id删除登陆信息
     * @param id 登陆信息id
     */
    void deleteLoginInfoById(int id);

    /**
     * 根据id查询登陆信息
     * @param id 登陆信息id
     * @return 登陆信息
     */
    LoginInfoEntity queryById(int id);

    /**
     * 根据 用户 name 查询登陆信息,并按日期倒叙排列
     * @param name 登陆信息name
     * @return 登陆信息
     */
    List<LoginInfoEntity> queryByName(String name);

}
