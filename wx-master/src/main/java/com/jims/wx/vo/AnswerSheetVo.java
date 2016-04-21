package com.jims.wx.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 处理问卷调查答案VO
 */
public class AnswerSheetVo implements Serializable {

    private String id;
    private String openId;
    private String patId;
    private String questionnaireId;
    private Date createTime;
    private List<AnswerResultVo> answerResults = new ArrayList<>() ;

    public AnswerSheetVo(String id, String openId, String patId, String questionnaireId, Date createTime, List<AnswerResultVo> answerResults) {
        this.id = id;
        this.openId = openId;
        this.patId = patId;
        this.questionnaireId = questionnaireId;
        this.createTime = createTime;
        this.answerResults = answerResults;
    }

    public AnswerSheetVo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getPatId() {
        return patId;
    }

    public void setPatId(String patId) {
        this.patId = patId;
    }

    public String getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(String questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<AnswerResultVo> getAnswerResults() {
        return answerResults;
    }

    public void setAnswerResults(List<AnswerResultVo> answerResults) {
        this.answerResults = answerResults;
    }


}
