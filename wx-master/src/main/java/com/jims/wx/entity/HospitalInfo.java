package com.jims.wx.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.UnsupportedEncodingException;

/**
 * update string->byte[] for column content
 * HospitalInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "HOSPITAL_INFO", schema = "WX")
public class HospitalInfo implements java.io.Serializable {

	// Fields

	private String id;
	private String hospitalName;
	private String appId;
	private String infoUrl;
	private byte[] content;

    @Transient
    private String tranContent;

	// Constructors

	/** default constructor */
	public HospitalInfo() {
	}

	/** full constructor */
	public HospitalInfo(String hospitalName, String appId, String infoUrl,
			byte[] content) {
		this.hospitalName = hospitalName;
		this.appId = appId;
		this.infoUrl = infoUrl;
		this.content = content;
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

	@Column(name = "HOSPITAL_ID", length = 64)
	public String getHospitalName() {
		return this.hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	@Column(name = "APP_ID", length = 64)
	public String getAppId() {
		return this.appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	@Column(name = "INFO_URL", length = 1024)
	public String getInfoUrl() {
		return this.infoUrl;
	}

	public void setInfoUrl(String infoUrl) {
		this.infoUrl = infoUrl;
	}

	@Column(name = "CONTENT")
	public byte[] getContent() {
		return this.content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

    @Transient
    public String getTranContent() {
        if (this.content != null && !"".equals(this.content)) {
            String s = "";
            try {
                s = new String(this.content, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return s;
        }
        return null;
    }

    public void setTranContent(String tranContent) {
        this.tranContent = tranContent;
    }
}