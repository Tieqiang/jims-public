package com.jims.wx.facade;

import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.UserCollection;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenxy on 2016/3/16.
 */
public class UserCollectionFacade extends BaseFacade {
    private EntityManager entityManager;


    @Inject
    public UserCollectionFacade(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Transactional
    public Map<String, Object> saveData(String docId, String openId, String clinicIndexId) {
        Map<String,Object> map=new HashMap<String,Object>();
        try {
            UserCollection userCollection=new UserCollection();
            userCollection.setOpenId(openId);
            userCollection.setClinicIndexId(clinicIndexId);
            userCollection.setDoctId(docId);
            userCollection=merge(userCollection);
            map.put("success",true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success",true);
        }
        return map;
    }

    /**
     * 我的收藏
     * @return
     */
    public List<UserCollection> findByOpenId(String openId) {
        List<UserCollection> userCollections= entityManager.createQuery("from UserCollection where openId='"+openId+"'").getResultList();
        if(!userCollections.isEmpty())
            return userCollections;
            return null;
    }
}
