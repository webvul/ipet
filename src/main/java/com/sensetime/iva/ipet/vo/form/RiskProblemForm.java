package com.sensetime.iva.ipet.vo.form;

import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * @author : ChaiLongLong
 * @date : 2018/8/9 10:02
 */
public class RiskProblemForm {

    private Integer id;
    @ApiModelProperty(value="项目id")
    private Integer projectId;
    @ApiModelProperty(value="风险/问题")
    private String risk;
    @ApiModelProperty(value="级别 1:H  2:M  3:L")
    private String level;
    @ApiModelProperty(value="具体措施")
    private String measure;
    @ApiModelProperty(value="发生日期，字符串\"yyyy-MM-dd\"")
    private String occurDate;
    @ApiModelProperty(value="发计划解决日期，字符串\"yyyy-MM-dd\"")
    private String planedSolveDate;
    @ApiModelProperty(value="状态 1:待解决 2:处理中 3:已解决 4:可接受")
    private Integer status;
    @ApiModelProperty(value="责任人")
    private String personLiable;
    @ApiModelProperty(value="备注")
    private String remark;

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

    public String getRisk() {
        return risk;
    }

    public void setRisk(String risk) {
        this.risk = risk;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getOccurDate() {
        return occurDate;
    }

    public void setOccurDate(String occurDate) {
        this.occurDate = occurDate;
    }

    public String getPlanedSolveDate() {
        return planedSolveDate;
    }

    public void setPlanedSolveDate(String planedSolveDate) {
        this.planedSolveDate = planedSolveDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPersonLiable() {
        return personLiable;
    }

    public void setPersonLiable(String personLiable) {
        this.personLiable = personLiable;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        RiskProblemForm that = (RiskProblemForm) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(projectId, that.projectId) &&
                Objects.equals(risk, that.risk) &&
                Objects.equals(level, that.level) &&
                Objects.equals(measure, that.measure) &&
                Objects.equals(occurDate, that.occurDate) &&
                Objects.equals(planedSolveDate, that.planedSolveDate) &&
                Objects.equals(status, that.status) &&
                Objects.equals(personLiable, that.personLiable) &&
                Objects.equals(remark, that.remark);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, projectId, risk, level, measure, occurDate, planedSolveDate, status, personLiable, remark);
    }

    @Override
    public String toString() {
        return "RiskProblemForm{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", risk='" + risk + '\'' +
                ", level='" + level + '\'' +
                ", measure='" + measure + '\'' +
                ", occurDate='" + occurDate + '\'' +
                ", planedSolveDate='" + planedSolveDate + '\'' +
                ", status=" + status +
                ", personLiable='" + personLiable + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
