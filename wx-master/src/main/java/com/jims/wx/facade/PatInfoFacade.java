package com.jims.wx.facade;

import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.AnswerResult;
import com.jims.wx.entity.AppUser;
import com.jims.wx.entity.PatInfo;
import com.jims.wx.vo.PatInfoVo;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenxy on 2016/3/16.
 */
public class PatInfoFacade extends BaseFacade {
    private EntityManager entityManager;
    private AppUserFacade appUserFacade;
//    private PatVsUserFacade

    @Inject
    public PatInfoFacade(EntityManager entityManager,AppUserFacade appUserFacade){
        this.appUserFacade=appUserFacade;
        this.entityManager=entityManager;
    }

    @Transactional
    public PatInfo save(PatInfo patInfo) {
         return entityManager.merge(patInfo);
    }

    /**
     * 根据主键查询患者的idCard
     * @param patId
     * @return
     */
    public String findIdCard(String patId) {
//        PatInfo patInfo;
        String sql="select p.idCard from PatInfo as p where p.id='"+patId+"'";
         return (String)entityManager.createQuery(sql).getSingleResult();
    }

    /**
     * 根据Id查询patInfo
     * @param patId
     * @return
     */
    public PatInfo findById(String patId) {
        String sql="from PatInfo where id='"+patId+"'";
        return (PatInfo)entityManager.createQuery(sql).getSingleResult();
    }


    /**
     *
     * @param list
     */
    @Transactional
    public void delete(List<PatInfo> list) {
        for(PatInfo patInfo:list){
            remove(patInfo);
        }
     }

    public List<PatInfoVo> findByOpenId(String openId){
        AppUser appuser=appUserFacade.findAppUserByOpenId(openId);
//        appuser.getId();
        String sql="select c.id,c.cellphone,c.name,c.sex from  pat_vs_user b ,pat_info c  where  b.pat_id=c.id and  b.user_id='"+appuser.getId()+"'";
//        String sql="select * from pat_vs_user a ,pat_info b where a.Pat_id=b.id and USER_ID='"+appuser.getId()+"'";
        System.out.print(sql);
        List<PatInfoVo> patInfos =new ArrayList<>();
        Query qu = entityManager.createNativeQuery(sql);
        List<Object[]> resultList = qu.getResultList();
        for(Object[] objects:resultList){
            objects=objects;
            PatInfoVo patInfoVo = new PatInfoVo();
            patInfoVo.setId(objects[0]==null?null:objects[0].toString());
            patInfoVo.setCellphone(objects[1]==null?null:objects[1].toString());
            patInfoVo.setName(objects[2]==null?null:objects[2].toString());
            patInfoVo.setSex(objects[3]==null?null:objects[3].toString());
            patInfos.add(patInfoVo);
        }

        return  patInfos;
    }

    /**
     * 删除
     * @param patInfo
     * @return
     */
    @Transactional
    public void deleteByObject(PatInfo patInfo) {
          entityManager.remove(patInfo);
    }

    /**
     *
     * @param idCard
     * @return
     */
    public PatInfo findByIdCard(String idCard) {
        List<PatInfo> list=entityManager.createQuery("from PatInfo where idCard='"+idCard+"'").getResultList();
        if(!list.isEmpty())
            return list.get(0);
            return null;
    }
}
