package com.sensetime.iva.ipet.mapper;

import com.sensetime.iva.ipet.entity.LoginInfoEntity;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @Author: ChaiLongLong
 * @date : 2018/9/12 14:07
 *
 */
@Repository
public interface LoginInfoMapper {

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
