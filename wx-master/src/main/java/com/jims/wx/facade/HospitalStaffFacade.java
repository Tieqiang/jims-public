package com.jims.wx.facade;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.HospitalStaff;
import com.jims.wx.vo.BeanChangeVo;

import javax.persistence.EntityManager;
import java.util.ArrayList;
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

    public List<HospitalStaff> findByOpenId(String openId) {
//        HospitalStaff
        return  entityManager.createQuery("from HospitalStaff where openId='"+openId+"'").getResultList();
    }


    @Transactional
    public HospitalStaff save(HospitalStaff hospitalStaff){
        hospitalStaff = super.merge(hospitalStaff);
        return hospitalStaff;
    }
    @Transactional
    public List<HospitalStaff> savePc(BeanChangeVo<HospitalStaff> beanChangeVo) {
        List<HospitalStaff> newUpdateDict = new ArrayList<>();
        List<HospitalStaff> inserted = beanChangeVo.getInserted();
        List<HospitalStaff> updated = beanChangeVo.getUpdated();
        List<HospitalStaff> deleted = beanChangeVo.getDeleted();
        for (HospitalStaff dict : inserted) {
            HospitalStaff merge = merge(dict);
            newUpdateDict.add(merge);
        }
        for (HospitalStaff dict : updated) {
            HospitalStaff merge = merge(dict);
            newUpdateDict.add(merge);
        }

        List<String> ids = new ArrayList<>();

        for (HospitalStaff dict : deleted) {
            ids.add(dict.getId());
            newUpdateDict.add(dict);
        }
        this.removeByStringIds(HospitalStaff.class, ids);
        return newUpdateDict;
    }


}
