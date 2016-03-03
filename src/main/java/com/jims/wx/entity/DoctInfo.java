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
import org.hibernate.annotations.GenericGenerator;

/**
 * DoctInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "DOCT_INFO", schema = "WX")
public class DoctInfo implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private String title;
	private String hospitalId;
	private String headUrl;
	private String description;
	private Set<ClinicIndex> clinicIndexes = new HashSet<ClinicIndex>(0);
	// Constructors
	/** default constructor */
	public DoctInfo() {
	}

	/** minimal constructor */
	public DoctInfo(String name) {
		this.name = name;
	}

	/** full constructor */
	public DoctInfo(String name, String title, String hospitalId,
			String headUrl, String description, Set<ClinicIndex> clinicIndexes) {
		this.name = name;
		this.title = title;
		this.hospitalId = hospitalId;
		this.headUrl = headUrl;
		this.description = description;
		this.clinicIndexes = clinicIndexes;
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

	@Column(name = "NAME", nullable = false, length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "TITLE", length = 10)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "HOSPITAL_ID", length = 64)
	public String getHospitalId() {
		return this.hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	@Column(name = "HEAD_URL", length = 1024)
	public String getHeadUrl() {
		return this.headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}

	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "doctInfo")
	public Set<ClinicIndex> getClinicIndexes() {
		return this.clinicIndexes;
	}

	public void setClinicIndexes(Set<ClinicIndex> clinicIndexes) {
		this.clinicIndexes = clinicIndexes;
	}

}