package com.sensetime.iva.ipet.vo.form;

import com.sensetime.iva.ipet.entity.AccountEntity;
import com.sensetime.iva.ipet.entity.BaseEntity;
import com.sensetime.iva.ipet.entity.ProjectStage;

import java.io.Serializable;
import java.util.List;

/**
 * @author gongchao
 * @date 9:51 2018/9/11
 */
public class ProjectForm extends BaseEntity implements Serializable{

        /**项目pk*/
        private Integer id;
        /**项目名称*/
        private String name;
        /**项目编号*/
        private String serial;
        /**是否旧项目新需求*/
        private Integer  fromOldProject;
        /**旧项目编号*/
        private String oldProjectSerial;
        /**CRM编号*/
        private String crmNo;
        /**项目类型(合同/pk试点/试点转合同)*/
        private Integer type;
        /**项目级别(P0/P1/P2/P3)*/
        private Integer level;
        /**成单金额*/
        private String amount;
        /**销售经理*/
        private String saleManager;
        /**解决方案经理*/
        private String solutionManager;
        /**售前工程师*/
        private String preSale;
        /**甲方*/
        private String firstParty;
        /**合作伙伴/集成商名称*/
        private String partners;
        /**友商*/
        private String friends;
        /**项目状态(立项中、进行中、已完成等)*/
        private Integer status;
        /**立项报告id*/
        private Integer docFileId;
        /**项目经理*/
        private Integer createUserId;
        /**项目阶段*/
        private List<ProjectStage> projectStages;
        /**项目背景*/
        private String background;
        /**附加说明*/
        private String attachment;
        /**结项前的状态*/
        private Integer lastStatus;
        /**项目经理*/
        private AccountEntity account;
        /**项目经理*/
        private Float completionRate=0F;
        /**区域名称*/
        private String areaName;
        public ProjectForm() {
        }

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

    public Integer getFromOldProject() {
        return fromOldProject;
    }

    public void setFromOldProject(Integer fromOldProject) {
        this.fromOldProject = fromOldProject;
    }

    public String getOldProjectSerial() {
        return oldProjectSerial;
    }

    public void setOldProjectSerial(String oldProjectSerial) {
        this.oldProjectSerial = oldProjectSerial;
    }

    public String getCrmNo() {
        return crmNo;
    }

    public void setCrmNo(String crmNo) {
        this.crmNo = crmNo;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getSaleManager() {
        return saleManager;
    }

    public void setSaleManager(String saleManager) {
        this.saleManager = saleManager;
    }

    public String getSolutionManager() {
        return solutionManager;
    }

    public void setSolutionManager(String solutionManager) {
        this.solutionManager = solutionManager;
    }

    public String getPreSale() {
        return preSale;
    }

    public void setPreSale(String preSale) {
        this.preSale = preSale;
    }

    public String getFirstParty() {
        return firstParty;
    }

    public void setFirstParty(String firstParty) {
        this.firstParty = firstParty;
    }

    public String getPartners() {
        return partners;
    }

    public void setPartners(String partners) {
        this.partners = partners;
    }

    public String getFriends() {
        return friends;
    }

    public void setFriends(String friends) {
        this.friends = friends;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDocFileId() {
        return docFileId;
    }

    public void setDocFileId(Integer docFileId) {
        this.docFileId = docFileId;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public List<ProjectStage> getProjectStages() {
        return projectStages;
    }

    public void setProjectStages(List<ProjectStage> projectStages) {
        this.projectStages = projectStages;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public Integer getLastStatus() {
        return lastStatus;
    }

    public void setLastStatus(Integer lastStatus) {
        this.lastStatus = lastStatus;
    }

    public AccountEntity getAccount() {
        return account;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
    }

    public Float getCompletionRate() {
        return completionRate;
    }

    public void setCompletionRate(Float completionRate) {
        this.completionRate = completionRate;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    @Override
    public String toString() {
        return "ProjectForm{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", serial='" + serial + '\'' +
                ", fromOldProject=" + fromOldProject +
                ", oldProjectSerial='" + oldProjectSerial + '\'' +
                ", crmNo='" + crmNo + '\'' +
                ", type=" + type +
                ", level=" + level +
                ", amount=" + amount +
                ", saleManager='" + saleManager + '\'' +
                ", solutionManager='" + solutionManager + '\'' +
                ", preSale='" + preSale + '\'' +
                ", firstParty='" + firstParty + '\'' +
                ", partners='" + partners + '\'' +
                ", friends='" + friends + '\'' +
                ", status=" + status +
                ", docFileId=" + docFileId +
                ", createUserId=" + createUserId +
                ", projectStages=" + projectStages +
                ", background='" + background + '\'' +
                ", attachment='" + attachment + '\'' +
                ", lastStatus=" + lastStatus +
                ", account=" + account +
                ", completionRate=" + completionRate +
                ", areaName='" + areaName + '\'' +
                "} " + super.toString();
    }
}
