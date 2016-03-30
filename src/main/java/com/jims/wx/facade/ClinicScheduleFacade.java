package com.jims.wx.facade;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.ClinicSchedule;
import com.jims.wx.vo.BeanChangeVo;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjing on 2016/3/21.
 */
public class ClinicScheduleFacade extends BaseFacade {
    @Inject
    public ClinicScheduleFacade(EntityManager entityManager){
        this.entityManager=entityManager;
    }

    //find by typeId
    public List<ClinicSchedule> findByTypeId(String id){
        String sqls = "from ClinicSchedule where clinicIndexId=" +"'" +id+ "'";
        return entityManager.createQuery(sqls).getResultList();
    }

    /**
     * 保存增删改
     *
     * @param beanChangeVo
     */
    @Transactional
    public List<ClinicSchedule> save(BeanChangeVo<ClinicSchedule> beanChangeVo) {
        List<ClinicSchedule> newUpdateDict = new ArrayList<>();
        List<ClinicSchedule> inserted = beanChangeVo.getInserted();
        List<ClinicSchedule> updated = beanChangeVo.getUpdated();
        List<ClinicSchedule> deleted = beanChangeVo.getDeleted();
        for (ClinicSchedule dict : inserted) {
            ClinicSchedule merge = merge(dict);
            newUpdateDict.add(merge);
        }

        for (ClinicSchedule dict : updated) {
            ClinicSchedule merge = merge(dict);
            newUpdateDict.add(merge);
        }

        List<String> ids = new ArrayList<>();

        for (ClinicSchedule dict : deleted) {
            ids.add(dict.getId());
            newUpdateDict.add(dict);
        }
        this.removeByStringIds(ClinicSchedule.class, ids);
        return newUpdateDict;
    }
}
