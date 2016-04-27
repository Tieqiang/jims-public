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
     *
     * @param id
     * @return
     */
    public List<ClinicTypeCharge> findById(String id) {
        String sqls = "from ClinicTypeCharge c where 1=1";
        if (null != id && !id.trim().equals("")) {
            sqls += " and c.clinicTypeId='" + id.trim() + "'";
        }
        return entityManager.createQuery(sqls).getResultList();
    }

    /**
     * 保存增删改
     *
     * @param beanChangeVo
     * @return
     */
    @Transactional
    public List<ClinicTypeCharge> save(BeanChangeVo<ClinicTypeCharge> beanChangeVo) {
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
        this.removeByStringIds(ClinicTypeCharge.class, ids);
        return lists;
    }

    /**
     * 根据号类的id 查询号表的价格
     *
     * @param clinicTypeId
     * @return
     */
    public Double findPriceByClinicTypeSettingId(String clinicTypeId) {
        Double priceCount = 0.0;
        String sql = "from ClinicTypeCharge where clinicTypeId='" + clinicTypeId + "'";
        List<ClinicTypeCharge> list = entityManager.createQuery(sql).getResultList();
        for (ClinicTypeCharge c : list) {
            priceCount = priceCount + c.getPrice();
        }
        return priceCount;
    }
}
