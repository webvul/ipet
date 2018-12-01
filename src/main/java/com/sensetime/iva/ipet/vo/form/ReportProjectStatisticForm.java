package com.sensetime.iva.ipet.vo.form;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author gongchao
 * @date 16:02 2018/9/14
 */
public class ReportProjectStatisticForm implements Serializable {
    @ApiModelProperty(value="yyyy-MM")
    private String date;
    @ApiModelProperty(value="数量")
    private Integer count;

    public ReportProjectStatisticForm() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "ReportProjectStatisticForm{" +
                "date='" + date + '\'' +
                ", count=" + count +
                '}';
    }
}
