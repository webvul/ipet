package com.sensetime.iva.ipet.mapper;

import com.sensetime.iva.ipet.entity.BusinessSystem;
import com.sensetime.iva.ipet.entity.BusinessSystemPlatform;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author gongchao
 */
@Repository
public interface BusinessSystemMapper {
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
    int updateByPrimaryKey(BusinessSystem businessSystem );

    /**
     * 名称查
     * @param name 名称
     * @return 系统
     */
    @Select("select * from business_system where name =#{name}")
    BusinessSystem selectByName(String name);


}