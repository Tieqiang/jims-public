package com.jims.wx.entity;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * QuestionnaireVsSubject entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "QUESTIONNAIRE_VS_SUBJECT", schema = "WX")
public class QuestionnaireVsSubject implements java.io.Serializable {
    @JsonBackReference
    private QuestionnaireModel questionnaireModel;
    @JsonBackReference(value="subject")
    private Subject subject;
    private String seriaNo;

    public QuestionnaireVsSubject() {
    }

    public QuestionnaireVsSubject(QuestionnaireModel questionnaireModel, Subject subject, String seriaNo) {
        this.questionnaireModel = questionnaireModel;
        this.subject = subject;
        this.seriaNo = seriaNo;
    }
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QUESTIONNAIRE_ID")
    public QuestionnaireModel getQuestionnaireModel() {
        return questionnaireModel;
    }

    public void setQuestionnaireModel(QuestionnaireModel questionnaireModel) {
        this.questionnaireModel = questionnaireModel;
    }


    @JsonBackReference(value="subject")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUBJECT_ID")
    public Subject getSubject() {
        return this.subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    @GenericGenerator(name = "generator", strategy = "uuid.hex")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "SERIA_NO",unique = true, nullable = false, length = 64)
    public String getSeriaNo() {
        return this.seriaNo;
    }

    public void setSeriaNo(String seriaNo) {
        this.seriaNo = seriaNo;
    }

}