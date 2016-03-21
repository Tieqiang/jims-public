package com.jims.wx.facade;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.ClinicMaster;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjing on 2016/3/21.
 */
public class ClinicMasterFacade extends BaseFacade {
    private EntityManager entityManager;

    @Inject
    public ClinicMasterFacade(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * 保存对象内容
     * @param saveData
     * @return
     */
    public List<ClinicMaster> save(List<ClinicMaster> saveData) {
        List<ClinicMaster> newUpdateDict = new ArrayList<>();
        if (saveData.size() > 0) {
            for (ClinicMaster obj : saveData) {
                ClinicMaster merge = merge(obj);
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
    public List<ClinicMaster> update(List<ClinicMaster> updateData) {

        List<ClinicMaster> newUpdateDict = new ArrayList<>();
        if (updateData.size() > 0) {
            for (ClinicMaster obj : updateData) {
                ClinicMaster merge = merge(obj);
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
    public List<ClinicMaster> delete(List<ClinicMaster> deleteData) {

        List<ClinicMaster> newUpdateDict = new ArrayList<>();
        if (deleteData.size() > 0) {
            List<String> ids = new ArrayList<>();
            for (ClinicMaster obj : deleteData) {
                ids.add(obj.getId());
            }
            super.removeByStringIds(ClinicMaster.class, ids);
            newUpdateDict.addAll(deleteData);
        }
        return newUpdateDict;
    }
}
