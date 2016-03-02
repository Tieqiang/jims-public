package com.jims.wx.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.GenericGenerator;

/**
 * AppUser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "APP_USER", schema = "WX", uniqueConstraints = @UniqueConstraint(columnNames = "OPEN_ID"))
public class AppUser implements java.io.Serializable {

	// Fields

	private String id;
	private AppUserGroups appUserGroups;
	private BigDecimal subscribe;
	private String openId;
	private String nickName;
	private BigDecimal sex;
	private String city;
	private String country;
	private String province;
	private String language;
	private String headImgUrl;
	private String subscrbeTime;
	private String remark;
	private String appId;
	private Set<PatVsUser> patVsUsers = new HashSet<PatVsUser>(0);

	// Constructors

	/** default constructor */
	public AppUser() {
	}

	/** full constructor */
	public AppUser(AppUserGroups appUserGroups, BigDecimal subscribe,
			String openId, String nickName, BigDecimal sex, String city,
			String country, String province, String language,
			String headImgUrl, String subscrbeTime, String remark,
			String appId, Set<PatVsUser> patVsUsers) {
		this.appUserGroups = appUserGroups;
		this.subscribe = subscribe;
		this.openId = openId;
		this.nickName = nickName;
		this.sex = sex;
		this.city = city;
		this.country = country;
		this.province = province;
		this.language = language;
		this.headImgUrl = headImgUrl;
		this.subscrbeTime = subscrbeTime;
		this.remark = remark;
		this.appId = appId;
		this.patVsUsers = patVsUsers;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GROUP_ID")
	public AppUserGroups getAppUserGroups() {
		return this.appUserGroups;
	}

	public void setAppUserGroups(AppUserGroups appUserGroups) {
		this.appUserGroups = appUserGroups;
	}

	@Column(name = "SUBSCRIBE", precision = 22, scale = 0)
	public BigDecimal getSubscribe() {
		return this.subscribe;
	}

	public void setSubscribe(BigDecimal subscribe) {
		this.subscribe = subscribe;
	}

	@Column(name = "OPEN_ID", unique = true, length = 64)
	public String getOpenId() {
		return this.openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	@Column(name = "NICK_NAME", length = 64)
	public String getNickName() {
		return this.nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	@Column(name = "SEX", precision = 22, scale = 0)
	public BigDecimal getSex() {
		return this.sex;
	}

	public void setSex(BigDecimal sex) {
		this.sex = sex;
	}

	@Column(name = "CITY", length = 30)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "COUNTRY", length = 30)
	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Column(name = "PROVINCE", length = 30)
	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	@Column(name = "LANGUAGE", length = 20)
	public String getLanguage() {
		return this.language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@Column(name = "HEAD_IMG_URL", length = 1024)
	public String getHeadImgUrl() {
		return this.headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}

	@Column(name = "SUBSCRBE_TIME", length = 0)
	public String getSubscrbeTime() {
		return this.subscrbeTime;
	}

	public void setSubscrbeTime(String subscrbeTime) {
		this.subscrbeTime = subscrbeTime;
	}

	@Column(name = "REMARK", length = 100)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "APP_ID", length = 64)
	public String getAppId() {
		return this.appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "appUser")
	public Set<PatVsUser> getPatVsUsers() {
		return this.patVsUsers;
	}

	public void setPatVsUsers(Set<PatVsUser> patVsUsers) {
		this.patVsUsers = patVsUsers;
	}

}