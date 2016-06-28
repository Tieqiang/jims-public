package com.jims.wx.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * AccessTooken entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "source_video", schema = "WX")
public class SourceVideo implements java.io.Serializable {

	// Fields

	private String id;

    private String title;

    private String groupId;

    private String label;

    private  byte[] description;

    private String mediaId;
    private String videoLocalUrl;
    private String videoWxUrl;
    private String videoSize;
    private String delFlag="0";
    @Transient
    private String video;



	/** default constructor */
	public SourceVideo() {
	}


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

    @Column(name="title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name="group_id")
    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
    @Column(name="label")
     public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
    @Column(name="description")
    public byte[] getDescription() {
        return description;
    }

    public void setDescription(byte[] description) {
        this.description = description;
    }
    @Column(name="media_id")
     public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }
    @Column(name="video_local_url")
     public String getVideoLocalUrl() {
        return videoLocalUrl;
    }

    public void setVideoLocalUrl(String videoLocalUrl) {
        this.videoLocalUrl = videoLocalUrl;
    }
    @Column(name="video_wx_url")
     public String getVideoWxUrl() {
        return videoWxUrl;
    }

    public void setVideoWxUrl(String videoWxUrl) {
        this.videoWxUrl = videoWxUrl;
    }

    @Column(name="video_size")
    public String getVideoSize() {
        return videoSize;
    }

    public void setVideoSize(String videoSize) {
        this.videoSize = videoSize;
    }
    @Column(name="del_flag")
    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
    @Transient
    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}


