package com.jims.wx.facade;

import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.HospitalInfo;
import com.jims.wx.vo.HospitalInfoDTO;
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

    /**
     *
     * @return
     */
    public List<HospitalInfoDTO> findHospitalDTO() {
            List<HospitalInfoDTO> listDTO=new ArrayList<HospitalInfoDTO>();
            List<HospitalInfo> list=findAll(HospitalInfo.class);
            if (list!=null&&list.size()>0){
                 for(int i=0;i<list.size();i++){
                     HospitalInfoDTO hospitalInfoDTO=new HospitalInfoDTO();
                     hospitalInfoDTO.setId(list.get(i).getHospitalId());
                     hospitalInfoDTO.setText(list.get(i).getHospitalId());
                     listDTO.add(hospitalInfoDTO);
                 }
                return listDTO;
            }else{
                return null;
            }

    }
}
