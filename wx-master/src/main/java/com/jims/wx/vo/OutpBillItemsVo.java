package com.jims.wx.vo;

/**
 * Created by zhu on 2016/4/22.
 */
public class OutpBillItemsVo {
    private String itemName;
    private Double amount;
    private String units;
    private Double costs;
    private Double changes;

    public OutpBillItemsVo(){
    }

    public OutpBillItemsVo(String itemName,Double amount, String units, Double costs, Double changes) {
        this.itemName = itemName;
        this.amount=amount;
        this.units = units;
        this.costs = costs;
        this.changes = changes;
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

    public Double getChanges() {
        return changes;
    }

    public void setChanges(Double changes) {
        this.changes = changes;
    }
}
