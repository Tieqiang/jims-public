package com.jims.wx.facade;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.AppUserGroups;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjing on 2016/3/14.
 */
public class AppUserGroupFacade extends BaseFacade {
    private EntityManager entityManager;

    @Inject
    public AppUserGroupFacade(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * 同步分组信息到数据库
     *
     * @param obj
     * @return
     */
    @Transactional
    public void save(List<AppUserGroups> obj) {
        //删除所有同步前的数据
        List<AppUserGroups> deleteData = super.findAll(AppUserGroups.class);
        if (deleteData != null && deleteData.size() > 0) {
            List<String> ids = new ArrayList<>();
            for (AppUserGroups temp : deleteData) {
                ids.add(temp.getId());
            }
            super.removeByStringIds(AppUserGroups.class, ids);
        }
        //保存新同步来的数据
        if (obj != null && obj.size() > 0) {
            for (AppUserGroups group : obj) {
                super.merge(group);
            }
        }
    }

    /**
     * 保存新增分组信息
     * @param group
     */
    @Transactional
    public void add(AppUserGroups group) {
        super.merge(group);
    }
}
