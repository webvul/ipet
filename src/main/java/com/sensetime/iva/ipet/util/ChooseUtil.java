package com.sensetime.iva.ipet.util;

import com.sensetime.iva.ipet.common.ProjectArgs;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author  gongchao
 */
public class ChooseUtil {
    /**汉字对用数字转换*/

    /**
     * 项目状态转汉字导出
     * @param status 状态
     * @return 汉字对应的状态
     */
    public static String getProjectStatusToCH(int status){
        switch(status){
            case ProjectArgs
                    .PROJECT_STATUS_READY_NUM:
                return ProjectArgs.PROJECT_STATUS_READY_CH ;
            case ProjectArgs
                    .PROJECT_STATUS_INPROGRESS_NUM:
                return ProjectArgs.PROJECT_STATUS_INPROGRESS_CH;
            case ProjectArgs
                    .PROJECT_STATUS_DELAY_NUM:
                return ProjectArgs.PROJECT_STATUS_DELAY_CH;
            case ProjectArgs
                    .PROJECT_STATUS_ONLINE_NUM:
                return ProjectArgs.PROJECT_STATUS_ONLINE_CH;
            case ProjectArgs
                    .PROJECT_STATUS_ACCEPTED_NUM:
                return ProjectArgs.PROJECT_STATUS_ACCEPTED_CH;
            case ProjectArgs
                    .PROJECT_STATUS_JUNCTIONS_NUM:
                return ProjectArgs.PROJECT_STATUS_JUNCTIONS_CH;
            case ProjectArgs
                    .PROJECT_STATUS_CANCEL_NUM:
                return ProjectArgs.PROJECT_STATUS_CANCEL_CH;
            case -1:
                return "";
            default:
                return "没有找到对应的项目状态";
        }
    }

