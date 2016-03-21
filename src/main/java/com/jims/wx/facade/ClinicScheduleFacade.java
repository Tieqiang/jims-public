package com.jims.wx.facade;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.ClinicSchedule;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjing on 2016/3/21.
 */
public class ClinicScheduleFacade extends BaseFacade {
    private EntityManager entityManager;

    @Inject
    public ClinicScheduleFacade(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * 保存对象内容
     * @param saveData
     * @return
     */
    public List<ClinicSchedule> save(List<ClinicSchedule> saveData) {
        List<ClinicSchedule> newUpdateDict = new ArrayList<>();
        if (saveData.size() > 0) {
            for (ClinicSchedule obj : saveData) {
                ClinicSchedule merge = merge(obj);
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
    public List<ClinicSchedule> update(List<ClinicSchedule> updateData) {

        List<ClinicSchedule> newUpdateDict = new ArrayList<>();
        if (updateData.size() > 0) {
            for (ClinicSchedule obj : updateData) {
                ClinicSchedule merge = merge(obj);
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
    public List<ClinicSchedule> delete(List<ClinicSchedule> deleteData) {

        List<ClinicSchedule> newUpdateDict = new ArrayList<>();
        if (deleteData.size() > 0) {
            List<String> ids = new ArrayList<>();
            for (ClinicSchedule obj : deleteData) {
                ids.add(obj.getId());
            }
            super.removeByStringIds(ClinicSchedule.class, ids);
            newUpdateDict.addAll(deleteData);
        }
        return newUpdateDict;
    }
}
