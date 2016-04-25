package com.jims.wx.vo;

import java.io.Serializable;

/**
 * Created by admin on 2016/3/30.
 */
public class ClinicForRegistVO implements Serializable {

    private String id;
    /**
     * 号类名称
     */
    private String clinicSettingType;
    /**
     * 号别名称
     */
    private String clinicLabel;
    /**
     * 所属科室
     */
    private String clinicDept;
    /**
     * 医生姓名
     */
    private String doctName;
    /**
     * 当日已经挂号人数
     */
    private Integer currentRegistCount;
    /**
     * 诊疗项目名称
     */
    private String treatItemName;
    /**
     * 收费项目名称
     */
    private String chargeItemName;
    /**
     * 挂号时间
     */
    private String registTime;
    /**
     * 当前号
     */
    private Integer currentRegistNo;
    /**
     * 描述
     */
    private String description;
    /**
     * 限号数
     */
    private Integer registrationLimits;
    /**
     * 限约号数
     */
    private Integer appointmentLimits;

    public ClinicForRegistVO() {

    }

    public Integer getCurrentRegistNo() {
        return currentRegistNo;
    }

    public void setCurrentRegistNo(Integer currentRegistNo) {
        this.currentRegistNo = currentRegistNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClinicSettingType() {
        return clinicSettingType;
    }

    public void setClinicSettingType(String clinicSettingType) {
        this.clinicSettingType = clinicSettingType;
    }

    public String getClinicLabel() {
        return clinicLabel;
    }

    public void setClinicLabel(String clinicLabel) {
        this.clinicLabel = clinicLabel;
    }

    public String getClinicDept() {
        return clinicDept;
    }

    public void setClinicDept(String clinicDept) {
        this.clinicDept = clinicDept;
    }

    public Integer getCurrentRegistCount() {
        return currentRegistCount;
    }

    public void setCurrentRegistCount(Integer currentRegistCount) {
        this.currentRegistCount = currentRegistCount;
    }

    public String getDoctName() {
        return doctName;
    }

    public void setDoctName(String doctName) {
        this.doctName = doctName;
    }

    public String getTreatItemName() {
        return treatItemName;
    }

    public void setTreatItemName(String treatItemName) {
        this.treatItemName = treatItemName;
    }

    public String getChargeItemName() {
        return chargeItemName;
    }

    public void setChargeItemName(String chargeItemName) {
        this.chargeItemName = chargeItemName;
    }

    public String getRegistTime() {
        return registTime;
    }

    public void setRegistTime(String registTime) {
        this.registTime = registTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getRegistrationLimits() {
        return registrationLimits;
    }

    public void setRegistrationLimits(Integer registrationLimits) {
        this.registrationLimits = registrationLimits;
    }

    public Integer getAppointmentLimits() {
        return appointmentLimits;
    }

    public void setAppointmentLimits(Integer appointmentLimits) {
        this.appointmentLimits = appointmentLimits;
    }

    public String getAllowRegistTime() {
        return allowRegistTime;
    }

    public void setAllowRegistTime(String allowRegistTime) {
        this.allowRegistTime = allowRegistTime;
    }

    /**
     * 允许挂号时间范围
     */
    private String allowRegistTime;


}
