package com.sensetime.iva.ipet.common;

/**
 * @author  gongchao
 */
public class ProjectArgs {
    /**项目相关的参数如项目类型、项目状态、项目级别等*/
    /**可能后期会对应不同的数字，所以即使相同暂时对应不同静态变量*/
    /**项目类型*/
    public static final String PROJECT_TYPE_TXT_HT = "合同";
    public static final int PROJECT_TYPE_NUM_HT= 0;
    public static final String PROJECT_TYPE_TXT_PK = "PK";
    public static final int PROJECT_TYPE_NUM_PK = 1;
    public static final String PROJECT_TYPE_TXT_SD = "试点";
    public static final int PROJECT_TYPE_NUM_SD = 2;
    public static final String PROJECT_TYPE_TXT_HT_TO_SD = "试点转合同";
    public static final int PROJECT_TYPE_NUM_HT_TO_SD = 3;
    /**项目级别*/
    public static final String PROJECT_LEVEL_TXT_P0 = "普通";
    public static final int PROJECT_LEVEL_NUM_P0 = 0;
    public static final String PROJECT_LEVEL_TXT_P1 = "VIP";
    public static final int PROJECT_LEVEL_NUM_P1 = 1;

    /**是否新项目旧需求*/
    public static final int PROJECT_IS_FROM_OLD= 1;
    public static final int PROJECT_NOT_FROM_OLD=0;
    /**项目阶段*/
    public static final String PROJECT_STEP_ONE_CH="阶段一";
    public static final int PROJECT_STEP_ONE_EN=1;
    public static final String PROJECT_STEP_TWO_CH="阶段二";
    public static final int PROJECT_STEP_TWO_EN=2;
    public static final String PROJECT_STEP_THREE_CH="阶段三";
    public static final int PROJECT_STEP_THREE_EN=3;
    public static final String PROJECT_STEP_FOUR_CH="阶段四";
    public static final int PROJECT_STEP_FOUR_EN=4;
    public static final String PROJECT_STEP_FIVE_CH="阶段五";
    public static final int PROJECT_STEP_FIVE_EN=5;
    public static final String PROJECT_STEP_SIX_CH="阶段六";
    public static final int PROJECT_STEP_SIX_EN=6;
    public static final int PROJECT_REQUIRE_NUM=1;
    public static final String PROJECT_REQUIRE_CH="有，已填写";
    public static final int PROJECT_NONE_REQUIRE_NUM=0;
    public static final String PROJECT_NONE_REQUIRE_CH="无，未填写";

    /**项目状态 每次新增需要在reportMapper的查询方法新增状态及ReportService新增常量参数*/
    public static final String PROJECT_STATUS_READY_CH="待交付";
    public static final int PROJECT_STATUS_READY_NUM=1;
    public static final String PROJECT_STATUS_INPROGRESS_CH="交付中";
    public static final int PROJECT_STATUS_INPROGRESS_NUM=2;
    public static final String PROJECT_STATUS_DELAY_CH="延期";
    public static final int PROJECT_STATUS_DELAY_NUM=3;
    public static final String PROJECT_STATUS_ONLINE_CH="已上线";
    public static final int PROJECT_STATUS_ONLINE_NUM=4;
    public static final String PROJECT_STATUS_ACCEPTED_CH="已验收";
    public static final int PROJECT_STATUS_ACCEPTED_NUM=5;
    public static final String PROJECT_STATUS_JUNCTIONS_CH="已结项";
    public static final int PROJECT_STATUS_JUNCTIONS_NUM=6;
    public static final String PROJECT_STATUS_CANCEL_CH="已取消";
    public static final int PROJECT_STATUS_CANCEL_NUM=7;

    /**单一项目计划最大上传文件数量*/
    public static final int PROJECT_PLAN_MAX_COUNT=30;
    /**单一项目拓扑图或物理图最大上传文件数量*/
    public static final int MAP_OR_GRAPH_MAX_COUNT=20;
    /**周报类型*/
    public static final int STAGE_PROJECT_PLAN_TYPE=1;
    /**工时类型*/
    public static final int STAGE_PROJECT_MAN_HOUR_TYPE=2;
    /**项目干系人--项目成员类型*/
    public static final int STAGE_PROJECT_MEMBER_TYPE=0;
    /**项目干系人--接口单位类型*/
    public static final int STAGE_PROJECT_INTERFACE_UNIT_TYPE=1;

    /**项目结项状态 未处理*/
    public static final int UNHANDLE = 1;
    /**项目结项状态 待审批*/
    public static final int WAIT_FOR_APPROVAL = 2;
    /**项目结项状态 通过*/
    public static final int ADOPT = 3;
    /**项目结项状态 打回*/
    public static final int REPULSE = 4;

    /**项目结项身份*/
    public static final int PROJECT_CONCLUSION_IDENTITY_1=1;
    public static final String PROJECT_CONCLUSION_IDENTITY_1_CH="代表我司";
    public static final int PROJECT_CONCLUSION_IDENTITY_2=2;
    public static final String PROJECT_CONCLUSION_IDENTITY_2_CH="代表合作伙伴";
    public static final int PROJECT_CONCLUSION_IDENTITY_3=3;
    public static final String PROJECT_CONCLUSION_IDENTITY_3_CH="代表代理商";


