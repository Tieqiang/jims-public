package com.jims.wx.facade;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.ClinicTypeSetting;
import com.jims.wx.vo.BeanChangeVo;
import com.jims.wx.vo.ComboboxVo;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class ClinicTypeSettingFacade extends BaseFacade {

    @Inject
    public ClinicTypeSettingFacade(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * @return
     */
    public List<ComboboxVo> findComboxData() {
        List<ComboboxVo> comboboxVolist = new ArrayList<ComboboxVo>();
        List<ClinicTypeSetting> list = entityManager.createQuery("from ClinicTypeSetting").getResultList();
        for (int i = 0; i < list.size(); i++) {
            ClinicTypeSetting c = list.get(i);
            ComboboxVo v = new ComboboxVo();
            v.setId(c.getId());
            v.setText(c.getClinicType());
            comboboxVolist.add(v);
        }
        return comboboxVolist;

    }

    //find by typeId
    public List<ClinicTypeSetting> findById(String id) {
        String hql = "from ClinicTypeSetting where id='" + id + "'";
        return entityManager.createQuery(hql).getResultList();
    }

    /**
     * 保存增删改
     *
     * @param beanChangeVo
     */
    @Transactional
    public List<ClinicTypeSetting> save(BeanChangeVo<ClinicTypeSetting> beanChangeVo) {
        List<ClinicTypeSetting> newUpdateDict = new ArrayList<>();
        List<ClinicTypeSetting> inserted = beanChangeVo.getInserted();
        List<ClinicTypeSetting> updated = beanChangeVo.getUpdated();
        List<ClinicTypeSetting> deleted = beanChangeVo.getDeleted();
        for (ClinicTypeSetting dict : inserted) {
            ClinicTypeSetting merge = merge(dict);
            newUpdateDict.add(merge);
        }

        for (ClinicTypeSetting dict : updated) {
            ClinicTypeSetting merge = merge(dict);
            newUpdateDict.add(merge);
        }

        List<String> ids = new ArrayList<>();

        for (ClinicTypeSetting dict : deleted) {
            ids.add(dict.getId());
            newUpdateDict.add(dict);
        }
        this.removeByStringIds(ClinicTypeSetting.class, ids);
        return newUpdateDict;
    }
}
