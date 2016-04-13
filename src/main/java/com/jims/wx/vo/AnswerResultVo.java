package com.jims.wx.vo;

import java.io.Serializable;

/**
 * Created by zhu on 2016/4/8.
 */
public class AnswerResultVo implements Serializable {

    private String sheetId ;
    private String subjectId ;
    private String answer ;
    private String answerContent;
    private String subjectName;
    private String questionType;
    private String preAnswer;



    public AnswerResultVo(String sheetId, String subjectId, String answer, String answerContent, String subjectName, String questionType, String preAnswer) {
        this.sheetId = sheetId;
        this.subjectId = subjectId;
        this.answer = answer;
        this.answerContent = answerContent;
        this.subjectName=subjectName;
        this.questionType = questionType;
        this.preAnswer=preAnswer;
    }

    public AnswerResultVo() {
    }

    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getPreAnswer() {
        return preAnswer;
    }

    public void setPreAnswer(String preAnswer) {
        this.preAnswer = preAnswer;
    }

    public String getSheetId() {
        return sheetId;
    }

    public void setSheetId(String sheetId) {
        this.sheetId = sheetId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }
}
