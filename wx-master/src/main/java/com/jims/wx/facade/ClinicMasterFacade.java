package com.jims.wx.facade;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.ClinicMaster;
import com.jims.wx.vo.ClinicMasterVo;

import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wangjing on 2016/3/21.
 */
public class ClinicMasterFacade extends BaseFacade {
    private EntityManager entityManager;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private ClinicForRegistFacade clinicForRegistFacade;

    private ClinicIndexFacade clinicIndexFacade;

    private PatInfoFacade patInfoFacade;

    @Inject
    public ClinicMasterFacade(EntityManager entityManager, ClinicForRegistFacade clinicForRegistFacade, ClinicIndexFacade clinicIndexFacade, PatInfoFacade patInfoFacade) {
        this.entityManager = entityManager;
        this.clinicForRegistFacade = clinicForRegistFacade;
        this.clinicIndexFacade = clinicIndexFacade;
        this.patInfoFacade = patInfoFacade;
    }

    /**
     * 保存对象内容
     *
     * @param saveData
     * @return
     */
    public List<ClinicMaster> save(List<ClinicMaster> saveData) {
        List<ClinicMaster> newUpdateDict = new ArrayList<>();
        if (saveData.size() > 0) {
            for (ClinicMaster obj : saveData) {
                ClinicMaster merge = merge(obj);
                newUpdateDict.add(merge);
            }
        }
        return newUpdateDict;
    }

    /**
     * 修改对象内容
     *
     * @param updateData
     * @return
     */
    @Transactional
    public List<ClinicMaster> update(List<ClinicMaster> updateData) {

        List<ClinicMaster> newUpdateDict = new ArrayList<>();
        if (updateData.size() > 0) {
            for (ClinicMaster obj : updateData) {
                ClinicMaster merge = merge(obj);
                newUpdateDict.add(merge);

            }
        }
        return newUpdateDict;
    }

    /**
     * 删除对象
     *
     * @param deleteData
     * @return
     */
    @Transactional
    public List<ClinicMaster> delete(List<ClinicMaster> deleteData) {

        List<ClinicMaster> newUpdateDict = new ArrayList<>();
        if (deleteData.size() > 0) {
            List<String> ids = new ArrayList<>();
            for (ClinicMaster obj : deleteData) {
                ids.add(obj.getId());
            }
            super.removeByStringIds(ClinicMaster.class, ids);
            newUpdateDict.addAll(deleteData);
        }
        return newUpdateDict;
    }

    /**
     * 保存单个对象
     *
     * @param clinicMaster
     * @return
     */
    @Transactional
    public ClinicMaster saveRecord(ClinicMaster clinicMaster) {
        return entityManager.merge(clinicMaster);
    }

    /**
     * 修改取号状态
     *
     * @param clinicMasterId
     */
    public ClinicMaster updateTakeRegistStatus(String clinicMasterId) {
        String sql = "from ClinicMaster where takeStatus='1' and  id='" + clinicMasterId + "'";
        ClinicMaster clinicMaster = (ClinicMaster) entityManager.createQuery(sql).getSingleResult();
        if (clinicMaster != null && !"".equals(clinicMaster)) {
            clinicMaster.setTakeStatus("1");
            clinicMaster = saveRecord(clinicMaster);
            return clinicMaster;
        } else {
            return null;
        }
    }

    /**
     * 查找我的挂号记录
     *
     * @param patientIds
     * @return
     */
    public Map<String, Object> findMyRegist(List<String> patientIds) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<ClinicMasterVo> today = new ArrayList<ClinicMasterVo>();
        List<ClinicMasterVo> history = new ArrayList<ClinicMasterVo>();
        String patientIdStr = "";
        for (String str : patientIds) {
            patientIdStr += "'" + str + "'" + ",";
        }
        if (patientIdStr == null || patientIdStr == "") {
            return null;
        }
        patientIdStr = patientIdStr.substring(0, patientIdStr.length() - 1);
        String todayHql = "from ClinicMaster where patientId in (" + patientIdStr + ") and to_char(registDate,'YYYY-MM-DD') like '%" + sdf.format(new Date()) + "%'";
        List<ClinicMaster> clinicMasters = entityManager.createQuery(todayHql).getResultList();
        for (ClinicMaster clinicMaster : clinicMasters) {
            ClinicMasterVo c = new ClinicMasterVo();
            c.setClinicLabel(clinicIndexFacade.findById(clinicForRegistFacade.findById(clinicMaster.getClincRegistId()).getClinicIndex().getId()).getClinicLabel());
            c.setName(patInfoFacade.findByPaientId(clinicMaster.getPatientId()));
            c.setRegistDate(sdf.format(clinicMaster.getRegistDate()));
            today.add(c);
        }
        map.put("today", today);

        String historyHql = "from ClinicMaster where patientId in (" + patientIdStr + ") and to_char(registDate,'YYYY-MM-DD') < '" + sdf.format(new Date()) + "'";

        List<ClinicMaster> clinicMasters2 = entityManager.createQuery(historyHql).getResultList();

        for (ClinicMaster clinicMaster : clinicMasters2) {
            ClinicMasterVo c = new ClinicMasterVo();
            c.setClinicLabel(clinicIndexFacade.findById(clinicForRegistFacade.findById(clinicMaster.getClincRegistId()).getClinicIndex().getId()).getClinicLabel());
            c.setName(patInfoFacade.findByPaientId(clinicMaster.getPatientId()));
            c.setRegistDate(sdf.format(clinicMaster.getRegistDate()));
            history.add(c);
        }
        map.put("history", history);
        return map;
    }
}
