package com.sensetime.iva.ipet.entity;

import java.io.Serializable;

/**
 * @author gongchao
 */
public class ProjectStage extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 619443898577389740L;
    private Integer id;
    /**立项id*/
    private Integer projectId;
    /**项目阶段*/
    private Integer step;
    /**目标*/
    private String target;
    /**yyyy-MM-dd*/
    private String deliveryDate;
    /**系统*/
    private Integer businessSystemId;
    /**平台*/
    private Integer platformId;
    /**阶段规模*/
    private String stageScale;
    /**预期规模*/
    private String expectedScale;
    /**产品要求*/
    private Integer productRequire;
    /**定制化需求*/
    private Integer customization;
    /**立项信息*/
    private Project project;
    public ProjectStage() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Integer getBusinessSystemId() {
        return businessSystemId;
    }

    public void setBusinessSystemId(Integer businessSystemId) {
        this.businessSystemId = businessSystemId;
    }

    public Integer getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Integer platformId) {
        this.platformId = platformId;
    }

    public String getStageScale() {
        return stageScale;
    }

    public void setStageScale(String stageScale) {
        this.stageScale = stageScale;
    }

    public String getExpectedScale() {
        return expectedScale;
    }

    public void setExpectedScale(String expectedScale) {
        this.expectedScale = expectedScale;
    }

    public Integer getProductRequire() {
        return productRequire;
    }

    public void setProductRequire(Integer productRequire) {
        this.productRequire = productRequire;
    }

    public Integer getCustomization() {
        return customization;
    }

    public void setCustomization(Integer customization) {
        this.customization = customization;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return "ProjectStage{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", step=" + step +
                ", target='" + target + '\'' +
                ", deliveryDate='" + deliveryDate + '\'' +
                ", businessSystemId=" + businessSystemId +
                ", platformId=" + platformId +
                ", stageScale='" + stageScale + '\'' +
                ", productRequire=" + productRequire +
                ", customization=" + customization +
                "} " + super.toString();
    }
}
