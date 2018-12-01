package com.sensetime.iva.ipet.vo.form;

import java.util.List;

/**
 * @author : ChaiLongLong
 * @date : 2018/8/16 12:14
 */
public class RiskProblemListForm {

    private List<RiskProblemForm> riskProblemForms;

    public List<RiskProblemForm> getRiskProblemForms() {
        return riskProblemForms;
    }

    public void setRiskProblemForms(List<RiskProblemForm> riskProblemForms) {
        this.riskProblemForms = riskProblemForms;
    }

    @Override
    public String toString() {
        return "RiskProblemListForm{" +
                "riskProblemForms=" + riskProblemForms +
                '}';
    }
}
