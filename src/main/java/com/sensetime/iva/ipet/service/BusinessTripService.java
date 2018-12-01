package com.sensetime.iva.ipet.service;

import com.sensetime.iva.ipet.entity.BusinessTripEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: ChaiLongLong
 * @date : 2018/8/8 14:52
 */
@Service
public interface BusinessTripService {

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
     * 查询所有出差信息
     * @return 所有出差信息
     */
    List<BusinessTripEntity> selectAll();
}
