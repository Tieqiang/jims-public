package com.jims.wx.vo;

/**
 * Created by admin on 2016/6/27.
 * used by 封装 requestMessage 表中的字段
 */
public class MessageVo {

    private String id;
    private String fromUserName;//用户

    private String content;

    private String msgType;

    private String createTime;

    private String toUserName;//

    private String replyContent;

    private  String replyFlag;

    private  String openId;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getReplyFlag() {
        return replyFlag;
    }

    public void setReplyFlag(String replyFlag) {
        this.replyFlag = replyFlag;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public MessageVo(){

    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public MessageVo(String id, String fromUserName, String content, String msgType, String createTime) {
        this.id = id;
        this.fromUserName = fromUserName;
        this.content = content;
        this.msgType = msgType;
        this.createTime = createTime;
    }
}


