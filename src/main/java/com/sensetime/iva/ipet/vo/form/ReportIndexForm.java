package com.sensetime.iva.ipet.vo.form;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author gongchao
 * @date 14:07 2018/9/13
 */
public class ReportIndexForm implements Serializable {
    @ApiModelProperty(value="看板数据")
    List<ReportBoardFrom> boardList;
    @ApiModelProperty(value="延迟项目")
    List<ReportDelayProjectForm> delayProjectList;
    @ApiModelProperty(value="合同项目区域分布-admin")
    List<ReportProjectAreaForm>  onlyContractAreaList;
    @ApiModelProperty(value="非合同其它项目区域分布-admin")
    List<ReportProjectAreaForm> exceptContractAreaList;
    @ApiModelProperty(value="项目类型")
    List<ReportProjectTypeForm> projectTypeList;
    @ApiModelProperty(value="系统数量-pm")
    List<ReportSystemFrom> systemList;
    @ApiModelProperty(value="平台数量-pm")
    List<ReportPlatformFrom> platformList;
    @ApiModelProperty(value="项目类型的总数量统计")
    private Integer projectAmount;
    public ReportIndexForm() {
    }

    public List<ReportBoardFrom> getBoardList() {
        return boardList;
    }

    public void setBoardList(List<ReportBoardFrom> boardList) {
        this.boardList = boardList;
    }

    public List<ReportDelayProjectForm> getDelayProjectList() {
        return delayProjectList;
    }

    public void setDelayProjectList(List<ReportDelayProjectForm> delayProjectList) {
        this.delayProjectList = delayProjectList;
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

    public Integer getProjectAmount() {
        return projectAmount;
    }

    public void setProjectAmount(Integer projectAmount) {
        this.projectAmount = projectAmount;
    }

    @Override
    public String toString() {
        return "ReportIndexForm{" +
                "boardList=" + boardList +
                ", delayProjectList=" + delayProjectList +
                ", onlyContractAreaList=" + onlyContractAreaList +
                ", exceptContractAreaList=" + exceptContractAreaList +
                ", projectTypeList=" + projectTypeList +
                ", systemList=" + systemList +
                ", platformList=" + platformList +
                ", projectAmount=" + projectAmount +
                '}';
    }
}
