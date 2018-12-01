package com.sensetime.iva.ipet.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author  gongchao
 */
public class DeliverList extends  BaseEntity implements Serializable {
    private static final long serialVersionUID = 4615822886948587843L;
    private Integer id;
    /**项目id*/
    private Integer projectId;
    /**交付物名称*/
    private String name;
    /**交付对象*/
    private String target;
    /**交付物类型*/
    private Integer type;
    /**交付物路径*/
    private String path;
    /**备注*/
    private String remark;
    public DeliverList() {
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

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "DeliverList{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", name='" + name + '\'' +
                ", target='" + target + '\'' +
                ", type=" + type +
                ", path='" + path + '\'' +
                ", remark='" + remark + '\'' +
                "} " + super.toString();
    }
}