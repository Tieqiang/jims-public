package com.jims.wx.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * Article entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ARTICLE", schema = "WX")
public class Article implements java.io.Serializable {

	// Fields

	private String id;
	private String title;
	private String thumbMediaId;
	private String author;
	private String digest;
	private String showCoverPic;
	private String content;
	private String contentSourceUrl;
	private String appId;

	// Constructors

	/** default constructor */
	public Article() {
	}

	/** full constructor */
	public Article(String title, String thumbMediaId, String author,
			String digest, String showCoverPic, String content,
			String contentSourceUrl, String appId) {
		this.title = title;
		this.thumbMediaId = thumbMediaId;
		this.author = author;
		this.digest = digest;
		this.showCoverPic = showCoverPic;
		this.content = content;
		this.contentSourceUrl = contentSourceUrl;
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

	@Column(name = "TITLE", length = 50)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "THUMB_MEDIA_ID", length = 64)
	public String getThumbMediaId() {
		return this.thumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}

	@Column(name = "AUTHOR", length = 20)
	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Column(name = "DIGEST", length = 500)
	public String getDigest() {
		return this.digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	@Column(name = "SHOW_COVER_PIC", length = 2)
	public String getShowCoverPic() {
		return this.showCoverPic;
	}

	public void setShowCoverPic(String showCoverPic) {
		this.showCoverPic = showCoverPic;
	}

	@Column(name = "CONTENT")
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "CONTENT_SOURCE_URL", length = 1024)
	public String getContentSourceUrl() {
		return this.contentSourceUrl;
	}

	public void setContentSourceUrl(String contentSourceUrl) {
		this.contentSourceUrl = contentSourceUrl;
	}

	@Column(name = "APP_ID", length = 64)
	public String getAppId() {
		return this.appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

}