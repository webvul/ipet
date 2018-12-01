package com.sensetime.iva.ipet.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author  gongchao
 */
public class ProjectPlan extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 4185552771301919352L;
    private Integer id;
    /**项目id*/
    private Integer projectId;
    /**项目进度*/
    private String projectProgress;
    /**风险求助*/
    private String riskAndHelp;
    /**项目状态*/
    private Integer projectStatus;
    /**汇报周期*/
    private String reportCycle;
    /**本周计划*/
    private String weekProgress;
    /**下周计划*/
    private String nextWeekPlan;
    /**file表id*/
    private Integer attachment;
    /**本周起始日期时间戳*/
    private String startDate;
    /**项目*/
    private Project project;
    /**当前项目计划的所有上传文件*/
    List<File> files;
    private Integer stageId;
    private StageEntity stageEntity;
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

    public String getProjectProgress() {
        return projectProgress;
    }

    public void setProjectProgress(String projectProgress) {
        this.projectProgress = projectProgress;
    }

    public String getRiskAndHelp() {
        return riskAndHelp;
    }

    public void setRiskAndHelp(String riskAndHelp) {
        this.riskAndHelp = riskAndHelp;
    }

    public Integer getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(Integer projectStatus) {
        this.projectStatus = projectStatus;
    }

    public String getReportCycle() {
        return reportCycle;
    }

    public void setReportCycle(String reportCycle) {
        this.reportCycle = reportCycle;
    }

    public String getWeekProgress() {
        return weekProgress;
    }

    public void setWeekProgress(String weekProgress) {
        this.weekProgress = weekProgress;
    }

    public String getNextWeekPlan() {
        return nextWeekPlan;
    }

    public void setNextWeekPlan(String nextWeekPlan) {
        this.nextWeekPlan = nextWeekPlan;
    }

    public Integer getAttachment() {
        return attachment;
    }

    public void setAttachment(Integer attachment) {
        this.attachment = attachment;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public StageEntity getStageEntity() {
        return stageEntity;
    }

    public void setStageEntity(StageEntity stageEntity) {
        this.stageEntity = stageEntity;
    }

    public Integer getStageId() {
        return stageId;
    }

    public void setStageId(Integer stageId) {
        this.stageId = stageId;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    @Override
    public String toString() {
        return "ProjectPlan{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", projectProgress='" + projectProgress + '\'' +
                ", riskAndHelp='" + riskAndHelp + '\'' +
                ", projectStatus=" + projectStatus +
                ", reportCycle='" + reportCycle + '\'' +
                ", weekProgress='" + weekProgress + '\'' +
                ", nextWeekPlan='" + nextWeekPlan + '\'' +
                ", attachment=" + attachment +
                ", startDate='" + startDate + '\'' +
                ", project=" + project +
                ", files=" + files +
                ", stageId=" + stageId +
                "} " + super.toString();
    }

    public ProjectPlan() {
    }
}