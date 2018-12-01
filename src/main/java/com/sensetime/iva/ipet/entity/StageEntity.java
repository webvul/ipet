package com.sensetime.iva.ipet.entity;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author: ChaiLongLong
 * @date : 2018/8/8 9:19
 */
public class StageEntity extends BaseEntity implements Serializable {


    private static final long serialVersionUID = -6638864037530117032L;
    private Integer id;
    /**
     * 项目计划id
     */
    private Integer projectId;
    /**
     * 阶段开始时间
     */
    private Date startDate;
    /**
     * 阶段结束时间
     */
    private Date endDate;
    /**
     * 阶段类型    1：每周看板    2：工时
     */
    private int type;

    /**
     * 当阶段为工时时，对应该阶段下多个员工的工时数据
     */
    private List<WorkTimeEntity> workTimeEntities;

    public List<WorkTimeEntity> getWorkTimeEntities() {
        return workTimeEntities;
    }

    public void setWorkTimeEntities(List<WorkTimeEntity> workTimeEntities) {
        this.workTimeEntities = workTimeEntities;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "StageEntity{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", type=" + type +
                ", workTimeEntities=" + workTimeEntities +
                "} " + super.toString();
    }

    public StageEntity() {
    }
}