    /**
     * 项目级别汉字转数字保存数据库
     * @param level 级别
     * @return 数字对应的级别
     */
    public static Integer getProjectLevel(String level){
        switch(level){
            case ProjectArgs
                    .PROJECT_LEVEL_TXT_P0:
                return ProjectArgs.PROJECT_LEVEL_NUM_P0;
            case ProjectArgs
                    .PROJECT_LEVEL_TXT_P1:
                return ProjectArgs.PROJECT_LEVEL_NUM_P1;
            default:
                return null;
        }
    }
    /**
     * 项目级别转汉字导出
     * @param level 级别
     * @return 汉字对应的级别
     */
    public static String getProjectLevelToCH(Integer level){
        switch(level){
            case ProjectArgs
                    .PROJECT_LEVEL_NUM_P0:
                return ProjectArgs.PROJECT_LEVEL_TXT_P0 ;
            case ProjectArgs
                    .PROJECT_LEVEL_NUM_P1:
                return ProjectArgs.PROJECT_LEVEL_TXT_P1;
            default:
                return "";
        }
    }
    /**
     * 项目类型汉字转数字保存数据库
     * @param type 项目类型
     * @return 数字对应的类型
     */
    public static Integer getProjectType(String type){
        switch(type){
            case ProjectArgs
                    .PROJECT_TYPE_TXT_HT:
                return ProjectArgs.PROJECT_TYPE_NUM_HT;
            case ProjectArgs
                    .PROJECT_TYPE_TXT_HT_TO_SD:
                return ProjectArgs.PROJECT_TYPE_NUM_HT_TO_SD;
            case ProjectArgs
                    .PROJECT_TYPE_TXT_SD:
                return ProjectArgs.PROJECT_TYPE_NUM_SD;
            case ProjectArgs
                    .PROJECT_TYPE_TXT_PK:
                return ProjectArgs.PROJECT_TYPE_NUM_PK;
            default:
                return null;
        }
    }
    /**
     * 项目类型汉字转汉字保导出
     * @param type 项目类型
     * @return 数字汉字的类型
     */
    public static String getProjectTypeToCH(Integer type){
        switch(type){
            case ProjectArgs
                    .PROJECT_TYPE_NUM_HT:
                return ProjectArgs.PROJECT_TYPE_TXT_HT;
            case ProjectArgs
                    .PROJECT_TYPE_NUM_HT_TO_SD:
                return ProjectArgs.PROJECT_TYPE_TXT_HT_TO_SD;
            case ProjectArgs
                    .PROJECT_TYPE_NUM_SD:
                return ProjectArgs.PROJECT_TYPE_TXT_SD;
            case ProjectArgs
                    .PROJECT_TYPE_NUM_PK:
                return ProjectArgs.PROJECT_TYPE_TXT_PK;
            default:
                return "";
        }
    }
    /**
     * 将excel项目阶段转化成数字
     * @param step  项目阶段
     * @return 数字对应项目阶段
     */
    public static int getProjectStep(String step){
        switch(step){
            case ProjectArgs
                    .PROJECT_STEP_ONE_CH:
                return ProjectArgs.PROJECT_STEP_ONE_EN;
            case ProjectArgs
                    .PROJECT_STEP_TWO_CH:
                return ProjectArgs.PROJECT_STEP_TWO_EN;
            case ProjectArgs
                    .PROJECT_STEP_THREE_CH:
                return ProjectArgs.PROJECT_STEP_THREE_EN;
            case ProjectArgs
                    .PROJECT_STEP_FOUR_CH:
                return ProjectArgs.PROJECT_STEP_FOUR_EN;
            case ProjectArgs
                    .PROJECT_STEP_FIVE_CH:
                return ProjectArgs.PROJECT_STEP_FIVE_EN;
            case ProjectArgs
                    .PROJECT_STEP_SIX_CH:
                return ProjectArgs.PROJECT_STEP_SIX_EN;
            default:
                return -1;
        }
    }
    /**
     * 将excel项目阶段转化成汉字
     * @param step  项目阶段
     * @return 汉字项目阶段
     */
    public static String getProjectStepToCH(int step){
        switch(step){
            case ProjectArgs
                    .PROJECT_STEP_ONE_EN:
                return ProjectArgs.PROJECT_STEP_ONE_CH;
            case ProjectArgs
                    .PROJECT_STEP_TWO_EN:
                return ProjectArgs.PROJECT_STEP_TWO_CH;
            case ProjectArgs
                    .PROJECT_STEP_THREE_EN:
                return ProjectArgs.PROJECT_STEP_THREE_CH;
            case ProjectArgs
                    .PROJECT_STEP_FOUR_EN:
                return ProjectArgs.PROJECT_STEP_FOUR_CH;
            case ProjectArgs
                    .PROJECT_STEP_FIVE_EN:
                return ProjectArgs. PROJECT_STEP_FIVE_CH;
            case ProjectArgs
                    .PROJECT_STEP_SIX_EN:
                return ProjectArgs. PROJECT_STEP_SIX_CH;
            case -1:
                return "";
            default:
                return "未查询到对应阶段的信息";
        }
    }
    /**
     * 将ecxel有无需求转换成数字
     * @param require 需求
     * @return 数字对应需求
     */
    public static int getProjectRequire(String require){
        switch(require){
            case ProjectArgs
                    .PROJECT_REQUIRE_CH:
                return ProjectArgs.PROJECT_REQUIRE_NUM;
            case ProjectArgs
                    .PROJECT_NONE_REQUIRE_CH:
                return ProjectArgs.PROJECT_NONE_REQUIRE_NUM;
            default:
                return -1;
        }
    }
    /**
     * 将ecxel有无需求转换成汉字
     * @param require 需求
     * @return 汉字对应需求
     */
    public static String getProjectRequireToCH(int require){
        switch(require){
            case ProjectArgs
                    .PROJECT_REQUIRE_NUM:
                return ProjectArgs. PROJECT_REQUIRE_CH;
            case ProjectArgs
                    .PROJECT_NONE_REQUIRE_NUM:
                return ProjectArgs. PROJECT_NONE_REQUIRE_CH;
            case -1:
                return "";
            default:
                return "找不到对应产需求品或定制化需求";
        }
    }

    /**
     * 将项目结项中的身份转为汉字
     * @param require 需求
     * @return 汉字对应需求
     */
    public static String getProjectConclusionIdentityToCH(int require){
        switch(require){
            case ProjectArgs
                    .PROJECT_CONCLUSION_IDENTITY_1:
                return ProjectArgs.PROJECT_CONCLUSION_IDENTITY_1_CH;
            case ProjectArgs
                    .PROJECT_CONCLUSION_IDENTITY_2:
                return ProjectArgs.PROJECT_CONCLUSION_IDENTITY_2_CH;
            case ProjectArgs
                    .PROJECT_CONCLUSION_IDENTITY_3:
                return ProjectArgs.PROJECT_CONCLUSION_IDENTITY_3_CH;
            default:
                return "";
        }
    }

    /**
     * 交付物数字转汉字
     * @param type 类型
     * @return 汉字对应的级别
     */
    public static String getDeliverType(int type){
        switch(type){
            case ProjectArgs
                    .DOC_NUM:
                return ProjectArgs.DOC_CH;
            case ProjectArgs
                    .TOOL_NUM:
                return ProjectArgs.TOOL_CH;
            case ProjectArgs
                    .OPERATE_NUM:
                return ProjectArgs.OPERATE_CH;
            default:
                return "无对应类型";
        }
    }

