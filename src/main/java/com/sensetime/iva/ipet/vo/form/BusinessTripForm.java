package com.sensetime.iva.ipet.vo.form;

import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * @author : ChaiLongLong
 * @date : 2018/8/8 17:45
 */
public class BusinessTripForm {

    private Integer id;
    @ApiModelProperty(value="项目id")
    private Integer projectId;
    @ApiModelProperty(value="出差人员")
    private String name;
    @ApiModelProperty(value="职能")
    private String workDesc;
    @ApiModelProperty(value="出差类型 1:长途  2:短途")
    private int type;
    @ApiModelProperty(value="出差地")
    private String destination;
    @ApiModelProperty(value="出发日期，字符串\"yyyy-MM-dd\"")
    private String startDate;
    @ApiModelProperty(value="结束日期，字符串\"yyyy-MM-dd\"")
    private String endDate;
    @ApiModelProperty(value="住宿费RMB")
    private float accommodation;
    @ApiModelProperty(value="交通费RMB")
    private float traffic;
    @ApiModelProperty(value="其他RMB")
    private float other;
    @ApiModelProperty(value="差旅总计RMB")
    private float total;

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public float getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(float accommodation) {
        this.accommodation = accommodation;
    }

    public float getTraffic() {
        return traffic;
    }

    public void setTraffic(float traffic) {
        this.traffic = traffic;
    }

    public float getOther() {
        return other;
    }

    public void setOther(float other) {
        this.other = other;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        BusinessTripForm that = (BusinessTripForm) o;
        return type == that.type &&
                Float.compare(that.accommodation, accommodation) == 0 &&
                Float.compare(that.traffic, traffic) == 0 &&
                Float.compare(that.other, other) == 0 &&
                Float.compare(that.total, total) == 0 &&
                Objects.equals(id, that.id) &&
                Objects.equals(projectId, that.projectId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(workDesc, that.workDesc) &&
                Objects.equals(destination, that.destination) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, projectId, name, workDesc, type, destination, startDate, endDate, accommodation, traffic, other, total);
    }

    @Override
    public String toString() {
        return "BusinessTripForm{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", name='" + name + '\'' +
                ", workDesc='" + workDesc + '\'' +
                ", type=" + type +
                ", destination='" + destination + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", accommodation=" + accommodation +
                ", traffic=" + traffic +
                ", other=" + other +
                ", total=" + total +
                '}';
    }
}
