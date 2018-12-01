package com.sensetime.iva.ipet.service;

import com.sensetime.iva.ipet.vo.form.CostStatisticsForm;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

/**
 * @Author: ChaiLongLong
 * @date : 2018/8/10 17:04
 */
@Service
public interface CostStatisticsService {

    /**
     * 处理提交的成本统计
     * @param costStatisticsForm 成本统计
     * @throws Exception 异常
     */
    void submitCostStatisticsData(CostStatisticsForm costStatisticsForm) throws Exception;

    /**
     * 根据项目id查询成本统计
     * @param projectId 项目id
     * @return 成本统计封装form
     * @throws Exception 异常
     */
    CostStatisticsForm initCostStatisticsData(int projectId) throws Exception;

    /**
     * 导出成本统计数据（工时为最新一周的）
     * @param wb
     * @param projectId
     */
    void exportCostStatistics(HSSFWorkbook wb, Integer projectId);
}
