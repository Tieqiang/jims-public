package com.jims.wx.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * PatVsUser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "PAT_VS_USER", schema = "WX", uniqueConstraints = @UniqueConstraint(columnNames = {
		"PAT_ID", "USER_ID" }))
public class PatVsUser implements java.io.Serializable {

	// Fields

	private String id;
	private PatInfo patInfo;
	private AppUser appUser;

	// Constructors

	/** default constructor */
	public PatVsUser() {
	}

	/** minimal constructor */
	public PatVsUser(String id) {
		this.id = id;
	}

	/** full constructor */
	public PatVsUser(String id, PatInfo patInfo, AppUser appUser) {
		this.id = id;
		this.patInfo = patInfo;
		this.appUser = appUser;
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
	@JoinColumn(name = "PAT_ID")
	public PatInfo getPatInfo() {
		return this.patInfo;
	}

	public void setPatInfo(PatInfo patInfo) {
		this.patInfo = patInfo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	public AppUser getAppUser() {
		return this.appUser;
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}

}