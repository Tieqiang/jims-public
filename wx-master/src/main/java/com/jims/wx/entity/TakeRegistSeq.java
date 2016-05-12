package com.jims.wx.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by cxy on 2016/5/9.
 */
@Entity
@Table(name = "take_regist_seq", schema = "WX")
public class TakeRegistSeq implements Serializable {

    private String id;//主键

    private String math;

    private String time;

    public TakeRegistSeq() {

    }


    @GenericGenerator(name = "generator", strategy = "uuid.hex")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "id", unique = true, nullable = false, length = 64)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "math")
    public String getMath() {
        return math;
    }

    public void setMath(String math) {
        this.math = math;
    }
    @Column(name="time")
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public TakeRegistSeq(String math, String time) {
        this.math = math;
        this.time = time;
    }
}
