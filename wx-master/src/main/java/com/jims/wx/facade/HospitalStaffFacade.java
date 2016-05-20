package com.jims.wx.facade;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.HospitalStaff;
import com.jims.wx.vo.BeanChangeVo;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wei on 2016/4/26.
 */
public class HospitalStaffFacade extends BaseFacade {

    private EntityManager entityManager;

    @Inject
    public HospitalStaffFacade(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Map<String, Object> findById(String personId, String openId) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<HospitalStaff> list = entityManager.createQuery("from HospitalStaff where personId='" + personId + "'").getResultList();
        if (list != null && !list.isEmpty()) {
            String openIdStr = list.get(0).getOpenId();
            if (openIdStr.equals(openId)) {//已经绑定过
                map.put("message", "您已经绑定！");
                map.put("success", false);
            } else if (openIdStr != null && !"".equals(openIdStr) && !openIdStr.equals(openId)) {
                map.put("message", "已被别人绑定！");
                map.put("success", false);
            } else {
                map.put("success", true);
            }
        } else {
            map.put("message", "员工idCard不存在！");
            map.put("success", false);
        }
        return map;
    }

    public List<HospitalStaff> findByOpenId(String openId) {
        return entityManager.createQuery("from HospitalStaff where openId='" + openId + "'").getResultList();
    }


    @Transactional
    public HospitalStaff save(HospitalStaff hospitalStaff) {
        hospitalStaff = super.merge(hospitalStaff);
        return hospitalStaff;
    }

    //根据主键id查询
    public List<HospitalStaff> getById(String id) {
        return entityManager.createQuery("from HospitalStaff where id='" + id + "'").getResultList();
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
