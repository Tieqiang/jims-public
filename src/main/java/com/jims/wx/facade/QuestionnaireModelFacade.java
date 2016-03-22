package com.jims.wx.facade;

import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.QuestionnaireModel;
import com.jims.wx.entity.QuestionnaireVsSubject;
import com.jims.wx.entity.Subject;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by zhu on 2016/3/17.
 */
public class QuestionnaireModelFacade extends BaseFacade {
    private EntityManager entityManager;

    @Inject
    public QuestionnaireModelFacade (EntityManager entityManager){
        this.entityManager=entityManager;
    }

    //find-id
    public QuestionnaireModel getById(String id){
        QuestionnaireModel questionnaireModel=get(QuestionnaireModel.class,id);
        return questionnaireModel;
    }

    //新增
    @Transactional
    public void save (QuestionnaireModel questionnaireModel){
        String ids = questionnaireModel.getSubIds();
        questionnaireModel = merge(questionnaireModel);
        if(ids!=null && ids.length()>0){
            String[] idsArray = ids.split(";");
            System.out.print(idsArray.length);
            Set<QuestionnaireVsSubject> modelVsSub = new HashSet<QuestionnaireVsSubject>();
            if(idsArray.length > 0){
                QuestionnaireVsSubject questionnaireVsSubject;
                for(String id:idsArray){
                    System.out.print(id);
                    questionnaireVsSubject = new QuestionnaireVsSubject();

                    QuestionnaireModel model = new QuestionnaireModel();
                    model.setId(questionnaireModel.getId());
                    questionnaireVsSubject.setQuestionnaireModel(model);

                    Subject sub = new Subject();
                    sub.setId(id);
                    questionnaireVsSubject.setSubject(sub);

                    modelVsSub.add(questionnaireVsSubject);

                }
                questionnaireModel.setQuestionnaireVsSubjects(modelVsSub);
            }
        }

        merge(questionnaireModel);
    }
}
