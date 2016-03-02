package com.jims.wx.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * PatVsUserId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class PatVsUserId implements java.io.Serializable {

	// Fields

	private String patId;
	private String userId;

	// Constructors

	/** default constructor */
	public PatVsUserId() {
	}

	/** full constructor */
	public PatVsUserId(String patId, String userId) {
		this.patId = patId;
		this.userId = userId;
	}

	// Property accessors

	@Column(name = "PAT_ID", length = 64)
	public String getPatId() {
		return this.patId;
	}

	public void setPatId(String patId) {
		this.patId = patId;
	}

	@Column(name = "USER_ID", length = 64)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PatVsUserId))
			return false;
		PatVsUserId castOther = (PatVsUserId) other;

		return ((this.getPatId() == castOther.getPatId()) || (this.getPatId() != null
				&& castOther.getPatId() != null && this.getPatId().equals(
				castOther.getPatId())))
				&& ((this.getUserId() == castOther.getUserId()) || (this
						.getUserId() != null && castOther.getUserId() != null && this
						.getUserId().equals(castOther.getUserId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getPatId() == null ? 0 : this.getPatId().hashCode());
		result = 37 * result
				+ (getUserId() == null ? 0 : this.getUserId().hashCode());
		return result;
	}

}