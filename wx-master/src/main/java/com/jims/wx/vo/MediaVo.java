package com.jims.wx.vo;

import weixin.popular.bean.media.Media;
import weixin.popular.bean.media.UploadimgResult;

import java.io.Serializable;

/**
 * Created by heren on 2016/4/21.
 */
public class MediaVo implements Serializable {

    private Media media ;
    private UploadimgResult uploadimgResult ;

    public MediaVo(Media media, UploadimgResult uploadimgResult) {
        this.media = media;
        this.uploadimgResult = uploadimgResult;
    }

    public MediaVo() {
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public UploadimgResult getUploadimgResult() {
        return uploadimgResult;
    }

    public void setUploadimgResult(UploadimgResult uploadimgResult) {
        this.uploadimgResult = uploadimgResult;
    }
}
