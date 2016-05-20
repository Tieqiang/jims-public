package com.jims.wx.facade;

import com.google.inject.persist.Transactional;

import com.jims.wx.BaseFacade;
import com.jims.wx.entity.*;
import com.jims.wx.util.PinYin2Abbreviation;
import com.jims.wx.vo.BeanChangeVo;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 系统模块维护功能
 * Created by heren on 2015/10/26.
 */
public class ModuleDictFacade extends BaseFacade {
    /**
     * 查找
     *
     * @param name
     * @return
     */
    public List<ModulDict> findAll(String name, String hospitalId) {
        String hql = "from ModulDict as dict where 1=1";
        if (name != null && name.trim().length() > 0) {
            hql += " and dict.moduleName like '%" + name.trim() + "%'";
        }
        if (null != hospitalId && !hospitalId.trim().equals("")) {
            hql += " and dict.hospitalId='" + hospitalId + "'";
        }
        Query query = entityManager.createQuery(hql);
        List resultList = query.getResultList();
        return resultList;
    }

    public List<ModulDict> findAllTabs(String loginId, String moduleId, String hospitalId) {
        //String hql = "select module_load ,menu_name module_name from Modul_Dict,menu_dict where Modul_Dict.MODULE_LOAD = menu_dict.HREF and";
        //if (name != null && name.trim().length() > 0) {
        //    hql += "  Modul_Dict.module_Name like '%" + name.trim() + "%'";
        //}
        //if(null !=hospitalId && !hospitalId.trim().equals("")){
        //    hql += " and Modul_Dict.hospital_Id='"+hospitalId+"'";
        //}
        //List<ModulDict> nativeQuery = super.createNativeQuery(hql, new ArrayList<Object>(), ModulDict.class);
        String sql = "select a.module_load, b.menu_name module_name\n" +
                "  from modul_dict a, menu_dict b, role_vs_menu c, staff_vs_role d\n" +
                " where a.module_load = b.href\n" +
                "   and b.id = c.menu_id\n" +
                "   and c.role_id = d.role_id\n" +
                "   and d.staff_id = '" + loginId + "'" +
                "   and a.id='" + moduleId + "' " +
                "   and a.hospital_id='" + hospitalId + "'";
        List<ModulDict> nativeQuery = super.createNativeQuery(sql, new ArrayList<Object>(), ModulDict.class);
        return nativeQuery;
    }

    /**
     * 根据医院编号和员工编号查询模块列表
     *
     * @param staffId
     * @param hospitalId
     * @return
     */
    public List<ModulDict> findByStaff(String staffId, String hospitalId) {
        String sql = "select a.* from Modul_Dict a,Staff_Vs_Module b where a.id=b.module_Id";
        if (staffId != null && staffId.trim().length() > 0) {
            sql += " and b.staff_Id='" + staffId.trim() + "'";
        }
        if (null != hospitalId && !hospitalId.trim().equals("")) {
            sql += " and a.hospital_Id='" + hospitalId + "'";
        }
        List<ModulDict> nativeQuery = super.createNativeQuery(sql, new ArrayList<Object>(), ModulDict.class);
        return nativeQuery;
    }

    /**
     * 保存模块儿维护信息
     *
     * @param modulDict
     */
    @Transactional
    public void save(BeanChangeVo<ModulDict> modulDict) {

        List<ModulDict> inserted = modulDict.getInserted();
        List<ModulDict> updated = modulDict.getUpdated();
        List<ModulDict> deleted = modulDict.getDeleted();

        inserted.addAll(updated);
        for (ModulDict dict : inserted) {
            dict.setInputCode(PinYin2Abbreviation.cn2py(dict.getModuleName()));
            merge(dict);
        }

        List<String> ids = new ArrayList<>();

        for (ModulDict dict : deleted) {
            ids.add(dict.getId());
        }

        if (ids.size() > 0) {
            super.removeByStringIds(ModulDict.class, ids);
        }
    }


    /**
     * 保存模块与菜单的对照关系表
     *
     * @param moduleId
     * @param menuIds
     * @return
     */
    @Transactional
    public List<ModuleVsMenu> saveModuleVsMenu(String moduleId, List<String> menuIds) {
        ModulDict modulDict = get(ModulDict.class, moduleId);
        List<ModuleVsMenu> moduleVsMenus = new ArrayList<>();

        //先删除已经分配的菜单
        Set<ModuleVsMenu> moduleVsMenus1 = modulDict.getModuleVsMenus();
        List<String> ids = new ArrayList<>();
        for (ModuleVsMenu moduleVsMenu : moduleVsMenus1) {
            remove(moduleVsMenu);
        }

        Set<ModuleVsMenu> moduleVsMenuSet = new HashSet<>();
        for (String menuId : menuIds) {
            ModuleVsMenu moduleVsMenu = new ModuleVsMenu();
            MenuDict menuDict = get(MenuDict.class, menuId);
            moduleVsMenu.setModulDict(modulDict);
            moduleVsMenu.setMenuDict(menuDict);
            moduleVsMenus.add(moduleVsMenu);
            moduleVsMenuSet.add(moduleVsMenu);
        }
        modulDict.setModuleVsMenus(moduleVsMenuSet);
        merge(modulDict);
        return moduleVsMenus;
    }

    /**
     * 保存模块与staff的对照关系表
     *
     * @param moduleId
     * @param staffIds
     * @return
     */
    @Transactional
    public List<StaffVsModule> saveStaffVsModule(String moduleId, List<String> staffIds) {
        ModulDict modulDict = get(ModulDict.class, moduleId);
        List<StaffVsModule> staffVsModules = new ArrayList<>();

        //先删除已经分配的菜单
        Set<StaffVsModule> staffVsModule1 = modulDict.getStaffVsModules();
        List<String> ids = new ArrayList<>();
        for (StaffVsModule staffVsModule : staffVsModule1) {
            remove(staffVsModule);
        }

        Set<StaffVsModule> staffVsModuleSet = new HashSet<>();
        for (String staffId : staffIds) {
            StaffVsModule staffVsModule = new StaffVsModule();
            StaffDict staffDict = get(StaffDict.class, staffId);
            staffVsModule.setModulDict(modulDict);
            staffVsModule.setStaffDict(staffDict);

            staffVsModuleSet.add(staffVsModule);
            staffVsModules.add(staffVsModule);
        }
        modulDict.setStaffVsModules(staffVsModuleSet);
        merge(modulDict);
        return staffVsModules;
    }

    /**
     * 根据模块ID查出所有的模块菜单关系数据
     *
     * @param moduleId
     * @return
     */
    public List<ModuleVsMenu> listMenusByModule(String moduleId) {
        String hql = "From ModuleVsMenu as mm where mm.modulDict.id='" + moduleId + "'";
        Query query = entityManager.createQuery(hql);
        List resultList = query.getResultList();
        return resultList;
    }
}
