package com.jims.wx.facade;

import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.QuestionnaireModel;
import com.jims.wx.entity.QuestionnaireVsSubject;
import com.jims.wx.entity.Subject;
import com.jims.wx.entity.SubjectOptions;
import com.jims.wx.vo.QuestionnaireVsSubjectVo;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.*;

/**
 * Created by zhu on 2016/3/17.
 */
public class QuestionnaireModelFacade extends BaseFacade {
    private EntityManager entityManager;

    @Inject
    public QuestionnaireModelFacade (EntityManager entityManager){
        this.entityManager=entityManager;
    }

    //新增
    @Transactional
    public QuestionnaireModel save(QuestionnaireModel questionnaireModel){
        //对象有ID修改,否则新增
        if(questionnaireModel.getId() != null && !questionnaireModel.getId().trim().equals("")){
            //首先删除此题目多有的选项
            QuestionnaireModel old = get(QuestionnaireModel.class, questionnaireModel.getId());
            Set<QuestionnaireVsSubject> deleteData = old.getQuestionnaireVsSubjects();
            if(deleteData != null && deleteData.size() > 0){
                List<String> ids = new ArrayList<>();
                for (QuestionnaireVsSubject obj : deleteData) {
                    ids.add(obj.getSeriaNo());
                }
                super.removeByStringIds(QuestionnaireVsSubject.class, ids);
            }
        }
        if(null != questionnaireModel && !questionnaireModel.getSubIds().trim().equals("")){
            String subIds = questionnaireModel.getSubIds();
            System.out.println(subIds);
            String[] idsArray = subIds.split(";");

            if(idsArray.length > 0){
                questionnaireModel.setTotalNumbers(new Double(idsArray.length));
                questionnaireModel = merge(questionnaireModel);
                Set<QuestionnaireVsSubject> modelVsSub = new HashSet<QuestionnaireVsSubject>();

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

        questionnaireModel=super.merge(questionnaireModel);
        return questionnaireModel;
    }

    @Transactional
    public void delQuestionnaireModel(String modelId){
        if(null!=modelId){
            QuestionnaireModel questionnaireModel=get(QuestionnaireModel.class,modelId);
            Set<QuestionnaireVsSubject>deleteData =questionnaireModel.getQuestionnaireVsSubjects();
            List<String>qvsIds=new ArrayList<>();
            for(QuestionnaireVsSubject qvs:deleteData){
                qvsIds.add(qvs.getSeriaNo());
            }
            super.removeByStringIds(QuestionnaireVsSubject.class,qvsIds);
            super.remove(questionnaireModel);
        }
    }

    /**
     * 通过问题ID获取问题及其选项
     * @param id
     * @return
     */
    public QuestionnaireModel getById(String id){
        //通过主键获取问题
        QuestionnaireModel obj = get(QuestionnaireModel.class, id);

        //把问题转换成自定义VO的集合
        List<QuestionnaireVsSubjectVo> voList = new ArrayList<QuestionnaireVsSubjectVo>();
        Set<QuestionnaireVsSubject> vsSub = obj.getQuestionnaireVsSubjects();
        if(vsSub != null && vsSub.size() > 0){
            QuestionnaireVsSubjectVo vo;
            Iterator ite = vsSub.iterator();
            while(ite.hasNext()){
                QuestionnaireVsSubject temp = (QuestionnaireVsSubject)ite.next();
                Subject subject = temp.getSubject() ;
                String hql = "from SubjectOptions as op where op.subject.id ='"+subject.getId()+"'" ;
                List<SubjectOptions> optionses = createQuery(SubjectOptions.class,hql,new ArrayList<Object>()).getResultList() ;

                vo = new QuestionnaireVsSubjectVo();
                vo.setSubjectOptionses(optionses);
                vo.setQuestionVsSubId(temp.getSeriaNo());
                vo.setQuestModelId(obj.getId());
                vo.setQuestionType(temp.getSubject().getQuestionType());
                vo.setQuestionContent(temp.getSubject().getQuestionContent());
                vo.setPreAnswer(temp.getSubject().getPreAnswer());
                vo.setId(temp.getSubject().getId());
                vo.setPicture(temp.getSubject().getImg());
                voList.add(vo);
            }
            obj.setQuestionnaireVsSubjectVo(voList);
        }
        return obj;
    }
}

