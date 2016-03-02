package com.jims.wx.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * AccessTooken entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ACCESS_TOOKEN", schema = "WX")
public class AccessTooken implements java.io.Serializable {

	// Fields

	private String id;
	private String accessTooken;
	private String appId;
	private String startTime;

	// Constructors

	/** default constructor */
	public AccessTooken() {
	}

	/** full constructor */
	public AccessTooken(String accessTooken, String appId, String startTime) {
		this.accessTooken = accessTooken;
		this.appId = appId;
		this.startTime = startTime;
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

	@Column(name = "ACCESS_TOOKEN", length = 80)
	public String getAccessTooken() {
		return this.accessTooken;
	}

	public void setAccessTooken(String accessTooken) {
		this.accessTooken = accessTooken;
	}

	@Column(name = "APP_ID", length = 100)
	public String getAppId() {
		return this.appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	@Column(name = "START_TIME", length = 0)
	public String getStartTime() {
		return this.startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

}