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
 * ModuleVsMenu entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "MODULE_VS_MENU", schema = "JIMS")
public class ModuleVsMenu implements java.io.Serializable {

	// Fields

	private String id;
	private MenuDict menuDict;
	private ModulDict modulDict;

	// Constructors

	/** default constructor */
	public ModuleVsMenu() {
	}

	/** full constructor */
	public ModuleVsMenu(MenuDict menuDict, ModulDict modulDict) {
		this.menuDict = menuDict;
		this.modulDict = modulDict;
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
	@JoinColumn(name = "MENU_ID")
	public MenuDict getMenuDict() {
		return this.menuDict;
	}

	public void setMenuDict(MenuDict menuDict) {
		this.menuDict = menuDict;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "MODULE_ID")
	public ModulDict getModulDict() {
		return this.modulDict;
	}

	public void setModulDict(ModulDict modulDict) {
		this.modulDict = modulDict;
	}

}