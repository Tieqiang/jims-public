package com.jims.wx.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by cxy on 2016/5/17.
 */
@Entity
@Table(name = "user_collection", schema = "WX")
public class UserCollection implements Serializable {

    private String id;//主键

    private String doctId;//医生id

    private String clinicIndexId;//号别id

    private String openId;//openId

    public UserCollection(){

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
    @Column(name="doc_id")
    public String getDoctId() {
        return doctId;
    }

    public void setDoctId(String doctId) {
        this.doctId = doctId;
    }
    @Column(name="CLINIC_INDEX_ID")
    public String getClinicIndexId() {
        return clinicIndexId;
    }

    public void setClinicIndexId(String clinicIndexId) {
        this.clinicIndexId = clinicIndexId;
    }
    @Column(name="OPENID")
    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public UserCollection(String id, String doctId, String clinicIndexId, String openId) {
        this.id = id;
        this.doctId = doctId;
        this.clinicIndexId = clinicIndexId;
        this.openId = openId;
    }
}
