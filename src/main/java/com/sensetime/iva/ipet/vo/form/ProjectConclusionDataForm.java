package com.sensetime.iva.ipet.vo.form;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author : ChaiLongLong
 * @date : 2018/8/13 15:13
 */
public class ProjectConclusionDataForm {

    @ApiModelProperty(value="项目结项id")
    private Integer id;
    @ApiModelProperty(value="项目名称",hidden = true)
    private String name;
    @ApiModelProperty(value="项目编号",hidden = true)
    private String serial;
    @ApiModelProperty(value="项目经理",hidden = true)
    private String projectManager;
    @ApiModelProperty(value="友商",hidden = true)
    private String friends;
    @ApiModelProperty(value="项目立项id")
    private int projectId;
    @ApiModelProperty(value="项目目标")
    private String target;
    @ApiModelProperty(value="项目周期")
    private String cycle;
    @ApiModelProperty(value="身份 1:代表我司 2:代表合作伙伴 3:代表代理商")
    private int identity;
    @ApiModelProperty(value="方案")
    private String plan;
    @ApiModelProperty(value="阶段性结论")
    private String phaseConclusion;
    @ApiModelProperty(value="方案设计周期")
    private int planDesignCycle;
    @ApiModelProperty(value="方案设计说明")
    private String planDesignDescribe;
    @ApiModelProperty(value="定制化开发周期")
    private int customizationDevelopmentCycle;
    @ApiModelProperty(value="定制化开发说明")
    private String customizationDevelopmentDescribe;
    @ApiModelProperty(value="预交付周期")
    private int preDeliverCycle;
    @ApiModelProperty(value="预交付说明")
    private String preDeliverDescribe;
    @ApiModelProperty(value="交付周期")
    private int deliverCycle;
    @ApiModelProperty(value="交付说明")
    private String deliverDescribe;
    @ApiModelProperty(value="维护周期")
    private int maintenanceCycle;
    @ApiModelProperty(value="维护说明")
    private String maintenanceDescribe;
    @ApiModelProperty(value="验收周期")
    private int acceptanceCycle;
    @ApiModelProperty(value="验收说明")
    private String acceptanceDescribe;
    @ApiModelProperty(value="售后周期")
    private int afterSaleCycle;
    @ApiModelProperty(value="售后说明")
    private String afterSaleDescribe;
    @ApiModelProperty(value="故障次数")
    private int faultNum;
    @ApiModelProperty(value="故障说明")
    private String faultNumDescribe;
    @ApiModelProperty(value="实施次数")
    private int implementNum;
    @ApiModelProperty(value="实施说明")
    private String implementDescribe;
    @ApiModelProperty(value="总计")
    private int total;
    @ApiModelProperty(value="总计说明")
    private String totalDescribe;
    @ApiModelProperty(value="备注")
    private String remark;
    @ApiModelProperty(value="主要行动及事件")
    private String actionEvent;
    @ApiModelProperty(value="我司优劣势")
    private String ourStrengthsWeaknesses;
    @ApiModelProperty(value="友商优劣势")
    private String friendsStrengthsWeaknesses;
    @ApiModelProperty(value="遗留问题")
    private String legacy;
    @ApiModelProperty(value="经验总结")
    private String experienceSummary;
    @ApiModelProperty(value="改进建议")
    private String improvement;
    @ApiModelProperty(value="状态 1.未处理 2.待评审 3.通过 4.打回",hidden = true)
    private int status;

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

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }

    public String getFriends() {
        return friends;
    }

    public void setFriends(String friends) {
        this.friends = friends;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public int getIdentity() {
        return identity;
    }

    public void setIdentity(int identity) {
        this.identity = identity;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getPhaseConclusion() {
        return phaseConclusion;
    }

    public void setPhaseConclusion(String phaseConclusion) {
        this.phaseConclusion = phaseConclusion;
    }

    public int getPlanDesignCycle() {
        return planDesignCycle;
    }

    public void setPlanDesignCycle(int planDesignCycle) {
        this.planDesignCycle = planDesignCycle;
    }

    public String getPlanDesignDescribe() {
        return planDesignDescribe;
    }

    public void setPlanDesignDescribe(String planDesignDescribe) {
        this.planDesignDescribe = planDesignDescribe;
    }

    public int getCustomizationDevelopmentCycle() {
        return customizationDevelopmentCycle;
    }

    public void setCustomizationDevelopmentCycle(int customizationDevelopmentCycle) {
        this.customizationDevelopmentCycle = customizationDevelopmentCycle;
    }

    public String getCustomizationDevelopmentDescribe() {
        return customizationDevelopmentDescribe;
    }

    public void setCustomizationDevelopmentDescribe(String customizationDevelopmentDescribe) {
        this.customizationDevelopmentDescribe = customizationDevelopmentDescribe;
    }

    public int getPreDeliverCycle() {
        return preDeliverCycle;
    }

    public void setPreDeliverCycle(int preDeliverCycle) {
        this.preDeliverCycle = preDeliverCycle;
    }

    public String getPreDeliverDescribe() {
        return preDeliverDescribe;
    }

    public void setPreDeliverDescribe(String preDeliverDescribe) {
        this.preDeliverDescribe = preDeliverDescribe;
    }

    public int getDeliverCycle() {
        return deliverCycle;
    }

    public void setDeliverCycle(int deliverCycle) {
        this.deliverCycle = deliverCycle;
    }

    public String getDeliverDescribe() {
        return deliverDescribe;
    }

    public void setDeliverDescribe(String deliverDescribe) {
        this.deliverDescribe = deliverDescribe;
    }

    public int getMaintenanceCycle() {
        return maintenanceCycle;
    }

    public void setMaintenanceCycle(int maintenanceCycle) {
        this.maintenanceCycle = maintenanceCycle;
    }

    public String getMaintenanceDescribe() {
        return maintenanceDescribe;
    }

    public void setMaintenanceDescribe(String maintenanceDescribe) {
        this.maintenanceDescribe = maintenanceDescribe;
    }

    public int getAcceptanceCycle() {
        return acceptanceCycle;
    }

    public void setAcceptanceCycle(int acceptanceCycle) {
        this.acceptanceCycle = acceptanceCycle;
    }

    public String getAcceptanceDescribe() {
        return acceptanceDescribe;
    }

    public void setAcceptanceDescribe(String acceptanceDescribe) {
        this.acceptanceDescribe = acceptanceDescribe;
    }

    public int getAfterSaleCycle() {
        return afterSaleCycle;
    }

    public void setAfterSaleCycle(int afterSaleCycle) {
        this.afterSaleCycle = afterSaleCycle;
    }

    public String getAfterSaleDescribe() {
        return afterSaleDescribe;
    }

    public void setAfterSaleDescribe(String afterSaleDescribe) {
        this.afterSaleDescribe = afterSaleDescribe;
    }

    public int getFaultNum() {
        return faultNum;
    }

    public void setFaultNum(int faultNum) {
        this.faultNum = faultNum;
    }

    public String getFaultNumDescribe() {
        return faultNumDescribe;
    }

    public void setFaultNumDescribe(String faultNumDescribe) {
        this.faultNumDescribe = faultNumDescribe;
    }

    public int getImplementNum() {
        return implementNum;
    }

    public void setImplementNum(int implementNum) {
        this.implementNum = implementNum;
    }

    public String getImplementDescribe() {
        return implementDescribe;
    }

    public void setImplementDescribe(String implementDescribe) {
        this.implementDescribe = implementDescribe;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getTotalDescribe() {
        return totalDescribe;
    }

    public void setTotalDescribe(String totalDescribe) {
        this.totalDescribe = totalDescribe;
    }

    public int getStatus() {
        return status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getActionEvent() {
        return actionEvent;
    }

    public void setActionEvent(String actionEvent) {
        this.actionEvent = actionEvent;
    }

    public String getOurStrengthsWeaknesses() {
        return ourStrengthsWeaknesses;
    }

    public void setOurStrengthsWeaknesses(String ourStrengthsWeaknesses) {
        this.ourStrengthsWeaknesses = ourStrengthsWeaknesses;
    }

    public String getFriendsStrengthsWeaknesses() {
        return friendsStrengthsWeaknesses;
    }

    public void setFriendsStrengthsWeaknesses(String friendsStrengthsWeaknesses) {
        this.friendsStrengthsWeaknesses = friendsStrengthsWeaknesses;
    }

    public String getLegacy() {
        return legacy;
    }

    public void setLegacy(String legacy) {
        this.legacy = legacy;
    }

    public String getExperienceSummary() {
        return experienceSummary;
    }

    public void setExperienceSummary(String experienceSummary) {
        this.experienceSummary = experienceSummary;
    }

    public String getImprovement() {
        return improvement;
    }

    public void setImprovement(String improvement) {
        this.improvement = improvement;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ProjectConclusionDataForm{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", serial='" + serial + '\'' +
                ", projectManager='" + projectManager + '\'' +
                ", friends='" + friends + '\'' +
                ", projectId=" + projectId +
                ", target='" + target + '\'' +
                ", cycle='" + cycle + '\'' +
                ", identity=" + identity +
                ", plan='" + plan + '\'' +
                ", phaseConclusion='" + phaseConclusion + '\'' +
                ", planDesignCycle=" + planDesignCycle +
                ", planDesignDescribe='" + planDesignDescribe + '\'' +
                ", customizationDevelopmentCycle=" + customizationDevelopmentCycle +
                ", customizationDevelopmentDescribe='" + customizationDevelopmentDescribe + '\'' +
                ", preDeliverCycle=" + preDeliverCycle +
                ", preDeliverDescribe='" + preDeliverDescribe + '\'' +
                ", deliverCycle=" + deliverCycle +
                ", deliverDescribe='" + deliverDescribe + '\'' +
                ", maintenanceCycle=" + maintenanceCycle +
                ", maintenanceDescribe='" + maintenanceDescribe + '\'' +
                ", acceptanceCycle=" + acceptanceCycle +
                ", acceptanceDescribe='" + acceptanceDescribe + '\'' +
                ", afterSaleCycle=" + afterSaleCycle +
                ", afterSaleDescribe='" + afterSaleDescribe + '\'' +
                ", faultNum=" + faultNum +
                ", faultNumDescribe='" + faultNumDescribe + '\'' +
                ", implementNum=" + implementNum +
                ", implementDescribe='" + implementDescribe + '\'' +
                ", total=" + total +
                ", totalDescribe='" + totalDescribe + '\'' +
                ", remark='" + remark + '\'' +
                ", actionEvent='" + actionEvent + '\'' +
                ", ourStrengthsWeaknesses='" + ourStrengthsWeaknesses + '\'' +
                ", friendsStrengthsWeaknesses='" + friendsStrengthsWeaknesses + '\'' +
                ", legacy='" + legacy + '\'' +
                ", experienceSummary='" + experienceSummary + '\'' +
                ", improvement='" + improvement + '\'' +
                ", status=" + status +
                '}';
    }
}
