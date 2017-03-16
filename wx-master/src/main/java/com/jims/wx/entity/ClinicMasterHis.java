package com.jims.wx.entity;


import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ClinicMaster entity. @author chenxy
 */
//@Entity
//@Table(name = "CLINIC_MASTER", schema = "OUTPADM")
public class ClinicMasterHis implements java.io.Serializable {


    private Date visitDate1;
     private Integer visitNo;
     private String clinicLabel;
     private String visitTimeDesc1;
     private Integer serialNo;
     private String patientId;
     private String name;
     private String namePhonetic;
     private String sex;
     private Integer age;
     private String identity;
     private String chargeType;

     private String clinicType;
     private String visitDept;
     private String doctor;
     private Integer registrationStatus;
     private Date registeringDate;
     private Double registFee;
     private Double clinicFee;
     private Double otherFee;
     private Double clinicCharge;
     private String operator;
     private String modeCode;
     private String cardName;

     private String cardNo;
     private String payWay;
     private String clinicNo;
     private Date dateOfBirth;//DATE_OF_BIRTH
     private String id;

    private Integer mrProvidedIndicator;// MR_PROVIDED_INDICATOR
    // Fields

    private SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
    private SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd");





    public Integer getMrProvidedIndicator() {
        return mrProvidedIndicator;
    }

    public void setMrProvidedIndicator(Integer mrProvidedIndicator) {
        this.mrProvidedIndicator = mrProvidedIndicator;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }


    public Date getVisitDate1() {
        return visitDate1;
    }

    public void setVisitDate1(Date visitDate1) {
        this.visitDate1 = visitDate1;
    }


    public Integer getVisitNo() {
        return visitNo;
    }

    public void setVisitNo(Integer visitNo) {
        this.visitNo = visitNo;
    }


    public String getClinicLabel() {
        return clinicLabel;
    }

    public void setClinicLabel(String clinicLabel) {
        this.clinicLabel = clinicLabel;
    }


    public String getVisitTimeDesc1() {
        return visitTimeDesc1;
    }

    public void setVisitTimeDesc1(String visitTimeDesc1) {
        this.visitTimeDesc1 = visitTimeDesc1;
    }


    public Integer getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(Integer serialNo) {
        this.serialNo = serialNo;
    }


    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getNamePhonetic() {
        return namePhonetic;
    }

    public void setNamePhonetic(String namePhonetic) {
        this.namePhonetic = namePhonetic;
    }


    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

     public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }


    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }


    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    public String getClinicType() {
        return clinicType;
    }

    public void setClinicType(String clinicType) {
        this.clinicType = clinicType;
    }


    public String getVisitDept() {
        return visitDept;
    }

    public void setVisitDept(String visitDept) {
        this.visitDept = visitDept;
    }


    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }


    public Integer getRegistrationStatus() {
        return registrationStatus;
    }

    public void setRegistrationStatus(Integer registrationStatus) {
        this.registrationStatus = registrationStatus;
    }


    public Date getRegisteringDate() {
        return registeringDate;
    }

    public void setRegisteringDate(Date registeringDate) {
        this.registeringDate = registeringDate;
    }


    public Double getRegistFee() {
        return registFee;
    }

    public void setRegistFee(Double registFee) {
        this.registFee = registFee;
    }


    public Double getClinicFee() {
        return clinicFee;
    }

    public void setClinicFee(Double clinicFee) {
        this.clinicFee = clinicFee;
    }


    public Double getOtherFee() {
        return otherFee;
    }

    public void setOtherFee(Double otherFee) {
        this.otherFee = otherFee;
    }


    public Double getClinicCharge() {
        return clinicCharge;
    }

    public void setClinicCharge(Double clinicCharge) {
        this.clinicCharge = clinicCharge;
    }


    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }


    public String getModeCode() {
        return modeCode;
    }

    public void setModeCode(String modeCode) {
        this.modeCode = modeCode;
    }


    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }


    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

     public String getClinicNo() {
        return clinicNo;
    }

    public void setClinicNo(String clinicNo) {
        this.clinicNo = clinicNo;
    }


    public String getVisitDateTrans(){
         return sdf.format(this.visitDate1);
    }
    public String getVisitDateTrans1(){
        return sdf2.format(this.visitDate1);
    }
    public String getRegistDateTrans(){
        return sdf.format(new Date());
    }
    public String getRegistDateTrans1(){
        return sdf2.format(new Date());
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}