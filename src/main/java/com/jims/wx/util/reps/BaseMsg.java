package com.jims.wx.util.reps;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

/**
 * Created by heren on 2016/3/1.
 */

public class BaseMsg implements Serializable {


    @XStreamAlias("ToUserName")
    private String toUserName ;
    @XStreamAlias("FromUserName")
    private String fromUserName ;
    @XStreamAlias("CreateTime")
    private Long createTime ;
    @XStreamAlias("MsgType")
    private String msgType ;

    public BaseMsg() {
    }

    public BaseMsg(String toUserName, String fromUserName, Long createTime, String msgType) {
        this.toUserName = toUserName;
        this.fromUserName = fromUserName;
        this.createTime = createTime;
        this.msgType = msgType;
    }


    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }
}
