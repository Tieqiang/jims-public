package com.jims.wx.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.GenericGenerator;

/**
 * ClinicIndex entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "CLINIC_INDEX", schema = "WX", uniqueConstraints = @UniqueConstraint(columnNames = "DOCTOR_ID"))
public class ClinicIndex implements java.io.Serializable {

	// Fields

	private String id;
	private ClinicTypeSetting clinicTypeSetting;
	private DoctInfo doctInfo;
	private String clinicLabel;
	private String clinicDept;
	//private Set<ClinicSchedule> clinicSchedules = new HashSet<ClinicSchedule>(0);
	//private Set<ClinicForRegist> clinicForRegists = new HashSet<ClinicForRegist>(
	//		0);

	// Constructors

	/** default constructor */
	public ClinicIndex() {
	}


    /** full constructor */
    public ClinicIndex(String id, ClinicTypeSetting clinicTypeSetting, DoctInfo doctInfo, String clinicLabel, String clinicDept) {
        this.id = id;
        this.clinicTypeSetting = clinicTypeSetting;
        this.doctInfo = doctInfo;
        this.clinicLabel = clinicLabel;
        this.clinicDept = clinicDept;
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
	@JoinColumn(name = "CLINIC_TYPE_ID")
	public ClinicTypeSetting getClinicTypeSetting() {
		return this.clinicTypeSetting;
	}

	public void setClinicTypeSetting(ClinicTypeSetting clinicTypeSetting) {
		this.clinicTypeSetting = clinicTypeSetting;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DOCTOR_ID", unique = true)
	public DoctInfo getDoctInfo() {
		return this.doctInfo;
	}

	public void setDoctInfo(DoctInfo doctInfo) {
		this.doctInfo = doctInfo;
	}

	@Column(name = "CLINIC_LABEL", length = 20)
	public String getClinicLabel() {
		return this.clinicLabel;
	}

	public void setClinicLabel(String clinicLabel) {
		this.clinicLabel = clinicLabel;
	}

	@Column(name = "CLINIC_DEPT", length = 10)
	public String getClinicDept() {
		return this.clinicDept;
	}

	public void setClinicDept(String clinicDept) {
		this.clinicDept = clinicDept;
	}

	//@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "clinicIndex")
	//public Set<ClinicSchedule> getClinicSchedules() {
	//	return this.clinicSchedules;
	//}
    //
	//public void setClinicSchedules(Set<ClinicSchedule> clinicSchedules) {
	//	this.clinicSchedules = clinicSchedules;
	//}
    //
	//@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "clinicIndex")
	//public Set<ClinicForRegist> getClinicForRegists() {
	//	return this.clinicForRegists;
	//}
    //
	//public void setClinicForRegists(Set<ClinicForRegist> clinicForRegists) {
	//	this.clinicForRegists = clinicForRegists;
	//}

}