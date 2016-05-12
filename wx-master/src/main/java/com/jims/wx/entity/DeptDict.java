package com.jims.wx.entity;

import com.google.inject.Inject;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

/**
 * DeptDict entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "DEPT_DICT", schema = "WX")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DeptDict implements java.io.Serializable {

    // Fields

    private String id;
    //	private HospitalDict hospitalDict;
    private String deptCode;
    private String deptName;
    private String deptAlis;
    private String deptAttr;
    private String deptOutpInp;
    private String inputCode;
    private String deptDevideAttr;
    private String deptLocation;
    private String deptOther;
    private String deptStopFlag;
    private String deptInfo;
    private String parentId;
    //	private Set<StaffDict> staffDicts = new HashSet<StaffDict>(0);
    private String deptType;//科室类型，一般将科室分为：直接医疗类科室、医疗技术类科室、医疗辅助类科室、管理类科室、未纳入成本
    private String deptClass;//科室类别，一般为经营科室和其他
    private String endDept;//是否末级科室
    private String imgUrl;//图标url
    private String img;

    private String imgUrl2;

    private String text;
    // Constructors

    /**
     * default constructor
     */

    public DeptDict() {

    }

    /**
     * full constructor
     */
    public DeptDict(/*HospitalDict hospitalDict,*/
                    String deptCode, String deptName, String deptAlis, String deptAttr,
                    String deptOutpInp, String inputCode, String deptDevideAttr,
                    String deptLocation, String deptOther, String deptStopFlag,
                    String deptInfo, String parentId, /*Set<StaffDict> staffDicts,*/ String deptType, String deptClass, String endDept) {
//		this.hospitalDict = hospitalDict;
        this.deptCode = deptCode;
        this.deptName = deptName;
        this.deptAlis = deptAlis;
        this.deptAttr = deptAttr;
        this.deptOutpInp = deptOutpInp;
        this.inputCode = inputCode;
        this.deptDevideAttr = deptDevideAttr;
        this.deptLocation = deptLocation;
        this.deptOther = deptOther;
        this.deptStopFlag = deptStopFlag;
        this.deptInfo = deptInfo;
        this.parentId = parentId;
//        this.staffDicts = staffDicts;
        this.deptType = deptType;
        this.deptClass = deptClass;
        this.endDept = endDept;
    }

    // Property accessors
    @GenericGenerator(name = "generator", strategy = "uuid.hex")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "id", unique = true, nullable = false, length = 64)
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Column(name = "parent_id")
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

//    //@JsonBackReference
//    @ManyToOne
//	@JoinColumn(name = "hospital_id")
//	public HospitalDict getHospitalDict() {
//		return this.hospitalDict;
//	}
//
//	public void setHospitalDict(HospitalDict hospitalDict) {
//		this.hospitalDict = hospitalDict;
//	}

    @Column(name = "dept_code", length = 9)
    public String getDeptCode() {
        return this.deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    @Column(name = "dept_name", length = 40)
    public String getDeptName() {
        return this.deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    @Column(name = "dept_alis", length = 40)
    public String getDeptAlis() {
        return this.deptAlis;
    }

    public void setDeptAlis(String deptAlis) {
        this.deptAlis = deptAlis;
    }

    @Column(name = "dept_attr", length = 2)
    public String getDeptAttr() {
        return this.deptAttr;
    }

    public void setDeptAttr(String deptAttr) {
        this.deptAttr = deptAttr;
    }

    @Column(name = "dept_outp_inp", length = 1)
    public String getDeptOutpInp() {
        return this.deptOutpInp;
    }

    public void setDeptOutpInp(String deptOutpInp) {
        this.deptOutpInp = deptOutpInp;
    }

    @Column(name = "input_code", length = 20)
    public String getInputCode() {
        return this.inputCode;
    }

    public void setInputCode(String inputCode) {
        this.inputCode = inputCode;
    }

    @Column(name = "dept_devide_attr", length = 24)
    public String getDeptDevideAttr() {
        return this.deptDevideAttr;
    }

    public void setDeptDevideAttr(String deptDevideAttr) {
        this.deptDevideAttr = deptDevideAttr;
    }

    @Column(name = "dept_location", length = 80)
    public String getDeptLocation() {
        return this.deptLocation;
    }

    public void setDeptLocation(String deptLocation) {
        this.deptLocation = deptLocation;
    }

    @Column(name = "dept_other", length = 50)
    public String getDeptOther() {
        return this.deptOther;
    }

    public void setDeptOther(String deptOther) {
        this.deptOther = deptOther;
    }

    @Column(name = "dept_stop_flag", length = 1)
    public String getDeptStopFlag() {
        return this.deptStopFlag;
    }

    public void setDeptStopFlag(String deptStopFlag) {
        this.deptStopFlag = deptStopFlag;
    }

    @Column(name = "dept_info", length = 500)
    public String getDeptInfo() {
        return this.deptInfo;
    }

    public void setDeptInfo(String deptInfo) {
        this.deptInfo = deptInfo;
    }

//    @JsonIgnore
//	@OneToMany(cascade = CascadeType.ALL, mappedBy = "deptDict")
//	public Set<StaffDict> getStaffDicts() {
//		return this.staffDicts;
//	}
//
//
//	public void setStaffDicts(Set<StaffDict> staffDicts) {
//		this.staffDicts = staffDicts;
//	}


    @Column(name = "dept_type")
    public String getDeptType() {
        return deptType;
    }

    public void setDeptType(String deptType) {
        this.deptType = deptType;
    }

    @Column(name = "dept_class")
    public String getDeptClass() {
        return deptClass;
    }

    public void setDeptClass(String deptClass) {
        this.deptClass = deptClass;
    }

    @Column(name = "end_dept")
    public String getEndDept() {
        return endDept;
    }

    public void setEndDept(String endDept) {
        this.endDept = endDept;
    }


    @Column(name = "IMG_URL")
    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Transient
    public String getImgUrl2() {
        return this.imgUrl2;
    }

    public void setImgUrl2(String imgUrl2) {
        this.imgUrl2 = imgUrl2;
    }

    @Transient
    public String getImg() {
        return this.img;
    }


    public void setImg(String url) {
        this.img = "<img src='" + url + "'/>";
    }

    @Transient
    public String getTranDeptInfo() {
        if (this.deptInfo != null && !"".equals(deptInfo)) {
            deptInfo = deptInfo.replaceAll("</?[a-zA-Z]+[^><]*>", "").
                    replace("<h1>", "").replace("</h1>", "").
                    replace("<h2>", "").replace("</h2>", "").
                    replace("<h3>", "").replace("</h3>", "").
                    replace("<h4>", "").replace("</h4>", "").
                    replace("<h5>", "").replace("</h5>", "").
                    replace("<h6>", "").replace("</h6>", "");
            return deptInfo;
        }
        return "";
    }

    @Transient
    public String getText() {
        return this.deptName;
    }
}