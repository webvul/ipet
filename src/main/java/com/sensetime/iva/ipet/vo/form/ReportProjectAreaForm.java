package com.sensetime.iva.ipet.vo.form;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author gongchao
 * @date 14:01 2018/9/13
 */
public class ReportProjectAreaForm implements Serializable {
    @ApiModelProperty(value="区域名称")
    private String areaName;
    @ApiModelProperty(value="项目在该区域数量")
    private Integer areaAmount;

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getAreaAmount() {
        return areaAmount;
    }

    public void setAreaAmount(Integer areaAmount) {
        this.areaAmount = areaAmount;
    }

    public ReportProjectAreaForm() {
    }

    @Override
    public String toString() {
        return "ReportProjectAreaForm{" +
                "areaName='" + areaName + '\'' +
                ", areaAmount=" + areaAmount +
                '}';
    }
}
