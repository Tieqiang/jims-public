package com.jims.wx.vo;


import java.io.Serializable;

/**
 * Created by heren on 2015/11/28.
 */
public class Config implements Serializable {

    private String loginName ;
    private String staffName;
    private String hospitalId ;
    private String hospitalName ;
    private String storageCode ;
    private String storageName;
    private String loginId ;
    private String moduleId ;
    private String moduleName ;
    private String exportClass ;
    private String reportServerIp ;
    private String reportServerPort ;
    private String reportServerResourcePath ;
    private String defaultSupplier ;
    private String reportServerName;
    private String defaultReportPath;
    private String acctDeptId ;//核算单元
    private String appId;//公众号


    public Config() {
    }

    public Config(String loginName, String staffName, String hospitalId, String hospitalName, String storageCode, String storageName, String loginId, String moduleId, String moduleName, String exportClass, String reportServerIp, String reportServerPort, String reportServerResourcePath, String defaultSupplier, String reportServerName, String defaultReportPath, String acctDeptId, String appId) {
        this.loginName = loginName;
        this.staffName = staffName;
        this.hospitalId = hospitalId;
        this.hospitalName = hospitalName;
        this.storageCode = storageCode;
        this.storageName = storageName;
        this.loginId = loginId;
        this.moduleId = moduleId;
        this.moduleName = moduleName;
        this.exportClass = exportClass;
        this.reportServerIp = reportServerIp;
        this.reportServerPort = reportServerPort;
        this.reportServerResourcePath = reportServerResourcePath;
        this.defaultSupplier = defaultSupplier;
        this.reportServerName = reportServerName;
        this.defaultReportPath = defaultReportPath;
        this.acctDeptId = acctDeptId;
        this.appId = appId;
    }

    public String getDefaultReportPath() {
        return defaultReportPath;
    }

    public void setDefaultReportPath(String defaultReportPath) {
        this.defaultReportPath = defaultReportPath;
    }

    public String getReportServerName() {
        return reportServerName;
    }

    public void setReportServerName(String reportServerName) {
        this.reportServerName = reportServerName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getStorageCode() {
        return storageCode;
    }

    public void setStorageCode(String storageCode) {
        this.storageCode = storageCode;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getExportClass() {
        if("".equals(exportClass)||exportClass==null){
            return "'发放出库','批量出库','退账入库'" ;
        }
        return exportClass;
    }

    public void setExportClass(String exportClass) {
        this.exportClass = exportClass;
    }

    public String getReportServerIp() {

        return reportServerIp;
    }

    public void setReportServerIp(String reportServerIp) {
        this.reportServerIp = reportServerIp;
    }

    public String getReportServerPort() {
        return reportServerPort;
    }

    public void setReportServerPort(String reportServerPort) {
        this.reportServerPort = reportServerPort;
    }

    public String getReportServerResourcePath() {
        return reportServerResourcePath;
    }

    public void setReportServerResourcePath(String reportServerResourcePath) {
        this.reportServerResourcePath = reportServerResourcePath;
    }

    public String getDefaultSupplier() {
        return defaultSupplier;
    }

    public void setDefaultSupplier(String defaultSupplier) {
        this.defaultSupplier = defaultSupplier;
    }

    public String getStorageName() {
        return storageName;
    }

    public void setStorageName(String storageName) {
        this.storageName = storageName;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getAcctDeptId() {
        return acctDeptId;
    }

    public void setAcctDeptId(String acctDeptId) {
        this.acctDeptId = acctDeptId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}

