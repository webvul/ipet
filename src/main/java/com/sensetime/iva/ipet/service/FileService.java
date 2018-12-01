package com.sensetime.iva.ipet.service;

import com.sensetime.iva.ipet.entity.File;

import java.util.List;

/**
 * @author gongchao
 */
public interface FileService {
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
     * 获取项目计划上传文件的数量
     * @param projectId 项目id
     * @return 数量
     */
    int selectCountByProjectId(Integer projectId);
    /**
     * 获取项目上传文件
     * @param projectId 项目id
     * @return 文件信息
     */
    List<File> selectByProjectId(Integer projectId);
}
