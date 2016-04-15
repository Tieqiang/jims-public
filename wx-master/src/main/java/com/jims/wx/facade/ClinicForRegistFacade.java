package com.jims.wx.facade;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.ClinicForRegist;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjing on 2016/3/21.
 */
public class ClinicForRegistFacade extends BaseFacade {
    private EntityManager entityManager;

    @Inject
    public ClinicForRegistFacade(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * 保存对象内容
     *
     * @param saveData
     * @return
     */
    public List<ClinicForRegist> save(List<ClinicForRegist> saveData) {
        List<ClinicForRegist> newUpdateDict = new ArrayList<>();
        if (saveData.size() > 0) {
            for (ClinicForRegist obj : saveData) {
                ClinicForRegist merge = merge(obj);
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
    public List<ClinicForRegist> update(List<ClinicForRegist> updateData) {

        List<ClinicForRegist> newUpdateDict = new ArrayList<>();
        if (updateData.size() > 0) {
            for (ClinicForRegist obj : updateData) {
                ClinicForRegist merge = merge(obj);
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
    public List<ClinicForRegist> delete(List<ClinicForRegist> deleteData) {

        List<ClinicForRegist> newUpdateDict = new ArrayList<>();
        if (deleteData.size() > 0) {
            List<String> ids = new ArrayList<>();
            for (ClinicForRegist obj : deleteData) {
                ids.add(obj.getId());
            }
            super.removeByStringIds(ClinicForRegist.class, ids);
            newUpdateDict.addAll(deleteData);
        }
        return newUpdateDict;
    }
}
