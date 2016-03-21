package com.jims.wx.facade;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.ClinicTypeCharge;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjing on 2016/3/21.
 */
public class ClinicTypeChargeFacade extends BaseFacade {
    private EntityManager entityManager;

    @Inject
    public ClinicTypeChargeFacade(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * 保存对象内容
     * @param saveData
     * @return
     */
    public List<ClinicTypeCharge> save(List<ClinicTypeCharge> saveData) {
        List<ClinicTypeCharge> newUpdateDict = new ArrayList<>();
        if (saveData.size() > 0) {
            for (ClinicTypeCharge obj : saveData) {
                ClinicTypeCharge merge = merge(obj);
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
    public List<ClinicTypeCharge> update(List<ClinicTypeCharge> updateData) {

        List<ClinicTypeCharge> newUpdateDict = new ArrayList<>();
        if (updateData.size() > 0) {
            for (ClinicTypeCharge obj : updateData) {
                ClinicTypeCharge merge = merge(obj);
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
    public List<ClinicTypeCharge> delete(List<ClinicTypeCharge> deleteData) {

        List<ClinicTypeCharge> newUpdateDict = new ArrayList<>();
        if (deleteData.size() > 0) {
            List<String> ids = new ArrayList<>();
            for (ClinicTypeCharge obj : deleteData) {
                ids.add(obj.getId());
            }
            super.removeByStringIds(ClinicTypeCharge.class, ids);
            newUpdateDict.addAll(deleteData);
        }
        return newUpdateDict;
    }
}
