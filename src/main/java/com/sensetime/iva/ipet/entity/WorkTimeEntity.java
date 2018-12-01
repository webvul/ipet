package com.sensetime.iva.ipet.entity;

/**
 * @Author: ChaiLongLong
 * @date : 2018/8/7 13:49
 */
public class WorkTimeEntity extends BaseEntity {

    private Integer id;
    private Integer stageId;
    private String name;
    private String workDesc;
    private Float workHour1;
    private Float workHour2;
    private Float workHour3;
    private Float workHour4;
    private Float workHour5;
    private Float workHour6;
    private Float workHour7;
    private Float weekTotalHour;
    private Float total;

    public WorkTimeEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStageId() {
        return stageId;
    }

    public void setStageId(Integer stageId) {
        this.stageId = stageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorkDesc() {
        return workDesc;
    }

    public void setWorkDesc(String workDesc) {
        this.workDesc = workDesc;
    }

    public Float getWorkHour1() {
        return workHour1;
    }

    public void setWorkHour1(Float workHour1) {
        this.workHour1 = workHour1;
    }

    public Float getWorkHour2() {
        return workHour2;
    }

    public void setWorkHour2(Float workHour2) {
        this.workHour2 = workHour2;
    }

    public Float getWorkHour3() {
        return workHour3;
    }

    public void setWorkHour3(Float workHour3) {
        this.workHour3 = workHour3;
    }

    public Float getWorkHour4() {
        return workHour4;
    }

    public void setWorkHour4(Float workHour4) {
        this.workHour4 = workHour4;
    }

    public Float getWorkHour5() {
        return workHour5;
    }

    public void setWorkHour5(Float workHour5) {
        this.workHour5 = workHour5;
    }

    public Float getWorkHour6() {
        return workHour6;
    }

    public void setWorkHour6(Float workHour6) {
        this.workHour6 = workHour6;
    }

    public Float getWorkHour7() {
        return workHour7;
    }

    public void setWorkHour7(Float workHour7) {
        this.workHour7 = workHour7;
    }

    public Float getWeekTotalHour() {
        return weekTotalHour;
    }

    public void setWeekTotalHour(Float weekTotalHour) {
        this.weekTotalHour = weekTotalHour;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "WorkTimeEntity{" +
                "id=" + id +
                ", stageId=" + stageId +
                ", name='" + name + '\'' +
                ", workDesc='" + workDesc + '\'' +
                ", workHour1=" + workHour1 +
                ", workHour2=" + workHour2 +
                ", workHour3=" + workHour3 +
                ", workHour4=" + workHour4 +
                ", workHour5=" + workHour5 +
                ", workHour6=" + workHour6 +
                ", workHour7=" + workHour7 +
                ", weekTotalHour=" + weekTotalHour +
                ", total=" + total +
                "} " + super.toString();
    }
}
