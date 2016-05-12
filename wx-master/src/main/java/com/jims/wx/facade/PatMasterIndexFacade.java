package com.jims.wx.facade;

import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.PatMasterIndex;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;

/**
 * Created by chenxy on 2016/3/16.
 */
public class PatMasterIndexFacade extends BaseFacade {
    private EntityManager entityManager;


    @Inject
    public PatMasterIndexFacade(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     *
     * @param idCard
     * @return
     */
    public String checkIdCard(String idCard) {
         String sql="from PatMasterIndex where idNo='"+idCard+"'";
        List<PatMasterIndex> list=entityManager.createQuery(sql).getResultList();
        if(!list.isEmpty())
            return list.get(0).getPatientId();
            return "";
    }

    /**
     * save data
     * @param patMasterIndex
     */
    @Transactional
    public void save(PatMasterIndex patMasterIndex) {
         merge(patMasterIndex);
    }
}
