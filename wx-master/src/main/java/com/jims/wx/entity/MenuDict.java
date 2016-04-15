package com.jims.wx.entity;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * MenuDict entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "MENU_DICT", schema = "WX")
public class MenuDict implements java.io.Serializable {

    // Fields
    private String id;
    private String parentId;
    private String parentIds;
    private String menuName;
    private String href;
    private String icon;
    private String showFlag;
    private Integer position;
    private Set<ModuleVsMenu> moduleVsMenus = new HashSet<ModuleVsMenu>(0);
    private Set<RoleVsMenu> roleVsMenus = new HashSet<RoleVsMenu>(0);

    // Constructors

    /** default constructor */
    public MenuDict() {
    }

    /** full constructor */
    public MenuDict(String parentId, String parentIds, String menuName,
                    String href, String icon, String showFlag, Integer position,
                    Set<ModuleVsMenu> moduleVsMenus, Set<RoleVsMenu> roleVsMenus) {
        this.parentId = parentId;
        this.parentIds = parentIds;
        this.menuName = menuName;
        this.href = href;
        this.icon = icon;
        this.showFlag = showFlag;
        this.position = position;
        this.moduleVsMenus = moduleVsMenus;
        this.roleVsMenus = roleVsMenus;
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

    @Column(name = "PARENT_ID", length = 64)
    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Column(name = "PARENT_IDS", length = 300)
    public String getParentIds() {
        return this.parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    @Column(name = "MENU_NAME", length = 50)
    public String getMenuName() {
        return this.menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    @Column(name = "HREF", length = 128)
    public String getHref() {
        return this.href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    @Column(name = "ICON", length = 16)
    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Column(name = "SHOW_FLAG", length = 1)
    public String getShowFlag() {
        return this.showFlag;
    }

    public void setShowFlag(String showFlag) {
        this.showFlag = showFlag;
    }

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "menuDict")
    public Set<ModuleVsMenu> getModuleVsMenus() {
        return this.moduleVsMenus;
    }

    public void setModuleVsMenus(Set<ModuleVsMenu> moduleVsMenus) {
        this.moduleVsMenus = moduleVsMenus;
    }

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "menuDict")
    public Set<RoleVsMenu> getRoleVsMenus() {
        return this.roleVsMenus;
    }

    public void setRoleVsMenus(Set<RoleVsMenu> roleVsMenus) {
        this.roleVsMenus = roleVsMenus;
    }

    @Column(name = "POSITION", precision = 4, scale = 0)
    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}