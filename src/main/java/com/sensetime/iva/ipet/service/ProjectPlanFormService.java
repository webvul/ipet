package com.sensetime.iva.ipet.service;

import com.sensetime.iva.ipet.vo.form.ProjectPlanForm;
import org.springframework.stereotype.Service;

/**
 * @author  gongchao
 */
@Service
public interface ProjectPlanFormService {
    /**
     * 根据项目id项目计划相关信息
     * @param projectId 项目id
     * @return 项目计划相关信息
     @throws Exception 异常
     */
    ProjectPlanForm getByProjectId(Integer projectId) throws Exception;

    /**
     * 更新或新建
     * @param projectPlanVo 项目计划信息
     * @return 更新后的项目计划相关信息
     */
    ProjectPlanForm createOrUpdate(ProjectPlanForm projectPlanVo);
}
