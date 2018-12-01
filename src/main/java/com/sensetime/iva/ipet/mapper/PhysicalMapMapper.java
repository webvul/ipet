package com.sensetime.iva.ipet.mapper;

import com.sensetime.iva.ipet.entity.PhysicalMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author  gongchao
 */
@Repository
public interface PhysicalMapMapper {
    /**
     *删除
     * @param id id
     * @return成功数量
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *新增物理图
     * @param physicalMap 物理图
     * @return 成功数量
     */
    int insert(PhysicalMap physicalMap);

    /**
     *id查询物理图
     * @param id id
     * @return 物理图
     */
    PhysicalMap selectByPrimaryKey(Integer id);

    /**
     *查询全部物理图     * @param projectId 项目id
     * @return  全部物理图
     */
    List<PhysicalMap> selectAll();

    /**
     * 更新物理图
     * @param physicalMap 物理图
     * @return 成功数量
     */
    int updateByPrimaryKey(PhysicalMap physicalMap);
    /**
     * 获取项目计划上传文件的数量
     * @param  projectId 项目id
     * @return 数量
     */
    @Select("select count(id) from physical_map where project_id = #{project_id}")
    int selectCountByProjectId(Integer projectId);

    /**
     *项目id查询拓扑图
     * @param projectId 项目id
     * @return 拓扑图
     */
    @Select("select * from physical_map where project_id = #{project_id}")
    List<PhysicalMap> selectByProjectId(Integer projectId);
}