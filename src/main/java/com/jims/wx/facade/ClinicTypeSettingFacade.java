package com.jims.wx.facade;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.ClinicTypeSetting;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjing on 2016/3/21.
 */
public class ClinicTypeSettingFacade extends BaseFacade {
    private EntityManager entityManager;

    @Inject
    public ClinicTypeSettingFacade(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * 保存对象内容
     * @param saveData
     * @return
     */
    public List<ClinicTypeSetting> save(List<ClinicTypeSetting> saveData) {
        List<ClinicTypeSetting> newUpdateDict = new ArrayList<>();
        if (saveData.size() > 0) {
            for (ClinicTypeSetting obj : saveData) {
                ClinicTypeSetting merge = merge(obj);
                newUpdateDict.add(merge);
            }
        }
        return newUpdateDict;
    }

    /**
     * 修改对象内容
     *
     * @param updateData
     * @return
     */
    @Transactional
    public List<ClinicTypeSetting> update(List<ClinicTypeSetting> updateData) {

        List<ClinicTypeSetting> newUpdateDict = new ArrayList<>();
        if (updateData.size() > 0) {
            for (ClinicTypeSetting obj : updateData) {
                ClinicTypeSetting merge = merge(obj);
                newUpdateDict.add(merge);

            }
        }
        return newUpdateDict;
    }

    /**
     * 删除对象
     *
     * @param deleteData
     * @return
     */
    @Transactional
    public List<ClinicTypeSetting> delete(List<ClinicTypeSetting> deleteData) {

        List<ClinicTypeSetting> newUpdateDict = new ArrayList<>();
        if (deleteData.size() > 0) {
            List<String> ids = new ArrayList<>();
            for (ClinicTypeSetting obj : deleteData) {
                ids.add(obj.getId());
            }
            super.removeByStringIds(ClinicTypeSetting.class, ids);
            newUpdateDict.addAll(deleteData);
        }
        return newUpdateDict;
    }
}
