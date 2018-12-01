package com.sensetime.iva.ipet.service;

import com.sensetime.iva.ipet.entity.BusinessSystem;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author  gongchao
 */
@Service
public interface BusinessSystemService {
    /**
     * 删除
     * @param id id
     * @return 成功数量
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入
     * @param businessSystem 系统
     * @return 成功数量
     */
    int insert(BusinessSystem businessSystem);

    /**
     * 查询
     * @param id id
     * @return 对象
     */
    BusinessSystem selectByPrimaryKey(Integer id);

    /**
     * 查所有
     * @return 所有
     */
    List<BusinessSystem> selectAll();

    /**
     * 更新
     * @param businessSystem 系统
     * @return 成功数量
     */
    int updateByPrimaryKey(BusinessSystem businessSystem);

    /**
     * 名称查平台
     * @param name 名称
     * @return 平台
     */
    BusinessSystem selectByName(String name);
}
