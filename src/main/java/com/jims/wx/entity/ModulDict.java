package com.jims.wx.entity;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * ModulDict entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "MODUL_DICT", schema = "WX")
public class ModulDict implements java.io.Serializable {

	// Fields

	private String id;
	private String moduleName;
	private String inputCode;
	private Set<ModuleVsMenu> moduleVsMenus = new HashSet<ModuleVsMenu>(0);
    private String menuIds ;
    private String hospitalId;
    private Set<StaffVsModule> staffVsModules = new HashSet<StaffVsModule>(0);
    private String staffIds;
    private String moduleLoad;

	// Constructors

	/** default constructor */
	public ModulDict() {
	}

	/** full constructor */
	public ModulDict(String moduleName, String inputCode,
                     Set<ModuleVsMenu> moduleVsMenus, String menuIds, String hospitalId, Set<StaffVsModule> staffVsModules, String staffIds, String moduleLoad) {
		this.moduleName = moduleName;
		this.inputCode = inputCode;
		this.moduleVsMenus = moduleVsMenus;
        this.menuIds = menuIds;
        this.hospitalId = hospitalId;
        this.staffVsModules = staffVsModules;
        this.staffIds = staffIds;
        this.moduleLoad = moduleLoad;
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

	@Column(name = "MODULE_NAME", length = 100)
	public String getModuleName() {
		return this.moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	@Column(name = "INPUT_CODE", length = 50)
	public String getInputCode() {
		return this.inputCode;
	}

	public void setInputCode(String inputCode) {
		this.inputCode = inputCode;
	}

    @Column(name = "HOSPITAL_ID", length = 64)
    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }
    @Column(name = "MODULE_LOAD", length = 100)
    public String getModuleLoad() {
        return moduleLoad;
    }

    public void setModuleLoad(String moduleLoad) {
        this.moduleLoad = moduleLoad;
    }

    @JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "modulDict")
	public Set<ModuleVsMenu> getModuleVsMenus() {
		return this.moduleVsMenus;
	}

	public void setModuleVsMenus(Set<ModuleVsMenu> moduleVsMenus) {
		this.moduleVsMenus = moduleVsMenus;
	}

    @Transient
    public String getMenuIds() {
        Set<ModuleVsMenu> moduleVsMenus1 = this.getModuleVsMenus();
        StringBuffer buffer = new StringBuffer() ;
        for(ModuleVsMenu moduleVsMenu :moduleVsMenus1){
            buffer.append(moduleVsMenu.getMenuDict().getId()) ;
            buffer.append(",") ;
        }
        this.menuIds= buffer.toString() ;
        return menuIds ;
    }

    public void setMenuIds(String menuIds) {
        this.menuIds  = menuIds ;
    }

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "modulDict")
    public Set<StaffVsModule> getStaffVsModules() {
        return staffVsModules;
    }

    public void setStaffVsModules(Set<StaffVsModule> staffVsModules) {
        this.staffVsModules = staffVsModules;
    }

    @Transient
    public String getStaffIds() {
        Set<StaffVsModule> moduleVsStaff = this.getStaffVsModules();
        StringBuffer buffer = new StringBuffer();
        for (StaffVsModule staffVsModule : moduleVsStaff) {
            buffer.append(staffVsModule.getStaffDict().getId());
            buffer.append(",");
        }
        this.staffIds = buffer.toString();
        return staffIds;
    }

    public void setStaffIds(String staffIds) {
        this.staffIds = staffIds;
    }
}