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
 * Subject entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SUBJECT", schema = "WX")
public class Subject implements java.io.Serializable {

	// Fields

	private String id;
	private String questionContent;
	private String questionType;
	private String preAnswer;
	//private Set<AnswerResult> answerResults = new HashSet<AnswerResult>(0);
	//private Set<QuestionnaireVsSubject> questionnaireVsSubjects = new HashSet<QuestionnaireVsSubject>(
	//		0);
	//private Set<SubjectOptions> subjectOptionses = new HashSet<SubjectOptions>(
	//		0);

	// Constructors

	/** default constructor */
	public Subject() {
	}

	/** full constructor */
	public Subject(String questionContent, String questionType,
			String preAnswer) {
		this.questionContent = questionContent;
		this.questionType = questionType;
		this.preAnswer = preAnswer;
	}

    //public Subject(String questionContent, String questionType,
    //               String preAnswer, Set<AnswerResult> answerResults,
    //               Set<QuestionnaireVsSubject> questionnaireVsSubjects,
    //               Set<SubjectOptions> subjectOptionses) {
    //    this.questionContent = questionContent;
    //    this.questionType = questionType;
    //    this.preAnswer = preAnswer;
    //    this.answerResults = answerResults;
    //    this.questionnaireVsSubjects = questionnaireVsSubjects;
    //    this.subjectOptionses = subjectOptionses;
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

	@Column(name = "QUESTION_CONTENT", length = 500)
	public String getQuestionContent() {
		return this.questionContent;
	}

	public void setQuestionContent(String questionContent) {
		this.questionContent = questionContent;
	}

	@Column(name = "QUESTION_TYPE", length = 2)
	public String getQuestionType() {
		return this.questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	@Column(name = "PRE_ANSWER", length = 20)
	public String getPreAnswer() {
		return this.preAnswer;
	}

	public void setPreAnswer(String preAnswer) {
		this.preAnswer = preAnswer;
	}

	//@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "subject")
	//public Set<AnswerResult> getAnswerResults() {
	//	return this.answerResults;
	//}
    //
	//public void setAnswerResults(Set<AnswerResult> answerResults) {
	//	this.answerResults = answerResults;
	//}
    //
	//@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "subject")
	//public Set<QuestionnaireVsSubject> getQuestionnaireVsSubjects() {
	//	return this.questionnaireVsSubjects;
	//}
    //
	//public void setQuestionnaireVsSubjects(
	//		Set<QuestionnaireVsSubject> questionnaireVsSubjects) {
	//	this.questionnaireVsSubjects = questionnaireVsSubjects;
	//}
    //
	//@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "subject")
	//public Set<SubjectOptions> getSubjectOptionses() {
	//	return this.subjectOptionses;
	//}
    //
	//public void setSubjectOptionses(Set<SubjectOptions> subjectOptionses) {
	//	this.subjectOptionses = subjectOptionses;
	//}

}