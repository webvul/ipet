package com.sensetime.iva.ipet.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author  gongchao
 */
public class PhysicalMap implements Serializable {
    private static final long serialVersionUID = -5670785456455040564L;
    private Integer id;
    /**项目id*/
    private Integer projectId;
    /**上传文件名称*/
    private String name;
    /**上传路径*/
    private String path;
    /**上传时间*/
    private Date uploadTime;

    public PhysicalMap() {
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

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    @Override
    public String toString() {
        return "PhysicalMap{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", uploadTime=" + uploadTime +
                '}';
    }
}