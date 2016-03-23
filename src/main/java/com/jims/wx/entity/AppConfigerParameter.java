package com.jims.wx.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * AppConfigerParameter entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "APP_CONFIGER_PARAMETER", schema = "WX", uniqueConstraints = @UniqueConstraint(columnNames = {
		"APP_NAME", "DEPT_CODE", "EMP_NO", "PARAMETER_NAME", "HOSPITAL_ID" }))
public class AppConfigerParameter implements java.io.Serializable {

	// Fields

	private String id;
	private String appName;
	private String deptCode;
	private String empNo;
	private String parameterName;
	private String parameterValue;
	private String position;
	private String hospitalId;

	// Constructors

	/** default constructor */
	public AppConfigerParameter() {
	}

	/** full constructor */
	public AppConfigerParameter(String appName, String deptCode, String empNo,
			String parameterName, String parameterValue, String position,
			String hospitalId) {
		this.appName = appName;
		this.deptCode = deptCode;
		this.empNo = empNo;
		this.parameterName = parameterName;
		this.parameterValue = parameterValue;
		this.position = position;
		this.hospitalId = hospitalId;
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

	@Column(name = "APP_NAME", length = 10)
	public String getAppName() {
		return this.appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	@Column(name = "DEPT_CODE", length = 8)
	public String getDeptCode() {
		return this.deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	@Column(name = "EMP_NO", length = 10)
	public String getEmpNo() {
		return this.empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	@Column(name = "PARAMETER_NAME", length = 100)
	public String getParameterName() {
		return this.parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	@Column(name = "PARAMETER_VALUE", length = 500)
	public String getParameterValue() {
		return this.parameterValue;
	}

	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}

	@Column(name = "POSITION", length = 20)
	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	@Column(name = "HOSPITAL_ID", length = 64)
	public String getHospitalId() {
		return this.hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

}