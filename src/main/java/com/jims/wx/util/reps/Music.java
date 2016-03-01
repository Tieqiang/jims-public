package com.jims.wx.util.reps;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by heren on 2016/3/1.
 */
public class Music {
    @XStreamAlias("Title")
    private String title;
    @XStreamAlias("Description")
    private String description;
    @XStreamAlias("MusicUrl")
    private String musicUrl;
    @XStreamAlias("HQMusicUrl")
    private String HQMusicUrl;
    @XStreamAlias("ThumbMediaId")
    private String thumbMediaId;

    public Music() {
    }

    public Music(String title, String description, String musicUrl, String HQMusicUrl, String thumbMediaId) {
        this.title = title;
        this.description = description;
        this.musicUrl = musicUrl;
        this.HQMusicUrl = HQMusicUrl;
        this.thumbMediaId = thumbMediaId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMusicUrl() {
        return musicUrl;
    }

    public void setMusicUrl(String musicUrl) {
        this.musicUrl = musicUrl;
    }

    public String getHQMusicUrl() {
        return HQMusicUrl;
    }

    public void setHQMusicUrl(String HQMusicUrl) {
        this.HQMusicUrl = HQMusicUrl;
    }

    public String getThumbMediaId() {
        return thumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId;
    }
}
