package com.jims.wx.facade;

import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.AppUser;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjing on 2016/3/14.
 */
public class AppUserFacade extends BaseFacade {
    private EntityManager entityManager;

    @Inject
    public AppUserFacade(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * 保存关注人信息到数据库
     * @param obj
     * @return
     */
    @Transactional
    public void save(List<AppUser> obj) {
        //删除所有同步前的数据
        List<AppUser> deleteData = super.findAll(AppUser.class);
        if (deleteData != null && deleteData.size() > 0) {
            List<String> ids = new ArrayList<>();
            for (AppUser temp : deleteData) {
                ids.add(temp.getId());
            }
            super.removeByStringIds(AppUser.class, ids);
        }
        //保存新同步来的数据
        if(obj != null && obj.size() > 0){
            for (AppUser user:obj) {
                super.merge(user);
            }
        }
    }

    /**
     * 修改备注名
     * @param user
     */
    @Transactional
    public AppUser updateTip(AppUser user) {
        AppUser old = get(AppUser.class,user.getId());
        old.setRemark(user.getRemark());
        user = super.merge(old);
        return user;
    }
    /**
     * 根据分组groupId查找用户
     * @param groupId
     * @return
     */
    public List<AppUser> findByGroupId(String groupId){
        String hql = "FROM AppUser u WHERE 1=1";
        if (groupId != null && !groupId.equals("")) {
            hql += " and u.groupId = '" + groupId + "'";
        }

        List<AppUser> result = entityManager.createQuery(hql).getResultList();
        return result;
    }
}
