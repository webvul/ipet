package com.sensetime.iva.ipet.vo.form;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author gongchao
 * @date 11:40 2018/9/17
 */
public class ReportCreateJunctionsForm implements Serializable {
    @ApiModelProperty(value="每月立项数量")
    private String monthlyDate;
    @ApiModelProperty(value="每月结项数量")
    private Integer monthlyJunctionsAmount;
    @ApiModelProperty(value="每月活跃数量")
    private Integer monthlyCreateAmount;
    @ApiModelProperty(value="累计立项数量")
    private Integer sumCreateAmount;
    @ApiModelProperty(value="累计结项数量")
    private Integer sumJunctionsAmount;
    @ApiModelProperty(value="累计活跃数量")
    private Integer sumActiveAmount;

    public ReportCreateJunctionsForm() {
    }

    public String getMonthlyDate() {
        return monthlyDate;
    }

    public void setMonthlyDate(String monthlyDate) {
        this.monthlyDate = monthlyDate;
    }

    public Integer getSumCreateAmount() {
        return sumCreateAmount;
    }

    public void setSumCreateAmount(Integer sumCreateAmount) {
        this.sumCreateAmount = sumCreateAmount;
    }

    public Integer getMonthlyJunctionsAmount() {
        return monthlyJunctionsAmount;
    }

    public void setMonthlyJunctionsAmount(Integer monthlyJunctionsAmount) {
        this.monthlyJunctionsAmount = monthlyJunctionsAmount;
    }

    public Integer getMonthlyCreateAmount() {
        return monthlyCreateAmount;
    }

    public void setMonthlyCreateAmount(Integer monthlyCreateAmount) {
        this.monthlyCreateAmount = monthlyCreateAmount;
    }

    public Integer getSumJunctionsAmount() {
        return sumJunctionsAmount;
    }

    public void setSumJunctionsAmount(Integer sumJunctionsAmount) {
        this.sumJunctionsAmount = sumJunctionsAmount;
    }

    public Integer getSumActiveAmount() {
        return sumActiveAmount;
    }

    public void setSumActiveAmount(Integer sumActiveAmount) {
        this.sumActiveAmount = sumActiveAmount;
    }

    @Override
    public String toString() {
        return "ReportCreateJunctionsForm{" +
                "monthlyDate='" + monthlyDate + '\'' +
                ", sumCreateAmount=" + sumCreateAmount +
                ", monthlyJunctionsAmount=" + monthlyJunctionsAmount +
                ", monthlyCreateAmount=" + monthlyCreateAmount +
                ", sumJunctionsAmount=" + sumJunctionsAmount +
                ", sumActiveAmount=" + sumActiveAmount +
                '}';
    }
}
