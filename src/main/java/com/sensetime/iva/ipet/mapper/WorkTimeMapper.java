package com.sensetime.iva.ipet.mapper;

import com.sensetime.iva.ipet.entity.WorkTimeEntity;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: ChaiLongLong
 * @date : 2018/8/7 15:06
 */
@Repository
public interface WorkTimeMapper {

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
    @Select("select * from work_time")
    List<WorkTimeEntity> selectAll();
}
