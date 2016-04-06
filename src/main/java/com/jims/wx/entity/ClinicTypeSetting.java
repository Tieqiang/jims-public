package com.jims.wx.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

/**
 * ClinicTypeSetting entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "CLINIC_TYPE_SETTING", schema = "WX", uniqueConstraints = @UniqueConstraint(columnNames = "CLINIC_TYPE"))
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ClinicTypeSetting implements java.io.Serializable {
    // Fields
    private String id;
    private String clinicType;
    private String hospitalId;
    private String appId;

    /**
     * default constructor
     */
    public ClinicTypeSetting() {
    }

    /**
     * full constructor
     */
    public ClinicTypeSetting(String clinicType, String hospitalId,
                             String appId) {
        this.clinicType = clinicType;
        this.hospitalId = hospitalId;
        this.appId = appId;
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

    @Column(name = "CLINIC_TYPE", unique = true, length = 8)
    public String getClinicType() {
        return this.clinicType;
    }

    public void setClinicType(String clinicType) {
        this.clinicType = clinicType;
    }

    @Column(name = "HOSPITAL_ID", length = 64)
    public String getHospitalId() {
        return this.hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    @Column(name = "APP_ID", length = 64)
    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}