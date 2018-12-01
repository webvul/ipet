package com.sensetime.iva.ipet.entity;

/**
 * @Author: ChaiLongLong
 * @date : 2018/8/8 19:52
 */
public class RiskProblemEntity extends BaseEntity {

    private Integer id;
    private Integer projectId;
    private String risk;
    private String level;
    private String measure;
    private String occurDate;
    private String planedSolveDate;
    private Integer status;
    private String personLiable;
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
    public String toString() {
        return "RiskProblemEntity{" +
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
                "} " + super.toString();
    }
}
