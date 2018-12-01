package com.sensetime.iva.ipet.vo.form;

import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * @author : ChaiLongLong
 * @date : 2018/8/7 14:44
 */
public class WorkTimeForm {

    private Integer id;
    @ApiModelProperty(value="姓名")
    private String name;
    @ApiModelProperty(value="职能")
    private String workDesc;
    @ApiModelProperty(value="工时1")
    private Float workHour1;
    @ApiModelProperty(value="工时2")
    private Float workHour2;
    @ApiModelProperty(value="工时3")
    private Float workHour3;
    @ApiModelProperty(value="工时4")
    private Float workHour4;
    @ApiModelProperty(value="工时5")
    private Float workHour5;
    @ApiModelProperty(value="工时6")
    private Float workHour6;
    @ApiModelProperty(value="工时7")
    private Float workHour7;
    @ApiModelProperty(value="本周工时")
    private Float weekTotalHour;
    @ApiModelProperty(value="总计",hidden = true)
    private Float total;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        WorkTimeForm that = (WorkTimeForm) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(workDesc, that.workDesc) &&
                Objects.equals(workHour1, that.workHour1) &&
                Objects.equals(workHour2, that.workHour2) &&
                Objects.equals(workHour3, that.workHour3) &&
                Objects.equals(workHour4, that.workHour4) &&
                Objects.equals(workHour5, that.workHour5) &&
                Objects.equals(workHour6, that.workHour6) &&
                Objects.equals(workHour7, that.workHour7) &&
                Objects.equals(weekTotalHour, that.weekTotalHour) &&
                Objects.equals(total, that.total);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, workDesc, workHour1, workHour2, workHour3, workHour4, workHour5, workHour6, workHour7, weekTotalHour, total);
    }

    @Override
    public String toString() {
        return "WorkTimeForm{" +
                "id=" + id +
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
                '}';
    }
}
