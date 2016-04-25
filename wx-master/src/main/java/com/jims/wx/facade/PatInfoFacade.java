package com.jims.wx.facade;

import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.AnswerResult;
import com.jims.wx.entity.PatInfo;

import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * Created by chenxy on 2016/3/16.
 */
public class PatInfoFacade extends BaseFacade {
    private EntityManager entityManager;
//    private PatVsUserFacade

    @Inject
    public PatInfoFacade(EntityManager entityManager){
        this.entityManager=entityManager;
    }

    @Transactional
    public PatInfo save(PatInfo patInfo) {
         return entityManager.merge(patInfo);
    }


}