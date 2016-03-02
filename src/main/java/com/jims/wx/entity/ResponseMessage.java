package com.jims.wx.entity;

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
import org.hibernate.annotations.GenericGenerator;

/**
 * ResponseMessage entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "RESPONSE_MESSAGE", schema = "WX")
public class ResponseMessage implements java.io.Serializable {

	// Fields

	private String id;
	private String toUserName;
	private String fromUserName;
	private String createTime;
	private String msgType;
	private String content;
	private String mediaId;
	private String title;
	private String description;
	private String url;
	private String thumbMediaId;
	private String appId;
	private String responseStatus;
	private String musicUrl;
	private String hqMusicUrl;
	private Set<ResponseArticle> responseArticles = new HashSet<ResponseArticle>(
			0);

	// Constructors

	/** default constructor */
	public ResponseMessage() {
	}

	/** full constructor */
	public ResponseMessage(String toUserName, String fromUserName,
			String createTime, String msgType, String content, String mediaId,
			String title, String description, String url, String thumbMediaId,
			String appId, String responseStatus, String musicUrl,
			String hqMusicUrl, Set<ResponseArticle> responseArticles) {
		this.toUserName = toUserName;
		this.fromUserName = fromUserName;
		this.createTime = createTime;
		this.msgType = msgType;
		this.content = content;
		this.mediaId = mediaId;
		this.title = title;
		this.description = description;
		this.url = url;
		this.thumbMediaId = thumbMediaId;
		this.appId = appId;
		this.responseStatus = responseStatus;
		this.musicUrl = musicUrl;
		this.hqMusicUrl = hqMusicUrl;
		this.responseArticles = responseArticles;
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

	@Column(name = "TO_USER_NAME", length = 64)
	public String getToUserName() {
		return this.toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	@Column(name = "FROM_USER_NAME", length = 64)
	public String getFromUserName() {
		return this.fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	@Column(name = "CREATE_TIME", length = 0)
	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@Column(name = "MSG_TYPE", length = 64)
	public String getMsgType() {
		return this.msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	@Column(name = "CONTENT", length = 1200)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "MEDIA_ID", length = 64)
	public String getMediaId() {
		return this.mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	@Column(name = "TITLE", length = 100)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "DESCRIPTION", length = 200)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "URL", length = 1024)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "THUMB_MEDIA_ID", length = 64)
	public String getThumbMediaId() {
		return this.thumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}

	@Column(name = "APP_ID", length = 64)
	public String getAppId() {
		return this.appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	@Column(name = "RESPONSE_STATUS", length = 2)
	public String getResponseStatus() {
		return this.responseStatus;
	}

	public void setResponseStatus(String responseStatus) {
		this.responseStatus = responseStatus;
	}

	@Column(name = "MUSIC_URL", length = 1024)
	public String getMusicUrl() {
		return this.musicUrl;
	}

	public void setMusicUrl(String musicUrl) {
		this.musicUrl = musicUrl;
	}

	@Column(name = "HQ_MUSIC_URL", length = 1024)
	public String getHqMusicUrl() {
		return this.hqMusicUrl;
	}

	public void setHqMusicUrl(String hqMusicUrl) {
		this.hqMusicUrl = hqMusicUrl;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "responseMessage")
	public Set<ResponseArticle> getResponseArticles() {
		return this.responseArticles;
	}

	public void setResponseArticles(Set<ResponseArticle> responseArticles) {
		this.responseArticles = responseArticles;
	}

}