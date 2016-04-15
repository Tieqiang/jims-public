package com.jims.wx.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * ClinicSchedule entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "CLINIC_SCHEDULE", schema = "WX")
public class ClinicSchedule implements java.io.Serializable {

	// Fields

	private String id;
	private String clinicIndexId;
	private String dayOfWeek;
    private String timeOfDay;
	private Double registrationLimits;

	// Constructors

	/** default constructor */
	public ClinicSchedule() {
	}

	/** full constructor */
	public ClinicSchedule(String clinicIndexId, String dayOfWeek,String timeOfDay,
			Double registrationLimits) {
		this.clinicIndexId = clinicIndexId;
		this.dayOfWeek = dayOfWeek;
        this.timeOfDay = timeOfDay;
		this.registrationLimits = registrationLimits;
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

	@Column(name = "CLINIC_INDEX_ID")
	public String  getClinicIndexId() {
		return this.clinicIndexId;
	}

	public void setClinicIndexId(String clinicIndexId) {
		this.clinicIndexId = clinicIndexId;
	}

	@Column(name = "DAY_OF_WEEK", length = 10)
	public String getDayOfWeek() {
		return this.dayOfWeek;
	}

	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	@Column(name = "REGISTRATION_LIMITS", precision = 22, scale = 0)
	public Double getRegistrationLimits() {
		return this.registrationLimits;
	}

	public void setRegistrationLimits(Double registrationLimits) {
		this.registrationLimits = registrationLimits;
	}

    @Column(name = "TIME_OF_DAY", length = 10)
    public String getTimeOfDay() {
        return timeOfDay;
    }

    public void setTimeOfDay(String timeOfDay) {
        this.timeOfDay = timeOfDay;
    }
}