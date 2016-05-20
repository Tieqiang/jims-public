package com.jims.wx.facade;

import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.MedicalCardMemo;

import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * Created by chenxy on 2016/3/16.
 */
public class MedicalCardMemoFacade extends BaseFacade {
    private EntityManager entityManager;


    @Inject
    public MedicalCardMemoFacade(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * save
     *
     * @param medicalCardMemo
     */
    @Transactional
    public void save(MedicalCardMemo medicalCardMemo) {
        merge(medicalCardMemo);
    }
}
