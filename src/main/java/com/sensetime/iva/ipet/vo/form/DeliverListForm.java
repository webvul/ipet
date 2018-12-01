package com.sensetime.iva.ipet.vo.form;

import com.sensetime.iva.ipet.entity.DeliverList;

import java.io.Serializable;
import java.util.List;

/**
 * @author  gongchao
 */
public class DeliverListForm implements Serializable {
    private static final long serialVersionUID = -6910007498516765542L;
    private List<DeliverList> deliverLists;

    public DeliverListForm() {
    }

    public List<DeliverList> getDeliverLists() {
        return deliverLists;
    }

    public void setDeliverLists(List<DeliverList> deliverLists) {
        this.deliverLists = deliverLists;
    }

    @Override
    public String toString() {
        return "DeliverListForm{" +
                "deliverLists=" + deliverLists +
                '}';
    }
}
