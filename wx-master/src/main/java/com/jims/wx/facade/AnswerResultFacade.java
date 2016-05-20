package com.jims.wx.facade;

import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.AnswerResult;

import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * Created by zhu on 2016/3/16.
 */
public class AnswerResultFacade extends BaseFacade {
    private EntityManager entityManager;

    @Inject
    public AnswerResultFacade(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public AnswerResult save(AnswerResult answerResult) {
        answerResult = super.merge(answerResult);
        return answerResult;
    }

    public AnswerResult findById(String id) {
        Object result = entityManager.createQuery("from AnswerResult where answerSheetId='" + id + "'").getSingleResult();
        if (result != null)
            return (AnswerResult) result;
        return null;

    }
}
