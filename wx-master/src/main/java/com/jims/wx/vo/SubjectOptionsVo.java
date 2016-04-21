package com.jims.wx.vo;

/**
 * Created by wei on 2016/3/21.
 */
public class SubjectOptionsVo implements java.io.Serializable  {
    private String id;
    private String subjectId;
    private String optContent;
    private String optStatus;
    private String image;

    public SubjectOptionsVo() {
    }

    public SubjectOptionsVo(String id, String subjectId, String optContent, String optStatus, String image) {
        this.id = id;
        this.subjectId = subjectId;
        this.optContent = optContent;
        this.optStatus = optStatus;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getOptContent() {
        return optContent;
    }

    public void setOptContent(String optContent) {
        this.optContent = optContent;
    }

    public String getOptStatus() {
        return optStatus;
    }

    public void setOptStatus(String optStatus) {
        this.optStatus = optStatus;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
