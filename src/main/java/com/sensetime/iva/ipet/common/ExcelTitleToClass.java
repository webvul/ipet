package com.sensetime.iva.ipet.common;

/**
 * @author  gongchao
 */
public class ExcelTitleToClass {
    public static final String EN_YES= "Y";
    public static final String CH_YES = "是";
    public static final String NUM_YES = "1";
    public static final String EN_NO= "N";
    public static final String CH_NO = "否";
    public static final String NUM_NO = "0";
    /**根据Excel的标题去拆分放入对应的类中*/
    public static final String IMPORT_PROJECT = "立项申请";
    public static final String PROJECT = "项目基本信息";
    public static final String PROJECT_STAGE = "项目目标";
    public static final String PROJECT_REVIEW_RESULTS = "项目评审结果";
    /**立项导入最多解析到多少列*/
    public static final int PROJECT_MAX_COLUMN = 8;
    /**导出Excel的名称前缀及sheet名称*/
    public static final String EXPORT_PROJECT = "Project";
    /**导出Excel的名称*/
    public static final String EXPORT_PROJECT_PLAN = "项目计划";
    public static final String EXPORT_PROJECT_CHECK = "项目验收";
    /**导出Excel的sheet名称*/
    public static final String EXPORT_PROJECT_RELATED_PERSON = "项目干系人";
    public static final String EXPORT_PROJECT_CHECK_APPLY = "实施清单";
    public static final String EXPORT_PROJECT_CHECK_SURVEY = "工勘清单";
    public static final String EXPORT_PROJECT_CHECK_WARE = "软硬件清单";
    public static final String EXPORT_PROJECT_CHECK_TOPOLOGICAL = "拓扑图";
    public static final String EXPORT_PROJECT_CHECK_PHYSICAL = "物理图";
    public static final String EXPORT_PROJECT_CHECK_DELIVER = "交接单";


    /**项目结项信息*/
    public static final String EXPORT_PROJECT_CONCLUSION = "项目结项";
    public static final String EXPORT_PROJECT_CONCLUSION_SHEET = "结项报告";
    public static final String EXPORT_PROJECT_CONCLUSION_TITLE = "项目情况";
    public static final String EXPORT_PROJECT_CONCLUSION_TITLE_PROCESS = "过程";
    public static final String EXPORT_PROJECT_CONCLUSION_TITLE_ANALYSIS_SUMMARY = "分析总结";

    /**项目计划信息*/
    public static final String EXPORT_PROJECT_PLAN_COST_STATISTICS_SHEET = "成本与统计";
    public static final String EXPORT_PROJECT_PLAN_RISK_PROBLEM_SHEET = "项目风险&问题汇总";
    public static final String EXPORT_PROJECT_PLAN_WORK_TIME_TITLE = "工时统计";
    public static final String EXPORT_PROJECT_PLAN_BUSINESS_TRIP_TITLE = "差旅统计";
    public static final String EXPORT_PROJECT_PLAN_EQUIPMENT_TITLE = "设备统计";
    public static final String EXPORT_PROJECT_PLAN_RISK_PROBLEM_TITLE = "项目风险/问题汇总";



}
