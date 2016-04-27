package com.jims.wx.facade;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.HospitalStaff;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by wei on 2016/4/26.
 */
public class HospitalStaffFacade extends BaseFacade {

    private EntityManager entityManager;
    @Inject
    public HospitalStaffFacade(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<HospitalStaff> findById(String personId) {
        return  entityManager.createQuery("from HospitalStaff where personId='"+personId+"'").getResultList();
    }

    @Transactional
    public HospitalStaff save(HospitalStaff hospitalStaff){
        hospitalStaff = super.merge(hospitalStaff);
        return hospitalStaff;
    }


}
