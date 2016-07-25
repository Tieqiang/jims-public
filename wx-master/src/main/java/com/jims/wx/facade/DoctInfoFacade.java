package com.jims.wx.facade;

import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.DoctInfo;
import org.apache.commons.lang.StringUtils;


import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by chenxiaoyang on 2016/3/25.
 */
public class DoctInfoFacade extends BaseFacade {

    /**
     * 保存 修改
     *
     * @param doctInfo
     * @return
     */
    @Transactional
    public DoctInfo save(DoctInfo doctInfo) {
        return super.merge(doctInfo);
    }

    /**
     * 根据 医生姓名和所在编号模糊查询
     * @param clazz
     * @param name
     * @return
     */
    public List<DoctInfo> queryByCondition(Class<DoctInfo> clazz, String name) {
        String sql = "from DoctInfo where  1=1";
        if (StringUtils.isNotBlank(name)) {
            sql += " and name like '%" + name + "%'";
        }
        TypedQuery query = entityManager.createQuery(sql, clazz);
        List<DoctInfo> list = query.getResultList();
        return list;
    }

    /**
     * 根据 ids 批量或单条删除
     *
     * @param doctInfoClass
     * @param list
     */
    @Transactional
    public void removeByIds(Class<DoctInfo> doctInfoClass, List<String> list) {
        super.removeByStringIds(DoctInfo.class, list);
    }

    /**
     * 根据id 查询医生信息
     *
     * @param doctId
     * @return
     */
    public DoctInfo findById(String doctId) {
        if(doctId==null || "".equals(doctId)){
            return null;
        }
        return (DoctInfo) entityManager.createQuery("from DoctInfo where id='" + doctId + "'").getSingleResult();
    }
 }
