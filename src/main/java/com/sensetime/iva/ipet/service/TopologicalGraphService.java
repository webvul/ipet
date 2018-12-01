package com.sensetime.iva.ipet.service;

import com.sensetime.iva.ipet.entity.PhysicalMap;
import com.sensetime.iva.ipet.entity.TopologicalGraph;
import com.sensetime.iva.ipet.vo.form.FileForm;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author  gongchao
 */
@Service
public interface TopologicalGraphService {

    /**
     *删除
     * @param id id
     * @return  成功数量
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *新增拓扑图
     * @param topologicalGraph
     * @return 拓扑图
     */
    int insert(TopologicalGraph topologicalGraph);

    /**
     *id查询拓扑图
     * @param id id
     * @return 拓扑图
     */
    TopologicalGraph selectByPrimaryKey(Integer id);

    /**
     *查询全部拓扑图
     * @return 拓扑图
     */
    List<TopologicalGraph> selectAll();

    /**
     *更新拓扑图
     * @param topologicalGraph 拓扑图
     * @return 成功数量
     */
    int updateByPrimaryKey(TopologicalGraph topologicalGraph);

    /**
     * 上传拓扑图
     * @param file 上传的文件
     * @param pathPrefix 路径前缀即配置文件的路径
     * @param  projectId 項目id
     * @return  上传拓扑图的信息
     * @throws IOException
     */
    TopologicalGraph uploadTopologicalGraph(MultipartFile file, String pathPrefix,Integer projectId) throws Exception ;

    /**
     * 获取项目计划上传文件的数量
     * @param projectId 项目id
     * @return 数量
     */
    int selectCountByProjectId(Integer projectId);

    /**
     * 上傳文件
     * @param file 文件
     * @param projectId 項目id
     * @return 拓撲圖信息
     * @throws Exception
     */
     TopologicalGraph importTopologicalGraph(MultipartFile file, Integer projectId) throws Exception ;
    /**
     * 删除拓扑图
     * @param id id
     * @return 当前项目的拓扑图集合
     * @throws Exception
     */
    List<TopologicalGraph> deleteTopologicalGraph(Integer id) throws Exception;

    /**
     * 根据项目id查询拓扑图
     * @param projectId 项目id
     * @return 拓扑图
     */
    List<TopologicalGraph> selectByProjectId(Integer projectId);

    /**
     * 根据项目id导出拓扑图
     * @param wb 初始化excel
     * @param projectId 项目id
     * @throws Exception
     */
    void exportTopological(HSSFWorkbook wb, Integer projectId)throws  Exception;
    /**
     * 设置拓扑图图片
     * @param wb wb
     * @param sheet sheet
     * @param  topologicalGraphs 拓扑图数据图片
     * @throws Exception
     */
    void setImages(HSSFWorkbook wb, HSSFSheet sheet, List<TopologicalGraph> topologicalGraphs) throws  Exception;
    /**
     * 返回转换后的文件路径
     * @param topologicalGraphs 拓扑图
     * @return 路径信息
     */
    FileForm getFilePath(List<TopologicalGraph> topologicalGraphs);
}
