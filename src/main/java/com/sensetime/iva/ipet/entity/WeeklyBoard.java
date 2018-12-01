package com.sensetime.iva.ipet.entity;

import java.io.Serializable;

/**
 * @author  gongchao
 */
public class WeeklyBoard extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 8576118254130064766L;
    private Integer id;
    /**项目id*/
    private Integer projectId;
    /**项目阶段*/
    private Integer stage;
    /**任务项*/
    private Integer task;
    /**具体工作*/
    private String workDesc;
    /**输出物*/
    private String output;
    /**计划开始时间*/
    private String planStartDate;
    /**实际开始时间*/
    private String realStartDate;
    /**计划完成时间*/
    private String planFinishDate;
    /**真实完成时间*/
    private String realFinishDate;
    /**完成率*/
    private Float completionRate;
    /**工作量格式:(人名-人天)*/
    private String workload;
    /**责任人*/
    private String personLiable;
    /**风险、问题及备注*/
    private String remark;

    public WeeklyBoard() {
    }

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

    public Integer getStage() {
        return stage;
    }

    public void setStage(Integer stage) {
        this.stage = stage;
    }

    public Integer getTask() {
        return task;
    }

    public void setTask(Integer task) {
        this.task = task;
    }

    public String getWorkDesc() {
        return workDesc;
    }

    public void setWorkDesc(String workDesc) {
        this.workDesc = workDesc;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getPlanStartDate() {
        return planStartDate;
    }

    public void setPlanStartDate(String planStartDate) {
        this.planStartDate = planStartDate;
    }

    public String getRealStartDate() {
        return realStartDate;
    }

    public void setRealStartDate(String realStartDate) {
        this.realStartDate = realStartDate;
    }

    public String getPlanFinishDate() {
        return planFinishDate;
    }

    public void setPlanFinishDate(String planFinishDate) {
        this.planFinishDate = planFinishDate;
    }

    public String getRealFinishDate() {
        return realFinishDate;
    }

    public void setRealFinishDate(String realFinishDate) {
        this.realFinishDate = realFinishDate;
    }

    public Float getCompletionRate() {
        return completionRate;
    }

    public void setCompletionRate(Float completionRate) {
        this.completionRate = completionRate;
    }

    public String getWorkload() {
        return workload;
    }

    public void setWorkload(String workload) {
        this.workload = workload;
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
        return "WeeklyBoard{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", stage=" + stage +
                ", task=" + task +
                ", workDesc='" + workDesc + '\'' +
                ", output='" + output + '\'' +
                ", planStartDate='" + planStartDate + '\'' +
                ", realStartDate='" + realStartDate + '\'' +
                ", planFinishDate='" + planFinishDate + '\'' +
                ", realFinishDate='" + realFinishDate + '\'' +
                ", completionRate=" + completionRate +
                ", workload='" + workload + '\'' +
                ", personLiable='" + personLiable + '\'' +
                ", remark='" + remark + '\'' +
                "} " + super.toString();
    }
}