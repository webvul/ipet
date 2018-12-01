package com.sensetime.iva.ipet.service;

import com.sensetime.iva.ipet.vo.form.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @author gongchao
 * @date 9:43 2018/9/13
 */
@Service
public interface ReportService {
    /**
     * 获取当前登陆人的同一区域的项目经理的id集合
     * @param areaId 区域id
     * @return
     */
    List<Integer> getSameUserByAreaId(Integer areaId);
    /**
     * 获取每周的立项数量
     * @param  userIds 项目经理id
     * @return
     */
    List<ReportBoardFrom> getReportBoardCount(List<Integer> userIds);

    /**
     * 获取开始和结束时间的所有立项数量
     * @param reportBoardFroms
     * @return
     */
    List<ReportBoardFrom> getReportBoard(List<ReportBoardFrom> reportBoardFroms);

    /**
     * 获取项目类型
     * @param isContract 是否是合同类型或其它类型
     * @return
     */
    List<Integer> getIsContractProjectType(Boolean isContract);
    /**
     * 根據項目类型獲取區域信息
     * @param types 项目类型
     * @return
     */
    List<ReportProjectAreaForm> getReportProjectArea(List<Integer> types );
    /**
     * 获取项目类型
     * @param userIds 项目经理id集合
     * @return
     */
    List<ReportProjectTypeForm> getReportProjectTypeForm(List<Integer> userIds);

    /**
     * 获取首页信息
     * @param isPM 是否是PM
     * @return
     * @throws Exception
     */
    ReportIndexForm getReportIndexForm(Boolean isPM) throws  Exception;
    /**
     * 根據項目狀態獲取區域信息
     * @param userIds  项目经理id
     * @param statusList  项目状态
     * @return
     */
    List<ReportDelayProjectForm> getReportDelayProjectForm( List<Integer> userIds, List<Integer> statusList);

    /**
     * 获取系统数量
     * @param userIds
     * @return
     */
    List<ReportSystemFrom> getReportSystemFrom(List<Integer> userIds);

    /**
     * 获取平台数量
     * @param userIds
     * @return
     */
    List<ReportPlatformFrom> getReportPlatformFrom(List<Integer> userIds);

    /**
     * 获取项目数量
     * @param  reportProjectTypeForm 项目类型数量集合
     * @return
     */
    Integer getProjectAmount(List<ReportProjectTypeForm> reportProjectTypeForm);
    /**
     * 根据创建时间和状态查询项目数量
     * @param statusList
     * @return
     */
    List<ReportProjectStatisticForm> getReportProjectByCreateTimeAndStatus(List<Integer> statusList);
    /**
     * 根据更新时间和状态查询项目数量
     * @param statusList
     * @return
     * */
    List<ReportProjectStatisticForm> getReportProjectByUpdateTimeAndStatus(List<Integer> statusList);

    /**
     * 获取管理员看板信息
     * @return
     * @throws Exception
     */
    ReportAdminBoardForm getReportAdminBoardForm() throws  Exception;

    /**
     * 获取PM看板信息
     * @return
     * @throws Exception
     */
    ReportPmBoardForm getReportPmBoardForm() throws  Exception;

    /**
     * 获取项目状态
     * @param isJunctions 是否是结项状态
     * @return
     */
    List<Integer> getIsJunctionsProjectStatus(Boolean isJunctions);

    /**
     * 获取项目信息的报表数据
     * @param junctionList 历史结项数据
     * @param createList  历史立项数据
     * @return
     * @throws Exception
     */
    List<ReportCreateJunctionsForm> getReportProjectStatisticForm
    (List<ReportProjectStatisticForm> junctionList,List<ReportProjectStatisticForm> createList) throws Exception;

    /**
     * 只有立项项目
     * @param createList
     * @return
     * @throws Exception
     */
    List<ReportCreateJunctionsForm> onlyCreateList(List<ReportProjectStatisticForm> createList) throws Exception;

    /**
     * 结项项目和立项项目
     * @param junctionList 结项项目
     * @param createList 立项项目
     * @return
     * @throws Exception
     */
    List<ReportCreateJunctionsForm> bothCreateAndJunctionList(List<ReportProjectStatisticForm> junctionList,List<ReportProjectStatisticForm> createList) throws Exception;
    /**
     * 计算结项项目历史数据
     * @param  forms 处理后的数据表单
     * @param thisMonth 本月数据
     * @param lastMonth 上月数据
     * @param  thisDate 本月日期
     */
    void sumCreateForm(List<ReportCreateJunctionsForm>forms,ReportProjectStatisticForm thisMonth,ReportCreateJunctionsForm lastMonth,String thisDate);

    /**
     * 计算结项项目历史数据
     * @param  forms 处理后的数据表单
     * @param lastMonth 上月数据
     * @param  tempStartDateKey 本月日期
     * @param  createMap 所有查询出的立项数据
     * @param  junMap 所有查询出的结项数据
     * @throws Exception
     */
    void sumCreateOrJunctionsForm(List<ReportCreateJunctionsForm>forms,ReportCreateJunctionsForm lastMonth, String tempStartDateKey,
                                  HashMap<String,ReportProjectStatisticForm> createMap, HashMap<String,ReportProjectStatisticForm> junMap);
    /**
     * 获取对应项目状态下的问题及帮助
     * @param status 项目状态
     * @param  isPm 是否是pm
     * @throws Exception
     * @return
     */
    List<ReportRiskAndHelpForm> getReportRiskAndHelpForm(Integer status,Boolean isPm) throws Exception;


    /**
     * 获取项目状态和项目经理id查询结项项目
     * @param userIds
     * @param  status
     * @return
     */
    List<ReportJunctionsBaseForm> getReportJunctionsBaseForm(String userIds, String status);
    /**
     * 获取项目状态和项目经理id查询项目类型
     * @param userIds
     * @param  type
     * @return
     */
    List<ReportJunctionsBaseForm> getReportTypeBaseJunctionsForm(String userIds, String type);
    /**
     * 获取项目状态和项目经理id查询项目级别
     * @param userIds
     * @param  level
     * @return
     */
    List<ReportJunctionsBaseForm> getReportLevelBaseJunctionsForm(String userIds, String level);
    /**
     * 获取项目状态和项目经理id查询项目类型
     * @param userIds
     * @return
     */
    List<ReportJunctionsTypeForm> getReportTypeJunctionsForm(List<Integer> userIds);
    /**
     * 获取项目状态和项目经理id查询项目级别
     * @param userIds
     * @return
     */
    List<ReportJunctionsLevelForm> getReportLevelJunctionsForm(List<Integer> userIds);

    /**
     * 获取结项项目报表
     * @param isPm
     * @return
     * @throws Exception
     */
    ReportJunctionsProjectForm getReportJunctionsProjectForm(Boolean isPm) throws Exception;

    /**
     * 统计项目经理的总数量和结项数量
     * @param userIds
     * @param status
     * @return
     */
    List<ReportJunctionsProjectAmountForm> getReportJunctionsProjectAmountForm(List<Integer> userIds,String status);

}
