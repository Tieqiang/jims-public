package com.jims.wx.util.reps;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.List;

/**
 * Created by heren on 2016/3/1.
 */
@XStreamAlias("xml")
public class ArticleMsg extends BaseMsg {
    @XStreamAlias("ArticleCount")
    private int articleCount ;
    @XStreamAlias("Articles")
    private List<Article> articles;

    public ArticleMsg(int articleCount, List<Article> articles) {
        this.articleCount = articleCount;
        this.articles = articles;
    }

    public ArticleMsg() {
    }

    public int getArticleCount() {
        return articleCount;
    }

    public void setArticleCount(int articleCount) {
        this.articleCount = articleCount;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
