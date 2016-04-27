package com.jims.wx.vo;

import java.io.Serializable;

/**
 * Created by zhu on 2016/4/22.
 */
public class OutpRcptMasterVo implements Serializable{
    //收据编号
    private String rcptNo;
    //总费用
    private Double totalCosts;
    //应收金额
    private Double totalChanges;
    //患者姓名
    private String Name;

    public OutpRcptMasterVo(){
    }

    public OutpRcptMasterVo(String rcptNo, Double totalCosts, Double totalChanges, String name) {
        this.rcptNo = rcptNo;
        this.totalCosts = totalCosts;
        this.totalChanges = totalChanges;
        Name = name;
    }

    public String getRcptNo() {
        return rcptNo;
    }

    public void setRcptNo(String rcptNo) {
        this.rcptNo = rcptNo;
    }

    public Double getTotalCosts() {
        return totalCosts;
    }

    public void setTotalCosts(Double totalCosts) {
        this.totalCosts = totalCosts;
    }

    public Double getTotalChanges() {
        return totalChanges;
    }

    public void setTotalChanges(Double totalChanges) {
        this.totalChanges = totalChanges;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
