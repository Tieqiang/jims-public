package com.jims.wx.facade;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.ClinicMaster;
import com.jims.wx.entity.ClinicMasterHis;
import com.jims.wx.vo.ClinicMasterVo;

import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wangjing on 2016/3/21.
 */
public class ClinicMasterHisFacade extends BaseFacade {
    private EntityManager entityManager;

    @Inject
    public ClinicMasterHisFacade(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    /**
     * 保存单个对象
     *
     * @param clinicMaster
     * @return
     */
    @Transactional
    public ClinicMasterHis saveRecord(ClinicMasterHis clinicMaster) {
        return entityManager.merge(clinicMaster);
    }

    public Integer getMaxVisitNo(Date date) {
        SimpleDateFormat  s=new SimpleDateFormat("yyyy/MM/dd");
        String hql ="select max(i.visitNo) from ClinicMasterHis" +
                " as i where i.visitDate like '%"+s.format(date)+"%' ";
        Integer result=createQuery(Integer.class, hql, new ArrayList<Object>()).getSingleResult();
        if(result!=null)
            return result;
            return 0 ;
    }
}
