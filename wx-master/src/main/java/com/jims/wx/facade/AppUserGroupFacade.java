package com.jims.wx.facade;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.AppUser;
import com.jims.wx.entity.AppUserGroups;
import weixin.popular.api.UserAPI;
import weixin.popular.bean.token.Token;
import weixin.popular.bean.user.FollowResult;
import weixin.popular.bean.user.Group;
import weixin.popular.bean.user.User;
import weixin.popular.support.TokenManager;
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
     *
     * @param group
     */
    @Transactional
    public void add(AppUserGroups group) {
        super.merge(group);
    }

    @Transactional
    public void synUserGroup(Group group, FollowResult followResult) {
        String hql = "delete from AppUserGroups ";
        entityManager.createQuery(hql).executeUpdate();
        List<Group.GroupData> groupDatas = group.getGroups();
        for (Group.GroupData groupData : groupDatas) {
            AppUserGroups groups = new AppUserGroups();
            groups.setGroupId(groupData.getId());
            groups.setCount(groupData.getCount());
            groups.setName(groupData.getName());
            merge(groups);
        }

        String[] openIds = followResult.getData().getOpenid();

        for(String id:openIds){
            User user = UserAPI.userInfo(TokenManager.getDefaultToken(),id) ;
            createUser(user);
        }
    }

    /**
     * 创建User
     * @param user
     */
    public void createUser(User user) {
        String hql = "from AppUser as user where user.openId = '"+user.getOpenid()+"'" ;
        AppUser appUser =null ;
        List<AppUser> appUsers = createQuery(AppUser.class,hql,new ArrayList<Object>()).getResultList() ;
        if(appUsers.size()>0){
            appUser = appUsers.get(0) ;
        }else{
            appUser = new AppUser() ;
        }

        appUser.setCity(user.getCity());
        appUser.setCountry(user.getCountry());
        appUser.setGroupId(user.getGroupid());
        appUser.setHeadImgUrl(user.getHeadimgurl());
        appUser.setLanguage(user.getLanguage());
        appUser.setNickName(user.getNickname());
        appUser.setOpenId(user.getOpenid());
        appUser.setProvince(user.getProvince());
        appUser.setSex(user.getSex());
        appUser.setRemark(user.getRemark());
        appUser.setSubscribe(user.getSubscribe());
        appUser.setSubscrbeTime(user.getSubscribe_time());
        merge(appUser) ;
    }

    /**
     * 用户分组管理
     * @param id
     * @param groupName
     */
    @Transactional
    public void modifyGroup(String id, String groupName) {
        String hql = "from AppUserGroups as g where g.groupId = '"+id+"'" ;
        List<AppUserGroups> appUserGroupses = createQuery(AppUserGroups.class,hql,new ArrayList<Object>()).getResultList() ;

        if(appUserGroupses.size()>0){
            AppUserGroups appUserGroups = appUserGroupses.get(0) ;
            appUserGroups.setName(groupName);
            merge(appUserGroups) ;
        }

    }

    /**
     * 删除分组
     * @param groupId
     */
    @Transactional
    public void deleteUserGroup(String groupId) {
        //删除用户组ID之前需要该组内，所有的用户归到未分组列表
        String appUserHql = "from AppUser as u where u.groupId="+groupId ;
        List<AppUser> appUsers = createQuery(AppUser.class,appUserHql,new ArrayList<Object>()).getResultList();
        for(AppUser user:appUsers){
            user.setGroupId(0);
            merge(user) ;
        }

        String delUserGroupHql = "delete from AppUserGroups as group where group.groupId="+groupId ;
        entityManager.createQuery(delUserGroupHql).executeUpdate() ;
    }

    /**
     * 保存创建分组
     * @param g
     */
    @Transactional
    public void addUserGroup(Group g) {
        Group.GroupData groupData = g.getGroup() ;
        AppUserGroups groups = new AppUserGroups();
        groups.setGroupId(groupData.getId());
        if(groupData.getCount()==null){
            groups.setCount(0);
        }else{
            groups.setCount(groupData.getCount());
        }
        groups.setName(groupData.getName());
        merge(groups);
    }
}



