package com.jims.wx.facade;

import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.AnswerResult;
import com.jims.wx.entity.AnswerSheet;
import com.jims.wx.entity.SubjectOptions;
import com.jims.wx.vo.AnswerResultVo;
import com.jims.wx.vo.AnswerSheetVo;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhu on 2016/3/16.
 */
public class AnswerSheetFacade extends BaseFacade {
    private EntityManager entityManager;
    private SubjectFacade subjectFacade;

    @Inject
    public AnswerSheetFacade(EntityManager entityManager, SubjectFacade subjectFacade) {
        this.entityManager = entityManager;
        this.subjectFacade = subjectFacade;
    }

    //find by modelId
    public List<AnswerSheet> findByModelId(String modelId) {
        String sqls = "from AnswerSheet where 1=1";
        if (null != modelId && !modelId.trim().equals("")) {
            sqls += " and questionnaireId='" + modelId + "'";
        }
        return entityManager.createQuery(sqls).getResultList();
    }

    public List<AnswerResultVo> findById(String id) {
        List<AnswerResultVo> vos = new ArrayList<AnswerResultVo>();
        List<AnswerResult> list = entityManager.createQuery("from AnswerResult as a where a.answerSheet.id='" + id + "'").getResultList();
        if (list != null && !list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                AnswerResult ar = list.get(i);
                List<SubjectOptions> subopt = entityManager.createQuery("from SubjectOptions  where id='" + ar.getAnswer() + "'").getResultList();
                AnswerResultVo vo = new AnswerResultVo();
                vo.setSubjectId(ar.getSubject().getId());
                vo.setSubjectName(ar.getSubject().getQuestionContent());
                vo.setSheetId(id);
                vo.setQuestionType(ar.getSubject().getQuestionType());
                vo.setPreAnswer(ar.getSubject().getPreAnswer());
                vo.setAnswer(ar.getAnswer());
                vo.setAnswerContent(subopt.get(0).getOptContent());
                vo.setImg(ar.getSubject().getImg());
                vo.setImage(subopt.get(0).getImage());
                vos.add(vo);
            }
        }
        return vos;
    }

    /**
     * 保存用户答题记录
     */
    @Transactional
    public AnswerSheetVo saveAll(AnswerSheetVo answerSheetVo) {
        if (null != answerSheetVo) {
            AnswerSheet answerSheet = new AnswerSheet();
            answerSheet.setPatId(answerSheetVo.getPatId());
            answerSheet.setOpenId(answerSheetVo.getOpenId());
            answerSheet.setQuestionnaireId(answerSheetVo.getQuestionnaireId());
            answerSheet.setCreateTime(new Date());
            answerSheet = merge(answerSheet);
            if (null != answerSheetVo.getAnswerResults()) {
                for (AnswerResultVo results : answerSheetVo.getAnswerResults()) {
                    AnswerResult answerResult = new AnswerResult();
                    answerResult.setAnswer(results.getAnswer());
                    answerResult.setSubject(subjectFacade.findById(results.getSubjectId()));
                    answerResult.setAnswerSheet(answerSheet);
                    answerResult = merge(answerResult);
                }
            }
        }
        return answerSheetVo;
    }
}
