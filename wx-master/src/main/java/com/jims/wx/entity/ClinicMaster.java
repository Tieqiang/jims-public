package com.jims.wx.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

/**
 * ClinicMaster entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "CLINIC_MASTER", schema = "WX")
public class ClinicMaster implements java.io.Serializable {

	// Fields

	private String id;
	private PayWayDict payWayDict;
	private Date visitDate;
	private String clincRegistId;
	private String patientId;
	private Double registFee;
	private Double clinicFee;
	private Double otherFee;
	private Double clinicCharge;
	private String takeStatus;
	private Date takeTime;
	private Date registDate;
	private Date acctDateTime;
	private String acctNo;
	private String acctOperator;

	// Constructors

	/** default constructor */
	public ClinicMaster() {
	}

	/** minimal constructor */
	public ClinicMaster(String takeStatus) {
		this.takeStatus = takeStatus;
	}

	/** full constructor */
	public ClinicMaster(PayWayDict payWayDict, Date visitDate,
			String clincRegistId, String patientId, Double registFee,
			Double clinicFee, Double otherFee, Double clinicCharge,
			String takeStatus, Date takeTime, Date registDate,
			Date acctDateTime, String acctNo, String acctOperator) {
		this.payWayDict = payWayDict;
		this.visitDate = visitDate;
		this.clincRegistId = clincRegistId;
		this.patientId = patientId;
		this.registFee = registFee;
		this.clinicFee = clinicFee;
		this.otherFee = otherFee;
		this.clinicCharge = clinicCharge;
		this.takeStatus = takeStatus;
		this.takeTime = takeTime;
		this.registDate = registDate;
		this.acctDateTime = acctDateTime;
		this.acctNo = acctNo;
		this.acctOperator = acctOperator;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PAY_WAY_ID")
	public PayWayDict getPayWayDict() {
		return this.payWayDict;
	}

	public void setPayWayDict(PayWayDict payWayDict) {
		this.payWayDict = payWayDict;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "VISIT_DATE", length = 7)
	public Date getVisitDate() {
		return this.visitDate;
	}

	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}

	@Column(name = "CLINC_REGIST_ID", length = 64)
	public String getClincRegistId() {
		return this.clincRegistId;
	}

	public void setClincRegistId(String clincRegistId) {
		this.clincRegistId = clincRegistId;
	}

	@Column(name = "PATIENT_ID", length = 64)
	public String getPatientId() {
		return this.patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	@Column(name = "REGIST_FEE", precision = 5)
	public Double getRegistFee() {
		return this.registFee;
	}

	public void setRegistFee(Double registFee) {
		this.registFee = registFee;
	}

	@Column(name = "CLINIC_FEE", precision = 5)
	public Double getClinicFee() {
		return this.clinicFee;
	}

	public void setClinicFee(Double clinicFee) {
		this.clinicFee = clinicFee;
	}

	@Column(name = "OTHER_FEE", precision = 5)
	public Double getOtherFee() {
		return this.otherFee;
	}

	public void setOtherFee(Double otherFee) {
		this.otherFee = otherFee;
	}

	@Column(name = "CLINIC_CHARGE", precision = 5)
	public Double getClinicCharge() {
		return this.clinicCharge;
	}

	public void setClinicCharge(Double clinicCharge) {
		this.clinicCharge = clinicCharge;
	}

	@Column(name = "TAKE_STATUS", nullable = false, length = 20)
	public String getTakeStatus() {
		return this.takeStatus;
	}

	public void setTakeStatus(String takeStatus) {
		this.takeStatus = takeStatus;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "TAKE_TIME", length = 7)
	public Date getTakeTime() {
		return this.takeTime;
	}

	public void setTakeTime(Date takeTime) {
		this.takeTime = takeTime;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "REGIST_DATE", length = 7)
	public Date getRegistDate() {
		return this.registDate;
	}

	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "ACCT_DATE_TIME", length = 7)
	public Date getAcctDateTime() {
		return this.acctDateTime;
	}

	public void setAcctDateTime(Date acctDateTime) {
		this.acctDateTime = acctDateTime;
	}

	@Column(name = "ACCT_NO", length = 64)
	public String getAcctNo() {
		return this.acctNo;
	}

	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}

	@Column(name = "ACCT_OPERATOR", length = 64)
	public String getAcctOperator() {
		return this.acctOperator;
	}

	public void setAcctOperator(String acctOperator) {
		this.acctOperator = acctOperator;
	}

}