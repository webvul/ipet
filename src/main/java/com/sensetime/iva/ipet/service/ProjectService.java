package com.sensetime.iva.ipet.service;

import com.sensetime.iva.ipet.entity.File;
import com.sensetime.iva.ipet.entity.Project;
import com.sensetime.iva.ipet.entity.ProjectStage;
import com.sensetime.iva.ipet.vo.form.ProjectFilterForm;
import com.sensetime.iva.ipet.vo.form.ProjectForm;
import com.sensetime.iva.ipet.vo.form.ProjectInitFilterForm;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author gongchao
 */
@Service
public interface ProjectService {
    /**
     * 查询所有的项目
     * @param  projectFilterForm 项目过滤条件
     * @return 所有项目集合
     */
    List<ProjectForm> selectAll(@Param("projectFilterForm") ProjectFilterForm projectFilterForm);

    /**
     * 根据id查项目
     * @param id 项目id
     * @return 单一项目
     */
    Project selectByPrimaryKey(Integer id);

    /**
     * 跟新项目
     * @param project 项目
     * @return 更新成功条数
     */
    int updateByPrimaryKey(Project project);

    /**
     * 保存项目
     * @param project 项目
     * @return 插入成功数量
     */
    int  insert(Project project);

    /**
     * 删除项目
     * @param id 项目id
     * @return 删除成功数量
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 上传解析的文件
     * @param file 上传的文件
     * @return 上传的项目信息
     * @throws Exception 异常
     */
    Project importProject(MultipartFile file) throws  Exception;

    /**
     *将解析的excel数据设置到project中
     * @param valuesList excel解析出的数据
     * @param project 解析前项目
     * @return 解析后的项目
     * @throws Exception 异常
     */
    Project setProject(List<String> valuesList, Project project) throws  Exception;

    /**
     * 项目立项
     * @param project  项目
     * @param projectStages 阶段
     * @return 项目成功数量
     * @throws Exception
     */
    Project createProject(Project project, List<ProjectStage> projectStages)throws  Exception;

    /**
     * 上传文件
     * @param file 文件
     * @return 上传之后的文件信息
     * @throws Exception IO异常
     */
    File upLoad(MultipartFile file)throws Exception;

    /**
     * 项目立项excel导出
     * @param wb  excel模板
     * @param project  项目立项信息
     * @return 项目立项数据
     */
    HSSFWorkbook export(HSSFWorkbook wb, Project project);

    /**
     *合并单元格
     * @param sheet excel模板
     */
    void addMergedRegion(HSSFSheet sheet);

    /**
     *删除项目
     *@param project 项目
     * @return 成功数量
     */
    int delete(Project project);


    /**
     *获取上次导入项目
     * @return 项目
     */
    Project init();

    /**
     * 根查询不在当前项目状态的项目
     * @param status 项目状态
     * @return 项目
     */
    List<Project> selectNotInStatus(Integer status);

    /**
     * 更改项目状态
     * @param  id 项目id
     * @param status 项目状态
     */
    void changeProjectStatus(int id, int status);


    /**
     * 更改项目状态
     * @param  id 项目id
     * @throws Exception 异常
     */
    void activeProject(int id) throws Exception;

    /**
     * 获取项目的过滤条件
     * @param  isActive 点击的否是活跃项目
     * @param  isPm 是否是PM
     * @param  isArea 区域
     * @throws  Exception
     * @return 项目过滤条件
     */
    ProjectInitFilterForm getFilter(Boolean isActive, Boolean isPm,Boolean isArea) throws Exception;

    /**
     * 查询不同的销售经理
     * @param createUserId PM
     * @return
     */
    List<String> getDistinctSaleManager(Integer createUserId);

    /**
     * 查询不同的解决方案经理
     * @param createUserId PM
     * @return
     */
    List<String> getDistinctSolutionManager(Integer createUserId);

    /**
     *更新项目
     * @param project 项目
     * @return 项目信息
     */
    Project cacheProject(Project project);

    /**
     * 清除项目
     * @return
     */
    Project cleanCacheProject();
}
