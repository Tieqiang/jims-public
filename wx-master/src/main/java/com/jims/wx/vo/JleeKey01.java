package com.jims.wx.vo;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by admin on 2017/1/23.
 */
@Embeddable
public class JleeKey01 implements Serializable {

    private static final long serialVersionUID = -3304319243957837925L;

     private Date visitDate ;
     private Integer visitNo ;

    public Integer getVisitNo() {
        return visitNo;
    }

    public void setVisitNo(Integer visitNo) {
        this.visitNo = visitNo;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof JleeKey01){
            JleeKey01 key = (JleeKey01)o ;
            if(this.visitDate == key.getVisitDate() && this.visitNo.equals(key.getVisitNo())){
                return true ;
            }
        }
        return false ;
    }

    @Override
    public int hashCode() {
        return this.visitNo.hashCode();
    }

}
