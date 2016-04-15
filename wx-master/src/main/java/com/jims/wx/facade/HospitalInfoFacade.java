package com.jims.wx.facade;

import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.HospitalDict;
import com.jims.wx.entity.HospitalInfo;
import com.jims.wx.entity.WxOpenAccountConfig;
import com.jims.wx.vo.AppSetVo;
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

    @Transactional
    public HospitalInfo save (HospitalInfo hospitalInfo){
        hospitalInfo=super.merge(hospitalInfo);
        return hospitalInfo;
    }

    @Transactional
    public void addSet(AppSetVo appSetVo) {

        HospitalDict hospitalDict = new HospitalDict() ;
        HospitalInfo hospitalInfo = new HospitalInfo() ;
        WxOpenAccountConfig wxOpenAccountConfig = new WxOpenAccountConfig() ;
        //1,删除所有的医院信息
        String hql = "delete from HospitalDict " ;
        String delHospitalInfo = "delete from HospitalInfo" ;
        String delWx = "delete from WxOpenAccountConfig" ;
        entityManager.createQuery(hql).executeUpdate() ;
        entityManager.createQuery(delHospitalInfo).executeUpdate() ;
        entityManager.createQuery(delWx).executeUpdate() ;
        //2,组织三张表信息
        hospitalDict.setHospitalName(appSetVo.getHospitalName());
        HospitalDict mHospitalDict = merge(hospitalDict) ;

        wxOpenAccountConfig.setHospitalId(mHospitalDict.getId());
        wxOpenAccountConfig.setAppId(appSetVo.getAppId());
        wxOpenAccountConfig.setAppSecret(appSetVo.getAppSecret());
        wxOpenAccountConfig.setOpenName(appSetVo.getOpenName());
        wxOpenAccountConfig.setTooken(appSetVo.getAppToken());

        WxOpenAccountConfig mWxOpenAccountConfig = merge(wxOpenAccountConfig) ;
        hospitalInfo.setHospitalId(mHospitalDict.getId());
        hospitalInfo.setInfoUrl(appSetVo.getInfoUrl());
        hospitalInfo.setAppId(mWxOpenAccountConfig.getAppId());
        merge(hospitalInfo) ;
    }

    public AppSetVo findAppSetVo() {

        AppSetVo setVo = new AppSetVo() ;

        String hql = "from HospitalDict" ;
        String infoHql = "from HospitalInfo" ;
        String wxHql = "from WxOpenAccountConfig" ;

        List<HospitalDict> hospitalDictList = createQuery(HospitalDict.class,hql,new ArrayList<Object>()).getResultList() ;

        List<HospitalInfo> hospitalInfos = createQuery(HospitalInfo.class,infoHql,new ArrayList<Object>()).getResultList() ;
        List<WxOpenAccountConfig> wxOpenAccountConfigs = createQuery(WxOpenAccountConfig.class,wxHql,new ArrayList<Object>()).getResultList() ;

        if(hospitalDictList.size()>0){
            setVo.setHospitalName(hospitalDictList.get(0).getHospitalName());
        }
        if(wxOpenAccountConfigs.size()>0){
            WxOpenAccountConfig config = wxOpenAccountConfigs.get(0) ;
            setVo.setOpenName(config.getOpenName());
            setVo.setAppSecret(config.getAppSecret());
            setVo.setAppId(config.getAppId());
            setVo.setAppToken(config.getTooken());
        }
        if(hospitalInfos.size()>0){
            setVo.setInfoUrl(hospitalInfos.get(0).getInfoUrl());
        }

        return setVo ;

    }
}
