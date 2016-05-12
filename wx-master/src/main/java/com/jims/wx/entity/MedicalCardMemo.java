package com.jims.wx.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by cxy on 2016/5/9.
 */
@Entity
@Table(name = "medical_card_memo", schema = "WX")
public class MedicalCardMemo implements Serializable {

    private String cardNo;

    private String patientId;

    private String cardType;

    private Integer accountStatus;

    private String accountPwd;



    public MedicalCardMemo(){

    }



    @Id
//    @GeneratedValue(generator = "generator")
    @Column(name = "card_no", unique = true, nullable = false, length = 18)
    public String getCardNo() {
        return this.cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
    @Column(name="patient_id")
    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
    @Column(name="card_type")
    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }
    @Column(name="account_status")
    public Integer getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(Integer accountStatus) {
        this.accountStatus = accountStatus;
    }
    @Column(name="account_pwd")
    public String getAccountPwd() {
        return accountPwd;
    }

    public void setAccountPwd(String accountPwd) {
        this.accountPwd = accountPwd;
    }

    public MedicalCardMemo(String cardNo, String patientId, String cardType, Integer accountStatus, String accountPwd) {
        this.cardNo = cardNo;
        this.patientId = patientId;
        this.cardType = cardType;
        this.accountStatus = accountStatus;
        this.accountPwd = accountPwd;
    }
}
