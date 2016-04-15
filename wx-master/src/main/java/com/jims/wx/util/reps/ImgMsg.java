package com.jims.wx.util.reps;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by heren on 2016/3/1.
 */
@XStreamAlias("xml")
public class ImgMsg extends BaseMsg {
    @XStreamAlias("Image")
    private Image image;

    public ImgMsg() {
    }

    public ImgMsg(String toUserName, String fromUserName, Long createTime, String msgType, Image image) {
        super(toUserName, fromUserName, createTime, msgType);
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