    /**项目验收中项目实施的关键字(阶段-任务项)*/
    /**立项审批 立项申请*/
    public static final String PROJECT_APPROVAL_APPLY="0-0";
    public static final String APPROVAL="立项审批";
    public static final String APPLY="立项申请";
    /**立项审批 立项申请*/
    public static final String PROJECT_SHENPING="0-1";
    public static final String SHENPING="立项澄清";
    /**方案设计	产研方案设计*/
    public static final String DESIGN_SEARCH="1-0";
    public static final String DESIGN="方案设计";
    public static final String SEARCH="产研方案设计";
    /**方案设计	系统构架设计*/
    public static final String DESIGN_SYSTEM="1-1";
    public static final String SYSTEM="系统构架设计";
    /**方案设计	部署方案设计*/
    public static final String DESIGN_DELOPY="1-2";
    public static final String DELOPY="部署方案设计";
    /**定制化开发	产品研发*/
    public static final String DIY_PRODUCT="2-0";
    public static final String DIY="开发";
    public static final String DIY_DEV="定制化开发";
    /**定制化开发	交付开发*/
    public static final String DIY_DELIVERY="2-1";
    public static final String PRODUCT="产品开发";
    /**测试	产品测试*/
    public static final String TEST_PRODUCT_TEST="3-0";
    public static final String TEST="测试";
    public static final String PRODUCT_TEST="产品测试";

    /**预交付 工勘*/
    public static final String PRE_DELIVER="预交付";
    public static final String PRE_DELIVER_EXPLORATION="4-0";
    public static final String EXPLORATION="工勘";
    /**预交付 设备申请*/
    public static final String PRE_DELIVER_EDEVICE="4-1";
    public static final String DEVICE="设备申请";
    /**预交付 备货*/
    public static final String PRE_DELIVER_PREPARE="4-2";
    public static final String PREPARE="备货";
    /**预交付 预安装及验证*/
    public static final String PRE_DELIVER_CHECK="4-3";
    public static final String CHECK="预安装及验证";
    /**预交付 发货*/
    public static final String PRE_DELIVER_DELIVER="4-4";
    public static final String SEND="发货";

    /**交付 综合布线*/
    public static final String DELIVER="交付";
    public static final String DELIVER_LINE="5-0";
    public static final String LINE="综合布线";
    /**交付 部署*/
    public static final String DELIVER_DELOPY="5-1";
    public static final String DELI_DELOPY="部署";
    /**交付 接入调试*/
    public static final String DELIVER_DEBUGER="5-2";
    public static final String DEBUGER="接入调试";
    /**交付 迁移*/
    public static final String DELIVER_TRANS="5-3";
    public static final String TRANS="迁移";
    /**交付 升级*/
    public static final String DELIVER_PROMOTE="5-4";
    public static final String PROMOTE="升级";
    /**交付 回滚*/
    public static final String DELIVER_ROLLBACK="5-5";
    public static final String ROLLBACK="回滚";
    /**交付 扩容*/
    public static final String DELIVER_DILATATION="5-6";
    public static final String DILATATION="扩容";
    /**交付 裁撤*/
    public static final String DELIVER_ABOLISH="5-7";
    public static final String ABOLISH="裁撤";
    /**维护 实操培训*/
    public static final String MAINTAIN="维护";
    public static final String MAINTAIN_TRAIN="6-0";
    public static final String TRAIN="实操培训";
    /**维护 驻场*/
    public static final String MAINTAIN_STATION="6-1";
    public static final String STATION="驻场";
    /**维护 开发支持*/
    public static final String MAINTAIN_SUPPORT="6-2";
    public static final String SUPPORT="开发支持";
    /**维护 故障处理*/
    public static final String MAINTAIN_REPAIRE="6-3";
    public static final String REPAIR="故障处理";

    /**验收 验收*/
    public static final String ACCEPT_ACCEPT="7-0";
    public static final String PREFIX_ACCEPT="验收";
    public static final String SUFIX_ACCEPT="验收";
    /**售后 巡检*/
    public static final String AFTER_SALE="售后";
    public static final String AFTER_SALE_INSPECTION="8-0";
    public static final String INSPECTION="巡检";
    /**售后 保障*/
    public static final String AFTER_SALE_GUARANTEE="8-1";
    public static final String GUARANTEE="保障";

    /**交付物类型*/
    public static final String DOC_CH="文档";
    public static final String TOOL_CH="工具";
    public static final String OPERATE_CH="操作";
    public static final int DOC_NUM=1;
    public static final int TOOL_NUM=2;
    public static final int OPERATE_NUM=3;

    /**项目看板完成率和创建时间*/
    public static final String COMPLETION_RATE_ASC_CH="由低到高";
    public static final String COMPLETION_RATE_DESC_CH="由高到低";
    public static final int COMPLETION_RATE_ASC_NUM=1;
    public static final int COMPLETION_RATE_DESC_NUM=0;
    public static final String CREATE_TIME_ASC_CH="正序排列";
    public static final String CREATE_TIME_DESC_CH="倒序排列";
    public static final int CREATE_TIME_ASC_NUM=1;
    public static final int CREATE_TIME_DESC_NUM=0;

}
