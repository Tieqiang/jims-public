package com.jims.wx.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

/**
 * OpenAccountMenu entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "OPEN_ACCOUNT_MENU", schema = "WX")
public class OpenAccountMenu implements java.io.Serializable {

	// Fields

	private String id;
	private String parentId;
	private String type;
	private String name;
	private String key;
	private String url;
	private String mediaId;
	private String hospitalId;
	private String useStatus;
	private Date createTime;
	private String operator;
	private Date lastUpdateTime;
	private String lastUpdatOperator;
	private String appId;

	// Constructors
	/** default constructor */
	public OpenAccountMenu() {
	}

	/** minimal constructor */
	public OpenAccountMenu(String appId) {
		this.appId = appId;
	}

	/** full constructor */
	public OpenAccountMenu(String parentId, String type, String name,
			String key, String url, String mediaId, String hospitalId,
			String useStatus, Date createTime, String operator,
			Date lastUpdateTime, String lastUpdatOperator, String appId) {
		this.parentId = parentId;
		this.type = type;
		this.name = name;
		this.key = key;
		this.url = url;
		this.mediaId = mediaId;
		this.hospitalId = hospitalId;
		this.useStatus = useStatus;
		this.createTime = createTime;
		this.operator = operator;
		this.lastUpdateTime = lastUpdateTime;
		this.lastUpdatOperator = lastUpdatOperator;
		this.appId = appId;
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

	@Column(name = "TYPE", length = 50)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "NAME", length = 40)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "KEY", length = 128)
	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Column(name = "URL", length = 1024)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "MEDIA_ID", length = 100)
	public String getMediaId() {
		return this.mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	@Column(name = "HOSPITAL_ID", length = 64)
	public String getHospitalId() {
		return this.hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	@Column(name = "USE_STATUS", length = 2)
	public String getUseStatus() {
		return this.useStatus;
	}

	public void setUseStatus(String useStatus) {
		this.useStatus = useStatus;
	}

	@Temporal(TemporalType.DATE)
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

	@Temporal(TemporalType.DATE)
	@Column(name = "LAST_UPDATE_TIME", length = 7)
	public Date getLastUpdateTime() {
		return this.lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	@Column(name = "LAST_UPDAT_OPERATOR", length = 64)
	public String getLastUpdatOperator() {
		return this.lastUpdatOperator;
	}

	public void setLastUpdatOperator(String lastUpdatOperator) {
		this.lastUpdatOperator = lastUpdatOperator;
	}

	@Column(name = "APP_ID", nullable = false, length = 64)
	public String getAppId() {
		return this.appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

}