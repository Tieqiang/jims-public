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

    /**
     * 根据主键查询患者的idCard
     * @param patId
     * @return
     */
    public String findIdCard(String patId) {
//        PatInfo patInfo;
        String sql="select p.idCard from PatInfo as p where p.id='"+patId+"'";
         return (String)entityManager.createQuery(sql).getSingleResult();
    }

    /**
     * 根据Id查询patInfo
     * @param patId
     * @return
     */
    public PatInfo findById(String patId) {
        String sql="from PatInfo where id='"+patId+"'";
        return (PatInfo)entityManager.createQuery(sql).getSingleResult();
    }
}
