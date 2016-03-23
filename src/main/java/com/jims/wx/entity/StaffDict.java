package com.jims.wx.entity;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * StaffDict entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "STAFF_DICT", schema = "WX")
public class StaffDict implements java.io.Serializable {

	// Fields

	private String id;
	private DeptDict deptDict;
	private String loginName;
	private String password;
	private String job;
	private String title;
    private String roleIds ;
    private String roleNames ;
    private String hospitalId ;
    private String name ;
    private String empNo ;
    private String inputCode;
	private Set<StaffVsRole> staffVsRoles = new HashSet<StaffVsRole>(0);
    private String acctDeptId ;
    private String idNo ;
	// Constructors

	/** default constructor */
	public StaffDict() {
	}
    /** full constructor */
    public StaffDict(String id, DeptDict deptDict, String loginName, String password, String job, String title, String roleIds, String roleNames, String hospitalId, String name, String empNo, String inputCode, Set<StaffVsRole> staffVsRoles, String acctDeptId, String idNo) {
        this.id = id;
        this.deptDict = deptDict;
        this.loginName = loginName;
        this.password = password;
        this.job = job;
        this.title = title;
        this.roleIds = roleIds;
        this.roleNames = roleNames;
        this.hospitalId = hospitalId;
        this.name = name;
        this.empNo = empNo;
        this.inputCode = inputCode;
        this.staffVsRoles = staffVsRoles;
        this.acctDeptId = acctDeptId;
        this.idNo = idNo;
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

	@ManyToOne
	@JoinColumn(name = "DEPT_ID")
	public DeptDict getDeptDict() {
		return this.deptDict;
	}

	public void setDeptDict(DeptDict deptDict) {
		this.deptDict = deptDict;
	}

	@Column(name = "LOGIN_NAME", length = 12)
	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Column(name = "PASSWORD", length = 100)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "JOB", length = 12)
	public String getJob() {
		return this.job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	@Column(name = "TITLE", length = 12)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

    @JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "staffDict")
	public Set<StaffVsRole> getStaffVsRoles() {
		return this.staffVsRoles;
	}

	public void setStaffVsRoles(Set<StaffVsRole> staffVsRoles) {
		this.staffVsRoles = staffVsRoles;
	}


    @Transient
    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }
    @Transient
    public String getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(String roleNames) {
        this.roleNames = roleNames;
    }
    @Column(name = "HOSPITAL_ID", length = 64)
    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    @Column(name="name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "INPUT_CODE", length = 8)
    public String getInputCode() {
        return this.inputCode;
    }

    public void setInputCode(String inputCode) {
        this.inputCode = inputCode;
    }

    @Column(name="acct_dept_id")
    public String getAcctDeptId() {
        return acctDeptId;
    }

    public void setAcctDeptId(String acctDeptId) {
        this.acctDeptId = acctDeptId;
    }

    @Column(name="emp_no")
    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    @Column(name="id_no")
    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }
}