package com.jims.wx.vo;

/**
 * Created by admin on 2016/5/11.
 */
public class RegistInfoVO {

    private String timeDesc;

    private Integer currentNum;

    private Integer enabledNum;
    private Double price;

    private String rid;

    public String getTimeDesc() {
        return timeDesc;
    }

    public void setTimeDesc(String timeDesc) {
        this.timeDesc = timeDesc;
    }

    public Integer getCurrentNum() {
        return currentNum;
    }

    public void setCurrentNum(Integer currentNum) {
        this.currentNum = currentNum;
    }

    public Integer getEnabledNum() {
        return enabledNum;
    }

    public void setEnabledNum(Integer enabledNum) {
        this.enabledNum = enabledNum;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public RegistInfoVO() {
    }

    public RegistInfoVO(String timeDesc, Integer currentNum, Integer enabledNum, String rid, Double price) {
        this.timeDesc = timeDesc;
        this.currentNum = currentNum;
        this.enabledNum = enabledNum;
        this.rid = rid;
        this.price = price;
    }
}
