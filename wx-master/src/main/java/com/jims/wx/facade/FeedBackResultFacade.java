package com.jims.wx.facade;

import com.jims.wx.BaseFacade;

import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * Created by chenxy on 2016/3/16.
 */
public class FeedBackResultFacade extends BaseFacade {
    private EntityManager entityManager;


    @Inject
    public FeedBackResultFacade(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

   
}
