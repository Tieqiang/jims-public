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
    public PatInfoFacade(EntityManager entityManager, AppUserFacade appUserFacade) {
        this.appUserFacade = appUserFacade;
        this.entityManager = entityManager;
    }

    @Transactional
    public PatInfo save(PatInfo patInfo) {
        patInfo.setFlag("0");
        return entityManager.merge(patInfo);
    }

    /**
     * 根据主键查询患者的idCard
     *
     * @param patId
     * @return
     */
    public String findIdCard(String patId) {
        String sql = "select p.idCard from PatInfo as p where p.id='" + patId + "'";
        return (String) entityManager.createQuery(sql).getSingleResult();
    }

    /**
     * 根据Id查询patInfo
     *
     * @param patId
     * @return
     */
    public PatInfo findById(String patId) {
        String sql = "from PatInfo where id='" + patId + "' and flag='0'";
        Object result = entityManager.createQuery(sql).getSingleResult();
        if (result != null && !"".equals(result))
            return (PatInfo) result;
        return null;
    }

    /**
     * @param list
     */
    @Transactional
    public void delete(List<PatInfo> list) {
        for (PatInfo patInfo : list) {
            patInfo.setFlag("1");
            merge(patInfo);
        }
    }

    public List<PatInfoVo> findByOpenId(String openId) {
        AppUser appuser = appUserFacade.findAppUserByOpenId(openId);
        String sql = "select c.id,c.cellphone,c.name,c.sex from  pat_vs_user b ,pat_info c  where  b.pat_id=c.id and  b.user_id='" + appuser.getId() + "' and c.flag='0'";
        List<PatInfoVo> patInfos = new ArrayList<>();
        Query qu = entityManager.createNativeQuery(sql);
        List<Object[]> resultList = qu.getResultList();
        if (resultList != null && !resultList.isEmpty()) {
            for (Object[] objects : resultList) {
                objects = objects;
                PatInfoVo patInfoVo = new PatInfoVo();
                patInfoVo.setId(objects[0] == null ? null : objects[0].toString());
                patInfoVo.setCellphone(objects[1] == null ? null : objects[1].toString());
                patInfoVo.setName(objects[2] == null ? null : objects[2].toString());
                patInfoVo.setSex(objects[3] == null ? null : objects[3].toString());
                patInfos.add(patInfoVo);
            }
        }

        return patInfos;
    }

    /**
     * 删除
     *
     * @param patInfo
     * @return
     */
    @Transactional
    public void deleteByObject(PatInfo patInfo) {
        patInfo.setFlag("1");
        entityManager.merge(patInfo);
    }

    /**
     * @param idCard
     * @return
     */
    public PatInfo findByIdCard(String idCard) {
        List<PatInfo> list = entityManager.createQuery("from PatInfo where idCard='" + idCard + "' and flag='0'").getResultList();
        if (list != null && !list.isEmpty())
            return list.get(0);
        return null;
    }

    /**
     * @param patientId
     * @return
     */
    public String findByPaientId(String patientId) {

        String sql = "select name from PatInfo where patientId='" + patientId + "' and flag='0'";
        String name = (String) entityManager.createQuery(sql).getSingleResult();
        if (name != null && !"".equals(name))
            return name;
        return "";
    }

    /**
     * @param idCard
     * @return
     */
    public PatInfo findByFlag(String idCard) {
        String sql = "from PatInfo where flag='1' and idCard='" + idCard + "'";
        List<PatInfo> list = entityManager.createQuery(sql).getResultList();
        if (list != null && !list.isEmpty())
            return list.get(0);
        return null;
    }
}
