package com.jims.wx.vo;

/**
 * Created by Dt on 2016-03-04.
 */
public class hosInfoDictVo {
    private String id;
    private String hospitalName;
    private String parentHospital ;
    private String unitCode;
    private String location;
    private String zipCode;

    private String organizationFullCode;
    private String hospitalId;
    private String appId;
    private String infoUrl;
    private String content;

    public hosInfoDictVo() {
    }

    public hosInfoDictVo(String id, String hospitalName, String parentHospital, String unitCode, String location, String zipCode, String organizationFullCode, String hospitalId, String appId, String infoUrl, String content) {
        this.id = id;
        this.hospitalName = hospitalName;
        this.parentHospital = parentHospital;
        this.unitCode = unitCode;
        this.location = location;
        this.zipCode = zipCode;
        this.organizationFullCode = organizationFullCode;
        this.hospitalId = hospitalId;
        this.appId = appId;
        this.infoUrl = infoUrl;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getParentHospital() {
        return parentHospital;
    }

    public void setParentHospital(String parentHospital) {
        this.parentHospital = parentHospital;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getOrganizationFullCode() {
        return organizationFullCode;
    }

    public void setOrganizationFullCode(String organizationFullCode) {
        this.organizationFullCode = organizationFullCode;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getInfoUrl() {
        return infoUrl;
    }

    public void setInfoUrl(String infoUrl) {
        this.infoUrl = infoUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
