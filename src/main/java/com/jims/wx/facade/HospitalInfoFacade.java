package com.jims.wx.facade;

import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.HospitalInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Dt on 2016/3/4.
 */
public class HospitalInfoFacade extends BaseFacade {
    private final Logger LOGGER = LoggerFactory.getLogger(HospitalInfoFacade.class);

    private EntityManager entityManager ;

    @Inject
    public HospitalInfoFacade(EntityManager entityManager){
        this.entityManager = entityManager ;
    }


    /**
     * 添加
     * @param hospitalInfo
     * @return
     */
    @Transactional
    public HospitalInfo addHospitalInfo(HospitalInfo hospitalInfo){
       return  super.merge(hospitalInfo) ;
    }
    @Transactional
    public HospitalInfo updateHospitalInfo(HospitalInfo hospitalInfo){
       return  super.merge(hospitalInfo) ;
    }


    /**
     * 根据ID删除
     * @param id
     * @return
     */
    @Transactional
    public void deleteHospitalInfo(String id) {
        List<String>  ids = new ArrayList<>() ;
        ids.add(id) ;
        super.removeByStringIds(HospitalInfo.class,ids);
    }
}
