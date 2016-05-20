package com.jims.wx.facade;

import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.ClinicSickness;
import com.jims.wx.entity.ClinicSymptom;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by chenxy on 2016/3/16.
 */
public class ClinicSickessFacade extends BaseFacade {
    private EntityManager entityManager;


    @Inject
    public ClinicSickessFacade(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * 根据ids 主键的集合来查询疾病
     *
     * @param list
     * @return
     */
    public List<ClinicSickness> findByIds(List<String> list) {
        String ids = "";

        for (String id : list) {
            ids = ids + "'" + id + "'" + ",";
        }
        if (ids != null && !"".equals(ids)) {
            ids = ids.substring(0, ids.length() - 1);//
        }
        String sql = " from ClinicSickness where id in  (" + ids + ")";
        List<ClinicSickness> clinicSicknesses = entityManager.createQuery(sql).getResultList();
        if (clinicSicknesses != null && !clinicSicknesses.isEmpty())
            return clinicSicknesses;
        return null;
    }

    /**
     * 保存疾病
     *
     * @param clinicSickness
     */
    @Transactional
    public void save(ClinicSickness clinicSickness) {
        entityManager.merge(clinicSickness);
    }

    /**
     * @param clinicSymptomClass
     * @param list
     */
    @Transactional
    public void removeByIds(Class<ClinicSickness> clinicSymptomClass, List<String> list) {
        super.removeByStringIds(clinicSymptomClass, list);
    }

    /**
     * find Name by id
     *
     * @param sicknessId
     * @return
     */
    public String findNameById(String sicknessId) {
        ClinicSickness name = (ClinicSickness) entityManager.createQuery("from ClinicSickness as a where a.id='" + sicknessId + "'").getSingleResult();
        if (name != null && !"".equals(name))
            return name.getName();
        return "";
    }
}
