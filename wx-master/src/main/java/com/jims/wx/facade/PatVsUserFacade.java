package com.jims.wx.facade;

import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.AppUser;
import com.jims.wx.entity.PatInfo;
import com.jims.wx.entity.PatVsUser;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by chenxy on 2016/3/16.
 */
public class PatVsUserFacade extends BaseFacade {
    private EntityManager entityManager;

    @Inject
    public PatVsUserFacade(EntityManager entityManager){
        this.entityManager=entityManager;
    }

    @Transactional
    public PatInfo save(PatInfo patInfo) {
         return entityManager.merge(patInfo);
    }

    /**
     * 查询是否绑定
     * @param openId
     * @return
     */
    public Boolean findIsBangker(String openId) {
        List<PatVsUser> patVsUsers=null;
        patVsUsers=entityManager.createQuery("from PatVsUser where appUser.openId='"+openId+"'").getResultList();
        if(patVsUsers.isEmpty()){
            return false;
        }else{
            return true;
         }
    }

//    /**
//     * 根据patInfoId查询身份证号
//     * @param patInfoId
//     * @return
//     */
////    PatInfo
//    public String findIdCardById(String patInfoId) {
//         String sql="select p.idCard from PatInfo as p where p.id='"+patInfoId+"'";
//         return (String)entityManager.createQuery(sql).getSingleResult();
//      }

    /**
     *
     * @param appuserId
     * @return
     */
    public String findPatIdById(String appuserId) {
        String sql="select p.patInfo from PatVsUser as p where p.appUser.id='"+appuserId+"'";
        PatInfo patinfo=(PatInfo)entityManager.createQuery(sql).getSingleResult();
        if(patinfo!=null){
            return patinfo.getIdCard();
        }else{
        return "";}
    }
}
