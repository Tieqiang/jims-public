package com.jims.wx.vo;

import java.io.Serializable;

/**
 * Created by heren on 2015/11/28.
 */
public class ErrorMessager implements Serializable {

    private String errorMessage ;
    private String errorLocation ;

    public ErrorMessager(String errorMessage, String errorLocation) {
        this.errorMessage = errorMessage;
        this.errorLocation = errorLocation;
    }

    public ErrorMessager() {
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorLocation() {
        return errorLocation;
    }

    public void setErrorLocation(String errorLocation) {
        this.errorLocation = errorLocation;
    }
}
