package com.sensetime.iva.ipet.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author  gongchao
 */
@ConfigurationProperties(prefix = "LoadFileConfig")
@Component
public class LoadFileConfig {
    /**立项项目上传地址*/
    private String projectLocation;
    /**项目计划上传地址*/
    private String projectPlanLocation;
    /**拓扑图上传地址*/
    private String topologicalGraphLocation;
    /**物理图上传地址*/
    private String physicalMapLocation;
    /**下载服务器地址*/
    private String downloadIp;
    /**下载服务器地址端口*/
    private String downloadPort;
    public String getProjectLocation() {
        return projectLocation;
    }

    public void setProjectLocation(String projectLocation) {
        this.projectLocation = projectLocation;
    }

    public String getProjectPlanLocation() {
        return projectPlanLocation;
    }

    public void setProjectPlanLocation(String projectPlanLocation) {
        this.projectPlanLocation = projectPlanLocation;
    }

    public String getTopologicalGraphLocation() {
        return topologicalGraphLocation;
    }

    public void setTopologicalGraphLocation(String topologicalGraphLocation) {
        this.topologicalGraphLocation = topologicalGraphLocation;
    }

    public String getPhysicalMapLocation() {
        return physicalMapLocation;
    }

    public void setPhysicalMapLocation(String physicalMapLocation) {
        this.physicalMapLocation = physicalMapLocation;
    }

    public String getDownloadIp() {
        return downloadIp;
    }

    public void setDownloadIp(String downloadIp) {
        this.downloadIp = downloadIp;
    }

    public String getDownloadPort() {
        return downloadPort;
    }

    public void setDownloadPort(String downloadPort) {
        this.downloadPort = downloadPort;
    }
}
