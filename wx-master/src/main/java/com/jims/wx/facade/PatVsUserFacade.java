package com.jims.wx.facade;

import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.AppUser;
import com.jims.wx.entity.PatInfo;
import com.jims.wx.entity.PatVsUser;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenxy on 2016/3/16.
 */
public class PatVsUserFacade extends BaseFacade {
    private EntityManager entityManager;
    private AppUserFacade appUserFacade ;

    @Inject
    public PatVsUserFacade(EntityManager entityManager,AppUserFacade appUserFacade){
        this.entityManager=entityManager;
        this.appUserFacade=appUserFacade;
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

    /**
     *
     * @param appuserId
     * @return
     */
    public String findPatIdById(String appuserId) {
        String sql="select p.patInfo from PatVsUser as p where p.appUser.id='"+appuserId+"'";
        List<PatInfo> patinfo=entityManager.createQuery(sql).getResultList();
        if(!patinfo.isEmpty()){
            return patinfo.get(0).getIdCard();
        }else{
        return "";}
    }


    /**
     * 根据openId查询次微信用户是否绑定了患者
     * @param openid
     * @return
     */
    public boolean findIsExistsPatInfo(String openid) {
        AppUser appUser= appUserFacade.getAppUserByOpenId(openid);
        if(appUser!=null){
            String patId=appUser.getPatId();
            if(patId!=null&&!"".equals(patId)){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
    /**
     * 查询次患者否绑定
     * @param openId
     * @param idCard
     * @return
     */
    public Boolean findIsBangker(String idCard,String openId) {
        // find appUserId by openId
        String appUserId=null;
        List<AppUser> appUsers=appUserFacade.findByOpenId(openId);
        if(!appUsers.isEmpty()){
            appUserId=appUsers.get(0).getId();
        }
        List<PatInfo> list=findPatInfosByAppUserId(openId);
        if(!list.isEmpty()){//绑定了患者
            String var="";
            for(PatInfo patInfo:list){
                if(idCard.equals(patInfo.getIdCard())){
                    var=patInfo.getIdCard();
                    break;
                }
            }
            if(var!=null&&!"".equals(var)){//次患者已经绑定
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    /**
     * @description 通过aappUserId 查询所绑定患者的集合
     * @param appUserId
     * @return patInfos
     */
    public List<PatInfo> findPatInfosByAppUserId(String appUserId){
        List<PatInfo> patInfos=new ArrayList<PatInfo>();
        String sql="select p.patInfo from PatVsUser as p where p.appUser.id='"+appUserId+"' and p.patInfo.flag='0'";
        return entityManager.createQuery(sql).getResultList();
    }

    /**
     * 根据appUser的Id 删除
     * @param id
     */
    @Transactional
    public void deleteByAppUserId(String id) {
         String sql="delete from PatVsUser as p where p.appUser.id='"+id+"'";
         entityManager.createQuery(sql).executeUpdate();
     }
    @Transactional
     public void deleteByPatId(String patId) {
        String sql="delete from PatVsUser as p where p.patInfo.id='"+patId+"'";
        entityManager.createQuery(sql).executeUpdate();
    }
}
