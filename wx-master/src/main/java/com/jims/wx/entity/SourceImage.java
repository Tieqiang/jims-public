package com.jims.wx.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.InputStream;

/**
 * AccessTooken entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "source_image", schema = "WX")
public class SourceImage implements java.io.Serializable {

	// Fields

	private String id;
	private String imageName;
	private String imageLocalUrl;
	private String imageSize;
    private String imageGroup;
    private String delFlag="0";
    private String ImageWxUrl;
    private String mediaId;
    @Transient
    private String image;
//    @Transient
//    private InputStream input;

	// Constructors

	/** default constructor */
	public SourceImage() {
	}

	/** full constructor */

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

    @Column(name="image_name")
    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
    @Column(name="image_local_url")
    public String getImageLocalUrl() {
        return imageLocalUrl;
    }

    public void setImageLocalUrl(String imageUrl) {
        this.imageLocalUrl = imageUrl;
    }
    @Column(name="image_size")
    public String getImageSize() {
        return imageSize;
    }

//    public InputStream getInput() {
//        return input;
//    }
//
//    public void setInput(InputStream input) {
//        this.input = input;
//    }

    public void setImageSize(String imageSize) {
        this.imageSize = imageSize;
    }
    @Column(name="image_group")
    public String getImageGroup() {
        return imageGroup;
    }

    public void setImageGroup(String imageGroup) {
        this.imageGroup = imageGroup;
    }
    @Column(name="del_flag")
    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Column(name="image_wx_url")
    public String getImageWxUrl() {
        return ImageWxUrl;
    }public void setImageWxUrl(String imageWxUrl) {
    ImageWxUrl = imageWxUrl;
}

    @Column(name="media_id")
    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }
    @Transient
    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}


