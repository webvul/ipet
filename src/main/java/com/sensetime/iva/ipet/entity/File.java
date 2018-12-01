package com.sensetime.iva.ipet.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author  gongchao
 */
public class File implements Serializable {
    private static final long serialVersionUID = -5055429442976229597L;
    private Integer id;
    /**文件名*/
    private String name;
    /**路径*/
    private String path;
    /**上传时间*/
    private Timestamp uploadTime;
    /**项目id*/
    private Integer projectId;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Timestamp getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Timestamp uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    @Override
    public String toString() {
        return "File{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", uploadTime=" + uploadTime +
                '}';
    }

    public File() {
    }
}