    /**
     *获取用户项目过滤的活跃项目状态条件
     * @return
     */
    public static Map<Integer,String> getActiveStatusMap(){
        Map<Integer,String> statusMap = new HashMap<>(16);
        statusMap.put(ProjectArgs.PROJECT_STATUS_READY_NUM,ProjectArgs.PROJECT_STATUS_READY_CH);
        statusMap.put(ProjectArgs.PROJECT_STATUS_INPROGRESS_NUM,ProjectArgs.PROJECT_STATUS_INPROGRESS_CH);
        statusMap.put(ProjectArgs.PROJECT_STATUS_DELAY_NUM,ProjectArgs.PROJECT_STATUS_DELAY_CH);
        statusMap.put(ProjectArgs.PROJECT_STATUS_ONLINE_NUM,ProjectArgs.PROJECT_STATUS_ONLINE_CH);
        statusMap.put(ProjectArgs.PROJECT_STATUS_ACCEPTED_NUM,ProjectArgs.PROJECT_STATUS_ACCEPTED_CH);
        statusMap.put(ProjectArgs.PROJECT_STATUS_CANCEL_NUM,ProjectArgs.PROJECT_STATUS_CANCEL_CH);
        return statusMap;
    }
    /**
     *获取用户项目过滤的结项项目状态条件
     * @return
     */
    public static Map<Integer,String> getAcceptStatusMap(){
        Map<Integer,String> statusMap = new HashMap<>(16);
        statusMap.put(ProjectArgs.PROJECT_STATUS_JUNCTIONS_NUM,ProjectArgs.PROJECT_STATUS_JUNCTIONS_CH);
        return statusMap;
    }
    /**
     *获取用户项目过滤的项目级别条件
     * @return
     */
    public static Map<Integer,String> getLevelMap(){
        Map<Integer,String> levelMap = new HashMap<>(16);
        levelMap.put(ProjectArgs.PROJECT_LEVEL_NUM_P0,ProjectArgs.PROJECT_LEVEL_TXT_P0);
        levelMap.put(ProjectArgs.PROJECT_LEVEL_NUM_P1,ProjectArgs.PROJECT_LEVEL_TXT_P1);
        return levelMap;
    }
    /**
     *获取用户项目过滤的项目类型条件
     * @return
     */
    public static Map<Integer,String> getTypeMap(){
        Map<Integer,String> typeMap = new HashMap<>(16);
        typeMap.put(ProjectArgs.PROJECT_TYPE_NUM_HT,ProjectArgs.PROJECT_TYPE_TXT_HT);
        typeMap.put(ProjectArgs.PROJECT_TYPE_NUM_PK,ProjectArgs.PROJECT_TYPE_TXT_PK);
        typeMap.put(ProjectArgs.PROJECT_TYPE_NUM_SD,ProjectArgs.PROJECT_TYPE_TXT_SD);
        typeMap.put(ProjectArgs.PROJECT_TYPE_NUM_HT_TO_SD,ProjectArgs.PROJECT_TYPE_TXT_HT_TO_SD);
        return typeMap;
    }
    /**
     *获取项目创建时间排序的过滤条件
     * @return
     */
    public static Map<Integer,String> getCreateTimeMap(){
        Map<Integer,String> createTimeMap = new HashMap<>(16);
        createTimeMap.put(ProjectArgs.CREATE_TIME_ASC_NUM,ProjectArgs.CREATE_TIME_ASC_CH);
        createTimeMap.put(ProjectArgs.CREATE_TIME_DESC_NUM,ProjectArgs.CREATE_TIME_DESC_CH);
        return createTimeMap;
    }
    /**
     *获取项目看板完成率排序的过滤条件
     * @return
     */
    public static Map<Integer,String> getCompletionRateMap(){
        Map<Integer,String> completionRateMap = new HashMap<>(16);
        completionRateMap.put(ProjectArgs.COMPLETION_RATE_ASC_NUM,ProjectArgs.COMPLETION_RATE_ASC_CH);
        completionRateMap.put(ProjectArgs.COMPLETION_RATE_DESC_NUM,ProjectArgs.COMPLETION_RATE_DESC_CH);
        return completionRateMap;
    }

    /**
     * 集合装字符串
     * @param list
     * @return
     */
    public static String listToString(List<Object> list){
        return StringUtils.join(list.toArray(), ",");
    }
}
