package com.jims.wx.facade;

import com.google.inject.Inject;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.AppUser;
import com.jims.wx.vo.ClinicMasterVo;
import com.jims.wx.vo.OutpBillItemsVo;
import com.jims.wx.vo.OutpRcptMasterVo;
import com.jims.wx.vo.PatVsUserVo;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zhu on 2016/4/22.
 */
public class RcptMasterFacade extends BaseFacade {
    private EntityManager entityManager;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private AppUserFacade appUserFacade;

    @Inject
    public RcptMasterFacade(EntityManager entityManager, AppUserFacade appUserFacade) {
        this.entityManager = entityManager;
        this.appUserFacade = appUserFacade;

    }

    //根据patId和就诊日期查询clinic-master
    public List<ClinicMasterVo> getByPatId(String patId) {
        Date date = new Date();
        DateFormat ff = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        String sql = "select * from wx.clinic_master_view";
        sql += " where visit_Date >=to_date(" + "'" + ff.format(c.getTime()) + "','yyyy-mm-dd')";
//        System.out.print(sql);
        if (null != patId && !patId.trim().equals("")) {
            sql += " and patient_id='" + patId + "'";
        }
        List<ClinicMasterVo> clinicMasterVos = new ArrayList<>();
        Query qu = entityManager.createNativeQuery(sql);
        List<Objects[]> resultList = qu.getResultList();
        for (Object[] objects : resultList) {
            ClinicMasterVo clinicMasterVo = new ClinicMasterVo(objects[0].toString(), objects[6].toString(), objects[5].toString(), objects[2].toString(), objects[1].toString());
            String thisTime = clinicMasterVo.getVisitDate();
            thisTime = thisTime.substring(0, 10);
            clinicMasterVo.setVisitDate(thisTime);
            clinicMasterVos.add(clinicMasterVo);
        }
        return clinicMasterVos;
    }

    //根据patientId查询门诊收据记录outp_rcpt_master
    public List<OutpRcptMasterVo> getByPatientId(String patientId, String date, String visitNo) {
        if (null != patientId && !patientId.trim().equals("")) {

            String sql = "select distinct o.RCPT_NO, o.TOTAL_COSTS, o.TOTAL_CHARGES, v.NAME" +
                    " from wx.OUTP_BILL_ITEMS b, wx.outp_rcpt_master o, wx.clinic_master_view v" +
                    " where o.patient_id = '" + patientId + "'" +
                    " and v.patient_id = o.patient_id" +
                    " and to_char(v.visit_date, 'YYYY-MM-DD') =" +
                    " to_char(o.visit_Date, 'YYYY-MM-DD')" +
                    "   and  to_char(b.visit_date, 'YYYY-MM-DD') =" +
                    " to_char(o.visit_Date, 'YYYY-MM-DD')" +
                    " and  to_char(o.visit_Date, 'YYYY-MM-DD') like '" + date + "'and b.visit_no = v.visit_no and b.rcpt_no = o.rcpt_no and  v.VISIT_NO='" + visitNo + "'";
//            System.out.println("sql------------"+sql);
            List<OutpRcptMasterVo> outpRcptMasterVos = new ArrayList<>();
            Query qu = entityManager.createNativeQuery(sql);
            List<Objects[]> resultList = qu.getResultList();
            for (Object[] objects : resultList) {
                OutpRcptMasterVo outpRcptMasterVo = new OutpRcptMasterVo(objects[0].toString(), Double.parseDouble(objects[1].toString()), Double.parseDouble(objects[2].toString()), objects[3].toString());
                outpRcptMasterVos.add(outpRcptMasterVo);
            }
            return outpRcptMasterVos;
        } else {
            return null;
        }
    }

    public List<OutpBillItemsVo> getByRcptNo(String rcptNo) {
        if (null != rcptNo && !rcptNo.trim().equals("")) {
            String sql = "select ITEM_NAME,AMOUNT,UNITS,COSTS,CHARGES from wx.OUTP_BILL_ITEMS";
            sql += " where RCPT_NO='" + rcptNo + "'";
            System.out.print(sql);
            List<OutpBillItemsVo> outpBillItemsVos = new ArrayList<>();
            Query qu = entityManager.createNativeQuery(sql);
            List<Objects[]> resultList = qu.getResultList();
            for (Object[] objects : resultList) {
                OutpBillItemsVo outpBillItemsVo = new OutpBillItemsVo(objects[0].toString(), Double.parseDouble(objects[1].toString()), objects[2].toString(), Double.parseDouble(objects[3].toString()), Double.parseDouble(objects[4].toString()));
                outpBillItemsVos.add(outpBillItemsVo);
            }
            return outpBillItemsVos;
        } else {
            return null;
        }
    }

    public List<PatVsUserVo> getByAppUser(String openId) {
        List<PatVsUserVo> patVsUserVos = new ArrayList<>();
        List<AppUser> appUserList = appUserFacade.findByOpenId(openId);
        String appUser = "";
        if (appUserList.size() > 0) {
            appUser = appUserList.get(0).getId();
        }
        if (appUser != "") {
            String id = appUser;
            String sql = "select a.user_id,b.patient_id,b.name from pat_vs_user a ,pat_info b where a.Pat_id=b.id and USER_ID='" + id + "' and b.flag='0'";
            Query qu = entityManager.createNativeQuery(sql);
            List<Objects[]> resultList = qu.getResultList();
            for (Object[] objects : resultList) {
                PatVsUserVo patVsUserVo = new PatVsUserVo(objects[0] == null ? null : objects[0].toString(), objects[2] == null ? null : objects[2].toString(), objects[1] == null ? null : objects[1].toString());
                patVsUserVos.add(patVsUserVo);
            }
            return patVsUserVos;
        } else {
            return null;
        }
    }
}
