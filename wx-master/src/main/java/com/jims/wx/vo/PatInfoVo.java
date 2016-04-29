package com.jims.wx.vo;

/**
 * Created by wei on 2016/4/28.
 */
public class PatInfoVo {
    private String id;
    private String name;
    private String sex;
    private String cellphone;
    private String idCard;
    private String patId;


    public PatInfoVo() {
    }

    public PatInfoVo(String id, String name, String sex, String cellphone, String idCard,String patId) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.cellphone = cellphone;
        this.idCard = idCard;
        this.patId = patId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPatId() {
        return patId;
    }

    public void setPatId(String patId) {
        this.patId = patId;
    }
}
