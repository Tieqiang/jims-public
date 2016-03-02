package com.jims.wx.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * PatVsUser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "PAT_VS_USER", schema = "WX", uniqueConstraints = @UniqueConstraint(columnNames = {
		"PAT_ID", "USER_ID" }))
public class PatVsUser implements java.io.Serializable {

	// Fields

	private PatVsUserId id;
	private PatInfo patInfo;
	private AppUser appUser;

	// Constructors

	/** default constructor */
	public PatVsUser() {
	}

	/** minimal constructor */
	public PatVsUser(PatVsUserId id) {
		this.id = id;
	}

	/** full constructor */
	public PatVsUser(PatVsUserId id, PatInfo patInfo, AppUser appUser) {
		this.id = id;
		this.patInfo = patInfo;
		this.appUser = appUser;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "patId", column = @Column(name = "PAT_ID", length = 64)),
			@AttributeOverride(name = "userId", column = @Column(name = "USER_ID", length = 64)) })
	public PatVsUserId getId() {
		return this.id;
	}

	public void setId(PatVsUserId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PAT_ID", insertable = false, updatable = false)
	public PatInfo getPatInfo() {
		return this.patInfo;
	}

	public void setPatInfo(PatInfo patInfo) {
		this.patInfo = patInfo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", insertable = false, updatable = false)
	public AppUser getAppUser() {
		return this.appUser;
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}

}