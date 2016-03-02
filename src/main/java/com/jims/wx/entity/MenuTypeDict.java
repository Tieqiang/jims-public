package com.jims.wx.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * MenuTypeDict entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "MENU_TYPE_DICT", schema = "WX")
public class MenuTypeDict implements java.io.Serializable {

	// Fields

	private String id;
	private String typeDesign;
	private String typeCode;

	// Constructors

	/** default constructor */
	public MenuTypeDict() {
	}

	/** full constructor */
	public MenuTypeDict(String typeDesign, String typeCode) {
		this.typeDesign = typeDesign;
		this.typeCode = typeCode;
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

	@Column(name = "TYPE_DESIGN", length = 100)
	public String getTypeDesign() {
		return this.typeDesign;
	}

	public void setTypeDesign(String typeDesign) {
		this.typeDesign = typeDesign;
	}

	@Column(name = "TYPE_CODE", length = 50)
	public String getTypeCode() {
		return this.typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

}