package com.sensetime.iva.ipet.mapper;

import com.sensetime.iva.ipet.entity.AreaEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: ChaiLongLong
 * @date : 2018/7/31 14:07
 *
 */
@Repository
public interface AreaMapper {

    /**
     *查询所有区域
     * @return 所有区域
     */
    List<AreaEntity> getAll();

    /**
     * 添加区域
     * @param areaEntity 区域参数
     */
    void addArea(AreaEntity areaEntity);

    /**
     * 更新区域
     * @param areaEntity 区域参数
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
