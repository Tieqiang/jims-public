package com.jims.wx.entity;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * QuestionnaireVsSubjectId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class QuestionnaireVsSubjectId implements java.io.Serializable {

	// Fields

	private String questionnaireId;
	private String subjectId;
	private BigDecimal seriaNo;

	// Constructors

	/** default constructor */
	public QuestionnaireVsSubjectId() {
	}

	/** full constructor */
	public QuestionnaireVsSubjectId(String questionnaireId, String subjectId,
			BigDecimal seriaNo) {
		this.questionnaireId = questionnaireId;
		this.subjectId = subjectId;
		this.seriaNo = seriaNo;
	}

	// Property accessors

	@Column(name = "QUESTIONNAIRE_ID", length = 64)
	public String getQuestionnaireId() {
		return this.questionnaireId;
	}

	public void setQuestionnaireId(String questionnaireId) {
		this.questionnaireId = questionnaireId;
	}

	@Column(name = "SUBJECT_ID", length = 64)
	public String getSubjectId() {
		return this.subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	@Column(name = "SERIA_NO", precision = 22, scale = 0)
	public BigDecimal getSeriaNo() {
		return this.seriaNo;
	}

	public void setSeriaNo(BigDecimal seriaNo) {
		this.seriaNo = seriaNo;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof QuestionnaireVsSubjectId))
			return false;
		QuestionnaireVsSubjectId castOther = (QuestionnaireVsSubjectId) other;

		return ((this.getQuestionnaireId() == castOther.getQuestionnaireId()) || (this
				.getQuestionnaireId() != null
				&& castOther.getQuestionnaireId() != null && this
				.getQuestionnaireId().equals(castOther.getQuestionnaireId())))
				&& ((this.getSubjectId() == castOther.getSubjectId()) || (this
						.getSubjectId() != null
						&& castOther.getSubjectId() != null && this
						.getSubjectId().equals(castOther.getSubjectId())))
				&& ((this.getSeriaNo() == castOther.getSeriaNo()) || (this
						.getSeriaNo() != null && castOther.getSeriaNo() != null && this
						.getSeriaNo().equals(castOther.getSeriaNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getQuestionnaireId() == null ? 0 : this.getQuestionnaireId()
						.hashCode());
		result = 37 * result
				+ (getSubjectId() == null ? 0 : this.getSubjectId().hashCode());
		result = 37 * result
				+ (getSeriaNo() == null ? 0 : this.getSeriaNo().hashCode());
		return result;
	}

}