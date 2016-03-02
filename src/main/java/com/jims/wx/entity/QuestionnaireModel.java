package com.jims.wx.entity;

import java.math.BigDecimal;
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
 * QuestionnaireModel entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "QUESTIONNAIRE_MODEL", schema = "WX")
public class QuestionnaireModel implements java.io.Serializable {

	// Fields

	private String id;
	private String title;
	private String memo;
	private String createPerson;
	private BigDecimal totalNumbers;
	private String appId;
	private Set<QuestionnaireVsSubject> questionnaireVsSubjects = new HashSet<QuestionnaireVsSubject>(
			0);

	// Constructors

	/** default constructor */
	public QuestionnaireModel() {
	}

	/** full constructor */
	public QuestionnaireModel(String title, String memo, String createPerson,
			BigDecimal totalNumbers, String appId,
			Set<QuestionnaireVsSubject> questionnaireVsSubjects) {
		this.title = title;
		this.memo = memo;
		this.createPerson = createPerson;
		this.totalNumbers = totalNumbers;
		this.appId = appId;
		this.questionnaireVsSubjects = questionnaireVsSubjects;
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

	@Column(name = "TITLE", length = 100)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "MEMO", length = 400)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Column(name = "CREATE_PERSON", length = 20)
	public String getCreatePerson() {
		return this.createPerson;
	}

	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}

	@Column(name = "TOTAL_NUMBERS", precision = 22, scale = 0)
	public BigDecimal getTotalNumbers() {
		return this.totalNumbers;
	}

	public void setTotalNumbers(BigDecimal totalNumbers) {
		this.totalNumbers = totalNumbers;
	}

	@Column(name = "APP_ID", length = 64)
	public String getAppId() {
		return this.appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "questionnaireModel")
	public Set<QuestionnaireVsSubject> getQuestionnaireVsSubjects() {
		return this.questionnaireVsSubjects;
	}

	public void setQuestionnaireVsSubjects(
			Set<QuestionnaireVsSubject> questionnaireVsSubjects) {
		this.questionnaireVsSubjects = questionnaireVsSubjects;
	}

}