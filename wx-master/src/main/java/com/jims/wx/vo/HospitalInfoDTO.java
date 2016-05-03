package com.jims.wx.vo;

/**
 * Created by chenxiaoyang on 2016/3/28.
 */
public class HospitalInfoDTO {


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

        private String  id;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    private String  text;

    public HospitalInfoDTO() {

    }
    public HospitalInfoDTO(String id, String text) {
        this.id = id;
        this.text = text;
    }
}
