package com.jims.wx.facade;

import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.FeedBackResult;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by chenxy on 2016/3/16.
 */
public class FeedBackResultFacade extends BaseFacade {
    private EntityManager entityManager;


    @Inject
    public FeedBackResultFacade(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public void save(FeedBackResult f) {
        merge(f);
    }

    @Transactional
    public void removeByIds(Class<FeedBackResult> feedBackResultClass, List<String> list) {
        removeByStringIds(feedBackResultClass, list);
    }
}
