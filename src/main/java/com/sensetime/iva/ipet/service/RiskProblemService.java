package com.sensetime.iva.ipet.service;

import com.sensetime.iva.ipet.entity.RiskProblemEntity;
import com.sensetime.iva.ipet.vo.form.RiskProblemForm;
import com.sensetime.iva.ipet.vo.form.RiskProblemListForm;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: ChaiLongLong
 * @date : 2018/8/9 9:15
 */
@Service
public interface RiskProblemService {

    /**
     * 新增风险与问题
     * @param riskProblemEntity 风险与问题
     */
    void addRiskProblem(RiskProblemEntity riskProblemEntity);

    /**
     * 更新风险与问题
     * @param riskProblemEntity 风险与问题
     */
    void updateRiskProblem(RiskProblemEntity riskProblemEntity);

    /**
     * 根据项目id查询风险与问题
     * @param id 风险与问题id
     * @return 项目设备集合
     */
    List<RiskProblemEntity> queryByProject(int id);

    /**
     * 根据id删除风险与问题
     * @param id 风险与问题id
     */
    void deleteById(int id);

    /**
     * 根据id查询风险与问题
     * @param id 风险与问题id
     * @return 风险与问题
     */
    RiskProblemEntity queryById(int id);

    /**
     * 初始化项目风险与问题
     * @param projectId 项目id
     * @return 项目风险与问题form
     */
    List<RiskProblemForm> initRiskProblemData(Integer projectId);

    /**
     * 提交项目风险与计划
     * @param riskProblemListForm 表单
     * @throws Exception 异常
     */
    void submitRiskProblemData(RiskProblemListForm riskProblemListForm) throws Exception;

    /**
     * 导出成本统计数据（工时为最新一周的）
     * @param wb
     * @param projectId
     */
    void exportRiskProblem(HSSFWorkbook wb, Integer projectId);

    /**
     * 所有项目风险与计划
     * @return 项目风险与计划
     */
    List<RiskProblemEntity> selectAll();
}
