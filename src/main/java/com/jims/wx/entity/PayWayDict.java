package com.jims.wx.entity;

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
import org.hibernate.annotations.GenericGenerator;

/**
 * PayWayDict entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "PAY_WAY_DICT", schema = "WX")
public class PayWayDict implements java.io.Serializable {

	// Fields

	private String id;
	private String payWayName;
//	private Set<ClinicMaster> clinicMasters = new HashSet<ClinicMaster>(0);

	// Constructors

	/** default constructor */
	public PayWayDict() {
	}

	/** full constructor */
	public PayWayDict(String payWayName) {
		this.payWayName = payWayName;
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

	@Column(name = "PAY_WAY_NAME", length = 20)
	public String getPayWayName() {
		return this.payWayName;
	}

	public void setPayWayName(String payWayName) {
		this.payWayName = payWayName;
	}

	/*@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "payWayDict")
	public Set<ClinicMaster> getClinicMasters() {
		return this.clinicMasters;
	}

	public void setClinicMasters(Set<ClinicMaster> clinicMasters) {
		this.clinicMasters = clinicMasters;
	}*/

}