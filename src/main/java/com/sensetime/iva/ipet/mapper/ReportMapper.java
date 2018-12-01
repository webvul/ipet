package com.sensetime.iva.ipet.mapper;

import com.sensetime.iva.ipet.common.ProjectArgs;
import com.sensetime.iva.ipet.vo.form.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author gongchao
 * @date 9:44 2018/9/13
 */
@Repository
public interface ReportMapper {

    /**
     * 获取当前登陆人的同一区域的项目经理的id集合
     * @param areaId 区域id
     * @return
     */
    @Select("select id from account where area_id =#{areaId}")
    List<Integer> getSameUserByAreaId(Integer areaId);
    /**
     * 获取每周的立项数量。从本周六到下周五为一个区间
     * @param  userIds 项目经理id集合
     * @return
     */
    @Select("<script> SELECT DATE_FORMAT(date_sub(DATE_FORMAT(create_time,'%Y-%m-%d'),interval-2 day),'%Y-%u') as date,"
            +" count(id) count FROM project "+
            "<if test='userIds!=null'>"
            + " where create_user_id in (${userIds})"
            + "</if>"+
            " group by date </script>")
    List<ReportBoardFrom> getReportBoardCount(@Param("userIds") String userIds);

    /**
     * 根據項目狀態獲取區域信息
     * @param types 项目状态
     * @return
     */
    @Select("<script> select count(p.id) areaAmount,r.name areaName from project p" +
            " left join account a on a.id=p.create_user_id" +
            " left join area r on r.id=a.area_id"
            + "<if test='types!=null'>"
            + " where p.type in (${types})"
            + "</if>"+
            " GROUP BY areaName </script>")
    List<ReportProjectAreaForm> getReportProjectArea(@Param("types") String types );

    /**
     * 获取项目类型
     * @param userIds 项目经理id集合
     * @return
     */
    @Select("<script> select count(id) countAmount ,case type " +
            " when "+ProjectArgs.PROJECT_TYPE_NUM_HT+ " then '" + ProjectArgs.PROJECT_TYPE_TXT_HT +
            "' when "+ProjectArgs.PROJECT_TYPE_NUM_PK+ " then '" + ProjectArgs.PROJECT_TYPE_TXT_PK +
            "' when "+ProjectArgs.PROJECT_TYPE_NUM_SD+ " then '"+ ProjectArgs.PROJECT_TYPE_TXT_SD +
            "' when "+ProjectArgs.PROJECT_TYPE_NUM_HT_TO_SD+ " then '"+ ProjectArgs.PROJECT_TYPE_TXT_HT_TO_SD +
            "' else '未知类型' end as typeName from project"
            + "<if test='userIds!=null'>"
            + " where create_user_id in (${userIds})"
            + "</if>"+
            " GROUP BY typeName </script>")
    List<ReportProjectTypeForm> getReportProjectTypeForm(@Param("userIds") String userIds);
    /**
     * 根據項目狀態獲取區域信息
     * @param userIds  项目经理id
     * @param statusList  项目状态
     * @return
     */
    @Select("<script> select serial, name as projectName,case status " +
            " when "+ProjectArgs.PROJECT_STATUS_READY_NUM+ " then '" + ProjectArgs.PROJECT_STATUS_READY_CH +
            "' when "+ProjectArgs.PROJECT_STATUS_INPROGRESS_NUM+ " then '" + ProjectArgs.PROJECT_STATUS_INPROGRESS_CH +
            "' when "+ProjectArgs.PROJECT_STATUS_DELAY_NUM+ " then '"+ ProjectArgs.PROJECT_STATUS_DELAY_CH +
            "' when "+ProjectArgs.PROJECT_STATUS_ONLINE_NUM+ " then '"+ ProjectArgs.PROJECT_STATUS_ONLINE_CH +
            "' when "+ProjectArgs.PROJECT_STATUS_ACCEPTED_NUM+ " then '"+ ProjectArgs.PROJECT_STATUS_ACCEPTED_CH +
            "' when "+ProjectArgs.PROJECT_STATUS_JUNCTIONS_NUM+ " then '"+ ProjectArgs.PROJECT_STATUS_JUNCTIONS_CH +
            "' when "+ProjectArgs.PROJECT_STATUS_CANCEL_NUM+ " then '"+ ProjectArgs.PROJECT_STATUS_CANCEL_CH +
            "' else '未知状态' end as projectStatus from project "
            + "<if test='userIds!=null and statusList!=null'>"
            + " where create_user_id in (${userIds}) and status in (${statusList}) "
            + "</if>"
            + "<if test='userIds==null and statusList!=null'>"
            + " where status in (${statusList})"
            + "</if>"
            + "<if test='userIds!=null and statusList==null'>"
            + " where create_user_id in (${userIds})"
            + "</if>  order by create_time desc </script>")
    List<ReportDelayProjectForm> getReportDelayProjectForm(@Param("userIds") String userIds,@Param("statusList") String statusList);

