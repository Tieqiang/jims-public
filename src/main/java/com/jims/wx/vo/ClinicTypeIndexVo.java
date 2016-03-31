package com.jims.wx.vo;

import com.jims.wx.entity.ClinicIndex;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wei on 2016/3/31.
 */
public class ClinicTypeIndexVo implements Serializable {
    private String id;
    private String clinicName;
    private String hospitalId;
    private String appId;
    private String parentFlag;
    private String clinicDept;
    private List<ClinicTypeIndexVo> clinicIndex = new ArrayList<ClinicTypeIndexVo>(0);

    public ClinicTypeIndexVo() {
    }

    public ClinicTypeIndexVo(String id, String clinicName, String hospitalId, String appId, String parentFlag, String clinicDept, List<ClinicTypeIndexVo> clinicIndex) {
        this.id = id;
        this.clinicName = clinicName;
        this.hospitalId = hospitalId;
        this.appId = appId;
        this.parentFlag = parentFlag;
        this.clinicDept = clinicDept;
        this.clinicIndex = clinicIndex;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getParentFlag() {
        return parentFlag;
    }

    public void setParentFlag(String parentFlag) {
        this.parentFlag = parentFlag;
    }

    public List<ClinicTypeIndexVo> getClinicIndex() {
        return clinicIndex;
    }

    public void setClinicIndex(List<ClinicTypeIndexVo> clinicIndex) {
        this.clinicIndex = clinicIndex;
    }

    public String getClinicDept() {
        return clinicDept;
    }

    public void setClinicDept(String clinicDept) {
        this.clinicDept = clinicDept;
    }

}
