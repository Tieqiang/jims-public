package com.jims.wx.facade;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.AppUserGroups;

import javax.persistence.EntityManager;
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
     * 保存分组信息到数据库
     *
     * @param obj
     * @return
     */
    @Transactional
    public void save(List<AppUserGroups> obj) {
        if (obj != null && obj.size() > 0) {
            for (AppUserGroups group : obj) {
                super.merge(group);
            }
        }
    }
}
