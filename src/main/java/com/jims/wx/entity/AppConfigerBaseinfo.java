package com.jims.wx.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * AppConfigerBaseinfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "APP_CONFIGER_BASEINFO", schema = "WX", uniqueConstraints = @UniqueConstraint(columnNames = {
		"APP_NAME", "PARAMETER_NAME", "PARAMETER_NO" }))
public class AppConfigerBaseinfo implements java.io.Serializable {

	// Fields

	private String id;
	private String appName;
	private Integer parameterNo;
	private String parameterName;
	private String parainitValue;
	private String parameterScope;
	private String explanation;

	// Constructors

	/** default constructor */
	public AppConfigerBaseinfo() {
	}

	/** full constructor */
	public AppConfigerBaseinfo(String appName, Integer parameterNo,
			String parameterName, String parainitValue, String parameterScope,
			String explanation) {
		this.appName = appName;
		this.parameterNo = parameterNo;
		this.parameterName = parameterName;
		this.parainitValue = parainitValue;
		this.parameterScope = parameterScope;
		this.explanation = explanation;
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

	@Column(name = "APP_NAME", length = 16)
	public String getAppName() {
		return this.appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	@Column(name = "PARAMETER_NO", precision = 6, scale = 0)
	public Integer getParameterNo() {
		return this.parameterNo;
	}

	public void setParameterNo(Integer parameterNo) {
		this.parameterNo = parameterNo;
	}

	@Column(name = "PARAMETER_NAME", length = 100)
	public String getParameterName() {
		return this.parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	@Column(name = "PARAINIT_VALUE", length = 500)
	public String getParainitValue() {
		return this.parainitValue;
	}

	public void setParainitValue(String parainitValue) {
		this.parainitValue = parainitValue;
	}

	@Column(name = "PARAMETER_SCOPE", length = 500)
	public String getParameterScope() {
		return this.parameterScope;
	}

	public void setParameterScope(String parameterScope) {
		this.parameterScope = parameterScope;
	}

	@Column(name = "EXPLANATION", length = 500)
	public String getExplanation() {
		return this.explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

}