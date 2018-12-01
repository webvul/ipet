package com.sensetime.iva.ipet.entity;

/**
 * @Author: ChaiLongLong
 * @date : 2018/8/8 15:26
 */
public class EquipmentEntity extends BaseEntity {

    private Integer id;
    /**
     * 立项ID
     */
    private Integer projectId;
    /**
     * 产品版本
     */
    private String proType;
    /**
     * 软件版本
     */
    private String softwareVersion;
    /**
     * 设备类型
     */
    private String deviceType;
    /**
     * 设备数量
     */
    private int deviceNum;
    /**
     * 显卡编号
     */
    private String graphicsCardSerial;
    /**
     * 显卡数量
     */
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
    public String toString() {
        return "EquipmentEntity{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", proType='" + proType + '\'' +
                ", softwareVersion='" + softwareVersion + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", deviceNum=" + deviceNum +
                ", graphicsCardSerial='" + graphicsCardSerial + '\'' +
                ", graphicsCardNum=" + graphicsCardNum +
                "} " + super.toString();
    }
}
