package com.jims.wx.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.GenericGenerator;

/**
 * WxOpenAccountConfig entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "WX_OPEN_ACCOUNT_CONFIG", schema = "WX", uniqueConstraints = @UniqueConstraint(columnNames = "APP_ID"))
public class WxOpenAccountConfig implements java.io.Serializable {

	// Fields

	private String id;
	private String openName;
	private String appId;
	private String appSecret;
	private String hospitalId;
	private String jsRout;
	private String url;
	private String tooken;
    private String metchId ;
    private String key ;

	// Constructors

	/** default constructor */
	public WxOpenAccountConfig() {
	}

	/** full constructor */
	public WxOpenAccountConfig(String openName, String appId, String appSecret,
                               String hospitalId, String jsRout, String url, String tooken, String metchId, String key) {
		this.openName = openName;
		this.appId = appId;
		this.appSecret = appSecret;
		this.hospitalId = hospitalId;
		this.jsRout = jsRout;
		this.url = url;
		this.tooken = tooken;
        this.metchId = metchId;
        this.key = key;
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

	@Column(name = "OPEN_NAME", length = 100)
	public String getOpenName() {
		return this.openName;
	}

	public void setOpenName(String openName) {
		this.openName = openName;
	}

	@Column(name = "APP_ID", unique = true, length = 64)
	public String getAppId() {
		return this.appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	@Column(name = "APP_SECRET", length = 64)
	public String getAppSecret() {
		return this.appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	@Column(name = "HOSPITAL_ID", length = 64)
	public String getHospitalId() {
		return this.hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	@Column(name = "JS_ROUT", length = 100)
	public String getJsRout() {
		return this.jsRout;
	}

	public void setJsRout(String jsRout) {
		this.jsRout = jsRout;
	}

	@Column(name = "URL", length = 100)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "TOOKEN", length = 64)
	public String getTooken() {
		return this.tooken;
	}

	public void setTooken(String tooken) {
		this.tooken = tooken;
	}

    @Column(name="metch_id")
    public String getMetchId() {
        return metchId;
    }

    public void setMetchId(String metchId) {
        this.metchId = metchId;
    }

    @Column(name="key")
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


}