package com.sensetime.iva.ipet.service;

import com.sensetime.iva.ipet.entity.CompareDate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author gongchao
 * @date 14:33 2018/9/18
 */
@Component
public interface CompareDateService {
    /**
     *删除
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 新增
     * @param compareDate
     * @return
     */
    int insert(CompareDate compareDate);

    /**
     * 查询
     * @param id
     * @return
     */
    CompareDate selectByPrimaryKey(Integer id);

    /**
     * selectAll
     * @return
     */
    List<CompareDate> selectAll();

    /**
     * 更新
     * @param compareDate
     * @return
     */
    int updateByPrimaryKey(CompareDate compareDate);
}
