package com.sensetime.iva.ipet.service;

import com.sensetime.iva.ipet.entity.AreaEntity;
import com.sensetime.iva.ipet.mapper.AreaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * @Author: ChaiLongLong
 * @Date: 2018/7/31 14:14
 *
 */
@Service
public interface AreaService {

    /**
     *查询所有区域
     * @return 所有区域
     */
    List<AreaEntity> getAll();

    /**
     * 添加区域
     * @param areaEntity
     */
    void addArea(AreaEntity areaEntity);

    /**
     * 更新区域
     * @param areaEntity
     */
    void updateArea(AreaEntity areaEntity);

    /**
     * 删除区域
     * @param id 区域id
     */
    void deleteArea(int id);

    /**
     * 根据区域id查询区域
     * @param id 区域id
     * @return 区域
     */
    AreaEntity queryById(int id);

    /**
     * 根据名称查询区域
     * @param name 区域名称
     * @return 查询结果
     */
    AreaEntity queryByName(String name);

}
