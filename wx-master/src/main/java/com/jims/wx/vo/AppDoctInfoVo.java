package com.jims.wx.vo;

/**
 * Created by cxy on 2016/4/20.
 */
public class AppDoctInfoVo {


    private String rid;

    private String name;

    private String title;

    private String headUrl;

    private String description;

    private String timeDesc;

    private Integer currentNum;

    private Integer enabledNum;
    private Double price;
    private String patName ;

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    private String deptName;//科室名称

    private Integer doctCount;

    public Integer getDoctCount() {
        return this.doctCount;
    }

    public void setDoctCount(Integer doctCount) {
        this.doctCount = doctCount;
    }

    public AppDoctInfoVo() {

    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    //    public String getId() {
//        return this.id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
    public String getRid() {
        return this.rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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

    public String getPatName() {
        return patName;
    }

    public void setPatName(String patName) {
        this.patName = patName;
    }

    //    private
//    private

}
