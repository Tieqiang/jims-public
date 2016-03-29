package com.jims.wx.facade;

import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.ClinicIndex;
import com.jims.wx.vo.BeanChangeVo;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class ClinicIndexFacade extends BaseFacade {
    private EntityManager entityManager;

    @Inject
    public ClinicIndexFacade(EntityManager entityManager){
        this.entityManager=entityManager;
    }

    //find by typeId
    public List<ClinicIndex> findByTypeId(String typeId){
        String sqls = "from ClinicIndex where clinicTypeId=" +"'" +typeId+ "'";
        return entityManager.createQuery(sqls).getResultList();
    }
    /**
     * 保存增删改
     *
     * @param beanChangeVo
     */
    @Transactional
    public List<ClinicIndex> save (BeanChangeVo<ClinicIndex> beanChangeVo){
        List<ClinicIndex> newUpdateSheet = new ArrayList<>();
        List<ClinicIndex> inserted =beanChangeVo.getInserted();
        List<ClinicIndex> updated =beanChangeVo.getUpdated();
        List<ClinicIndex> deleted =beanChangeVo.getDeleted();
//        inserted.
        for (ClinicIndex sheet:inserted){
            ClinicIndex merge =merge(sheet);
            newUpdateSheet.add(merge);
        }
        for (ClinicIndex sheet : updated) {
            ClinicIndex merge = merge(sheet);
            newUpdateSheet.add(merge);
        }
        List<String> ids = new ArrayList<>();
        for (ClinicIndex sheet : deleted) {
            ids.add(sheet.getId());
            newUpdateSheet.add(sheet);
        }
        this.removeByStringIds(ClinicIndex.class,ids);
        return newUpdateSheet;
    }
}