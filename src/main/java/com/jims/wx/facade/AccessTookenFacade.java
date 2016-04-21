package com.jims.wx.facade;

import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.AccessTooken;
import com.jims.wx.vo.BeanChangeVo;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhu on 2016/4/5.
 */
public class AccessTookenFacade extends BaseFacade{
    private EntityManager entityManager;

    @Inject
    public AccessTookenFacade(EntityManager entityManager){
        this.entityManager=entityManager;
    }

    /**
     * 保存公众号信息
     * @param beanChangeVo
     * @return
     */
    @Transactional
    public List<AccessTooken> save(BeanChangeVo<AccessTooken> beanChangeVo) {
        List<AccessTooken> newUpdateDict = new ArrayList<>();
        List<AccessTooken> inserted = beanChangeVo.getInserted();
        List<AccessTooken> updated = beanChangeVo.getUpdated();
        List<AccessTooken> deleted = beanChangeVo.getDeleted();
        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time=format.format(date);
        System.out.println(time);
        for (AccessTooken dict : inserted) {
            AccessTooken merge = merge(dict);
            merge.setStartTime(time);
            newUpdateDict.add(merge);
        }

        for (AccessTooken dict : updated) {
            AccessTooken merge = merge(dict);
            merge.setStartTime(time);
            newUpdateDict.add(merge);
        }

        List<String> ids = new ArrayList<>();

        for (AccessTooken dict : deleted) {
            ids.add(dict.getId());
            newUpdateDict.add(dict);
        }
        this.removeByStringIds(AccessTooken.class, ids);
        return newUpdateDict;
    }

}
