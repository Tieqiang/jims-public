package com.jims.wx.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by cxy on 2016/5/9.
 */
@Entity
@Table(name = "feed_back_result", schema = "WX")
public class FeedBackResult implements Serializable {

    private String id;//主键

    private String patName;

    private String feedTime;

    private String feedBackTargetId;
    private String feedBackTargetName;
    private String feedBackContent;

    public FeedBackResult(){

    }
     @GenericGenerator(name = "generator", strategy = "uuid.hex")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "id", unique = true, nullable = false, length = 64)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @Column(name="pat_name")
    public String getPatName() {
        return patName;
    }

    public void setPatName(String patName) {
        this.patName = patName;
    }
    @Column(name="feed_back_target_name")
    public String getFeedBackTargetName() {
        return feedBackTargetName;
    }

    public void setFeedBackTargetName(String feedBackTargetName) {
        this.feedBackTargetName = feedBackTargetName;
    }
    @Column(name="feed_time")
    public String getFeedTime() {
        return feedTime;
    }

    public void setFeedTime(String feedTime) {
        this.feedTime = feedTime;
    }
    @Column(name="feed_back_target_id")
    public String getFeedBackTargetId() {
        return feedBackTargetId;
    }

    public void setFeedBackTargetId(String feedBackTargetId) {
        this.feedBackTargetId = feedBackTargetId;
    }
    @Column(name="feed_back_content")
     public String getFeedBackContent() {
        return feedBackContent;
    }

    public void setFeedBackContent(String feedBackContent) {
        this.feedBackContent = feedBackContent;
    }

    public FeedBackResult(String id, String patName, String feedTime, String feedBackTargetId, String feedBackTargetName, String feedBackContent) {
        this.id = id;
        this.patName = patName;
        this.feedTime = feedTime;
        this.feedBackTargetId = feedBackTargetId;
        this.feedBackTargetName = feedBackTargetName;
        this.feedBackContent = feedBackContent;
    }
}
