package com.jims.wx.facade;

import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.AppConfigerBaseinfo;


import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by txb on 2015/10/21.
 */
public class AppConfigerBaseinfoFacade extends BaseFacade {
    private EntityManager entityManager ;

    @Inject
    public AppConfigerBaseinfoFacade(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * 名称模糊查询
     * @param name
     * @return
     */
    public List<AppConfigerBaseinfo> findAll(String name) {
        String hql = "from AppConfigerBaseinfo a where 1=1";
        if (null != name && !name.trim().equals("")) {
            hql += " and a.appName like '%" + name.trim() + "%'";
        }
        return entityManager.createQuery(hql).getResultList();

    }
    /**
     * 保存内容(数组)
     * @param appConfigerBaseinfoList
     * @return
     */
    @Transactional
    public List<AppConfigerBaseinfo> saveAppConfigerBaseinfo(List<AppConfigerBaseinfo> appConfigerBaseinfoList){
        List<AppConfigerBaseinfo> newUpdateDict = new ArrayList<>();
        if (appConfigerBaseinfoList!=null&&appConfigerBaseinfoList.size() > 0) {
            for(AppConfigerBaseinfo appConfigerBaseinfo: appConfigerBaseinfoList){
                AppConfigerBaseinfo merge = merge(appConfigerBaseinfo);
                newUpdateDict.add(merge);
            }
        }
        return newUpdateDict;
    }
    /**
     * 保存内容(对象)
     * @param appConfigerBaseinfo
     * @return
     */
    @Transactional
    public AppConfigerBaseinfo saveAppConfigerBaseinfo(AppConfigerBaseinfo appConfigerBaseinfo){
                AppConfigerBaseinfo merge = merge(appConfigerBaseinfo);
        return merge;
    }
    //修改
    @Transactional
    public List<AppConfigerBaseinfo> updateAppConfigerBaseinfo(List<AppConfigerBaseinfo> updateData) {

        List<AppConfigerBaseinfo> newUpdateDict = new ArrayList<>();
        if (updateData!=null&&updateData.size() > 0) {
            for (AppConfigerBaseinfo appConfigerBaseinfo : updateData) {
                AppConfigerBaseinfo merge = merge(appConfigerBaseinfo);
                newUpdateDict.add(merge);

            }
        }
        return newUpdateDict;
    }
    //删除(列表)
    @Transactional
    public List<AppConfigerBaseinfo> deleteAppConfigerBaseinfo(List<AppConfigerBaseinfo> deleteData) {

        List<AppConfigerBaseinfo> newUpdateDict = new ArrayList<>();
        if (deleteData!=null&&deleteData.size() > 0) {
            List<String> ids = new ArrayList<>();
            for (AppConfigerBaseinfo appConfigerBaseinfo : deleteData) {
                ids.add(appConfigerBaseinfo.getId());
            }
            super.removeByStringIds(AppConfigerBaseinfo.class, ids);
            newUpdateDict.addAll(deleteData);
        }
        return newUpdateDict;
    }
    //删除(对象)
    @Transactional
    public AppConfigerBaseinfo deleteAppConfigerBaseinfo(AppConfigerBaseinfo appConfigerBaseinfo) {
        remove(merge(appConfigerBaseinfo));
        return appConfigerBaseinfo;
    }

    //查询列表
    public BigDecimal findMaxParameterNo() {
        return (BigDecimal)entityManager.createNativeQuery("select max(parameter_no) from app_configer_baseinfo").getResultList().get(0);
    }
}
