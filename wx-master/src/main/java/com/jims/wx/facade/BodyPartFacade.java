package com.jims.wx.facade;

import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.AppUser;
import com.jims.wx.entity.PatInfo;
import com.jims.wx.vo.PatInfoVo;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenxy on 2016/3/16.
 */
public class BodyPartFacade extends BaseFacade {
    private EntityManager entityManager;


    @Inject
    public BodyPartFacade(EntityManager entityManager) {
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

        Object bodyPartName = entityManager.createQuery(sql).getSingleResult();

        if (bodyPartName != null && !"".equals(bodyPartName))
            return (String) bodyPartName;
        return "";
    }
}
