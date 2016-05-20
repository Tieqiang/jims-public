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
            /**
             * 判断是否已经收藏
             *
             */
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

    /**
     *
     * @param openId
     * @param doctId
     * @return
     */
    public List<UserCollection> judge(String openId, String doctId) {
        String sql="from UserCollection where openId='"+openId+"' and doctId='"+doctId+"'";
        List<UserCollection> list=entityManager.createQuery(sql).getResultList();
        if(!list.isEmpty()){
            return list;
        }
        return null;
    }

    /**
     * 取消收藏
     * @param docId
     * @param openId
     * @return
     */
    @Transactional
    public int badCollect(String docId, String openId) {
        String sql="delete from user_collection where OPENID='"+openId+"' and doc_id='"+docId+"'";
        int count=entityManager.createNativeQuery(sql).executeUpdate();
        return count;
    }

    /**
     * find by id
     * @param collectionId
     * @return
     */
    public UserCollection findById(String collectionId) {
        String sql="from UserCollection where id='"+collectionId+"'";
        Object obj=entityManager.createQuery(sql).getSingleResult();
        if(obj!=null&&!"".equals(obj)){
            UserCollection userCollection=(UserCollection)obj;
            return userCollection;
        }
        return null;
     }

    /**
     *
     * @param dodId
     * @return
     */
    public boolean findISCollection(String dodId,String openId) {

        String sql="from UserCollection where doctId='"+dodId+"' and openId='"+openId+"'";
        List<UserCollection> userCollections=entityManager.createQuery(sql).getResultList();
        if(userCollections!=null&&!userCollections.isEmpty()){
            return true;
        }
        return false;
    }
}
