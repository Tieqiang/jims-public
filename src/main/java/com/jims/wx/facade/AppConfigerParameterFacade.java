package com.jims.wx.facade;

import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.AppConfigerParameter;


import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by txb on 2015/10/21.
 */
public class AppConfigerParameterFacade extends BaseFacade {
    private EntityManager entityManager ;

    @Inject
    public AppConfigerParameterFacade(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * 保存内容(数组)
     * @param insertData
     * @return
     */
    @Transactional
    public List<AppConfigerParameter> saveAppConfigerParameter(List<AppConfigerParameter> insertData){
        List<AppConfigerParameter> newUpdateDict = new ArrayList<>();
        if (insertData.size() > 0) {
            for(AppConfigerParameter appConfigerParameter: insertData){
                AppConfigerParameter merge = merge(appConfigerParameter);
                newUpdateDict.add(merge);
            }
        }
        return newUpdateDict;
    }

    //保存（对象）
    @Transactional
    public AppConfigerParameter saveAppConfigerParameter(AppConfigerParameter appConfigerParameter){
        AppConfigerParameter merge = merge(appConfigerParameter);
        return merge;
    }
    //修改
    @Transactional
    public List<AppConfigerParameter> updateAppConfigerParameter(List<AppConfigerParameter> updateData) {

        List<AppConfigerParameter> newUpdateDict = new ArrayList<>();
        if (updateData.size() > 0) {
            for (AppConfigerParameter dict : updateData) {
                AppConfigerParameter merge = merge(dict);
                newUpdateDict.add(merge);

            }
        }
        return newUpdateDict;
    }
    //删除(数组)
    @Transactional
    public List<AppConfigerParameter> deleteAppConfigerParameter(List<AppConfigerParameter> deleteData) {

        List<AppConfigerParameter> newUpdateDict = new ArrayList<>();
        if (deleteData.size() > 0) {
            List<String> ids = new ArrayList<>();
            for (AppConfigerParameter appConfigerParameter : deleteData) {
                ids.add(appConfigerParameter.getId());
            }
            super.removeByStringIds(AppConfigerParameter.class, ids);
            newUpdateDict.addAll(deleteData);
        }
        return newUpdateDict;
    }

    //删除（对象）
    @Transactional
    public AppConfigerParameter deleteAppConfigerParameter(AppConfigerParameter appConfigerParameter){
        remove(merge(appConfigerParameter));
        return appConfigerParameter;
    }
    //查询当前医院参数列表
    public List<AppConfigerParameter> findCurParameterList(String hospitalId, String name){
        String hql = "from AppConfigerParameter a where a.hospitalId = '" + hospitalId + "'";
        if(null != name && !name.trim().equals("")){
            hql += " and a.parameterName like '%"+name.trim()+"%'";
        }
        return entityManager.createQuery(hql).getResultList();

    }

}
