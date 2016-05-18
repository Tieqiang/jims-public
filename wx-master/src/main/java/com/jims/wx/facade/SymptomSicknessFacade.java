package com.jims.wx.facade;

import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.SymptomVsSickness;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by chenxy on 2016/3/16.
 */
public class SymptomSicknessFacade extends BaseFacade {
    private EntityManager entityManager;


    @Inject
    public SymptomSicknessFacade(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     *
     * @param ids
     * @return
     */
    public List<String> findSicknessBySymptom(String ids,String sexValue) {
             String idsStr="";
            String[] arr=ids.split(",");
            for(String str:arr){
                idsStr+="'"+str+"'"+",";
            }
            if(idsStr!=null&&!"".equals(idsStr)){
                idsStr=idsStr.substring(0,idsStr.length()-1);//
            }
        if("1".equals(sexValue)){
            sexValue="女";
        }else if("0".equals(sexValue)){
            sexValue="男";
        }
           String sql="select sicknessId from SymptomVsSickness where symptomId in   ("+idsStr+") and (sex='"+sexValue+"' or sex='-1')";
           List<String> list=entityManager.createQuery(sql).getResultList();
           if(!list.isEmpty())
              return list;
              return null;
    }

    /**
     * save
     * @param symptomVsSickness
     */
    @Transactional
    public void save(SymptomVsSickness symptomVsSickness) {
            merge(symptomVsSickness);
    }
}
