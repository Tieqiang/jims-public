package com.jims.wx.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by cxy on 2016/5/9.
 */
@Entity
@Table(name = "clinic_symptom", schema = "WX")
public class ClinicSymptom implements Serializable {

    private String id;//主键

    private String name;

    private String bodyPartId;//身体部位的主键

    private String bodyPartName;//身体部位

    private String text;

    private String sex;


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
    @Column(name="body_part_id")
    public String getBodyPartId() {
        return bodyPartId;
    }

    public void setBodyPartId(String bodyPartId) {
        this.bodyPartId = bodyPartId;
    }

    public ClinicSymptom(){

    }

    public ClinicSymptom(String id, String name, String bodyPartId) {
        this.id = id;
        this.name = name;
        this.bodyPartId = bodyPartId;
    }
    @Transient
    public String getBodyPartName() {
        return bodyPartName;
    }

    public void setBodyPartName(String bodyPartName) {
        this.bodyPartName = bodyPartName;
    }

    @Transient
    public String getText(){
        return this.name;
    }

    @Column(name="sex")
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
}

