package com.sensetime.iva.ipet.vo.form;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author gongchao
 * @date 16:25 2018/9/14
 */
public class ReportAdminBoardForm implements Serializable {
    @ApiModelProperty(value="合同项目区域分布")
    List<ReportProjectAreaForm> onlyContractAreaList;
    @ApiModelProperty(value="非合同其它项目区域分布")
    List<ReportProjectAreaForm> exceptContractAreaList;
    @ApiModelProperty(value="项目类型")
    List<ReportProjectTypeForm> projectTypeList;
    @ApiModelProperty(value="系统数量")
    List<ReportSystemFrom> systemList;
    @ApiModelProperty(value="平台数量")
    List<ReportPlatformFrom> platformList;
    @ApiModelProperty(value="项目立项-结项统计")
    List<ReportCreateJunctionsForm> createOrJunctionsList;
    @ApiModelProperty(value="项目类型的总数量统计")
    private Integer projectAmount;
    @ApiModelProperty(value="每个项目经理管理项目的数量及结项数量")
    private List<ReportJunctionsProjectAmountForm> projectAmountList;

    public ReportAdminBoardForm() {
    }

    public List<ReportProjectAreaForm> getOnlyContractAreaList() {
        return onlyContractAreaList;
    }

    public void setOnlyContractAreaList(List<ReportProjectAreaForm> onlyContractAreaList) {
        this.onlyContractAreaList = onlyContractAreaList;
    }

    public List<ReportProjectAreaForm> getExceptContractAreaList() {
        return exceptContractAreaList;
    }

    public void setExceptContractAreaList(List<ReportProjectAreaForm> exceptContractAreaList) {
        this.exceptContractAreaList = exceptContractAreaList;
    }

    public List<ReportProjectTypeForm> getProjectTypeList() {
        return projectTypeList;
    }

    public void setProjectTypeList(List<ReportProjectTypeForm> projectTypeList) {
        this.projectTypeList = projectTypeList;
    }

    public List<ReportSystemFrom> getSystemList() {
        return systemList;
    }

    public void setSystemList(List<ReportSystemFrom> systemList) {
        this.systemList = systemList;
    }

    public List<ReportPlatformFrom> getPlatformList() {
        return platformList;
    }

    public void setPlatformList(List<ReportPlatformFrom> platformList) {
        this.platformList = platformList;
    }

    public List<ReportCreateJunctionsForm> getCreateOrJunctionsList() {
        return createOrJunctionsList;
    }

    public void setCreateOrJunctionsList(List<ReportCreateJunctionsForm> createOrJunctionsList) {
        this.createOrJunctionsList = createOrJunctionsList;
    }

    public Integer getProjectAmount() {
        return projectAmount;
    }

    public void setProjectAmount(Integer projectAmount) {
        this.projectAmount = projectAmount;
    }

    public List<ReportJunctionsProjectAmountForm> getProjectAmountList() {
        return projectAmountList;
    }

    public void setProjectAmountList(List<ReportJunctionsProjectAmountForm> projectAmountList) {
        this.projectAmountList = projectAmountList;
    }

    @Override
    public String toString() {
        return "ReportAdminBoardForm{" +
                "onlyContractAreaList=" + onlyContractAreaList +
                ", exceptContractAreaList=" + exceptContractAreaList +
                ", projectTypeList=" + projectTypeList +
                ", systemList=" + systemList +
                ", platformList=" + platformList +
                ", createOrJunctionsList=" + createOrJunctionsList +
                ", projectAmount=" + projectAmount +
                ", projectAmountList=" + projectAmountList +
                '}';
    }
}
