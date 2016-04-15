package com.jims.wx.vo;


import java.io.Serializable;
import java.util.List;

/**
 * Created by heren on 2015/11/17.
 * 用户分页
 */
public class PageEntity<T> implements Serializable{

    private long total ;
    private List<T> rows ;


    public PageEntity() {
    }

    public PageEntity(int total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
