package com.jims.wx.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

import com.google.inject.persist.Transactional;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

/**
 * ClinicForRegist  MyEclipse Persistence Tools
 */
@Entity
@Table(name = "CLINIC_FOR_REGIST", schema = "WX")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ClinicForRegist implements java.io.Serializable {
    // Fields
    private String id;
    private ClinicIndex clinicIndex;
    private Date clinicDate;
    private String timeDesc;
    private Integer registrationLimits;
    private Integer appointmentLimits;
    private Integer currentNo;
    private Integer registrationNum;
    private Double registPrice;
    private String registTime;
    // Constructors

    /**
     * default constructor
     */
    public ClinicForRegist() {
    }

    /**
     * full constructor
     */
    public ClinicForRegist(ClinicIndex clinicIndex, Date clinicDate,
                           String timeDesc, Integer registrationLimits,
                           Integer appointmentLimits, Integer currentNo,
                           Integer registrationNum, Double registPrice) {
        this.clinicIndex = clinicIndex;
        this.clinicDate = clinicDate;
        this.timeDesc = timeDesc;
        this.registrationLimits = registrationLimits;
        this.appointmentLimits = appointmentLimits;
        this.currentNo = currentNo;
        this.registrationNum = registrationNum;
        this.registPrice = registPrice;
    }

    // Property accessors
    @GenericGenerator(name = "generator", strategy = "uuid.hex")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "ID", unique = true, nullable = false, length = 64)
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLINIC_INDEX_ID")
    public ClinicIndex getClinicIndex() {
        return this.clinicIndex;
    }

    public void setClinicIndex(ClinicIndex clinicIndex) {
        this.clinicIndex = clinicIndex;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "CLINIC_DATE", length = 7)
    public Date getClinicDate() {
        return this.clinicDate;
    }

    public void setClinicDate(Date clinicDate) {
        this.clinicDate = clinicDate;
    }

    @Column(name = "TIME_DESC", length = 10)
    public String getTimeDesc() {
        return this.timeDesc;
    }

    public void setTimeDesc(String timeDesc) {
        this.timeDesc = timeDesc;
    }

    @Column(name = "REGISTRATION_LIMITS", precision = 22, scale = 0)
    public Integer getRegistrationLimits() {
        return this.registrationLimits;
    }

    public void setRegistrationLimits(Integer registrationLimits) {
        this.registrationLimits = registrationLimits;
    }

    @Column(name = "APPOINTMENT_LIMITS", precision = 22, scale = 0)
    public Integer getAppointmentLimits() {
        return this.appointmentLimits;
    }

    public void setAppointmentLimits(Integer appointmentLimits) {
        this.appointmentLimits = appointmentLimits;
    }

    @Column(name = "CURRENT_NO", precision = 22, scale = 0)
    public Integer getCurrentNo() {
        return this.currentNo;
    }

    public void setCurrentNo(Integer currentNo) {
        this.currentNo = currentNo;
    }

    @Column(name = "REGISTRATION_NUM", precision = 22, scale = 0)
    public Integer getRegistrationNum() {
        return this.registrationNum;
    }

    public void setRegistrationNum(Integer registrationNum) {
        this.registrationNum = registrationNum;
    }

    @Column(name = "REGIST_PRICE", precision = 5)
    public Double getRegistPrice() {
        return this.registPrice;
    }

    public void setRegistPrice(Double registPrice) {
        this.registPrice = registPrice;
    }

    @Column(name = "regist_time")
    public String getRegistTime() {
        return registTime;
    }

    public void setRegistTime(String registTime) {
        this.registTime = registTime;
    }

    @Transient
    public String getClinicTypeId() {
        return this.clinicIndex.getId();
    }

    @Transient
    public String getClinicType() {
        if (this.clinicIndex != null && !"".equals(this.clinicIndex)) {
            return this.clinicIndex.getClinicLabel();
        } else {
            return "";
        }
    }

}