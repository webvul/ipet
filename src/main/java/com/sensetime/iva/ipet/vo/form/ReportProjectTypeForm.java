package com.sensetime.iva.ipet.vo.form;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author gongchao
 * @date 13:59 2018/9/13
 */
public class ReportProjectTypeForm implements Serializable {
    @ApiModelProperty(value="项目级别")
    private String typeName;
    @ApiModelProperty(value="数量")
    private Integer countAmount;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getCountAmount() {
        return countAmount;
    }

    public void setCountAmount(Integer countAmount) {
        this.countAmount = countAmount;
    }

    @Override
    public String toString() {
        return "ReportProjectTypeForm{" +
                "typeName='" + typeName + '\'' +
                ", countAmount=" + countAmount +
                '}';
    }
}
