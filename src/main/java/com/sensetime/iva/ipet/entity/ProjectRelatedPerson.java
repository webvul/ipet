package com.sensetime.iva.ipet.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author gongchao
 */
public class ProjectRelatedPerson extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -8197432524301829409L;
    private Integer id;
    /**项目id*/
    private Integer projectId;
    /**项目组成员/接口单位*/
    private Integer type;
    /**类型为接口单位时生效*/
    private String companyName;
    /**角色*/
    private String role;
    /**姓名*/
    private String name;
    /**备注一*/
    private String remark1;
    /**备注二*/
    private String remark2;
    /**备注三*/
    private String remark3;
    /**备注四*/
    private String remark4;

    public ProjectRelatedPerson() {
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2;
    }

    public String getRemark3() {
        return remark3;
    }

    public void setRemark3(String remark3) {
        this.remark3 = remark3;
    }

    public String getRemark4() {
        return remark4;
    }

    public void setRemark4(String remark4) {
        this.remark4 = remark4;
    }

}