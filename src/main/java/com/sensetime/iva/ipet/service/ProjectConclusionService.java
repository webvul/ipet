package com.sensetime.iva.ipet.service;

import com.sensetime.iva.ipet.entity.ProjectConclusionEntity;
import com.sensetime.iva.ipet.vo.form.ProjectConclusionApprovalForm;
import com.sensetime.iva.ipet.vo.form.ProjectConclusionDataForm;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author : ChaiLongLong
 * @date : 2018/8/13 14:12
 */
@Service
public interface ProjectConclusionService {

    /**
     * 新增项目结项
     * @param projectConclusionEntity 项目结项
     */
    void addProjectConclusion(ProjectConclusionEntity projectConclusionEntity);

    /**
     * 更新项目结项
     * @param projectConclusionEntity 项目结项
     */
    void updateProjectConclusion(ProjectConclusionEntity projectConclusionEntity);

    /**
     * 根据项目id查询项目结项信息
     * @param id 项目id
     * @return 项目结项信息
     */
    ProjectConclusionEntity queryByProject(int id);

    /**
     * 根据id删除项目结项
     * @param id 项目结项信息id
     */
    void deleteById(int id);

    /**
     * 根据id查询项目结项信息
     * @param id 项目结项id
     * @return 项目结项信息
     */
    ProjectConclusionEntity queryById(int id);

    /**
     * 更新项目结项状态
     * @param id 结项状态
     * @param status 结项状态
     * @param updateTime 更新时间
     */
    void updateStatus(int id, int status, Timestamp updateTime);

    /**
     * 根据项目id获取项目结项数据
     * @param projectId 项目id
     * @return 项目立项数据
     */
    ProjectConclusionDataForm initProjectConclusionData(int projectId);

    /**
     * 提交项目结项数据
     * @param projectConclusionDataForm 项目结项数据表单
     * @throws Exception 异常
     */
    void submitProjectConclusionData(ProjectConclusionDataForm projectConclusionDataForm) throws Exception;

    /**
     * 发起结项
     * @param projectConclusionId 项目立项id
     * @throws Exception    IllegalArgumentException("当前状态不可结项")
     */
    void launchConclusion(int projectConclusionId) throws Exception;

    /**
     * 发起结项
     * @param projectConclusionApprovalForm 审批表单
     * @throws Exception 异常
     */
    void approvalConclusion(ProjectConclusionApprovalForm projectConclusionApprovalForm) throws Exception;

    /**
     * 项目结项excel导出
     * @param wb  excel模板
     * @param projectConclusionEntity  项目结项信息
     * @return 项目结项数据
     */
    HSSFWorkbook export(HSSFWorkbook wb, ProjectConclusionEntity projectConclusionEntity);
    /**
     *查询全部项目结项
     * @return  全部项目结项
     */
    List<ProjectConclusionEntity> selectAll();
}
