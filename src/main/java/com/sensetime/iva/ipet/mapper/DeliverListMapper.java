package com.sensetime.iva.ipet.mapper;

import com.sensetime.iva.ipet.entity.DeliverList;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author  gongchao
 */
@Service
public interface DeliverListMapper {
    /**
     * 删除
     * @param id id
     * @return 成功数量
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *新增交接清单
     * @param deliver 交接清单
     * @return 成功数量
     */
    int insert(DeliverList deliver);

    /**
     *id查询交接清单
     * @param id id
     * @return 交接清单
     */
    DeliverList selectByPrimaryKey(Integer id);

    /**
     *查询全部交接清单
     * @return 全部交接清单
     */
    List<DeliverList> selectAll();

    /**
     *更新交接清单
     * @param deliver 交接清单
     * @return成功数量
     */
    int updateByPrimaryKey(DeliverList deliver);
    /**
     * 根据项目id交付物
     * @param projectId 项目id
     * @return 交付物信息
     */
    @Select("select * from deliver_list where project_id = #{projectId}")
    List<DeliverList> selectByProjectId(Integer projectId);
}