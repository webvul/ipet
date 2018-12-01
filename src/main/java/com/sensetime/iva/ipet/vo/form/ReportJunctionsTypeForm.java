package com.sensetime.iva.ipet.vo.form;

import com.sensetime.iva.ipet.common.ProjectArgs;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author gongchao
 * @date 12:44 2018/9/18
 */
public class ReportJunctionsTypeForm implements Serializable {
    @ApiModelProperty(value="日期")
    private String date;
    @ApiModelProperty(value="pk数量")
    private Integer pkAmount;
    @ApiModelProperty(value="合同数量")
    private Integer contractAmount;
    @ApiModelProperty(value="试点数量")
    private Integer experimentAmount;
    @ApiModelProperty(value="试点转合同数量")
    private Integer experimentToContractAmount;
    public ReportJunctionsTypeForm() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getPkAmount() {
        return pkAmount;
    }

    public void setPkAmount(Integer pkAmount) {
        this.pkAmount = pkAmount;
    }


    public Integer getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(Integer contractAmount) {
        this.contractAmount = contractAmount;
    }

    public Integer getExperimentAmount() {
        return experimentAmount;
    }

    public void setExperimentAmount(Integer experimentAmount) {
        this.experimentAmount = experimentAmount;
    }


    public Integer getExperimentToContractAmount() {
        return experimentToContractAmount;
    }

    public void setExperimentToContractAmount(Integer experimentToContractAmount) {
        this.experimentToContractAmount = experimentToContractAmount;
    }


    @Override
    public String toString() {
        return "ReportJunctionsTypeForm{" +
                "date='" + date + '\'' +
                ", pkAmount=" + pkAmount +
                ", contractAmount=" + contractAmount +
                ", experimentAmount=" + experimentAmount +
                ", experimentToContractAmount=" + experimentToContractAmount +
                '}';
    }
}
