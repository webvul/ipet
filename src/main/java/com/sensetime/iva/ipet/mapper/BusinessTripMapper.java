package com.sensetime.iva.ipet.mapper;

import com.sensetime.iva.ipet.entity.BusinessTripEntity;
import com.sensetime.iva.ipet.entity.WorkTimeEntity;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: ChaiLongLong
 * @date : 2018/8/8 14:48
 */
@Repository
public interface BusinessTripMapper {

    /**
     * 新增出差
     * @param businessTripEntity 出差
     */
    void addBusinessTrip(BusinessTripEntity businessTripEntity);

    /**
     * 更新出差
     * @param businessTripEntity 出差
     */
    void updateBusinessTrip(BusinessTripEntity businessTripEntity);

    /**
     * 根据项目id查询出差
     * @param id 项目id
     * @return 项目出差集合
     */
    List<BusinessTripEntity> queryByProject(int id);

    /**
     * 根据id删除出差
     * @param id 出差id
     */
    void deleteById(int id);

    /**
     * 根据id查询出差
     * @param id 出差id
     * @return 出差
     */
    BusinessTripEntity queryById(int id);

    /**
     * 查询所有出差
     * @return 所有出差信息
     */
    @Select("select * from business_trip")
    List<BusinessTripEntity> selectAll();
}
