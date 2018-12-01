package com.sensetime.iva.ipet.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author gongchao
 */
public class TopologicalGraph implements Serializable {
    private static final long serialVersionUID = 3376099902137993042L;
    private Integer id;
    /**项目id*/
    private Integer projectId;
    /**上传时间*/
    private Date uploadTime;
    /**文件名称*/
    private String name;
    /**文件路径*/
    private String path;

    public TopologicalGraph() {
    }

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

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "TopologicalGraph{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", uploadTime=" + uploadTime +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}