    /**
     * 获取系统数量
     * @param userIds
     * @return
     */
    @Select("<script> select count(s.id) systemAmount,IFNULL(b.name,'未填写') systemName" +
            " from project_stage s" +
            " left join project p on p.id=s.project_id" +
            " left join business_system b on b.id=s.business_system_id" +
            "<if test='userIds!=null'>"
            + " where p.create_user_id in (${userIds})"
            + "</if>"+
            " GROUP BY systemName </script>")
    List<ReportSystemFrom> getReportSystemFrom(@Param("userIds") String userIds);

    /**
     * 获取平台数量
     * @param userIds
     * @return
     */
    @Select("<script> select count(s.id) platformAmount,IFNULL(b.name,'未填写') platformName" +
            " from project_stage s" +
            " left join project p on p.id=s.project_id" +
            " left join business_system_platform b on b.id=s.platform_id" +
             "<if test='userIds!=null'>"
            + " where p.create_user_id in (${userIds})"
            + "</if>"+
            " GROUP BY platformName </script>")
    List<ReportPlatformFrom> getReportPlatformFrom(@Param("userIds") String userIds);


    /**
     * 根据创建时间分组,状态查询项目数量
     * @param status 项目状态
     * @return
     */
    @Select("<script> SELECT DATE_FORMAT(create_time,'%Y-%m') as date," +
            " count(id) count FROM project" +
            "<if test='status!=null'>"
            + " where status in (${status})"
            + "</if>"+
            " group by date </script>")
    List<ReportProjectStatisticForm> getReportProjectByCreateTimeAndStatus(@Param("status") String status);
   /**
    * 根据更新时间分组，项目状态查询项目数量
    * @param status 项目状态
    * @return
    * */
   @Select("<script> SELECT DATE_FORMAT(update_time,'%Y-%m') as date," +
           " count(id) count FROM project" +
           "<if test='status!=null'>"
           + " where status in (${status})"
           + "</if>"+
           " group by date </script>")
    List<ReportProjectStatisticForm> getReportProjectByUpdateTimeAndStatus(@Param("status") String status);

    /**
     * 获取对应项目状态下的问题及帮助
     * @param  status 项目状态
     * @param  userIds pmID
     * @return
     */
    @Select("<script> SELECT a.risk_and_help as riskAndHelp ,b.name as projectName " +
            "FROM project_plan a JOIN " +
              "( SELECT MAX( pp.id ) AS ppid,p.id as pid " +
                 "FROM project_plan pp JOIN project p ON p.id = pp.project_id" +
             "<if test='userIds!=null and status!=null'>"
            + " where p.create_user_id in (${userIds}) and p.status =#{status} "
            + "</if>"
            + "<if test='userIds==null and status!=null'>"
            + " where p.status = #{status}"
            + "</if>"
            + "<if test='userIds!=null and status==null'>"
            + " where p.create_user_id in (${userIds})"
            + "</if> "+
            " GROUP BY p.id ) t" +
            " ON t.ppid = a.id " +
            "join project b on t.pid=b.id </script>")
    List<ReportRiskAndHelpForm> getReportRiskAndHelpForm(@Param("status")String status,@Param("userIds")String userIds);

