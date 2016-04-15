package com.jims.wx.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


/**
 * ClinicIndex entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "CLINIC_INDEX", schema = "WX", uniqueConstraints = @UniqueConstraint(columnNames = "DOCTOR_ID"))
public class ClinicIndex implements java.io.Serializable {
	// Fields
	private String id;
	private String clinicLabel;
	private String clinicDept;
    private String clinicTypeId;
    private String doctorId;

	/** default constructor */
	public ClinicIndex() {
	}

    /** full constructor */
    public ClinicIndex(String id, String clinicTypeId, String doctorId, String clinicLabel, String clinicDept) {
        this.id = id;
        this.clinicTypeId = clinicTypeId;
        this.doctorId = doctorId;
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

    @Column(name = "CLINIC_TYPE_ID",length = 64)
    public String getClinicTypeId() {
        return clinicTypeId;
    }

    public void setClinicTypeId(String clinicTypeId) {
        this.clinicTypeId = clinicTypeId;
    }

    @Column(name = "DOCTOR_ID", unique = true,length = 64)
    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

}