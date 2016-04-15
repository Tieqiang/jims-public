package com.jims.wx.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * ResponseArticle entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "RESPONSE_ARTICLE", schema = "WX")
public class ResponseArticle implements java.io.Serializable {

	// Fields

	private String id;
	private ResponseMessage responseMessage;
	private String title;
	private String description;
	private String picUrl;
	private String url;
	private String operator;
	private String createTime;

	// Constructors

	/** default constructor */
	public ResponseArticle() {
	}

	/** full constructor */
	public ResponseArticle(ResponseMessage responseMessage, String title,
			String description, String picUrl, String url, String operator,
			String createTime) {
		this.responseMessage = responseMessage;
		this.title = title;
		this.description = description;
		this.picUrl = picUrl;
		this.url = url;
		this.operator = operator;
		this.createTime = createTime;
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
	@JoinColumn(name = "RESPONSE_ID")
	public ResponseMessage getResponseMessage() {
		return this.responseMessage;
	}

	public void setResponseMessage(ResponseMessage responseMessage) {
		this.responseMessage = responseMessage;
	}

	@Column(name = "TITLE", length = 100)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "DESCRIPTION", length = 400)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "PIC_URL", length = 1024)
	public String getPicUrl() {
		return this.picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	@Column(name = "URL", length = 1024)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "OPERATOR", length = 64)
	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	@Column(name = "CREATE_TIME", length = 0)
	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}