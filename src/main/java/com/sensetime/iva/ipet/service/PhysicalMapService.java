package com.sensetime.iva.ipet.service;

import com.sensetime.iva.ipet.entity.PhysicalMap;
import com.sensetime.iva.ipet.entity.WareList;
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
public interface PhysicalMapService {

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
     *查询全部物理图
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
     * 上传物理图
     * @param file 上传的文件
     * @param pathPrefix 路径前缀即配置文件的路径
     * @return  上传物理图的信息
     * @param  projectId 項目id
     * @throws Exception
     */
    PhysicalMap uploadPhysicalMap(MultipartFile file, String pathPrefix,Integer projectId) throws Exception;
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
     * @return  上傳后的文件信息
     * @throws Exception
     */
    PhysicalMap importPhysicalMap(MultipartFile file, Integer projectId) throws Exception;

    /**
     * 删除物理图
     * @param id id
     * @return 当前项目的物理图集合
     * @throws Exception
     */
    List<PhysicalMap> deletePhysicalMap(Integer id) throws Exception;

    /**
     * 根据项目id查询物理图
     * @param projectId 项目id
     * @return 物理图
     */
    List<PhysicalMap> selectByProjectId(Integer projectId);

    /**
     * 根据项目id导出物理图
     * @param wb 初始化excel
     * @param projectId 项目id
     * @throws Exception
     */
    void exportPhysical(HSSFWorkbook wb, Integer projectId)throws  Exception;
    /**
     * 设置物理图图片
     * @param wb wb
     * @param sheet sheet
     * @param  physicalMaps 物理图数据图片
     * @throws Exception
     */
    void setImages(HSSFWorkbook wb, HSSFSheet sheet, List<PhysicalMap> physicalMaps) throws  Exception;

    /**
     * 返回转换后的文件路径
     * @param physicalMaps 物理图
     * @return 路径信息
     */
    FileForm getFilePath(List<PhysicalMap> physicalMaps);
}
