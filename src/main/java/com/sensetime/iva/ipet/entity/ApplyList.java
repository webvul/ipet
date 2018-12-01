package com.sensetime.iva.ipet.entity;

import java.io.Serializable;

/**
 * @author  gongchao
 */
public class ApplyList extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1813490470160511633L;
    private Integer id;
    /**项目id*/
    private Integer projectId;
    /**阶段*/
    private Integer stage;
    /**任务项*/
    private Integer taskList;
    /**具体工作*/
    private String detailJob;
    /**工作量*/
    private Float workload;
    /**执行人*/
    private String executeMan;
    /**风险、问题及备注*/
    private String problemRemark;
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

    public Integer getTaskList() {
        return taskList;
    }

    public void setTaskList(Integer taskList) {
        this.taskList = taskList;
    }

    public String getDetailJob() {
        return detailJob;
    }

    public void setDetailJob(String detailJob) {
        this.detailJob = detailJob;
    }

    public Float getWorkload() {
        return workload;
    }

    public void setWorkload(Float workload) {
        this.workload = workload;
    }

    public String getExecuteMan() {
        return executeMan;
    }

    public void setExecuteMan(String executeMan) {
        this.executeMan = executeMan;
    }

    public String getProblemRemark() {
        return problemRemark;
    }

    public void setProblemRemark(String problemRemark) {
        this.problemRemark = problemRemark;
    }

    @Override
    public String toString() {
        return "ApplyList{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", stage=" + stage +
                ", taskList=" + taskList +
                ", detailJob='" + detailJob + '\'' +
                ", workload=" + workload +
                ", executeMan='" + executeMan + '\'' +
                ", problemRemark='" + problemRemark + '\'' +
                "} " + super.toString();
    }
}