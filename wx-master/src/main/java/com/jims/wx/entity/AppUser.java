package com.jims.wx.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
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
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

/**
 * AppUser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "APP_USER", schema = "WX", uniqueConstraints = @UniqueConstraint(columnNames = "OPEN_ID"))
public class AppUser implements java.io.Serializable {

	// Fields

	private String id;
	private Integer subscribe;
	private String openId;
	private String nickName;
	private Integer sex;
	private String city;
	private String country;
	private String province;
	private String language;
	private String headImgUrl;
	private Integer subscrbeTime;
	private String remark;
    private Integer groupId ;
    private String patId;
    private Set<PatVsUser> patVsUsers = new HashSet<PatVsUser>(0);

	// Constructors

    /** default constructor */
    public AppUser() {
    }

    /**
     * full constructor
     */
    public AppUser(String id,String patId, Integer subscribe, String openId, String nickName, Integer sex, String city, String country, String province, String language, String headImgUrl, Integer subscrbeTime, String remark, Integer groupId, Set<PatVsUser> patVsUsers) {
        this.id = id;
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
        this.groupId = groupId;
        this.patId=patId;
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




    @Column(name = "SUBSCRIBE", precision = 22, scale = 0)
    public Integer getSubscribe() {
        return this.subscribe;
    }

    public void setSubscribe(Integer subscribe) {
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
    public Integer getSex() {
        return this.sex;
    }

    public void setSex(Integer sex) {
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
	public Integer getSubscrbeTime() {
		return this.subscrbeTime;
	}

	public void setSubscrbeTime(Integer subscrbeTime) {
		this.subscrbeTime = subscrbeTime;
	}

	@Column(name = "REMARK", length = 100)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}



    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "appUser")
    public Set<PatVsUser> getPatVsUsers() {
        return this.patVsUsers;
    }

    public void setPatVsUsers(Set<PatVsUser> patVsUsers) {
        this.patVsUsers = patVsUsers;
    }

    @Column(name = "PAT_ID", length = 64)
    public String getPatId() {
        return patId;
    }

    public void setPatId(String patId) {
        this.patId = patId;
    }

    @Column(name="group_id")
    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }
}