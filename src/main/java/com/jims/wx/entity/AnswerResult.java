package com.jims.wx.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * AnswerResult entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ANSWER_RESULT", schema = "WX")
public class AnswerResult implements java.io.Serializable {

	// Fields

	private String id;
	private Subject subject;
	private AnswerSheet answerSheet;
	private String answer;

	// Constructors

	/** default constructor */
	public AnswerResult() {
	}

	/** full constructor */
	public AnswerResult(Subject subject, AnswerSheet answerSheet, String answer) {
		this.subject = subject;
		this.answerSheet = answerSheet;
		this.answer = answer;
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
	@JoinColumn(name = "SUBJECT_ID")
	public Subject getSubject() {
		return this.subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ANSWER_SHEET_ID")
	public AnswerSheet getAnswerSheet() {
		return this.answerSheet;
	}

	public void setAnswerSheet(AnswerSheet answerSheet) {
		this.answerSheet = answerSheet;
	}

	@Column(name = "ANSWER", length = 20)
	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

}