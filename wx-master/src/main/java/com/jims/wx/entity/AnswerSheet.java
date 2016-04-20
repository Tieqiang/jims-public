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
 * AnswerSheet entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ANSWER_SHEET", schema = "WX")
public class AnswerSheet implements java.io.Serializable {

	// Fields

	private String id;
	private String openId;
	private String patId;
	private String questionnaireId;
	private Date createTime;
	//private Set<AnswerResult> answerResults = new HashSet<AnswerResult>(0);

	// Constructors

	/** default constructor */
	public AnswerSheet() {
	}

	/** full constructor */
	public AnswerSheet(String openId, String patId, String questionnaireId,
			Date createTime) {
		this.openId = openId;
		this.patId = patId;
		this.questionnaireId = questionnaireId;
		this.createTime = createTime;
	}

    //public AnswerSheet(String openId, String patId, String questionnaireId,
    //                   Date createTime, Set<AnswerResult> answerResults) {
    //    this.openId = openId;
    //    this.patId = patId;
    //    this.questionnaireId = questionnaireId;
    //    this.createTime = createTime;
    //    this.answerResults = answerResults;
    //}

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

	@Column(name = "OPEN_ID", length = 64)
	public String getOpenId() {
		return this.openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	@Column(name = "PAT_ID", length = 64)
	public String getPatId() {
		return this.patId;
	}

	public void setPatId(String patId) {
		this.patId = patId;
	}

	@Column(name = "QUESTIONNAIRE_ID", length = 64)
	public String getQuestionnaireId() {
		return this.questionnaireId;
	}

	public void setQuestionnaireId(String questionnaireId) {
		this.questionnaireId = questionnaireId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TIME", length = 7)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	//@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "answerSheet")
	//public Set<AnswerResult> getAnswerResults() {
	//	return this.answerResults;
	//}
    //
	//public void setAnswerResults(Set<AnswerResult> answerResults) {
	//	this.answerResults = answerResults;
	//}

}