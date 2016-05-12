package com.jims.wx.entity;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashSet;
import java.util.Set;

/**
 * HospitalDict entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "HOSPITAL_DICT", schema = "WX")
@XmlRootElement
public class HospitalDict implements java.io.Serializable {

    // Fields

    private String id;
    private String hospitalName;
    private String parentHospital;
    private String unitCode;
    private String location;
    private String zipCode;
    private String organizationFullCode;
//	private Set<DeptDict> deptDicts = new HashSet<DeptDict>(0);

    // Constructors

    /**
     * default constructor
     */
    public HospitalDict() {
    }

    /**
     * full constructor
     */
    public HospitalDict(String hospitalName,
                        String parentHospital, String unitCode, String location, String zipCode,
                        String organizationFullCode
                        /*Set<DeptDict> deptDicts*/) {
        this.hospitalName = hospitalName;
        this.parentHospital = parentHospital;
        this.unitCode = unitCode;
        this.location = location;
        this.zipCode = zipCode;
        this.organizationFullCode = organizationFullCode;
//		this.deptDicts = deptDicts;
    }

    // Property accessors
    @GenericGenerator(name = "generator", strategy = "uuid.hex")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "id", unique = true, nullable = false, length = 64)
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "parent_hospital")
    public String getParentHospital() {
        return parentHospital;
    }

    public void setParentHospital(String parentHospital) {
        this.parentHospital = parentHospital;
    }

    @Column(name = "hospital_name", length = 100)
    public String getHospitalName() {
        return this.hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    @Column(name = "unit_code", length = 11)
    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    @Column(name = "location", length = 100)
    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Column(name = "zip_code", length = 6)
    public String getZipCode() {
        return this.zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Column(name = "ORGANIZATION_FULL_CODE", length = 30)
    public String getOrganizationFullCode() {
        return this.organizationFullCode;
    }

    public void setOrganizationFullCode(String organizationFullCode) {
        this.organizationFullCode = organizationFullCode;
    }
}


//    //@JsonManagedReference
//    @JsonIgnore
//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "hospitalDict")
//	public Set<DeptDict> getDeptDicts() {
//		return this.deptDicts;
//	}
//
//	public void setDeptDicts(Set<DeptDict> deptDicts) {
//		this.deptDicts = deptDicts;
//	}
//
//}