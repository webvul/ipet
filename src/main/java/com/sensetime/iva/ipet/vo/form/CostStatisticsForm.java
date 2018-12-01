package com.sensetime.iva.ipet.vo.form;

import java.util.List;

/**
 * @Author: ChaiLongLong
 * @date : 2018/8/8 16:09
 */
public class CostStatisticsForm {

    List<StageForm> stageForms;

    List<BusinessTripForm> businessTripForms;

    List<EquipmentForm> equipmentForms;

    public CostStatisticsForm() {
    }

    public CostStatisticsForm(List<StageForm> stageForms, List<BusinessTripForm> businessTripForms, List<EquipmentForm> equipmentForms) {
        this.stageForms = stageForms;
        this.businessTripForms = businessTripForms;
        this.equipmentForms = equipmentForms;
    }

    public List<StageForm> getStageForms() {
        return stageForms;
    }

    public void setStageForms(List<StageForm> stageForms) {
        this.stageForms = stageForms;
    }

    public List<BusinessTripForm> getBusinessTripForms() {
        return businessTripForms;
    }

    public void setBusinessTripForms(List<BusinessTripForm> businessTripForms) {
        this.businessTripForms = businessTripForms;
    }

    public List<EquipmentForm> getEquipmentForms() {
        return equipmentForms;
    }

    public void setEquipmentForms(List<EquipmentForm> equipmentForms) {
        this.equipmentForms = equipmentForms;
    }

    @Override
    public String toString() {
        return "CostStatisticsForm{" +
                "stageForms=" + stageForms +
                ", businessTripForms=" + businessTripForms +
                ", equipmentForms=" + equipmentForms +
                '}';
    }
}
