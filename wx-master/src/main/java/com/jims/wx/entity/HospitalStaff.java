package com.jims.wx.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by wei on 2016/4/26.
 */
@Entity
@Table(name = "HOSPITAL_STAFF", schema = "WX")
public class HospitalStaff  implements java.io.Serializable {

    private String id;
    private String openId;
    private String personId;
    private String name;

    public HospitalStaff() {
    }

    public HospitalStaff(String openId, String personId, String name) {
        this.openId = openId;
        this.personId = personId;
        this.name = name;
    }

    @GenericGenerator(name = "generator", strategy = "uuid.hex")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "ID", unique = true, nullable = false, length = 64)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @Column(name = "OPEN_ID", length = 64)
    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
    @Column(name = "PERSON_ID", length = 64)
    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }
    @Column(name = "NAME", length = 10)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
