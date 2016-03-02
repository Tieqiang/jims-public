package com.jims.wx.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * QuestionnaireVsSubject entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "QUESTIONNAIRE_VS_SUBJECT", schema = "WX", uniqueConstraints = @UniqueConstraint(columnNames = {
		"QUESTIONNAIRE_ID", "SUBJECT_ID", "SERIA_NO" }))
public class QuestionnaireVsSubject implements java.io.Serializable {

	// Fields

	private QuestionnaireVsSubjectId id;
	private QuestionnaireModel questionnaireModel;
	private Subject subject;

	// Constructors

	/** default constructor */
	public QuestionnaireVsSubject() {
	}

	/** minimal constructor */
	public QuestionnaireVsSubject(QuestionnaireVsSubjectId id) {
		this.id = id;
	}

	/** full constructor */
	public QuestionnaireVsSubject(QuestionnaireVsSubjectId id,
			QuestionnaireModel questionnaireModel, Subject subject) {
		this.id = id;
		this.questionnaireModel = questionnaireModel;
		this.subject = subject;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "questionnaireId", column = @Column(name = "QUESTIONNAIRE_ID", length = 64)),
			@AttributeOverride(name = "subjectId", column = @Column(name = "SUBJECT_ID", length = 64)),
			@AttributeOverride(name = "seriaNo", column = @Column(name = "SERIA_NO", precision = 22, scale = 0)) })
	public QuestionnaireVsSubjectId getId() {
		return this.id;
	}

	public void setId(QuestionnaireVsSubjectId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "QUESTIONNAIRE_ID", insertable = false, updatable = false)
	public QuestionnaireModel getQuestionnaireModel() {
		return this.questionnaireModel;
	}

	public void setQuestionnaireModel(QuestionnaireModel questionnaireModel) {
		this.questionnaireModel = questionnaireModel;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SUBJECT_ID", insertable = false, updatable = false)
	public Subject getSubject() {
		return this.subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

}