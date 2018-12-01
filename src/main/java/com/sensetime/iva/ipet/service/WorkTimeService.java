package com.sensetime.iva.ipet.service;

import com.sensetime.iva.ipet.entity.WorkTimeEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: ChaiLongLong
 * @date : 2018/8/7 15:08
 */
@Service
public interface WorkTimeService {

    /**
     * 新增员工工时
     * @param workTimeEntity 员工工时
     */
    void addWorkTime(WorkTimeEntity workTimeEntity);

    /**
     * 更新员工工时
     * @param workTimeEntity 员工工时
     */
    void updateWorkTime(WorkTimeEntity workTimeEntity);

    /**
     * 根据id查询工时
     * @param id 工时id
     * @return 工时
     */
    WorkTimeEntity queryById(int id);

    /**
     * 根据id 删除工时
     * @param id 工时id
     */
    void deleteById(int id);

    /**
     * 查询所有工时
     * @return 所有工时信息
     */
    List<WorkTimeEntity> selectAll();
}
