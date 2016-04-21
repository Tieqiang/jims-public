package com.jims.wx.vo;

/**
 * Created by admin on 2016/3/30.
 */
public class ComboboxVo {

    private String id;

    private String text;


    public String getText() {

        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ComboboxVo() {

    }
    public ComboboxVo(String id, String text) {
        this.id = id;
        this.text = text;
    }


}
