package com.jims.wx.facade;

import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.HospitalDict;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by heren on 2015/9/8.
 */
public class HospitalDictFacade extends BaseFacade {
    private final Logger LOGGER = LoggerFactory.getLogger(HospitalDictFacade.class);

    private EntityManager entityManager;

    @Inject
    public HospitalDictFacade(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    /**
     * 添加
     *
     * @param hospitalDict
     * @return
     */
    @Transactional
    public HospitalDict addHospitalDict(HospitalDict hospitalDict) {
        return super.merge(hospitalDict);
    }

    @Transactional
    public HospitalDict updateHospitalDict(HospitalDict hospitalDict) {
        return super.merge(hospitalDict);
    }


    /**
     * 根据ID删除
     *
     * @param id
     * @return
     */
    @Transactional
    public void deleteHospitalDict(String id) {
        List<String> ids = new ArrayList<>();
        ids.add(id);
        super.removeByStringIds(HospitalDict.class, ids);
    }

    /**
     * 根据ID获取医院的信息
     *
     * @param hospitalId
     * @return
     */
    public HospitalDict findHospitalDictById(String hospitalId) {
        return super.get(HospitalDict.class, hospitalId);
    }
}
