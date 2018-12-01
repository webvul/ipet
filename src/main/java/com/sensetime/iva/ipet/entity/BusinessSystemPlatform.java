package com.sensetime.iva.ipet.entity;

import java.io.Serializable;

/**
 * @author  gongchao
 */
public class BusinessSystemPlatform extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 7350741721685086654L;
    private Integer id;
    /**平台名称*/
    private String name;
    /**系统id*/
    private Integer businessSystemId;

    public BusinessSystemPlatform() {
    }

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

    public Integer getBusinessSystemId() {
        return businessSystemId;
    }

    public void setBusinessSystemId(Integer businessSystemId) {
        this.businessSystemId = businessSystemId;
    }

    @Override
    public String toString() {
        return "BusinessSystemPlatform{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", businessSystemId=" + businessSystemId +
                "} " + super.toString();
    }
}
