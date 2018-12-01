package com.sensetime.iva.ipet.vo.form;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author gongchao
 * @date 13:16 2018/9/14
 */
public class ReportPlatformFrom implements Serializable {
    @ApiModelProperty(value="平台数量")
    private Integer platformAmount;
    @ApiModelProperty(value="平台名称")
    private String  platformName;

    public Integer getPlatformAmount() {
        return platformAmount;
    }

    public void setPlatformAmount(Integer platformAmount) {
        this.platformAmount = platformAmount;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public ReportPlatformFrom() {
    }

    @Override
    public String toString() {
        return "ReportPlatformFrom{" +
                "platformAmount=" + platformAmount +
                ", platformName='" + platformName + '\'' +
                '}';
    }
}
