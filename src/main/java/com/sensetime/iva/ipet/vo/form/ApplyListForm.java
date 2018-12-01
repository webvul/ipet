package com.sensetime.iva.ipet.vo.form;

import com.sensetime.iva.ipet.entity.ApplyList;

import java.io.Serializable;
import java.util.List;

/**
 * @author  gongchao
 */
public class ApplyListForm implements Serializable {
    private static final long serialVersionUID = 7796234389247009672L;
    private List<ApplyList> applyLists;

    public List<ApplyList> getApplyLists() {
        return applyLists;
    }

    public void setApplyLists(List<ApplyList> applyLists) {
        this.applyLists = applyLists;
    }

    @Override
    public String toString() {
        return "ApplyListForm{" +
                "applyLists=" + applyLists +
                '}';
    }

    public ApplyListForm() {
    }
}
