package com.jims.wx.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * FodderDict entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FODDER_DICT", schema = "WX")
public class FodderDict implements java.io.Serializable {

	// Fields

	private FodderDictId id;

	// Constructors

	/** default constructor */
	public FodderDict() {
	}

	/** full constructor */
	public FodderDict(FodderDictId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "id", column = @Column(name = "ID", length = 64)),
			@AttributeOverride(name = "mediaId", column = @Column(name = "MEDIA_ID", length = 64)),
			@AttributeOverride(name = "type", column = @Column(name = "TYPE", length = 20)),
			@AttributeOverride(name = "createdAt", column = @Column(name = "CREATED_AT", length = 0)),
			@AttributeOverride(name = "storeStatus", column = @Column(name = "STORE_STATUS", length = 2)) })
	public FodderDictId getId() {
		return this.id;
	}

	public void setId(FodderDictId id) {
		this.id = id;
	}

}