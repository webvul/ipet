package com.sensetime.iva.ipet.vo.form;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * @Author: ChaiLongLong
 * @date : 2018/8/8 16:48
 */
public class StageForm {

    private Integer id;
    @ApiModelProperty(value="项目id")
    private Integer projectId;
    @ApiModelProperty(value="开始时间")
    private Date startDate;
    @ApiModelProperty(value="结束时间")
    private Date endDate;
    private List<WorkTimeForm> workTimeForms;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<WorkTimeForm> getWorkTimeForms() {
        return workTimeForms;
    }

    public void setWorkTimeForms(List<WorkTimeForm> workTimeForms) {
        this.workTimeForms = workTimeForms;
    }

    @Override
    public String toString() {
        return "StageForm{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", workTimeForms=" + workTimeForms +
                '}';
    }
}
