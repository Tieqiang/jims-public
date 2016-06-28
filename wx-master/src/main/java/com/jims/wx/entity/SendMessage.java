package com.jims.wx.entity;

/**
 * Created by admin on 2016/6/27.
 */
/**
 * 发送内容的内部类
 */
public class SendMessage {
    private String ids;
    private String content;
    public SendMessage(){

    }

    public SendMessage(String ids, String content) {
        this.ids = ids;
        this.content = content;
    }
    public SendMessage(String ids ) {
        this.ids = ids;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }
}