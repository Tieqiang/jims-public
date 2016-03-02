package com.jims.wx.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * HospitalInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "HOSPITAL_INFO", schema = "WX")
public class HospitalInfo implements java.io.Serializable {

	// Fields

	private String id;
	private String hospitalId;
	private String appId;
	private String infoUrl;
	private String content;

	// Constructors

	/** default constructor */
	public HospitalInfo() {
	}

	/** full constructor */
	public HospitalInfo(String hospitalId, String appId, String infoUrl,
			String content) {
		this.hospitalId = hospitalId;
		this.appId = appId;
		this.infoUrl = infoUrl;
		this.content = content;
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

	@Column(name = "INFO_URL", length = 1024)
	public String getInfoUrl() {
		return this.infoUrl;
	}

	public void setInfoUrl(String infoUrl) {
		this.infoUrl = infoUrl;
	}

	@Column(name = "CONTENT")
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}