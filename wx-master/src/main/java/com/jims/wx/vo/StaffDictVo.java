package com.jims.wx.vo;


import com.jims.wx.entity.DeptDict;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * Created by heren on 2015/9/22.
 */
@XmlRootElement
public class StaffDictVo implements Serializable {
    private String id;
    private DeptDict deptDict;
    private String loginName;
    private String password;
    private String job;
    private String title;
    private String hospitalId;
    private List<String> ids  ;
    private String name ;
    private String idNo;


    public StaffDictVo() {
    }

    public StaffDictVo(String id, DeptDict deptDict, String loginName, String password, String job, String title, String hospitalId, List<String> ids, String name, String idNo) {
        this.id = id;
        this.deptDict = deptDict;
        this.loginName = loginName;
        this.password = password;
        this.job = job;
        this.title = title;
        this.hospitalId = hospitalId;
        this.ids = ids;
        this.name = name;
        this.idNo = idNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DeptDict getDeptDict() {
        return deptDict;
    }

    public void setDeptDict(DeptDict deptDict) {
        this.deptDict = deptDict;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }
}
