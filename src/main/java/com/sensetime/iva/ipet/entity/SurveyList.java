package com.sensetime.iva.ipet.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author  gongchao
 */
public class SurveyList extends  BaseEntity implements Serializable {
    private static final long serialVersionUID = -324691575382970704L;
    private Integer id;
    /**项目id*/
    private Integer projectId;
    /**编号*/
    private String no;
    /**警区*/
    private String constablewick;
    /**地点*/
    private String location;
    /**数量*/
    private Integer amount;
    /**点位照度-强光*/
    private String glare;
    /**点位照度-背光*/
    private String backlight;
    /**点位照度-弱光*/
    private String weaklight;
    /**点位-过人率*/
    private String passingRate;
    /**点位-正脸率*/
    private String positiveRate;
    /**供电POE*/
    private String poe;
    /**直流电源*/
    private String dc;
    /**经度*/
    private Float longitude;
    /**纬度*/
    private Float latitude;
    /**方向角度（度）*/
    private Float directionAngle;
    /**镜头焦距（毫米）*/
    private Float focalLength;
    /**镜头取景距离（米）*/
    private Float viewDistance;
    /**镜头取景宽度（米）*/
    private Float viewWidth;
    /**架设高度（米）臂装/吊装/立杆*/
    private Float height;
    /**横向高度（米）装/吊装/立杆*/
    private Float widthHeight;
    /**是否有遮挡物*/
    private Boolean screen;
    /**是否防撞*/
    private Boolean antiCollision;
    /**点位验收（签字署名表示验收）*/
    private String pointCheck;
    /**眼间距（像素）*/
    private Integer eyeDistance;
    /**对焦图(通过)*/
    private String focalGraph;
    /**复验（签字署名表示验收）*/
    private String reexamination;

    public SurveyList() {
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

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getConstablewick() {
        return constablewick;
    }

    public void setConstablewick(String constablewick) {
        this.constablewick = constablewick;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getGlare() {
        return glare;
    }

    public void setGlare(String glare) {
        this.glare = glare;
    }

    public String getBacklight() {
        return backlight;
    }

    public void setBacklight(String backlight) {
        this.backlight = backlight;
    }

    public String getWeaklight() {
        return weaklight;
    }

    public void setWeaklight(String weaklight) {
        this.weaklight = weaklight;
    }

    public String getPassingRate() {
        return passingRate;
    }

    public void setPassingRate(String passingRate) {
        this.passingRate = passingRate;
    }

    public String getPositiveRate() {
        return positiveRate;
    }

    public void setPositiveRate(String positiveRate) {
        this.positiveRate = positiveRate;
    }

    public String getPoe() {
        return poe;
    }

    public void setPoe(String poe) {
        this.poe = poe;
    }

    public String getDc() {
        return dc;
    }

    public void setDc(String dc) {
        this.dc = dc;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getDirectionAngle() {
        return directionAngle;
    }

    public void setDirectionAngle(Float directionAngle) {
        this.directionAngle = directionAngle;
    }

    public Float getFocalLength() {
        return focalLength;
    }

    public void setFocalLength(Float focalLength) {
        this.focalLength = focalLength;
    }

    public Float getViewDistance() {
        return viewDistance;
    }

    public void setViewDistance(Float viewDistance) {
        this.viewDistance = viewDistance;
    }

    public Float getViewWidth() {
        return viewWidth;
    }

    public void setViewWidth(Float viewWidth) {
        this.viewWidth = viewWidth;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public Float getWidthHeight() {
        return widthHeight;
    }

    public void setWidthHeight(Float widthHeight) {
        this.widthHeight = widthHeight;
    }

    public Boolean getScreen() {
        return screen;
    }

    public void setScreen(Boolean screen) {
        this.screen = screen;
    }

    public Boolean getAntiCollision() {
        return antiCollision;
    }

    public void setAntiCollision(Boolean antiCollision) {
        this.antiCollision = antiCollision;
    }

    public String getPointCheck() {
        return pointCheck;
    }

    public void setPointCheck(String pointCheck) {
        this.pointCheck = pointCheck;
    }

    public Integer getEyeDistance() {
        return eyeDistance;
    }

    public void setEyeDistance(Integer eyeDistance) {
        this.eyeDistance = eyeDistance;
    }

    public String getFocalGraph() {
        return focalGraph;
    }

    public void setFocalGraph(String focalGraph) {
        this.focalGraph = focalGraph;
    }

    public String getReexamination() {
        return reexamination;
    }

    public void setReexamination(String reexamination) {
        this.reexamination = reexamination;
    }

    @Override
    public String toString() {
        return "SurveyList{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", no='" + no + '\'' +
                ", constablewick='" + constablewick + '\'' +
                ", location='" + location + '\'' +
                ", amount=" + amount +
                ", glare='" + glare + '\'' +
                ", backlight='" + backlight + '\'' +
                ", weaklight='" + weaklight + '\'' +
                ", passingRate='" + passingRate + '\'' +
                ", positiveRate='" + positiveRate + '\'' +
                ", poe='" + poe + '\'' +
                ", dc='" + dc + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", directionAngle=" + directionAngle +
                ", focalLength=" + focalLength +
                ", viewDistance=" + viewDistance +
                ", viewWidth=" + viewWidth +
                ", height=" + height +
                ", widthHeight=" + widthHeight +
                ", screen=" + screen +
                ", antiCollision=" + antiCollision +
                ", pointCheck='" + pointCheck + '\'' +
                ", eyeDistance=" + eyeDistance +
                ", focalGraph='" + focalGraph + '\'' +
                ", reexamination='" + reexamination + '\'' +
                "} " + super.toString();
    }
}