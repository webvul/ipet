package com.sensetime.iva.ipet.vo.form;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author gongchao
 * @date 16:26 2018/9/14
 */
public class ReportPmBoardForm implements Serializable {
    @ApiModelProperty(value="我的的项目类型")
    List<ReportProjectTypeForm> projectTypeList;
    @ApiModelProperty(value="我的系统数量")
    List<ReportSystemFrom> systemList;
    @ApiModelProperty(value="我的平台数量")
    List<ReportPlatformFrom> platformList;
    @ApiModelProperty(value="我的项目类型的总数量统计")
    private Integer projectAmount;
    @ApiModelProperty(value="区域项目类型")
    List<ReportProjectTypeForm> areaProjectTypeList;
    @ApiModelProperty(value="区域系统数量")
    List<ReportSystemFrom> areaSystemList;
    @ApiModelProperty(value="区域平台数量")
    List<ReportPlatformFrom> areaPlatformList;
    @ApiModelProperty(value="区域项目类型的总数量统计")
    private Integer areaProjectAmount;

    public ReportPmBoardForm() {
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

    public List<ReportProjectTypeForm> getAreaProjectTypeList() {
        return areaProjectTypeList;
    }

    public void setAreaProjectTypeList(List<ReportProjectTypeForm> areaProjectTypeList) {
        this.areaProjectTypeList = areaProjectTypeList;
    }

    public List<ReportSystemFrom> getAreaSystemList() {
        return areaSystemList;
    }

    public void setAreaSystemList(List<ReportSystemFrom> areaSystemList) {
        this.areaSystemList = areaSystemList;
    }

    public List<ReportPlatformFrom> getAreaPlatformList() {
        return areaPlatformList;
    }

    public void setAreaPlatformList(List<ReportPlatformFrom> areaPlatformList) {
        this.areaPlatformList = areaPlatformList;
    }

    public Integer getAreaProjectAmount() {
        return areaProjectAmount;
    }

    public void setAreaProjectAmount(Integer areaProjectAmount) {
        this.areaProjectAmount = areaProjectAmount;
    }

    @Override
    public String toString() {
        return "ReportPmBoardForm{" +
                "projectTypeList=" + projectTypeList +
                ", systemList=" + systemList +
                ", platformList=" + platformList +
                ", projectAmount=" + projectAmount +
                ", areaProjectTypeList=" + areaProjectTypeList +
                ", areaSystemList=" + areaSystemList +
                ", areaPlatformList=" + areaPlatformList +
                ", areaProjectAmount=" + areaProjectAmount +
                '}';
    }
}
