package com.jims.wx.facade;

import com.jims.wx.BaseFacade;

import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * Created by chenxy on 2016/3/16.
 */
public class FeedBackTargetFacade extends BaseFacade {
    private EntityManager entityManager;


    @Inject
    public FeedBackTargetFacade(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * 根据id查询身体的name
     *
     * @param bodyPartId
     * @return
     */
    public String findNameById(String bodyPartId) {

        String sql = "select b.name from BodyPart as b where  b.id='" + bodyPartId + "'";

        String bodyPartName = (String) entityManager.createQuery(sql).getSingleResult();

        if (bodyPartName != null && !"".equals(bodyPartName))
            return bodyPartName;
        return "";
    }
}
