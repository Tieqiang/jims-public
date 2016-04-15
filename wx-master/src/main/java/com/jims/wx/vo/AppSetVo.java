package com.jims.wx.vo;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by heren on 2016/4/14.
 */
@XmlRootElement
public class AppSetVo implements Serializable {

    private String hospitalName ;
    private String appId ;
    private String appSecret ;
    private String openName ;
    private String appToken;
    private String infoUrl ;

    public AppSetVo() {

    }

    public AppSetVo(String hospitalName, String appId, String appSecret, String openName, String appToken, String infoUrl) {
        this.hospitalName = hospitalName;
        this.appId = appId;
        this.appSecret = appSecret;
        this.openName = openName;
        this.appToken = appToken;
        this.infoUrl = infoUrl;
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
}
