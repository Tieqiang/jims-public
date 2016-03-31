package com.jims.wx.facade;

import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.HospitalInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.persistence.EntityManager;


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

    @Transactional
    public HospitalInfo save (HospitalInfo hospitalInfo){
        hospitalInfo=super.merge(hospitalInfo);
        return hospitalInfo;
    }

}
