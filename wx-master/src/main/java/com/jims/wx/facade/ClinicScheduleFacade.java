package com.jims.wx.facade;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.ClinicIndex;
import com.jims.wx.entity.ClinicSchedule;
import com.jims.wx.entity.ClinicTypeSetting;
import com.jims.wx.vo.BeanChangeVo;
import com.jims.wx.vo.ClinicTypeIndexVo;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by wangjing on 2016/3/21.
 */
public class ClinicScheduleFacade extends BaseFacade {
    @Inject
    public ClinicScheduleFacade(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    //find by typeId
    public List<ClinicSchedule> findByTypeId(String id) {
        String sqls = "from ClinicSchedule where clinicIndexId=" + "'" + id + "'";
        return entityManager.createQuery(sqls).getResultList();
    }

    /**
     * @param clinicIndexId 号别ID
     * @return List<String[]>
     * @description / 通过所选择的号别  查询出诊安排
     * @author created by chenxiaoyang
     */
//    ClinicSchedule
    public List<Object[]> queryDayAndTime(String clinicIndexId) {
        String hql = "select c.dayOfWeek,c.timeOfDay,c.registrationLimits from ClinicSchedule as c where c.clinicIndexId='" + clinicIndexId + "'";
        List<Object[]> list = entityManager.createQuery(hql).getResultList();
        return list;
    }

    /**
     * 保存增删改
     *
     * @param beanChangeVo
     */
    @Transactional
    public List<ClinicSchedule> save(BeanChangeVo<ClinicSchedule> beanChangeVo) {
        List<ClinicSchedule> newUpdateDict = new ArrayList<>();
        List<ClinicSchedule> inserted = beanChangeVo.getInserted();
        List<ClinicSchedule> updated = beanChangeVo.getUpdated();
        List<ClinicSchedule> deleted = beanChangeVo.getDeleted();
        for (ClinicSchedule dict : inserted) {
            ClinicSchedule merge = merge(dict);
            newUpdateDict.add(merge);
        }

        for (ClinicSchedule dict : updated) {
            ClinicSchedule merge = merge(dict);
            newUpdateDict.add(merge);
        }

        List<String> ids = new ArrayList<>();

        for (ClinicSchedule dict : deleted) {
            ids.add(dict.getId());
            newUpdateDict.add(dict);
        }
        this.removeByStringIds(ClinicSchedule.class, ids);
        return newUpdateDict;
    }


    public List<ClinicTypeIndexVo> listTree() {
        List<ClinicTypeIndexVo> result = new ArrayList<ClinicTypeIndexVo>();

        String hql = "from ClinicTypeSetting as type where 1=1";
//        if (null != hospitalId && !hospitalId.trim().equals("")) {
//            hql += " and type.hospitalId='" + hospitalId + "'";
//        }
        List<ClinicTypeSetting> settings = entityManager.createQuery(hql).getResultList();
        if (null != settings && settings.size() > 0) {
            Iterator settingIte = settings.iterator();

            while (settingIte.hasNext()) {
                ClinicTypeSetting type = (ClinicTypeSetting) settingIte.next();

                ClinicTypeIndexVo vo = new ClinicTypeIndexVo();
                vo.setId(type.getId());
                vo.setAppId(type.getAppId());
                vo.setClinicName(type.getClinicType());
                vo.setHospitalId(type.getHospitalId());
                vo.setParentFlag("Y");

                hql = "from ClinicIndex as index where 1=1";
                if (null != type && !type.getId().trim().equals("")) {
                    hql += " and index.clinicTypeId='" + type.getId() + "'";
                }
                List<ClinicIndex> indexes = entityManager.createQuery(hql).getResultList();
                if (null != indexes && indexes.size() > 0) {
                    List<ClinicTypeIndexVo> indexVos = new ArrayList<ClinicTypeIndexVo>();
                    for (ClinicIndex index : indexes) {
                        ClinicTypeIndexVo indexVo = new ClinicTypeIndexVo();
                        indexVo.setId(index.getId());
                        indexVo.setClinicName(index.getClinicLabel());
                        indexVo.setParentFlag("N");
                        indexVo.setClinicDept(index.getClinicDept());

                        indexVos.add(indexVo);
                    }

                    vo.setClinicIndex(indexVos);
                }
                result.add(vo);
            }
        }
        return result;
    }

    /**
     * @param clinicIndexId 号别ID
     * @return List<String[]>
     * @description / 通过所选择的号别  查询出诊安排
     * @author created by chenxiaoyang
     */
//    from ClinicTypeSetting as type where 1=1 and type.hospitalId='4028862d4fcf2590014fcf9aef480016'
////    ClinicSchedule
//    public List<Object[]> queryDayAndTime(String clinicIndexId) {
//        String hql = "select c.dayOfWeek,c.timeOfDay,c.registrationLimits from ClinicSchedule as c where c.clinicIndexId='" + clinicIndexId + "'";
//        List<Object[]> list = entityManager.createQuery(hql).getResultList();
//        return list;
//    }

    /**
     * @param id
     * @return
     */
//    ClinicSchedule
    public Double findLimitsByClinicIndexId(String id) {
        List<Double> list = (List<Double>) entityManager.createQuery("select c.registrationLimits from ClinicSchedule as c where c.clinicIndexId='" + id + "'").getResultList();
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 根据号别的id 查询出诊时间
     *
     * @param clinicIndexId
     * @return private String dayOfWeek;
     * private String timeOfDay;
     */
    public String findTime(String clinicIndexId) {
        String time = "";
        String sql = "from ClinicSchedule where clinicIndexId='" + clinicIndexId + "'";
        List<ClinicSchedule> list = entityManager.createQuery(sql).getResultList();
        for (ClinicSchedule clinicSchedule : list) {
            time += clinicSchedule.getDayOfWeek() + clinicSchedule.getTimeOfDay() + ";";
        }
        if (time != null && !"".equals(time)) {
            time = time.substring(0, time.length() - 1);
        }
        return time;
    }
}
