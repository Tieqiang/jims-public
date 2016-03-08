package com.jims.wx.expection;

import javax.ws.rs.WebApplicationException;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by heren on 2015/9/18.
 */
@XmlRootElement
public class ErrorException extends WebApplicationException {

    private String errorMessage ;


    public ErrorException() {
    }

    public ErrorException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }


    /**
     * 设置异常信息
     * @param e
     */
    public void setMessage(Exception e ){
        ByteArrayOutputStream bos  = new ByteArrayOutputStream();
        e.printStackTrace(new PrintStream(bos));
        String errorInfo  = bos.toString();

        String regEx = "Caused by:(.*)";
        Pattern pattern = Pattern.compile(regEx);
        Matcher mat = pattern.matcher(errorInfo);

        StringBuffer stringBuffer = new StringBuffer() ;

        while(mat.find()){
            stringBuffer.append(mat.group()) ;
        }

        this.setErrorMessage(stringBuffer.toString());

    }

}
