package com.jims.wx.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

/**
 * AppUserGroups entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "APP_USER_GROUPS", schema = "WX")
public class AppUserGroups implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private Integer count;
	private String groupStatus;
	private String appId;
	private Date createTime;
	private String operator;
	private Date updateTime;
	private String updateOperator;
    private String groupId;

	// Constructors

	/** default constructor */
	public AppUserGroups() {
	}

	/** full constructor */
	public AppUserGroups(String name, Integer count, String groupStatus,
			String appId, Date createTime, String operator, Date updateTime,
			String updateOperator,String groupId) {
		this.name = name;
		this.count = count;
		this.groupStatus = groupStatus;
		this.appId = appId;
		this.createTime = createTime;
		this.operator = operator;
		this.updateTime = updateTime;
		this.updateOperator = updateOperator;
        this.groupId = groupId;
		//this.appUsers = appUsers;
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

	@Column(name = "NAME", length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "COUNT", precision = 22, scale = 0)
	public Integer getCount() {
		return this.count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Column(name = "GROUP_STATUS", length = 2)
	public String getGroupStatus() {
		return this.groupStatus;
	}

	public void setGroupStatus(String groupStatus) {
		this.groupStatus = groupStatus;
	}

	@Column(name = "APP_ID", length = 64)
	public String getAppId() {
		return this.appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TIME", length = 7)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "OPERATOR", length = 64)
	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATE_TIME", length = 7)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "UPDATE_OPERATOR", length = 64)
	public String getUpdateOperator() {
		return this.updateOperator;
	}

	public void setUpdateOperator(String updateOperator) {
		this.updateOperator = updateOperator;
	}

    @Column(name = "GROUP_ID", length = 64)
    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

//@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "appUserGroups")
	//public Set<AppUser> getAppUsers() {
	//	return this.appUsers;
	//}
    //
	//public void setAppUsers(Set<AppUser> appUsers) {
	//	this.appUsers = appUsers;
	//}

}