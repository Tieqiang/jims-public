package com.jims.wx.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by heren on 2016/4/16.
 */
@Entity
@Table(name = "app_user_vs_group", schema = "WX", uniqueConstraints = @UniqueConstraint(columnNames = "OPEN_ID"))
public class AppUserVsGroup implements Serializable {

    private String id ;
    private String openId ;
    private String groupId ;

    public AppUserVsGroup(String id, String openId, String groupId) {
        this.id = id;
        this.openId = openId;
        this.groupId = groupId;
    }

    public AppUserVsGroup() {
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

    @Column(name = "open_id")
    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    @Column(name="group_id")
    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }


}
