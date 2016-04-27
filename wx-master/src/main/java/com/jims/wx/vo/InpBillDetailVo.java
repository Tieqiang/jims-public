package com.jims.wx.vo;

import java.io.Serializable;

/**
 * Created by wei on 2016/4/22.
 */
public class InpBillDetailVo implements Serializable {

    private String patientId ;//病人标识
    private Double visitId ;//病人本次住院标识
    private String itemName;//项目名称
    private  Double amount;//数量
    private String units;//单位
    private Double costs;//费用
    private Double charges;//应收费用

    public InpBillDetailVo() {
    }

    public InpBillDetailVo(String patientId, Double visitId, String itemName, Double amount, String units, Double costs, Double charges) {
        this.patientId = patientId;
        this.visitId = visitId;
        this.itemName = itemName;
        this.amount = amount;
        this.units = units;
        this.costs = costs;
        this.charges = charges;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public Double getVisitId() {
        return visitId;
    }

    public void setVisitId(Double visitId) {
        this.visitId = visitId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public Double getCosts() {
        return costs;
    }

    public void setCosts(Double costs) {
        this.costs = costs;
    }

    public Double getCharges() {
        return charges;
    }

    public void setCharges(Double charges) {
        this.charges = charges;
    }
}
