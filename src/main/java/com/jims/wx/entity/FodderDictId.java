package com.jims.wx.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * FodderDictId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class FodderDictId implements java.io.Serializable {

	// Fields

	private String id;
	private String mediaId;
	private String type;
	private String createdAt;
	private String storeStatus;

	// Constructors

	/** default constructor */
	public FodderDictId() {
	}

	/** full constructor */
	public FodderDictId(String id, String mediaId, String type,
			String createdAt, String storeStatus) {
		this.id = id;
		this.mediaId = mediaId;
		this.type = type;
		this.createdAt = createdAt;
		this.storeStatus = storeStatus;
	}

	// Property accessors

	@Column(name = "ID", length = 64)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "MEDIA_ID", length = 64)
	public String getMediaId() {
		return this.mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	@Column(name = "TYPE", length = 20)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "CREATED_AT", length = 0)
	public String getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	@Column(name = "STORE_STATUS", length = 2)
	public String getStoreStatus() {
		return this.storeStatus;
	}

	public void setStoreStatus(String storeStatus) {
		this.storeStatus = storeStatus;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof FodderDictId))
			return false;
		FodderDictId castOther = (FodderDictId) other;

		return ((this.getId() == castOther.getId()) || (this.getId() != null
				&& castOther.getId() != null && this.getId().equals(
				castOther.getId())))
				&& ((this.getMediaId() == castOther.getMediaId()) || (this
						.getMediaId() != null && castOther.getMediaId() != null && this
						.getMediaId().equals(castOther.getMediaId())))
				&& ((this.getType() == castOther.getType()) || (this.getType() != null
						&& castOther.getType() != null && this.getType()
						.equals(castOther.getType())))
				&& ((this.getCreatedAt() == castOther.getCreatedAt()) || (this
						.getCreatedAt() != null
						&& castOther.getCreatedAt() != null && this
						.getCreatedAt().equals(castOther.getCreatedAt())))
				&& ((this.getStoreStatus() == castOther.getStoreStatus()) || (this
						.getStoreStatus() != null
						&& castOther.getStoreStatus() != null && this
						.getStoreStatus().equals(castOther.getStoreStatus())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getId() == null ? 0 : this.getId().hashCode());
		result = 37 * result
				+ (getMediaId() == null ? 0 : this.getMediaId().hashCode());
		result = 37 * result
				+ (getType() == null ? 0 : this.getType().hashCode());
		result = 37 * result
				+ (getCreatedAt() == null ? 0 : this.getCreatedAt().hashCode());
		result = 37
				* result
				+ (getStoreStatus() == null ? 0 : this.getStoreStatus()
						.hashCode());
		return result;
	}

}