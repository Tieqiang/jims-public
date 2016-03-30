package com.jims.wx.facade;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.ClinicTypeCharge;
import com.jims.wx.entity.ClinicTypeSetting;
import com.jims.wx.vo.BeanChangeVo;

import javax.persistence.EntityManager;
import javax.ws.rs.core.Response;
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
     * 通过号类ID查询所有此号类的收费列表
     * @param id
     * @return
     */
    public List<ClinicTypeCharge> findById(String id){
        String sqls = "from ClinicTypeCharge where clinicTypeId='" + id + "'";
        return entityManager.createQuery(sqls).getResultList();
    }

    /**
     * 保存增删改
     * @param beanChangeVo
     * @return
     */
    @Transactional
    public List<ClinicTypeCharge> save(BeanChangeVo<ClinicTypeCharge> beanChangeVo){
        List<ClinicTypeCharge> lists = new ArrayList<>();
        List<ClinicTypeCharge> inserted = beanChangeVo.getInserted();   //获取增加的数据
        List<ClinicTypeCharge> updated = beanChangeVo.getUpdated();     //修改的数据
        List<ClinicTypeCharge> deleted = beanChangeVo.getDeleted();     //删除的数据

        for (ClinicTypeCharge insert : inserted) {
            ClinicTypeCharge clinicTypeCharge = merge(insert);
            lists.add(clinicTypeCharge);
        }

        for (ClinicTypeCharge update : updated) {
            ClinicTypeCharge cli = merge(update);
            lists.add(cli);
        }

        List<String> ids = new ArrayList<>();
        for (ClinicTypeCharge delete : deleted) {
            ids.add(delete.getId());
            lists.add(delete);
        }
        this.removeByStringIds(ClinicTypeCharge.class,ids);
        return lists;
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
