package com.jims.wx.facade;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.ClinicTypeSetting;
import com.jims.wx.entity.WxOpenAccountConfig;
import com.jims.wx.vo.BeanChangeVo;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fyg on 2016/3/31.
 */
public class WxOpenAccountConfigFacade extends BaseFacade{
    private EntityManager entityManager;
    @Inject
    public WxOpenAccountConfigFacade(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * 根据医院ID查询该医院的公众号
     * @param hospitalId
     * @return
     */
    public List<WxOpenAccountConfig> findById(String hospitalId){
        String sql = "from WxOpenAccountConfig w where 1=1";
        if(null != hospitalId && !hospitalId.trim().equals("")){
            sql += " and w.hospitalId='" + hospitalId.trim() + "'";
        }
        return entityManager.createQuery(sql).getResultList();
    }

    /**
     * 保存公众号信息
     * @param beanChangeVo
     * @return
     */
    @Transactional
    public List<WxOpenAccountConfig> save(BeanChangeVo<WxOpenAccountConfig> beanChangeVo) {
        List<WxOpenAccountConfig> newUpdateDict = new ArrayList<>();
        List<WxOpenAccountConfig> inserted = beanChangeVo.getInserted();
        List<WxOpenAccountConfig> updated = beanChangeVo.getUpdated();
        List<WxOpenAccountConfig> deleted = beanChangeVo.getDeleted();
        for (WxOpenAccountConfig dict : inserted) {
            WxOpenAccountConfig merge = merge(dict);
            newUpdateDict.add(merge);
        }

        for (WxOpenAccountConfig dict : updated) {
            WxOpenAccountConfig merge = merge(dict);
            newUpdateDict.add(merge);
        }

        List<String> ids = new ArrayList<>();

        for (WxOpenAccountConfig dict : deleted) {
            ids.add(dict.getId());
            newUpdateDict.add(dict);
        }
        this.removeByStringIds(WxOpenAccountConfig.class, ids);
        return newUpdateDict;
    }
}
