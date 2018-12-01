package com.sensetime.iva.ipet.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author  gongchao
 */
public class WareList extends  BaseEntity implements Serializable {
    private static final long serialVersionUID = 1615247296064068288L;
    private Integer id;
    /**项目id*/
    private Integer projectId;
    /**SN编号*/
    private String snNo;
    /**服务节点名称*/
    private String nodeName;
    /**服务器配置标准代号*/
    private String configCode;
    /**服务器尺寸*/
    private String size;
    /**服务器硬件配置*/
    private String hardwareConfig;
    /**软件/模块*/
    private String softwareModule;
    /**版本*/
    private String version;
    /**端口*/
    private String port;
    /**视频专网ip*/
    private String videoPrivateIp;
    /**公安网ip*/
    private String policeIp;
    /**服务器账户/密码*/
    private String accountPassword;
    /**更新日期*/
    private Date updateDate;
    /**加密狗到期时间*/
    private String licenseExpiration;
    /**修订记录*/
    private String revisedRecord;
    /**备注*/
    private String remark;

    public Integer getId() {
        return id;
    }

    public WareList() {
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

    public String getSnNo() {
        return snNo;
    }

    public void setSnNo(String snNo) {
        this.snNo = snNo;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getConfigCode() {
        return configCode;
    }

    public void setConfigCode(String configCode) {
        this.configCode = configCode;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getHardwareConfig() {
        return hardwareConfig;
    }

    public void setHardwareConfig(String hardwareConfig) {
        this.hardwareConfig = hardwareConfig;
    }

    public String getSoftwareModule() {
        return softwareModule;
    }

    public void setSoftwareModule(String softwareModule) {
        this.softwareModule = softwareModule;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getVideoPrivateIp() {
        return videoPrivateIp;
    }

    public void setVideoPrivateIp(String videoPrivateIp) {
        this.videoPrivateIp = videoPrivateIp;
    }

    public String getPoliceIp() {
        return policeIp;
    }

    public void setPoliceIp(String policeIp) {
        this.policeIp = policeIp;
    }

    public String getAccountPassword() {
        return accountPassword;
    }

    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }

    public String getLicenseExpiration() {
        return licenseExpiration;
    }

    public void setLicenseExpiration(String licenseExpiration) {
        this.licenseExpiration = licenseExpiration;
    }

    public String getRevisedRecord() {
        return revisedRecord;
    }

    public void setRevisedRecord(String revisedRecord) {
        this.revisedRecord = revisedRecord;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
    @Override
    public String toString() {
        return "WareList{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", snNo='" + snNo + '\'' +
                ", nodeName='" + nodeName + '\'' +
                ", configCode='" + configCode + '\'' +
                ", size='" + size + '\'' +
                ", hardwareConfig='" + hardwareConfig + '\'' +
                ", softwareModule='" + softwareModule + '\'' +
                ", version='" + version + '\'' +
                ", port='" + port + '\'' +
                ", videoPrivateIp='" + videoPrivateIp + '\'' +
                ", policeIp='" + policeIp + '\'' +
                ", accountPassword='" + accountPassword + '\'' +
                ", updateDate='" + updateDate + '\'' +
                ", licenseExpiration='" + licenseExpiration + '\'' +
                ", revisedRecord='" + revisedRecord + '\'' +
                ", remark='" + remark + '\'' +
                "} " + super.toString();
    }
}