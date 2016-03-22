package com.jims.wx.facade;

import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.QuestionnaireVsSubject;

import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * Created by zhu on 2016/3/18.
 */
public class QuestionnaireVsSubjectFacade extends BaseFacade{
    private EntityManager entityManager;

    @Inject
    public QuestionnaireVsSubjectFacade (EntityManager  entityManager){
        this.entityManager=entityManager;
    }

    @Transactional
    public QuestionnaireVsSubject save(QuestionnaireVsSubject questionnaireVsSubject){
        questionnaireVsSubject = super.merge(questionnaireVsSubject);
        return questionnaireVsSubject;
    }
}
