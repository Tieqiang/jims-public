package com.jims.wx.vo;

import java.io.Serializable;

/**
 * Created by zhu on 2016/4/8.
 */
public class AnswerResultVo implements Serializable {

    //答题记录ID
    private String sheetId;
    //问题ID
    private String subjectId;
    //答题选项的ID
    private String answer;
    //选项的内容
    private String answerContent;
    //题目的名字
    private String subjectName;
    //答题类型（0单选1多选）
    private String questionType;
    //预选答案
    private String preAnswer;

    private String img;

    private String image;


    public AnswerResultVo(String sheetId, String subjectId, String answer,
                          String answerContent, String subjectName, String questionType,
                          String preAnswer, String img, String image) {
        this.sheetId = sheetId;
        this.subjectId = subjectId;
        this.answer = answer;
        this.answerContent = answerContent;
        this.subjectName = subjectName;
        this.questionType = questionType;
        this.preAnswer = preAnswer;
        this.img = img;
        this.image = image;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
