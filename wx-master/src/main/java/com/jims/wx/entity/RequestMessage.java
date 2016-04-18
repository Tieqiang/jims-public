package com.jims.wx.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

/**
 * RequestMessage entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "REQUEST_MESSAGE", schema = "WX")
public class RequestMessage implements java.io.Serializable {

	// Fields

	private String id;
	private String toUserName;
	private String fromUserName;
	private Integer createTime;
	private String msgType;
	private String msgId;
	private String content;
	private String picUrl;
	private String mediaId;
	private String format;
	private String locationX;
	private String locationY;
	private String scale;
	private String label;
	private String title;
	private String description;
	private String url;
	private String thumbMediaId;
	private String appId;
	private String responseStatus;
	private String eventKey;
	private String event;
	private String ticket;
	private String latitude;
	private String longitude;
	private String precision;

	// Constructors

	/** default constructor */
	public RequestMessage() {
	}

	/** full constructor */
	public RequestMessage(String toUserName, String fromUserName,
                          Integer createTime, String msgType, String msgId, String content,
			String picUrl, String mediaId, String format, String locationX,
			String locationY, String scale, String label, String title,
			String description, String url, String thumbMediaId, String appId,
			String responseStatus, String eventKey, String event,
			String ticket, String latitude, String longitude, String precision) {
		this.toUserName = toUserName;
		this.fromUserName = fromUserName;
		this.createTime = createTime;
		this.msgType = msgType;
		this.msgId = msgId;
		this.content = content;
		this.picUrl = picUrl;
		this.mediaId = mediaId;
		this.format = format;
		this.locationX = locationX;
		this.locationY = locationY;
		this.scale = scale;
		this.label = label;
		this.title = title;
		this.description = description;
		this.url = url;
		this.thumbMediaId = thumbMediaId;
		this.appId = appId;
		this.responseStatus = responseStatus;
		this.eventKey = eventKey;
		this.event = event;
		this.ticket = ticket;
		this.latitude = latitude;
		this.longitude = longitude;
		this.precision = precision;
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
	public Integer getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}

	@Column(name = "MSG_TYPE", length = 64)
	public String getMsgType() {
		return this.msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	@Column(name = "MSG_ID", length = 64)
	public String getMsgId() {
		return this.msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	@Column(name = "CONTENT", length = 1200)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "PIC_URL", length = 1024)
	public String getPicUrl() {
		return this.picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	@Column(name = "MEDIA_ID", length = 64)
	public String getMediaId() {
		return this.mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	@Column(name = "FORMAT", length = 10)
	public String getFormat() {
		return this.format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	@Column(name = "LOCATION_X", length = 20)
	public String getLocationX() {
		return this.locationX;
	}

	public void setLocationX(String locationX) {
		this.locationX = locationX;
	}

	@Column(name = "LOCATION_Y", length = 20)
	public String getLocationY() {
		return this.locationY;
	}

	public void setLocationY(String locationY) {
		this.locationY = locationY;
	}

	@Column(name = "SCALE", length = 10)
	public String getScale() {
		return this.scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

	@Column(name = "LABEL", length = 200)
	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
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

	@Column(name = "EVENT_KEY", length = 100)
	public String getEventKey() {
		return this.eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}

	@Column(name = "EVENT", length = 10)
	public String getEvent() {
		return this.event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	@Column(name = "TICKET", length = 100)
	public String getTicket() {
		return this.ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	@Column(name = "LATITUDE", length = 20)
	public String getLatitude() {
		return this.latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	@Column(name = "LONGITUDE", length = 20)
	public String getLongitude() {
		return this.longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	@Column(name = "PRECISION", length = 20)
	public String getPrecision() {
		return this.precision;
	}

	public void setPrecision(String precision) {
		this.precision = precision;
	}

}