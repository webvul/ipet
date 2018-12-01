package com.sensetime.iva.ipet.vo.form;

import java.io.Serializable;
import java.util.List;

/**
 * @author gongchao
 */
public class ProjectFilterForm implements Serializable {
    /**项目名称*/
    private String name;
    /**项目类型(合同/pk试点/试点转合同)*/
    private Integer type;
    /**项目级别(P0/P1/P2/P3)*/
    private Integer level;
    /**销售经理*/
    private String saleManager;
    /**解决方案经理*/
    private String solutionManager;
    /**项目状态*/
    private List<Integer> statusList;
    /**项目经理ID*/
    private List<Integer> createUserIds;
    /**创建时间 1为升序 0 降序*/
    private Integer createTime=0;
    /**完成率 1 为升序 0降序*/
    private Integer completionRate=1;
    /**区域ID*/
    private List<Integer> areaIds;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<Integer> getCreateUserIds() {
        return createUserIds;
    }

    public void setCreateUserIds(List<Integer> createUserIds) {
        this.createUserIds = createUserIds;
    }

    public List<Integer> getAreaIds() {
        return areaIds;
    }

    public void setAreaIds(List<Integer> areaIds) {
        this.areaIds = areaIds;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getCompletionRate() {
        return completionRate;
    }

    public void setCompletionRate(Integer completionRate) {
        this.completionRate = completionRate;
    }

    public List<Integer> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<Integer> statusList) {
        this.statusList = statusList;
    }

    public ProjectFilterForm() {
    }

    @Override
    public String toString() {
        return "ProjectFilterForm{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", level=" + level +
                ", saleManager='" + saleManager + '\'' +
                ", solutionManager='" + solutionManager + '\'' +
                ", statusList=" + statusList +
                ", createUserIds=" + createUserIds +
                ", createTime=" + createTime +
                ", completionRate=" + completionRate +
                ", areaIds=" + areaIds +
                '}';
    }
}
