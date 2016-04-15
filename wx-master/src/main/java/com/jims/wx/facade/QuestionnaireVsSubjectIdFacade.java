package com.jims.wx.facade;

import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.QuestionnaireVsSubjectId;

import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * Created by zhu on 2016/3/21.
 */
public class QuestionnaireVsSubjectIdFacade extends BaseFacade {
    private EntityManager entityManager;

    @Inject
    public QuestionnaireVsSubjectIdFacade (EntityManager entityManager){
        this.entityManager=entityManager;
    }

    //新增
    @Transactional
    public QuestionnaireVsSubjectId save(QuestionnaireVsSubjectId questionnaireVsSubjectId){
        questionnaireVsSubjectId=super.merge(questionnaireVsSubjectId);
        return questionnaireVsSubjectId;
    }
}
