package com.jims.wx.facade;

import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.AnswerSheet;

import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * Created by zhu on 2016/3/16.
 */
public class AnswerSheetFacade extends BaseFacade {
    private EntityManager entityManager;

    @Inject
    public AnswerSheetFacade(EntityManager entityManager){
        this.entityManager=entityManager;
    }

    //增加
    @Transactional
    public AnswerSheet save(AnswerSheet answerSheet){
        answerSheet = super.merge(answerSheet);
        return answerSheet;
    }

}
