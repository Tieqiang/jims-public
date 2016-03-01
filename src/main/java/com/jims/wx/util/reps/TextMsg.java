package com.jims.wx.util.reps;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

/**
 * Created by heren on 2016/3/1.
 */
@XStreamAlias("xml")
public class TextMsg extends BaseMsg {


    @XStreamAlias("Content")
    private String Content ;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public TextMsg() {
    }

    public TextMsg(String toUserName, String fromUserName, Long createTime, String msgType, String content) {
        super(toUserName, fromUserName, createTime, msgType);
        Content = content;
    }
}
