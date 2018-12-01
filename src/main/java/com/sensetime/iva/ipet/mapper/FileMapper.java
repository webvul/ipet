package com.sensetime.iva.ipet.mapper;

import com.sensetime.iva.ipet.entity.File;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author  gongchao
 */
@Repository
public interface FileMapper {
    /**
     * 删除
     * @param id id
     * @return 成功数量
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入
     * @param file 上传文件
     * @return 成功数量
     */
    int insert(File file);

    /**
     *查询
     * @param id id
     * @return 单挑条
     */
    File selectByPrimaryKey(Integer id);

    /**
     *全部
     * @return 全部数据
     */
    List<File> selectAll();

    /**
     *更新
     * @param file 文件
     * @return 成功数量
     */
    int updateByPrimaryKey(File file);

    /**
     * 获取项目上传文件的数量
     * @param projectId 项目计划id
     * @return 数量
     */
    @Select("select count(project_id) from file where project_id=#{projectId}")
    int selectCountByProjectId(Integer projectId);

    /**
     * 获取项目上传文件
     * @param projectId 项目id
     * @return 文件信息
     */
    @Select("select * from file where project_id=#{projectId}")
    List<File> selectByProjectId(Integer projectId);
}