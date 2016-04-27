package com.jims.wx.vo;

import java.io.Serializable;

/**
 * Created by zhu on 2016/4/25.
 */
public class PatVsUserVo implements Serializable {
    private String id;
    private String name;
    private String patId;

    public PatVsUserVo(){
    }

    public PatVsUserVo(String id, String name, String patId) {
        this.id = id;
        this.name = name;
        this.patId = patId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatId() {
        return patId;
    }

    public void setPatId(String patId) {
        this.patId = patId;
    }
}
