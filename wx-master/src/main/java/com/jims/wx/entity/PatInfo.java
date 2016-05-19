package com.jims.wx.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

/**
 * PatInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "PAT_INFO", schema = "WX")
public class PatInfo implements java.io.Serializable {

	// Fields

	private String id;
	private String idCard;
	private String cellphone;
	private String name;
	private String defaultFlag;
	private String sex;
	private Date birthday;

    private String patientId;

    private String flag;



//    private String patId;
	// Constructors
 	/** default constructor */
	public PatInfo() {
	}

	/** full constructor */
	public PatInfo(String idCard, String cellphone, String name,
			String defaultFlag, String sex, Date birthday
			/*Set<PatVsUser> patVsUsers*/) {
		this.idCard = idCard;
		this.cellphone = cellphone;
		this.name = name;
		this.defaultFlag = defaultFlag;
		this.sex = sex;
		this.birthday = birthday;
//		this.patVsUsers = patVsUsers;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, length = 64)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "ID_CARD", length = 20)
	public String getIdCard() {
		return this.idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	@Column(name = "CELLPHONE", length = 20)
	public String getCellphone() {
		return this.cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	@Column(name = "NAME", length = 30)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "DEFAULT_FLAG", length = 2)
	public String getDefaultFlag() {
		return this.defaultFlag;
	}

	public void setDefaultFlag(String defaultFlag) {
		this.defaultFlag = defaultFlag;
	}

	@Column(name = "SEX", length = 2)
	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "BIRTHDAY", length = 7)
	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
    @Column(name="patient_id")
    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
    @Column(name="flag")
    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}