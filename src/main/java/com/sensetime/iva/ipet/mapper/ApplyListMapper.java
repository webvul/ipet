package com.sensetime.iva.ipet.mapper;

import com.sensetime.iva.ipet.entity.ApplyList;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author  gongchao
 */
@Repository
public interface ApplyListMapper {
    /**
     *删除
     * @param id id
     * @return 成功数量
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *新增实施清单
     * @param apply 实施清单
     * @return 成功数量
     */
    int insert(ApplyList apply);

    /**
     *查询
     * @param id id
     * @return 实施清单
     */
    ApplyList selectByPrimaryKey(Integer id);

    /**
     *查询所有实施清单
     * @return 实施清单
     */
    List<ApplyList> selectAll();

    /**
     *更新实施清单
     * @param apply 实施清单
     * @return 成功数量
     */
    int updateByPrimaryKey(ApplyList apply);
    /**
     * 根据项目id实施清单
     * @param projectId 项目id
     * @return 实施清单信息
     */
    @Select("select * from apply_list where project_id = #{projectId} order by stage,task_list")
    List<ApplyList> selectByProjectId(Integer projectId);
}