package com.jims.wx.facade;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.StaffDict;
import com.jims.wx.entity.StaffVsRole;
import com.jims.wx.util.PinYin2Abbreviation;
import com.jims.wx.util.reps.EnscriptAndDenScript;


import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by heren on 2015/9/22.
 */
public class StaffDictFacade extends BaseFacade {

    private EntityManager entityManager ;

    @Inject
    public StaffDictFacade(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * 名称模糊查询
     *
     * @param name
     * @return
     */
    public List<StaffDict> findAll(String name) {
        String hql = " from StaffDict as dict where 1=1";
        if (name != null && name.trim().length() > 0) {
            hql += " and dict.name like '%" + name.trim() + "%'";
        }
        Query query = entityManager.createQuery(hql);
        List resultList = query.getResultList();
        return resultList;
    }

    @Transactional
    public StaffDict saveStaffDict(StaffDict staffDict) {
        staffDict.setInputCode(PinYin2Abbreviation.cn2py(staffDict.getName()));
        StaffDict merge = super.merge(staffDict);
        String id = staffDict.getId() ;
        if(id !=null){
            String hql = "From StaffVsRole as sv where sv.staffDict.id='"+id+"'" ;
            Query query = entityManager.createQuery(hql);
            List<String> ids=new ArrayList<>();
            List resultList = query.getResultList();
            for(Object sv:resultList){
                StaffVsRole role = (StaffVsRole)sv ;
                ids.add(role.getId());
            }
            removeByStringIds(StaffVsRole.class,ids);
        }
        return merge ;
    }

    /**
     * 删除人员的同时删除与该人员相关的角色对照
     * @param id
     * @return
     */
    @Transactional
    public StaffDict deleteById(String id) {
        StaffDict staffDict = get(StaffDict.class,id) ;
        String hql = "From StaffVsRole as sv where sv.staffDict.id='"+id+"'" ;
        Query query = entityManager.createQuery(hql);
        List<String> ids=new ArrayList<>();
        List resultList = query.getResultList();
        for(Object sv:resultList){
            StaffVsRole role = (StaffVsRole)sv ;
            ids.add(role.getId());
        }
        removeByStringIds(StaffVsRole.class,ids);
        remove(staffDict);
        return staffDict ;
    }

    /**
     * 查询某一个医院的员工信息
     * @param hospitalId
     * @return
     */
    public List<StaffDict> findByHospital(String hospitalId, String inputCode) {
        String hql = "from StaffDict as sf where sf.hospitalId='"+hospitalId+"'" ;
        if (null != inputCode && !inputCode.trim().equals("")) {
            hql += "  and upper(sf.inputCode) like upper('" + inputCode + "%')";
        }
        return super.createQuery(StaffDict.class,hql,new ArrayList()).getResultList();
    }

    public StaffDict findByLogin(String loginName, String password, String hospitalId) {
        String hql = "from StaffDict as sf where sf.hospitalId='" + hospitalId + "' and sf.loginName='"+loginName+"' and sf.password='"+password+"'";
        List result = super.createQuery(StaffDict.class, hql, new ArrayList()).getResultList();
        if(result.size()==0){
            return null;
        }else{
            return (StaffDict)result.get(0);
        }

    }

    /**
     * 查询staff对应的role
     * @param staffId
     * @return
     */
    public List<StaffVsRole> listRolesByStaff(String staffId) {
        String hql = "From StaffVsRole as sv where sv.staffDict.id='" + staffId + "'";
        Query query = entityManager.createQuery(hql);
        List resultList = query.getResultList();
        return resultList;
    }

    public StaffDict findByLogin(String userName, String password) {
        String hql = "from StaffDict as sf where sf.loginName='"+userName+"' and sf.password='"+password+"'";
        List result = super.createQuery(StaffDict.class, hql, new ArrayList()).getResultList();
        if(result.size()==0){
            return null;
        }else{
            return (StaffDict)result.get(0);
        }
    }

    public StaffDict findByLoginName(String loginName) {

        String hql ="from StaffDict as sf where sf.loginName = upper('"+loginName+"')" ;
        List result = super.createQuery(StaffDict.class, hql, new ArrayList()).getResultList();
        if(result.size()>0){
            return (StaffDict)result.get(0);
        }else{
            return null;
        }
    }
    public StaffDict findByLoginId(String hospitalId,String loginId) {
        String hql ="from StaffDict as sf where sf.id = '"+ loginId+"' and sf.hospitalId = '"+hospitalId+"'" ;
        return (StaffDict)super.createQuery(StaffDict.class, hql, new ArrayList()).getResultList().get(0);
    }

    /**
     * 修改登录用户的密码
     * @param hospitalId
     * @param loginId
     * @param password
     * @return
     */
    @Transactional
    public StaffDict editPasswordByLoginId(String hospitalId, String loginId, String password) {
        StaffDict staffDict=findByLoginId(hospitalId, loginId);
        EnscriptAndDenScript eads = new EnscriptAndDenScript();
        staffDict.setPassword(eads.enScript(password));
        merge(staffDict);
        return staffDict;
    }

    /**
     * 修改相关人员的核算单元
     * @param staffDicts
     * @return
     */
    @Transactional
    public List<StaffDict> megerStaffWidthAcct(List<StaffDict> staffDicts) {
        for(StaffDict staff:staffDicts){
            if(staff.getId()==null || "".equals(staff.getId())){
                continue;
            }else{
                StaffDict staffDict = get(StaffDict.class,staff.getId()) ;
                staffDict.setAcctDeptId(staff.getAcctDeptId());
                merge(staffDict) ;
            }
        }
        return staffDicts ;
    }
}
