package com.jims.wx.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


/**
 * StaffVsModule entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "STAFF_VS_MODULE", schema = "JIMS")
public class StaffVsModule implements java.io.Serializable {

	// Fields

	private String id;
    private ModulDict modulDict;
	private StaffDict staffDict;

	// Constructors

	/** default constructor */
	public StaffVsModule() {
	}

	/** full constructor */
	public StaffVsModule(ModulDict modulDict, StaffDict staffDict) {
		this.modulDict = modulDict;
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
    @JoinColumn(name = "MODULE_ID")
    public ModulDict getModulDict() {
        return modulDict;
    }

    public void setModulDict(ModulDict modulDict) {
        this.modulDict = modulDict;
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