package com.jims.wx.facade;

import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.PayWayDict;
import com.jims.wx.vo.BeanChangeVo;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fyg on 2016/3/21.
 */
public class PayWayDictFacade extends BaseFacade {
    private EntityManager entityManager;

    @Inject
    public PayWayDictFacade(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * 保存增删改
     *
     * @param beanChangeVo
     */
    @Transactional
    public List<PayWayDict> save(BeanChangeVo<PayWayDict> beanChangeVo) {
        List<PayWayDict> newUpdateDict = new ArrayList<>();
        List<PayWayDict> inserted = beanChangeVo.getInserted();
        List<PayWayDict> updated = beanChangeVo.getUpdated();
        List<PayWayDict> deleted = beanChangeVo.getDeleted();
        for (PayWayDict dict : inserted) {
            PayWayDict merge = merge(dict);
            newUpdateDict.add(merge);
        }

        for (PayWayDict dict : updated) {
            PayWayDict merge = merge(dict);
            newUpdateDict.add(merge);
        }

        List<String> ids = new ArrayList<>();

        for (PayWayDict dict : deleted) {
            ids.add(dict.getId());
            newUpdateDict.add(dict);
        }
        this.removeByStringIds(PayWayDict.class, ids);
        return newUpdateDict;
    }
}
