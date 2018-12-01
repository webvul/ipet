package com.sensetime.iva.ipet.vo.form;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author gongchao
 * @date 9:32 2018/9/19
 */

public class ReportJunctionsProjectAmountForm implements Serializable {
    @ApiModelProperty(value="项目经理名称")
    private String username;
    @ApiModelProperty(value="总立项数")
    private Integer sumCreateAmount;
    @ApiModelProperty(value="总结项数")
    private Integer sumJunctionsAmount;

    public ReportJunctionsProjectAmountForm() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getSumCreateAmount() {
        return sumCreateAmount;
    }

    public void setSumCreateAmount(Integer sumCreateAmount) {
        this.sumCreateAmount = sumCreateAmount;
    }

    public Integer getSumJunctionsAmount() {
        return sumJunctionsAmount;
    }

    public void setSumJunctionsAmount(Integer sumJunctionsAmount) {
        this.sumJunctionsAmount = sumJunctionsAmount;
    }

    @Override
    public String toString() {
        return "ReportJunctionsProjectAmountForm{" +
                "username='" + username + '\'' +
                ", sumCreateAmount=" + sumCreateAmount +
                ", sumJunctionsAmount=" + sumJunctionsAmount +
                '}';
    }
}
