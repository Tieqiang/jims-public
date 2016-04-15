package com.jims.wx.vo;

/**
 * Created by zhu on 2016/3/25.
 */
public class QuestionnaireVsSubjectVo implements java.io.Serializable {
    private String id;



    private String questionVsSubId;
    private String questModelId;
    private String questionContent;
    private String questionType;
    private String preAnswer;

    public QuestionnaireVsSubjectVo() {
    }

    public QuestionnaireVsSubjectVo(String id,String questionVsSubId, String questModelId, String questionContent, String questionType, String preAnswer) {
        this.id = id;
        this.questionVsSubId=questionVsSubId;
        this.questModelId = questModelId;
        this.questionContent = questionContent;
        this.questionType = questionType;
        this.preAnswer = preAnswer;
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
}
