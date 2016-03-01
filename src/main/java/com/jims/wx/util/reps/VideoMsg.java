package com.jims.wx.util.reps;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by heren on 2016/3/1.
 */
@XStreamAlias("xml")
public class VideoMsg extends BaseMsg {
    @XStreamAlias("Video")
    private Video video;

    public VideoMsg() {
    }

    public VideoMsg(String toUserName, String fromUserName, Long createTime, String msgType, Video video) {
        super(toUserName, fromUserName, createTime, msgType);
        this.video = video;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }
}
