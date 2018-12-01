package com.sensetime.iva.ipet.common;

import java.io.Serializable;

/**
 * @author gongchao
 * @date 16:53 2018/9/6
 */
public class ReturnMsg implements Serializable {

    public static String SUCCESS="success";
    public static String PROJECT_LIST="获取项目列表";
    public static String PROJECT_INIT="获取上次导入的项目";
    public static String CREATE_PROJECT_FAIL="创建立项失败";
    public static String CREATE_PROJECT_SUCCESS="创建立项成功";
    public static String UPDATE_PROJECT_SUCCESS="更新项目信息成功";
    public static String PROJECT_STATUS_JUNCTIONS="项目已结项,不能修改";
    public static String PROJECT_STAGE_NOT_EXIST="原项目阶段不存在";
    public static String ACTIVE_PROJECT_SUCCESS="结项项目激活成功";
    public static String PROJECT_FILTER_SUCCESS="获取所有过滤条件成功";
    public static String CACHE_PROJECT_SUCCESS="保存项目信息成功";
    public static String CLEAN_CACHE_PROJECT_SUCCESS="清除项目缓存信息成功";

    public static String PROJECT_PLAN_LIST="获取周报列表";
    public static String CREATE_PROJECT_PLAN_FAIL="新增或更新项目周报失败";
    public static String CREATE_PROJECT_PLAN_SUCCESS="新增或更新项目周报成功";
    public static String PROJECT_PLAN_NOT_EXIST="项目周报不存在";
    public static String DELETE_PROJECT_PLAN_SUCCESS="删除项目周报成功";

    public static String APPLY_LIST="获取实施清单列表";
    public static String APPLY_LIST_FAIL="获取实施清单列表失败";
    public static String APPLY_LIST_EMPTY="未查询到实施清单信息，不能更新";
    public static String CREATE_APPLY_LIST="新增或更新实施清单信息成功";
    public static String CREATE_APPLY_LIST_FAIL="新增或更新实施清单信息失败";
    public static String DELETE_APPLY_LIST="删除实施清单信息成功";


    public static String ADD_OR_UPDATE_SURVEY_LIST="新增或更新工勘清单信息成功";
    public static String SURVEY_LIST_EMPTY="工勘清单为空";
    public static String DELETE_SURVEY_LIST="删除工勘清单信息成功";
    public static String ADD_OR_UPDATE_SURVEY_LIST_FAIL="新增或更新工勘清单信息失败";
    public static String ADD_OR_UPDATE_DELIVER_LIST="更新或新增交付物信息成功";
    public static String DELIVER_LIST_EMPTY="交付物信息为空";
    public static String DELETE_LIST_EMPTY="删除交付物信息成功";
    public static String DELETE_WEEKLY_BOARD="删除项目计划详细信息";
    public static String DELETE_PROJECT_STAGE="删除项目阶段信息";


}
