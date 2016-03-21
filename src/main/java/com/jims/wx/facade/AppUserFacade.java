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
        if(obj != null && obj.size() > 0){
            for (AppUser user:obj) {
                super.merge(user);
            }
        }
    }

    /**
     * 根据分组groupId查找用户
     * @param groupId
     * @return
     */
    public List<AppUser> findByGroupId(String groupId){
        String sql = "SELECT * FROM app_user WHERE 1=1";
        if(groupId != null && !groupId.equals("")){
            sql += " and group_id = '" + groupId +"'";
        }
        List<AppUser> result = super.createNativeQuery(sql, new ArrayList<Object>(), AppUser.class);
        return result;
    }
}
