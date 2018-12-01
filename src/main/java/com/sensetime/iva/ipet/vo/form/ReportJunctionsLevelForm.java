package com.sensetime.iva.ipet.vo.form;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author gongchao
 * @date 13:31 2018/9/18
 */
public class ReportJunctionsLevelForm  implements Serializable {
    @ApiModelProperty(value="日期")
    private String date;
    @ApiModelProperty(value="p0数量")
    private Integer p0Amount;
    @ApiModelProperty(value="p1数量")
    private Integer p1Amount;
    @ApiModelProperty(value="p2数量")
    private Integer p2Amount;
    @ApiModelProperty(value="p3数量")
    private Integer p3Amount;

    public ReportJunctionsLevelForm() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getP0Amount() {
        return p0Amount;
    }

    public void setP0Amount(Integer p0Amount) {
        this.p0Amount = p0Amount;
    }

    public Integer getP1Amount() {
        return p1Amount;
    }

    public void setP1Amount(Integer p1Amount) {
        this.p1Amount = p1Amount;
    }

    public Integer getP2Amount() {
        return p2Amount;
    }

    public void setP2Amount(Integer p2Amount) {
        this.p2Amount = p2Amount;
    }


    public Integer getP3Amount() {
        return p3Amount;
    }

    public void setP3Amount(Integer p3Amount) {
        this.p3Amount = p3Amount;
    }


    @Override
    public String toString() {
        return "ReportJunctionsLevelForm{" +
                "date='" + date + '\'' +
                ", p0Amount=" + p0Amount +
                ", p1Amount=" + p1Amount +
                ", p2Amount=" + p2Amount +
                ", p3Amount=" + p3Amount +
                '}';
    }
}
