package com.jims.wx.facade;

import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.MenuDict;
import com.jims.wx.entity.RoleDict;
import com.jims.wx.entity.RoleVsMenu;


import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by heren on 2015/9/16.
 */
public class RoleDictFacade extends BaseFacade {
    /**
     * 名称模糊查询
     * @param name
     * @return
     */
    public List<RoleDict> findAll(String name) {
        String hql = " from RoleDict as dict where 1=1";
        if (name != null && name.trim().length() > 0) {
            hql += " and dict.roleName like '%" + name.trim() + "%'";
        }
        Query query = entityManager.createQuery(hql);
        List resultList = query.getResultList();
        return resultList;
    }
    /**
     * 保存、新增、删除、修改的内容
     * @param insertData
     * @param updateData
     * @param deleteData
     * @return
     */
    @Transactional
    public List<RoleDict> saveRoleDict(List<RoleDict> insertData, List<RoleDict> updateData, List<RoleDict> deleteData) {

        List<RoleDict> newUpdateDict = new ArrayList<>() ;

        if(insertData.size() >0 ){
            for (RoleDict dict:insertData){
                RoleDict merge = merge(dict);
                newUpdateDict.add(merge) ;
            }
        }
        if(updateData.size()>0){
            for (RoleDict dict:updateData){
                RoleDict merge = merge(dict);
                newUpdateDict.add(merge) ;

            }
        }
        if(deleteData.size()>0){
            List<String> ids = new ArrayList<>();
            for (RoleDict dict:deleteData){
                ids.add(dict.getId()) ;
            }
            super.removeByStringIds(RoleDict.class,ids);
            newUpdateDict.addAll(deleteData) ;
        }
        return newUpdateDict;
    }

    /**
     * 分配角色的菜单
     * @param roleId
     * @param menuId
     * @return
     */
    @Transactional
    public RoleDict saveRoleVsMenus(String roleId, String[] menuId) {

        //1,删除此角色已经分配的菜单
        RoleDict roleDict = get(RoleDict.class,roleId) ;
        Set<RoleVsMenu> roleVsMenus = roleDict.getRoleVsMenus() ;
        List<String> roleVsMenuIds = new ArrayList<>() ;
        for(RoleVsMenu menu:roleVsMenus){
            roleVsMenuIds.add(menu.getId()) ;
        }
        removeByStringIds(RoleVsMenu.class, roleVsMenuIds);

        //2,重新分配菜单
        Set<RoleVsMenu> updateRoleVsMenu= new HashSet<>()  ;
        for (String id:menuId){
            MenuDict menuDict = get(MenuDict.class,id) ;
            RoleVsMenu roleVsMenu = new RoleVsMenu() ;
            roleVsMenu.setMenuDict(menuDict);
            roleVsMenu.setRoleDict(roleDict);
            updateRoleVsMenu.add(roleVsMenu) ;
        }
        roleDict.setRoleVsMenus(updateRoleVsMenu);
        RoleDict merge = merge(roleDict);
        return merge;
    }

    /**
     * 查询role对应的menu
     * @param roleId
     * @return
     */
    public List<RoleVsMenu> listMenusByRole(String roleId) {
        String hql = "From RoleVsMenu as rm where rm.roleDict.id='" + roleId + "'";
        Query query = entityManager.createQuery(hql);
        List resultList = query.getResultList();
        return resultList;
    }
}
