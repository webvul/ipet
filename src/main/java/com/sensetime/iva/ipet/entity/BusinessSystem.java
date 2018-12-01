package com.sensetime.iva.ipet.entity;

import java.io.Serializable;

/**
 * @author  gongchao
 */
public class BusinessSystem extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 3705836573127643122L;
    private Integer id;
    /**系统名称*/
    private String name;

    public BusinessSystem() {
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

    @Override
    public String toString() {
        return "BusinessSystem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                "} " + super.toString();
    }

}
