package com.jims.wx.facade;

import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.FeedBackTarget;

import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * Created by chenxy on 2016/3/16.
 */
public class FeedBackTargetFacade extends BaseFacade {
    private EntityManager entityManager;


    @Inject
    public FeedBackTargetFacade(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * @param feedTargetId
     * @return
     */
    public FeedBackTarget findByName(String feedTargetId) {
        String sql = "from FeedBackTarget where id='" + feedTargetId + "'";
        return (FeedBackTarget) entityManager.createQuery(sql).getSingleResult();
    }

    @Transactional
    public void save(FeedBackTarget feedBackTarget) {
        merge(feedBackTarget);
    }
}
