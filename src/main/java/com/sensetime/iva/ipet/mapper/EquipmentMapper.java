package com.sensetime.iva.ipet.mapper;

import com.sensetime.iva.ipet.entity.EquipmentEntity;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: ChaiLongLong
 * @date : 2018/8/8 14:48
 */
@Repository
public interface EquipmentMapper {

    /**
     * 新增设备
     * @param equipmentEntity 设备
     */
    void addEquipment(EquipmentEntity equipmentEntity);

    /**
     * 更新设备
     * @param equipmentEntity 设备
     */
    void updateEquipment(EquipmentEntity equipmentEntity);

    /**
     * 根据项目id查询设备
     * @param id 设备id
     * @return 项目设备集合
     */
    List<EquipmentEntity> queryByProject(int id);

    /**
     * 根据id删除设备
     * @param id 设备id
     */
    void deleteById(int id);

    /**
     * 根据id查询设备
     * @param id 设备id
     * @return 设备
     */
    EquipmentEntity queryById(int id);

    /**
     *查询所有设备信息
     * @return 备信息
     */
    @Select("select * from equipment")
    List<EquipmentEntity> selectAll();
}
