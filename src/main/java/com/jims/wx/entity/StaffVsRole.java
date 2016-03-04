package com.jims.wx.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * StaffVsRole entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "STAFF_VS_ROLE", schema = "JIMS")
public class StaffVsRole implements java.io.Serializable {

	// Fields

	private String id;
	private RoleDict roleDict;
	private StaffDict staffDict;

	// Constructors

	/** default constructor */
	public StaffVsRole() {
	}

	/** full constructor */
	public StaffVsRole(RoleDict roleDict, StaffDict staffDict) {
		this.roleDict = roleDict;
		this.staffDict = staffDict;
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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ROLE_id")
	public RoleDict getRoleDict() {
		return this.roleDict;
	}

	public void setRoleDict(RoleDict roleDict) {
		this.roleDict = roleDict;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "STAFF_ID")
	public StaffDict getStaffDict() {
		return this.staffDict;
	}

	public void setStaffDict(StaffDict staffDict) {
		this.staffDict = staffDict;
	}

}