    /**
     * 获取项目状态和项目经理id查询结项项目
     * @param userIds
     * @param  status
     * @return
     */
    @Select("<script> SELECT c.date, IFNULL( t1.count1, 0 ) amount FROM compare_date c " +
            "LEFT JOIN ( SELECT DATE_FORMAT(p.update_time,'%Y-%m') as date, COUNT( p.id ) AS count1" +
                         " FROM project p" +
                        "<if test='userIds!=null and status!=null'>"
                        + " where p.create_user_id in (${userIds}) and p.status =#{status} "
                        + "</if>"
                        + "<if test='userIds==null and status!=null'>"
                        + " where p.status = #{status}"
                        + "</if>"
                        + "<if test='userIds!=null and status==null'>"
                        + " where p.create_user_id in (${userIds})"
                        + "</if> "+
                        " GROUP BY date )" +
                        " t1 ON c.date = t1.date ORDER BY c.date </script>")
    List<ReportJunctionsBaseForm> getReportJunctionsBaseForm(@Param("userIds")String userIds,@Param("status")String status);
    /**
     * 获取结项的项目经理id和项目类型分布
     * @param userIds
     * @param  type
     * @return
     */
    @Select("<script> SELECT c.date, IFNULL( t1.count1, 0 ) amount FROM compare_date c " +
            "LEFT JOIN ( SELECT DATE_FORMAT(p.update_time,'%Y-%m') as date, COUNT( p.id ) AS count1" +
            " FROM project p where p.status = " +ProjectArgs.PROJECT_STATUS_JUNCTIONS_NUM+
            "<if test='userIds!=null and type!=null'>"
            + " and p.create_user_id in (${userIds}) and p.type =#{type} "
            + "</if>"
            + "<if test='userIds==null and type!=null'>"
            + " and p.type = #{type}"
            + "</if>"
            + "<if test='userIds!=null and type==null'>"
            + " and p.create_user_id in (${userIds})"
            + "</if> "+
            " GROUP BY date )" +
            " t1 ON c.date = t1.date ORDER BY c.date </script>")
    List<ReportJunctionsBaseForm> getReportTypeBaseJunctionsForm(@Param("userIds")String userIds,@Param("type")String type);
    /**
     * 获取结项的项目经理id和项目级别分布
     * @param userIds
     * @param  level
     * @return
     */
    @Select("<script> SELECT c.date, IFNULL( t1.count1, 0 ) amount FROM compare_date c " +
            "LEFT JOIN ( SELECT DATE_FORMAT(p.update_time,'%Y-%m') as date, COUNT( p.id ) AS count1" +
            " FROM project p where p.status = " +ProjectArgs.PROJECT_STATUS_JUNCTIONS_NUM+
            "<if test='userIds!=null and level!=null'>"
            + " and p.create_user_id in (${userIds}) and p.level =#{level} "
            + "</if>"
            + "<if test='userIds==null and level!=null'>"
            + " and p.level = #{level}"
            + "</if>"
            + "<if test='userIds!=null and level==null'>"
            + " and p.create_user_id in (${userIds})"
            + "</if> "+
            " GROUP BY date )" +
            " t1 ON c.date = t1.date ORDER BY c.date </script>")
    List<ReportJunctionsBaseForm> getReportLevelBaseJunctionsForm(@Param("userIds")String userIds,@Param("level")String level);

    /**
     * 统计项目经理的总数量和结项数量
     * @param userIds 项目经理id
     * @param status 项目状态
     * @return
     */
    @Select("<script> select cre.sumCreateAmount ,IFNULL(jun.sumJunctionsAmount,0) as sumJunctionsAmount,cre.username from " +
            "(select count(p.id) as sumCreateAmount, a.username" +
            "  from project p join account a on a.id=p.create_user_id" +
            "<if test='userIds!=null'>"
            + " where p.create_user_id in (${userIds})"
            + "</if>"+
            " GROUP BY a.username) cre" +
            " left join " +
            "   (select count(p.id) sumJunctionsAmount, a.username" +
            " from project p join account a on a.id=p.create_user_id " +
            "<if test='userIds!=null and status!=null'>"
            + " where p.create_user_id in (${userIds}) and p.status =#{status} "
            + "</if>"
            + "<if test='userIds==null and status!=null'>"
            + " where p.status = #{status}"
            + "</if>"
            + "<if test='userIds!=null and status==null'>"
            + " where p.create_user_id in (${userIds})"
            + "</if> "+
            " GROUP BY a.username) jun" +
            " on jun.username=cre.username </script>")
    List<ReportJunctionsProjectAmountForm> getReportJunctionsProjectAmountForm(@Param("userIds")String userIds,@Param("status")String status);
}
