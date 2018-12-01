package com.sensetime.iva.ipet.vo.form;

import com.sensetime.iva.ipet.common.ProjectArgs;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author gongchao
 * @date 14:03 2018/9/13
 */
public class ReportDelayProjectForm implements Serializable {
    @ApiModelProperty(value="项目编号")
    private String serial;
    @ApiModelProperty(value="项目名称")
    private String projectName;
    @ApiModelProperty(value="项目状态")
    private String projectStatus;

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }

    public ReportDelayProjectForm() {
    }

    @Override
    public String toString() {
        return "ReportDelayProjectFom{" +
                "serial='" + serial + '\'' +
                ", projectName='" + projectName + '\'' +
                ", projectStatus='" + projectStatus + '\'' +
                '}';
    }
}
