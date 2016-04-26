package com.jims.wx.vo;

import java.io.Serializable;

/**
 * Created by zhu on 2016/4/22.
 */
public class ClinicMasterVo implements Serializable{
    //就诊日期
    private String visitDate;
    //病人姓名
    private String name;
    //病人表示号
    private String patientId;
    //挂号科目
    private String clinicLabel;

    public ClinicMasterVo(){
    }

    public ClinicMasterVo(String visitDate, String name, String patientId,String clinicLabel) {
        this.visitDate = visitDate;
        this.name = name;
        this.patientId = patientId;
        this.clinicLabel = clinicLabel;

    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getClinicLabel() {
        return clinicLabel;
    }

    public void setClinicLabel(String clinicLabel) {
        this.clinicLabel = clinicLabel;
    }
}
