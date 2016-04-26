package com.jims.wx.vo;

import java.io.Serializable;

/**
 * Created by wei on 2016/4/22.
 */
public class PatVisitVo implements Serializable {

    private String patientId;
    private Double visitId ;
    private String nextOfKin;//联系人姓名
    private String deptAdmissionTo;//入院科室
    private String admissionDateTime;//入院时间
    private String deptDischargeFrom;//出院科室
    private String dischargeDateTime;//出院时间



    public PatVisitVo() {

    }

    public PatVisitVo(String patientId, Double visitId, String deptAdmissionTo, String admissionDateTime, String deptDischargeFrom, String dischargeDateTime, String nextOfKin) {

        this.patientId = patientId;
        this.visitId = visitId;
        this.deptAdmissionTo = deptAdmissionTo;
        this.admissionDateTime = admissionDateTime;
        this.deptDischargeFrom = deptDischargeFrom;
        this.dischargeDateTime = dischargeDateTime;
        this.nextOfKin = nextOfKin;
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

    public String getDeptAdmissionTo() {
        return deptAdmissionTo;
    }

    public void setDeptAdmissionTo(String deptAdmissionTo) {
        this.deptAdmissionTo = deptAdmissionTo;
    }

    public String getAdmissionDateTime() {
        return admissionDateTime;
    }

    public void setAdmissionDateTime(String admissionDateTime) {
        this.admissionDateTime = admissionDateTime;
    }

    public String getDeptDischargeFrom() {
        return deptDischargeFrom;
    }

    public void setDeptDischargeFrom(String deptDischargeFrom) {
        this.deptDischargeFrom = deptDischargeFrom;
    }

    public String getDischargeDateTime() {
        return dischargeDateTime;
    }

    public void setDischargeDateTime(String dischargeDateTime) {
        this.dischargeDateTime = dischargeDateTime;
    }

    public String getNextOfKin() {
        return nextOfKin;
    }

    public void setNextOfKin(String nextOfKin) {
        this.nextOfKin = nextOfKin;
    }
}
