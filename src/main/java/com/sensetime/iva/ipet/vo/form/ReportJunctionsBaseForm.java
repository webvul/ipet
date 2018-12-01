package com.sensetime.iva.ipet.vo.form;


import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author gongchao
 * @date 13:34 2018/9/18
 */
public class ReportJunctionsBaseForm implements Serializable {
    @ApiModelProperty(value="时间")
    private String date;
    @ApiModelProperty(value="数量")
    private Integer amount;

    public ReportJunctionsBaseForm() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "ReportJunctionsBaseForm{" +
                "date='" + date + '\'' +
                ", amount=" + amount +
                '}';
    }
}
