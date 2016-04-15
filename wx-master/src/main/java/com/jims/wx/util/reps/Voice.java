package com.jims.wx.util.reps;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by heren on 2016/3/1.
 */
public class Voice {
    @XStreamAlias("MediaId")
    private String mediaId;

    public Voice() {
    }

    public Voice(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }
}
