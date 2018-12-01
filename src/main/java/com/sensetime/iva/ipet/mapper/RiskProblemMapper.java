package com.sensetime.iva.ipet.mapper;

import com.sensetime.iva.ipet.entity.RiskProblemEntity;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: ChaiLongLong
 * @date : 2018/8/9 9:10
 */
@Repository
public interface RiskProblemMapper {

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
     * 所有项目风险与计划
     * @return 项目风险与计划
     */
    @Select("select * from risk_problem")
    List<RiskProblemEntity> selectAll();
}
