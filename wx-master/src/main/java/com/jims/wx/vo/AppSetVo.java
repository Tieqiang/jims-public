package com.jims.wx.vo;

import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by heren on 2016/4/14.
 */
@XmlRootElement
public class AppSetVo implements Serializable {

    private String hospitalName;
    private String appId;
    private String appSecret;
    private String openName;
    private String appToken;
    private String infoUrl;
    private String metchId;
    private String key;
    private byte[] content;
    private String hospitalImg;
    @Transient
    private String tranContent;
    private String hospitalId;

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getHospitalImg() {
        return hospitalImg;
    }

    public void setHospitalImg(String hospitalImg) {
        this.hospitalImg = hospitalImg;
    }

    public AppSetVo() {

    }

    public AppSetVo(String hospitalName, String appId, String appSecret, String openName, String appToken, String infoUrl, String metchId, String key, byte[] content, String tranContent) {
        this.hospitalName = hospitalName;
        this.appId = appId;
        this.appSecret = appSecret;
        this.openName = openName;
        this.appToken = appToken;
        this.infoUrl = infoUrl;
        this.metchId = metchId;
        this.key = key;
        this.content = content;
        this.tranContent = tranContent;
    }


    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getOpenName() {
        return openName;
    }

    public void setOpenName(String openName) {
        this.openName = openName;
    }

    public String getAppToken() {
        return appToken;
    }

    public void setAppToken(String appToken) {
        this.appToken = appToken;
    }

    public String getInfoUrl() {
        return infoUrl;
    }

    public void setInfoUrl(String infoUrl) {
        this.infoUrl = infoUrl;
    }

    public String getMetchId() {
        return metchId;
    }

    public void setMetchId(String metchId) {
        this.metchId = metchId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getTranContent() {
        return tranContent;
    }

    public void setTranContent(String tranContent) {
        this.tranContent = tranContent;
    }
}
