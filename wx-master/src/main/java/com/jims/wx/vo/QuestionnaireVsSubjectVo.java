package com.jims.wx.vo;

import com.jims.wx.entity.SubjectOptions;

import java.util.List;

/**
 * Created by zhu on 2016/3/25.
 */
public class QuestionnaireVsSubjectVo implements java.io.Serializable {
    private String id;
    private String questionVsSubId;
    private String questModelId;//所属问卷
    private String questionContent;//题干
    private String questionType;//题目类型
    private String preAnswer;//默认答案
    private String picture;//题干图片

    private List<SubjectOptions> subjectOptionses ;

    public QuestionnaireVsSubjectVo() {
    }

    public QuestionnaireVsSubjectVo(String id, String questionVsSubId, String questModelId, String questionContent, String questionType, String preAnswer, String picture, List<SubjectOptions> subjectOptionses) {
        this.id = id;
        this.questionVsSubId = questionVsSubId;
        this.questModelId = questModelId;
        this.questionContent = questionContent;
        this.questionType = questionType;
        this.preAnswer = preAnswer;
        this.picture = picture;
        this.subjectOptionses = subjectOptionses;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getPreAnswer() {
        return preAnswer;
    }

    public void setPreAnswer(String preAnswer) {
        this.preAnswer = preAnswer;
    }

    public String getQuestionVsSubId() {
        return questionVsSubId;
    }

    public void setQuestionVsSubId(String questionVsSubId) {
        this.questionVsSubId = questionVsSubId;
    }

    public String getQuestModelId() {
        return questModelId;
    }

    public void setQuestModelId(String questModelId) {
        this.questModelId = questModelId;
    }

    public List<SubjectOptions> getSubjectOptionses() {
        return subjectOptionses;
    }

    public void setSubjectOptionses(List<SubjectOptions> subjectOptionses) {
        this.subjectOptionses = subjectOptionses;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
