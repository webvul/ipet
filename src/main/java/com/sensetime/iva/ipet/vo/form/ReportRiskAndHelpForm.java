package com.sensetime.iva.ipet.vo.form;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author gongchao
 * @date 10:00 2018/9/18
 */
public class ReportRiskAndHelpForm implements Serializable {
    @ApiModelProperty(value="项目名称")
    private String projectName;
    @ApiModelProperty(value="风险及求助")
    private String riskAndHelp;

    public ReportRiskAndHelpForm() {
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getRiskAndHelp() {
        return riskAndHelp;
    }

    public void setRiskAndHelp(String riskAndHelp) {
        this.riskAndHelp = riskAndHelp;
    }

    @Override
    public String toString() {
        return "ReportRiskAndHelpForm{" +
                "projectName='" + projectName + '\'' +
                ", riskAndHelp='" + riskAndHelp + '\'' +
                '}';
    }
}
