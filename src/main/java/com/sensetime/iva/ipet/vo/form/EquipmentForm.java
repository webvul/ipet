package com.sensetime.iva.ipet.vo.form;

import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * @author : ChaiLongLong
 * @date : 2018/8/8 17:47
 */
public class EquipmentForm {

    private Integer id;
    @ApiModelProperty(value="项目ID")
    private Integer projectId;
    @ApiModelProperty(value="产品类型")
    private String proType;
    @ApiModelProperty(value="软件版本")
    private String softwareVersion;
    @ApiModelProperty(value="设备类型")
    private String deviceType;
    @ApiModelProperty(value="设备数量")
    private int deviceNum;
    @ApiModelProperty(value="显卡编号")
    private String graphicsCardSerial;
    @ApiModelProperty(value="显卡数量")
    private int graphicsCardNum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProType() {
        return proType;
    }

    public void setProType(String proType) {
        this.proType = proType;
    }

    public String getSoftwareVersion() {
        return softwareVersion;
    }

    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public int getDeviceNum() {
        return deviceNum;
    }

    public void setDeviceNum(int deviceNum) {
        this.deviceNum = deviceNum;
    }

    public String getGraphicsCardSerial() {
        return graphicsCardSerial;
    }

    public void setGraphicsCardSerial(String graphicsCardSerial) {
        this.graphicsCardSerial = graphicsCardSerial;
    }

    public int getGraphicsCardNum() {
        return graphicsCardNum;
    }

    public void setGraphicsCardNum(int graphicsCardNum) {
        this.graphicsCardNum = graphicsCardNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        EquipmentForm that = (EquipmentForm) o;
        return deviceNum == that.deviceNum &&
                graphicsCardNum == that.graphicsCardNum &&
                Objects.equals(id, that.id) &&
                Objects.equals(projectId, that.projectId) &&
                Objects.equals(proType, that.proType) &&
                Objects.equals(softwareVersion, that.softwareVersion) &&
                Objects.equals(deviceType, that.deviceType) &&
                Objects.equals(graphicsCardSerial, that.graphicsCardSerial);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, projectId, proType, softwareVersion, deviceType, deviceNum, graphicsCardSerial, graphicsCardNum);
    }

    @Override
    public String toString() {
        return "EquipmentForm{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", proType='" + proType + '\'' +
                ", softwareVersion='" + softwareVersion + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", deviceNum=" + deviceNum +
                ", graphicsCardSerial='" + graphicsCardSerial + '\'' +
                ", graphicsCardNum=" + graphicsCardNum +
                '}';
    }
}
