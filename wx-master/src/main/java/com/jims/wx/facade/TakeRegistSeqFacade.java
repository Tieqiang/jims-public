package com.jims.wx.facade;

import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.TakeRegistSeq;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by chenxy on 2016/3/16.
 */
public class TakeRegistSeqFacade extends BaseFacade {
    private EntityManager entityManager;


    @Inject
    public TakeRegistSeqFacade(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     *
     * @param takeRegistSeq
     */
    @Transactional
    public TakeRegistSeq save(TakeRegistSeq takeRegistSeq) {
        return entityManager.merge(takeRegistSeq);
    }

    /**
     *
     * @param registTime
     * @return
     */
    public TakeRegistSeq findByDate(String registTime) {
            List<TakeRegistSeq> list=entityManager.createQuery("from TakeRegistSeq where time like '%"+registTime+"%'").getResultList();
            if(!list.isEmpty()){
                return list.get(0);
            }else{
                return null;
            }
     }
}
