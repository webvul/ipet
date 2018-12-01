package com.sensetime.iva.ipet.mapper;

import com.sensetime.iva.ipet.entity.WareList;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author  gongchao
 */
@Repository
public interface WareListMapper {
    /**
     * 删除
     * @param id id
     * @return 成功数量
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *新增硬件清单
     * @param ware 硬件清单
     * @return 硬件清单
     */
    int insert(WareList ware);

    /**
     *查询
     * @param id id
     * @return 硬件清单
     */
    WareList selectByPrimaryKey(Integer id);

    /**
     *查询全部硬件清单
     * @return 全部硬件清单
     */
    List<WareList> selectAll();

    /**
     *更新硬件清单
     * @param hardware 硬件清单
     * @return 成功数量
     */
    int updateByPrimaryKey(WareList hardware);
    /**
     * 根据项目id查询软硬件
     * @param projectId 项目id
     * @return 软硬件
     */
    @Select("select * from ware_list where project_id = #{project_id}")
    List<WareList> selectByProjectId(Integer projectId);
}