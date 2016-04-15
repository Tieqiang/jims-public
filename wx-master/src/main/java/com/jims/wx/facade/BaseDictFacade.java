package com.jims.wx.facade;

import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.BaseDict;
import com.jims.wx.util.PinYin2Abbreviation;
import com.jims.wx.vo.BeanChangeVo;


import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by heren on 2015/10/9.
 */
public class BaseDictFacade extends BaseFacade {

    private EntityManager entityManager ;

    @Inject
    public BaseDictFacade(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * 根据字典名称查询相应的字典
     * @param baseType
     * @return
     */
    public List<BaseDict> findByBaseType(String baseType,int length) {
        if(baseType==null || "".equals(baseType.trim())){
            return findAll(BaseDict.class) ;
        }else{
            String hql = "from BaseDict as bd where upper(bd.baseType) like upper('%"+baseType.trim()+"%')" ;
            if(length > 0){
                hql+=" and length(bd.baseCode)="+length;
            }
            List resultList = this.entityManager.createQuery(hql).getResultList();
            return resultList ;
        }
    }

    /**
     * 根据传递过来的BaseDict增加、删除、删除相应的选项
     * @param baseDictBeanChangeVo
     */
    @Transactional
    public void mergeBaseDict(BeanChangeVo<BaseDict> baseDictBeanChangeVo) {
        List<BaseDict> inserted = baseDictBeanChangeVo.getInserted();
        List<BaseDict> updated = baseDictBeanChangeVo.getUpdated();
        List<BaseDict> deleted = baseDictBeanChangeVo.getDeleted();
        for (BaseDict dict :inserted){
            dict.setInputCode(PinYin2Abbreviation.cn2py(dict.getBaseName()));
            this.merge(dict) ;
        }

        for(BaseDict dict:updated){
            dict.setInputCode(PinYin2Abbreviation.cn2py(dict.getBaseName()));
            this.merge(dict) ;

        }

        List<String> ids = new ArrayList<>() ;

        for(BaseDict dict:deleted){
            ids.add(dict.getId()) ;
        }
        this.removeByStringIds(BaseDict.class,ids);
    }
}
