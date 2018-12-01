package com.sensetime.iva.ipet.vo.form;

import com.sensetime.iva.ipet.entity.SurveyList;

import java.io.Serializable;
import java.util.List;

/**
 * @author  gongchao
 */
public class SurveyListForm implements Serializable {
    private static final long serialVersionUID = 5307508649650290663L;
    private List<SurveyList> surveyLists;

    public List<SurveyList> getSurveyLists() {
        return surveyLists;
    }

    public void setSurveyLists(List<SurveyList> surveyLists) {
        this.surveyLists = surveyLists;
    }

    public SurveyListForm() {
    }

    @Override
    public String toString() {
        return "SurveyListForm{" +
                "surveyLists=" + surveyLists +
                '}';
    }
}
