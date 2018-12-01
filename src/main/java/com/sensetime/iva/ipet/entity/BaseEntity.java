package com.sensetime.iva.ipet.entity;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @Author: ChaiLongLong
 */
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 5841462583320351845L;

    @ApiModelProperty(value="createTime",hidden=true)
    private Timestamp createTime;
    @ApiModelProperty(value="updateTime",hidden=true)
    private Timestamp updateTime;

    public Timestamp getCreateTime() {
        if(createTime==null){
            return new Timestamp(System.currentTimeMillis());
        }
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return new Timestamp(System.currentTimeMillis());
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public BaseEntity() {
    }
}
