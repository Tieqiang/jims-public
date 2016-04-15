package com.jims.wx.util.reps;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by heren on 2016/3/1.
 */
@XStreamAlias("xml")
public class VoiceMsg extends BaseMsg {
    @XStreamAlias("Voice")
    private Voice voice;

    public VoiceMsg() {
    }

    public VoiceMsg(String toUserName, String fromUserName, Long createTime, String msgType, Voice voice) {
        super(toUserName, fromUserName, createTime, msgType);
        this.voice = voice;
    }

    public Voice getVoice() {
        return voice;
    }

    public void setVoice(Voice voice) {
        this.voice = voice;
    }
}
