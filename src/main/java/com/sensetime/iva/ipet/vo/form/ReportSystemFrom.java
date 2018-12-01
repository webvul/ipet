package com.sensetime.iva.ipet.vo.form;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author gongchao
 * @date 13:16 2018/9/14
 */
public class ReportSystemFrom implements Serializable {
    @ApiModelProperty(value="数量")
    private Integer systemAmount;
    @ApiModelProperty(value="系统名称")
    private String systemName;

    public ReportSystemFrom() {
    }

    public Integer getSystemAmount() {
        return systemAmount;
    }

    public void setSystemAmount(Integer systemAmount) {
        this.systemAmount = systemAmount;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    @Override
    public String toString() {
        return "ReportSystemFrom{" +
                "systemAmount=" + systemAmount +
                ", systemName='" + systemName + '\'' +
                '}';
    }
}
