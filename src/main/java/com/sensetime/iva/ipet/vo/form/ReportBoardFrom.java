package com.sensetime.iva.ipet.vo.form;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author gongchao
 * @date 9:40 2018/9/13
 */
public class ReportBoardFrom implements Serializable {
    @ApiModelProperty(value="每周五的日期")
    private String date;
    @ApiModelProperty(value="立项数量")
    private Integer count;

    public ReportBoardFrom() {
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
        return "WeeklyFrom{" +
                "date='" + date + '\'' +
                ", count=" + count +
                '}';
    }
}
