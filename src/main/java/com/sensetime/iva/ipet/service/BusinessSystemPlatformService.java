package com.sensetime.iva.ipet.service;

import com.sensetime.iva.ipet.entity.BusinessSystemPlatform;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author  gongchao
 */
@Service
public interface BusinessSystemPlatformService { /**
 * 删除
 * @param id id
 * @return 成功数量
 */
int deleteByPrimaryKey(Integer id);

    /**
     * 插入
     * @param businessSystemPlatform 系统
     * @return 成功数量
     */
    int insert(BusinessSystemPlatform businessSystemPlatform);

    /**
     * 查询
     * @param id id
     * @return 对象
     */
    BusinessSystemPlatform selectByPrimaryKey(Integer id);

    /**
     * 查所有
     * @return 所有
     */
    List<BusinessSystemPlatform> selectAll();

    /**
     * 更新
     * @param businessSystemPlatform 系统
     * @return 成功数量
     */
    int updateByPrimaryKey(BusinessSystemPlatform businessSystemPlatform);

    /**
     * 名称和系统id查询
     * @param name 名称
     * @param   businessSystemId   系统id
     * @return 系统
     */
    BusinessSystemPlatform selectByNameAndSystemId(String name, Integer businessSystemId);
}
