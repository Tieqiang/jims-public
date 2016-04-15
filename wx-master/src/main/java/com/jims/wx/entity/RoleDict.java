package com.jims.wx.entity;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * RoleDict entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ROLE_DICT", schema = "WX")
public class RoleDict implements java.io.Serializable {

	// Fields

	private String id;
	private String roleName;
    private String menuIds;
	private Set<RoleVsMenu> roleVsMenus = new HashSet<RoleVsMenu>(0);
	private Set<StaffVsRole> staffVsRoles = new HashSet<StaffVsRole>(0);

	// Constructors

	/** default constructor */
	public RoleDict() {
	}

	/** full constructor */
	public RoleDict(String roleName, Set<RoleVsMenu> roleVsMenus,
                    Set<StaffVsRole> staffVsRoles) {
		this.roleName = roleName;
		this.roleVsMenus = roleVsMenus;
		this.staffVsRoles = staffVsRoles;
    }

    public RoleDict(String roleName, String menuIds, Set<RoleVsMenu> roleVsMenus, Set<StaffVsRole> staffVsRoles) {
        this.roleName = roleName;
        this.menuIds = menuIds;
        this.roleVsMenus = roleVsMenus;
        this.staffVsRoles = staffVsRoles;
    }

    // Property accessors
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, length = 64)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "ROLE_NAME", length = 128)
	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

    @JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "roleDict")
	public Set<RoleVsMenu> getRoleVsMenus() {
		return this.roleVsMenus;
	}

	public void setRoleVsMenus(Set<RoleVsMenu> roleVsMenus) {
		this.roleVsMenus = roleVsMenus;
	}

    @JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "roleDict")
	public Set<StaffVsRole> getStaffVsRoles() {
		return this.staffVsRoles;
	}

	public void setStaffVsRoles(Set<StaffVsRole> staffVsRoles) {
		this.staffVsRoles = staffVsRoles;
	}
    @Transient
    public String getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(String menuIds) {
        this.menuIds = menuIds;
    }
}