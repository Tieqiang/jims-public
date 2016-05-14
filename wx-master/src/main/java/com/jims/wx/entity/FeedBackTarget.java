package com.jims.wx.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by cxy on 2016/5/9.
 */
@Entity
@Table(name = "feed_back_target", schema = "WX")
public class FeedBackTarget implements Serializable {

    private String id;//主键

    private String feedTarget;

    private String optionContent;

    private String questionContent;

    private List<String> options=new ArrayList<String>();

    public FeedBackTarget(String id, String questionContent, String feedTarget, String optionContent) {
        this.id = id;
        this.questionContent = questionContent;
        this.feedTarget = feedTarget;
        this.optionContent = optionContent;
    }
    @Column(name="option_content")
    public String getOptionContent() {

        return optionContent;
    }

    public void setOptionContent(String optionContent) {
        this.optionContent = optionContent;
    }
    @Column(name="feed_target")
    public String getFeedTarget() {
        return feedTarget;
    }

    public void setFeedTarget(String feedTarget) {
        this.feedTarget = feedTarget;
    }
    @Column(name="question_content")
    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }


    public FeedBackTarget(){

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

    @Transient
    public List<String> getOptions() {
        if(this.optionContent!=null&&!"".equals(this.optionContent)){
            String[]  optArr=this.optionContent.split(" ");
            return Arrays.asList(optArr);
        }else{
            return null;
        }
     }


}
