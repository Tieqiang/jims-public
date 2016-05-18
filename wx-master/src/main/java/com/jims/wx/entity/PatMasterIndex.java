package com.jims.wx.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by cxy on 2016/5/9.
 */
@Entity
@Table(name = "pat_master_index", schema = "wx")
public class PatMasterIndex implements Serializable {

    private String patientId;//主键

    private String InpNo;//住院号

    private String sex;

    private Date dateOfBirth;

    private String nation;

    private String idNo;//省份证号

    private String chargeType;

    private Date createDate;


    private String mailingAddress;
    @Column(name="inp_no")
    public String getInpNo() {
        return InpNo;
    }

    public void setInpNo(String inpNo) {
        InpNo = inpNo;
    }
    @Column(name="sex")
     public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
    @Column(name="date_of_birth")
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    @Column(name="nation")
     public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }
    @Column(name="id_no")
    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }
    @Column(name="charge_type")
    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }
    @Column(name="create_date")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
     @Column(name="mailing_address")
     public String getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(String mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    @Id
//    @GeneratedValue(generator = "generator")
    @Column(name = "patient_id", unique = true, nullable = false, length = 10)
    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
    public PatMasterIndex(){

    }
    public PatMasterIndex(String patientId, String inpNo, String sex, Date dateOfBirth, String nation, String idNo, String chargeType, Date createDate, String mailingAddress) {
        this.patientId = patientId;
        InpNo = inpNo;
        this.sex = sex;
        this.dateOfBirth = dateOfBirth;
        this.nation = nation;
        this.idNo = idNo;
        this.chargeType = chargeType;
        this.createDate = createDate;
        this.mailingAddress = mailingAddress;
    }
}
