package com.jims.wx.entity;


import javax.persistence.*;
import java.util.Date;

/**
 * ClinicMaster entity. @author chenxy
 */
@Entity
@Table(name = "CLINIC_MASTER", schema = "OUTPADM")
public class ClinicMasterHis implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    private Date visitDate;
    private Integer visitNo;
    private String clinicLabel;
    private String visitTimeDesc;
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
    private Integer mrProvidedIndicator;// MR_PROVIDED_INDICATOR
    // Fields




    @Column(name = "MR_PROVIDED_INDICATOR")
    public Integer getMrProvidedIndicator() {
        return mrProvidedIndicator;
    }

    public void setMrProvidedIndicator(Integer mrProvidedIndicator) {
        this.mrProvidedIndicator = mrProvidedIndicator;
    }
    @Column(name = "DATE_OF_BIRTH")
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Column(name = "visit_date")
    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    @Column(name = "visit_no")
    public Integer getVisitNo() {
        return visitNo;
    }

    public void setVisitNo(Integer visitNo) {
        this.visitNo = visitNo;
    }

    @Column(name = "clinic_label")
    public String getClinicLabel() {
        return clinicLabel;
    }

    public void setClinicLabel(String clinicLabel) {
        this.clinicLabel = clinicLabel;
    }

    @Column(name = "visit_time_desc")
    public String getVisitTimeDesc() {
        return visitTimeDesc;
    }

    public void setVisitTimeDesc(String visitTimeDesc) {
        this.visitTimeDesc = visitTimeDesc;
    }

    @Column(name = "serial_no")
    public Integer getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(Integer serialNo) {
        this.serialNo = serialNo;
    }

    @Column(name = "patient_id")
    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "name_phonetic")
    public String getNamePhonetic() {
        return namePhonetic;
    }

    public void setNamePhonetic(String namePhonetic) {
        this.namePhonetic = namePhonetic;
    }

    @Column(name = "sex")
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Column(name = "age")
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Column(name = "identity")
    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    @Column(name = "charge_type")
    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    @Column(name = "clinic_type")
    public String getClinicType() {
        return clinicType;
    }

    public void setClinicType(String clinicType) {
        this.clinicType = clinicType;
    }

    @Column(name = "visit_dept")
    public String getVisitDept() {
        return visitDept;
    }

    public void setVisitDept(String visitDept) {
        this.visitDept = visitDept;
    }

    @Column(name = "doctor")
    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    @Column(name = "registration_status")
    public Integer getRegistrationStatus() {
        return registrationStatus;
    }

    public void setRegistrationStatus(Integer registrationStatus) {
        this.registrationStatus = registrationStatus;
    }

    @Column(name = "registering_date")
    public Date getRegisteringDate() {
        return registeringDate;
    }

    public void setRegisteringDate(Date registeringDate) {
        this.registeringDate = registeringDate;
    }

    @Column(name = "regist_fee")
    public Double getRegistFee() {
        return registFee;
    }

    public void setRegistFee(Double registFee) {
        this.registFee = registFee;
    }

    @Column(name = "clinic_fee")
    public Double getClinicFee() {
        return clinicFee;
    }

    public void setClinicFee(Double clinicFee) {
        this.clinicFee = clinicFee;
    }

    @Column(name = "other_fee")
    public Double getOtherFee() {
        return otherFee;
    }

    public void setOtherFee(Double otherFee) {
        this.otherFee = otherFee;
    }

    @Column(name = "clinic_charge")
    public Double getClinicCharge() {
        return clinicCharge;
    }

    public void setClinicCharge(Double clinicCharge) {
        this.clinicCharge = clinicCharge;
    }

    @Column(name = "operator")
    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    @Column(name = "modeCode")
    public String getModeCode() {
        return modeCode;
    }

    public void setModeCode(String modeCode) {
        this.modeCode = modeCode;
    }

    @Column(name = "card_name")
    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    @Column(name = "card_no")
    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    @Column(name = "pay_way")
    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    @Column(name = "clinic_no")
    public String getClinicNo() {
        return clinicNo;
    }

    public void setClinicNo(String clinicNo) {
        this.clinicNo = clinicNo;
    }
}