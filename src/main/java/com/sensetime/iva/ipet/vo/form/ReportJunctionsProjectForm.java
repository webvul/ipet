package com.sensetime.iva.ipet.vo.form;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author gongchao
 * @date 12:46 2018/9/18
 */
public class ReportJunctionsProjectForm implements Serializable {
    @ApiModelProperty(value="结项数量")
    private  List<ReportJunctionsBaseForm>  junctionsList;
    @ApiModelProperty(value="类型分布")
    private  List<ReportJunctionsTypeForm> typeLIst;
    @ApiModelProperty(value="级别分布")
    private  List<ReportJunctionsLevelForm> levelLIst;
    @ApiModelProperty(value="项目经理手上的项目个数")
    private  List<ReportJunctionsProjectAmountForm> junctionsAmountList;
    public ReportJunctionsProjectForm() {
    }

    public List<ReportJunctionsBaseForm> getJunctionsList() {
        return junctionsList;
    }

    public void setJunctionsList(List<ReportJunctionsBaseForm> junctionsList) {
        this.junctionsList = junctionsList;
    }

    public List<ReportJunctionsTypeForm> getTypeLIst() {
        return typeLIst;
    }

    public void setTypeLIst(List<ReportJunctionsTypeForm> typeLIst) {
        this.typeLIst = typeLIst;
    }

    public List<ReportJunctionsLevelForm> getLevelLIst() {
        return levelLIst;
    }

    public void setLevelLIst(List<ReportJunctionsLevelForm> levelLIst) {
        this.levelLIst = levelLIst;
    }

    public List<ReportJunctionsProjectAmountForm> getJunctionsAmountList() {
        return junctionsAmountList;
    }

    public void setJunctionsAmountList(List<ReportJunctionsProjectAmountForm> junctionsAmountList) {
        this.junctionsAmountList = junctionsAmountList;
    }

    @Override
    public String toString() {
        return "ReportJunctionsProjectForm{" +
                "junctionsList=" + junctionsList +
                ", typeLIst=" + typeLIst +
                ", levelLIst=" + levelLIst +
                ", junctionsAmountList=" + junctionsAmountList +
                '}';
    }
}
