package com.jims.wx.util.reps;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by heren on 2016/3/1.
 */
@XStreamAlias("xml")
public class MusicMsg extends BaseMsg {
    @XStreamAlias("Music")
    private Music music;

    public MusicMsg() {
    }

    public MusicMsg(String toUserName, String fromUserName, Long createTime, String msgType, Music music) {
        super(toUserName, fromUserName, createTime, msgType);
        this.music = music;
    }

    public Music getMusic() {
        return music;
    }

    public void setMusic(Music music) {
        this.music = music;
    }
}
