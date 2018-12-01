package com.sensetime.iva.ipet.vo.form;

import com.sensetime.iva.ipet.entity.AreaEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author  gongchao
 */
public class ProjectInitFilterForm implements Serializable{
    /**销售经理*/
    private List<String> saleManagers ;
    /**方案解决*/
    private List<String> solutionManagers;
    /**项目经理*/
    private Map<Integer,String> projectManagerMap;
    /**项目状态*/
    private Map<Integer,String> statusMap;
    /**创建时间升序降序*/
    private Map<Integer,String> createTimeMap;
    /**完成率升序降序*/
    private Map<Integer,String> completionRateMap;
    /**项目类型*/
    private Map<Integer,String> typeMap;
    /**项目级别*/
    private Map<Integer,String> levelMap;
    /**区域*/
    private Map<Integer,String> areaMap;
    public List<String> getSaleManagers() {
        return saleManagers;
    }

    public void setSaleManagers(List<String> saleManagers) {
        this.saleManagers = saleManagers;
    }

    public List<String> getSolutionManagers() {
        return solutionManagers;
    }

    public void setSolutionManagers(List<String> solutionManagers) {
        this.solutionManagers = solutionManagers;
    }

    public Map<Integer, String> getProjectManagerMap() {
        return projectManagerMap;
    }

    public void setProjectManagerMap(Map<Integer, String> projectManagerMap) {
        this.projectManagerMap = projectManagerMap;
    }

    public Map<Integer, String> getStatusMap() {
        return statusMap;
    }

    public void setStatusMap(Map<Integer, String> statusMap) {
        this.statusMap = statusMap;
    }

    public Map<Integer, String> getCreateTimeMap() {
        return createTimeMap;
    }

    public void setCreateTimeMap(Map<Integer, String> createTimeMap) {
        this.createTimeMap = createTimeMap;
    }

    public Map<Integer, String> getCompletionRateMap() {
        return completionRateMap;
    }

    public void setCompletionRateMap(Map<Integer, String> completionRateMap) {
        this.completionRateMap = completionRateMap;
    }

    public Map<Integer,String> getTypeMap() {
        return typeMap;
    }

    public void setTypeMap(Map<Integer,String> typeMap) {
        this.typeMap = typeMap;
    }

    public Map<Integer,String> getLevelMap() {
        return levelMap;
    }

    public void setLevelMap(Map<Integer,String> levelMap) {
        this.levelMap = levelMap;
    }

    public ProjectInitFilterForm() {
    }

    public Map<Integer, String> getAreaMap() {
        return areaMap;
    }

    public void setAreaMap(Map<Integer, String> areaMap) {
        this.areaMap = areaMap;
    }

    @Override
    public String toString() {
        return "ProjectInitFilterForm{" +
                "saleManagers=" + saleManagers +
                ", solutionManagers=" + solutionManagers +
                ", projectManagerMap=" + projectManagerMap +
                ", statusMap=" + statusMap +
                ", createTimeMap=" + createTimeMap +
                ", completionRateMap=" + completionRateMap +
                ", typeMap=" + typeMap +
                ", levelMap=" + levelMap +
                ", areaMap=" + areaMap +
                '}';
    }
}
