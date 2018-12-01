package com.sensetime.iva.ipet.mapper;

import com.sensetime.iva.ipet.entity.BusinessSystemPlatform;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author  gongchao
 */
@Repository
public interface BusinessSystemPlatformMapper {
    /**
     * 删除
     * @param id  id
     * @return 成功数量
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入
     * @param record 业务平台
     * @return 出数量
     */
    int insert(BusinessSystemPlatform record);

    /**
     * 查询
     * @param id  id
     * @return 业务平台
     */
    BusinessSystemPlatform selectByPrimaryKey(Integer id);

    /**
     * 查询全部
     * @return 全部
     */
    List<BusinessSystemPlatform> selectAll();

    /**
     * 更新
     * @param record 业务平台
     * @return 成功数量
     */
    int updateByPrimaryKey(BusinessSystemPlatform record);

    /**
     * 根据名称查
     * @param name
     * @param businessSystemId 系统id
     * @return 系统下的平台
     */
    @Select({"select * from business_system_platform where name =#{name} and business_system_id=#{businessSystemId}"})
    BusinessSystemPlatform selectByNameAndSystemId(@Param("name") String name,@Param("businessSystemId")Integer businessSystemId);
}
