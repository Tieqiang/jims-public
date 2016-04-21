package com.jims.wx.entity;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * AnswerResult entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ANSWER_RESULT", schema = "WX")
public class AnswerResult implements java.io.Serializable {

    // Fields
    private String id;
    @JsonBackReference
    private Subject subject;
    private AnswerSheet answerSheet;
    private String answer;
    @Transient
    private String subjectId;

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

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "SUBJECT_ID")
    public Subject getSubject() {
        return this.subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    @ManyToOne
    @JoinColumn(name = "ANSWER_SHEET_ID")
    public AnswerSheet getAnswerSheet() {
        return this.answerSheet;
    }

    public void setAnswerSheet(AnswerSheet answerSheet) {
        this.answerSheet = answerSheet;
    }

    @Column(name = "ANSWER", length = 64)
    public String getAnswer() {
        return this.answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
    @Transient
    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }
}