package com.jims.wx.facade;

import com.google.inject.persist.Transactional;

import com.jims.wx.BaseFacade;
import com.jims.wx.entity.MenuDict;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by heren on 2015/9/11.
 */
public class MenuDictFacade extends BaseFacade {
    private final Logger LOGGER = LoggerFactory.getLogger(HospitalDictFacade.class);
    private EntityManager entityManager;

    @Inject
    public MenuDictFacade(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * 根据用户角色获取用户的菜单
     *
     * @param roleId
     * @return
     */
    public List<MenuDict> findMenuListByRole(String roleId) {
        return null;

    }

    @Transactional
    public MenuDict addMenuDict(MenuDict menuDict) {

        return super.merge(menuDict);
    }

    /**
     * 根据传入的ID删除相应的菜单
     *
     * @param id
     * @return
     */
    @Transactional
    public MenuDict deleteMenuDictById(String id) {
        String hql = "from MenuDict as md  where md.id='" + id + "'";
        Query query = entityManager.createQuery(hql);
        MenuDict menuDict = (MenuDict) query.getSingleResult();

        if (menuDict != null) {
            super.remove(menuDict);
        }
        return menuDict;
    }

    /**
     * 根据登录的用户名和选择的系统模块
     * 进行菜单的过滤
     *
     * @param moduleId
     * @param loginName
     * @return
     */
    public List<MenuDict> findByLoginNameAndModuleId(String moduleId, String loginName) {
        String sql = "\n" +
                "select distinct e.*\n" +
                "  from jims.staff_dict    a,\n" +
                "       jims.staff_vs_role b,\n" +
                "       modul_dict         c,\n" +
                "       module_vs_menu     d,\n" +
                "       menu_dict          e,\n" +
                "       role_vs_menu       f\n" +
                " where login_name = upper('" + loginName + "')\n" +
                "   and a.id = b.staff_id\n" +
                "   and c.id = d.module_id\n" +
                "   and d.menu_id = e.id\n" +
                "   and f.menu_id = d.menu_id\n" +
                "   and f.menu_id = e.id\n" +
                "   and f.role_id = b.role_id\n" +
                "   and d.module_id = '" + moduleId + "'" +
                " order by e.parent_id,position asc ";
        List<MenuDict> nativeQuery = createNativeQuery(sql, new ArrayList<Object>(), MenuDict.class);
        return nativeQuery;
    }

    /**
     * 根据父ID,POSITION排序查询
     *
     * @return
     */
    public List<MenuDict> findAllByPosition() {
        String hql = "from MenuDict as dict order by dict.parentId,dict.position";
        Query query = entityManager.createQuery(hql);
        List resultList = query.getResultList();
        return resultList;
    }
}
