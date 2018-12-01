package com.sensetime.iva.ipet.entity;

/**
 * @Author: ChaiLongLong
 * @date : 2018/8/8 14:02
 */
public class BusinessTripEntity extends BaseEntity {

    private Integer id;
    private Integer projectId;
    private String name;
    private String workDesc;
    private int type;
    private String destination;
    private String startDate;
    private String endDate;
    private float accommodation;
    private float traffic;
    private float other;
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
    public String toString() {
        return "BusinessTripEntity{" +
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
                "} " + super.toString();
    }
}
