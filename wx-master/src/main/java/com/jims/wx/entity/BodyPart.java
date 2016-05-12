package com.jims.wx.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by cxy on 2016/5/9.
 */
@Entity
@Table(name = "body_part", schema = "WX")
public class BodyPart implements Serializable {

    private String id;//主键

    private String name;//部位的名称

    private String flag;//备用字段

    public BodyPart(){

    }

    public BodyPart(String name, String id, String flag) {
        this.name = name;
        this.id = id;
        this.flag = flag;
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
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Column(name = "flag")
    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Transient
    public String getText(){
        if(this.name!=null&&!"".equals(this.name))
            return this.name;
            return "";
     }
}
