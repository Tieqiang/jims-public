package com.jims.wx.facade;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.ClinicIndex;


import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjing on 2016/3/21.
 */
public class ClinicIndexFacade extends BaseFacade {
    private EntityManager entityManager;

    @Inject
    public ClinicIndexFacade(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * 保存对象内容
     * @param saveData
     * @return
     */
    public List<ClinicIndex> save(List<ClinicIndex> saveData) {
        List<ClinicIndex> newUpdateDict = new ArrayList<>();
        if (saveData.size() > 0) {
            for (ClinicIndex obj : saveData) {
                ClinicIndex merge = merge(obj);
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
    public List<ClinicIndex> update(List<ClinicIndex> updateData) {

        List<ClinicIndex> newUpdateDict = new ArrayList<>();
        if (updateData.size() > 0) {
            for (ClinicIndex obj : updateData) {
                ClinicIndex merge = merge(obj);
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
    public List<ClinicIndex> delete(List<ClinicIndex> deleteData) {

        List<ClinicIndex> newUpdateDict = new ArrayList<>();
        if (deleteData.size() > 0) {
            List<String> ids = new ArrayList<>();
            for (ClinicIndex obj : deleteData) {
                ids.add(obj.getId());
            }
            super.removeByStringIds(ClinicIndex.class, ids);
            newUpdateDict.addAll(deleteData);
        }
        return newUpdateDict;
    }
}
